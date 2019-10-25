package com.image.tag.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class FileUtil {

	/**
	 * 删除多个文件
	 * @param urls
	 */
	public static void deleteFile(String... urls) {
		for (String url : urls) {
			File file = new File(url);
			if (file.exists() && file.isFile()) {
				file.delete();
			} 
		}
	}
	
	public static String fileMd5Code(File file) {
		String str = null;
		try {
			str = DigestUtils.md5Hex(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 删除文件夹
	 * @param path
	 */
	public static void deleteDir(String pathStr) {
		File path = new File(pathStr);
	    if (!path.exists())  
	        return;  
	    if (path.isFile()) {  
	        path.delete();  
	        return;  
	    }  
	    File[] files = path.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	    	deleteDir(files[i].getPath());  
	    }  
	    path.delete();  
	}
	
	/** 
     * 执行压缩操作 
     * @param zipFile 压缩的文件
     * @param srcPathName 需要被压缩的文件/文件夹 
     * @return 返回zip包的大小
     */  
    public static long zip(File zipFile, String srcPathName) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");    
        }   
            
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹 eg:zip.setIncludes("*.java");    
        //fileSet.setExcludes(...); //排除哪些文件或文件夹    
        zip.addFileset(fileSet);    
        zip.execute();    
        return zipFile.length();
    }
    
    /**
	 * 解压文件
	 * @param path 解压目录 
	 * @param file 解压文件
	 */
	public static void unZip(String path, File file) {
		int count = -1;
		int buffer = 2048;

		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		// 创建保存目录
		File unZipDir = new File(path);
		if (!unZipDir.exists()) {
			unZipDir.mkdirs();
		}
		
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file, "gbk"); // 解决中文乱码问题
			Enumeration<?> entries = zipFile.getEntries();

			while (entries.hasMoreElements()) {
				byte buf[] = new byte[buffer];

				ZipEntry entry = (ZipEntry) entries.nextElement();

				String filename = entry.getName();
				boolean ismkdir = false;
				if (filename.lastIndexOf("/") != -1) { // 检查此文件是否带有文件夹
					ismkdir = true;
				}
				filename = path + filename;

				if (entry.isDirectory()) { // 如果是文件夹先创建
					file = new File(filename);
					file.mkdirs();
					continue;
				}
				file = new File(filename);
				if (!file.exists()) { // 如果是目录先创建
					if (ismkdir) {
						new File(filename.substring(0, filename.lastIndexOf("/"))).mkdirs(); // 目录先创建
					}
				}
				file.createNewFile(); // 创建文件

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				bos.flush();
				bos.close();
				fos.close();

				is.close();
			}

			zipFile.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        if(!file.exists())
        	return null;
        String str = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
            	str += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
    
  //得到所有已上传的图片
    public static List<String> getDirAllFile(String path){
		File dir = new File(path);
		File[] listFiles = dir.listFiles();
		List<String> list = new ArrayList<String>();
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				list.add(listFiles[i].getName());
			}
		}
		return list;
	}
    
    /** 
     * 将文件转换成byte[] 
     *  
     * @param filePath 
     * @return 
     */  
    public static byte[] getBytes(String filePath) {  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  
	
    /**
     * 将当前文件夹下的所有文件移动到目标文件夹
     * @param sourceDir 当前文件夹
     * @param targetDir 目标文件夹
     */
    public static void moveDirAllFile(String sourceDir, String targetDir) {
    	File dir = new File(sourceDir);
    	if (dir.exists()) {
    		File[] listFiles = dir.listFiles();
    		if (listFiles != null) {
    			File target = new File(targetDir);
    			if (!target.exists()) {
    				target.mkdirs();
				}
    			for (File file : listFiles) {
    				file.renameTo(new File(targetDir + file.getName()));
				}
    		}
		} else {
			System.out.println("文件夹："+ sourceDir +" 不存在！");
		}
    }
    
   /**文件重命名 
    * @param path 文件目录 
    * @param oldname  原来的文件名 
    * @param newname 新文件名 
    */ 
    public static String renameFile(String path,String oldname, String newname) { 
        if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名 
            File oldfile=new File(path+"/"+oldname); 
            File newfile=new File(path+"/"+newname); 
            if(newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名 
            	System.out.println(newname+"已经存在！");
            	return oldname;
            } else{ 
                oldfile.renameTo(newfile);
                return newname;
            } 
        } else {
            System.out.println("新文件名和旧文件名相同...");
            return newname;
        }
    }
    
    /**
     * 合并多个文件，首尾相连
     * @param dirPath 输入、输出文件夹路径
     * @param outFileName 输出文件名
     * @param fileName 输入文件数组
     * @return 是否合并成功
     * @throws FileNotFoundException 
     */
    public static boolean mergeFile(String dirPath, String outFileName, String... fileName) {
    	if (fileName == null || fileName.length < 2) {
			System.err.println("请输入至少两个文件");
			return false;
		}
    	BufferedOutputStream bufferedOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
    	try {
	    	bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(dirPath + outFileName)));
			for (String string : fileName) {
				bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(dirPath + string + ".mp3")));
				byte[] byt = new byte[1024];
				int len;
				while ((len=bufferedInputStream.read(byt)) != -1){
					bufferedOutputStream.write(byt, 0, len);
					bufferedOutputStream.flush();
				}
				bufferedInputStream.close();
			}
			bufferedOutputStream.close();
		} catch (IOException e) {
			System.err.println("++++++++++合并文件出错， 错误原因："+ e.getMessage());
			try {
				bufferedInputStream.close();
			} catch (IOException e1) {
				System.err.println("bufferedInputStream close IOException:" + e.getMessage());
			}
			try {
				bufferedOutputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.err.println("bufferedOutputStream close IOException:" + e.getMessage());
			}
			FileUtil.deleteFile(dirPath + outFileName);  //删除生成的文件
		} 
    	return true;
    }
    
}
