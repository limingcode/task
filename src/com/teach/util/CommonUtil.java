package com.teach.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class CommonUtil {

	/**
	 * list 去除重复的值
	 * @param list
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
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
	
	public static List<String> getFileNameByRegularExpression(String path, String regularExpression){
		File dir = new File(path);
		FileFilter filter = new RegexFileFilter(regularExpression);
		File[] listFiles = dir.listFiles(filter);
		List<String> list = new ArrayList<String>();
		if (listFiles != null) {
			for (int i = 0; i < listFiles.length; i++) {
				String fileName = listFiles[i].getName();
				list.add(fileName);
			}
			Collections.sort(list,new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					int number_1 = Integer.parseInt(StringUtils.substring(o1, 4, o1.lastIndexOf(".")));
					int number_2 = Integer.parseInt(StringUtils.substring(o2, 4, o2.lastIndexOf(".")));
					return number_1-number_2;
				}
			});
		}
		return list;
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
	
	public static void main(String[] args) {
	}
}
