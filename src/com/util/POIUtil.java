package com.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * @author leno
 * @version v1.0
 * @param <T>
 *   应用泛型，代表任意一个符合javabean风格的类
 *   注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *   byte[]表jpg格式的图片数据
 */
public class POIUtil<T> {
	
	public void exportExcel(Collection<T> dataset, OutputStream out) {
        exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
    }
	
	public void exportExcel(String titleName,String[] headers, Collection<T> dataset,
            OutputStream out) {
        exportExcel(titleName, headers, dataset, out, "yyyy-MM-dd");
    }
	
	public void exportExcel(String[] headers, Collection<T> dataset,
            OutputStream out, String pattern) {
        exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
    }
	
	/**
	 * map数据导出（无特别样式）
	 * @param title 表格title
	 * @param list 数据
	 * @param per head   head@key(head：表head key：取值key)
	 */
	public static InputStream exportExcel(String title,Collection<Map<String, Object>> list, String[] per){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();  

		//创建5个样式  无 红 黄 蓝 绿
	    List<HSSFCellStyle> styles = new ArrayList<HSSFCellStyle>();
	    for(int si = 0 ; si<5 ; si++){
	    	HSSFCellStyle style = workbook.createCellStyle();
	    	style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			switch (si) {
			case 0:
				style.setFillForegroundColor(HSSFColor.WHITE.index);
				break;
			case 1:
				style.setFillForegroundColor(HSSFColor.RED.index);
				break;
			case 2:
				style.setFillForegroundColor(HSSFColor.YELLOW.index);
				break;
			case 3:
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				break;
			case 4:
				style.setFillForegroundColor(HSSFColor.LIME.index);
				break;
			}
	    	styles.add(style);
	    }
		List<String> keys = new ArrayList<String>();
	    HSSFSheet sheet = workbook.createSheet("sheet1");
	    //设置title
	    HSSFRow row0 = sheet.createRow(0);
	    HSSFCell cell0 = row0.createCell(0);
//	    row0.setHeight((short) 99);
//	    HSSFFont font = cell0.getCellStyle().getFont(workbook);
//	    font.setFontHeightInPoints((short) 18);// 字号  
//	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗  
//	    cell0.getCellStyle().setFont(font);
	    cell0.setCellValue(new HSSFRichTextString(title));
	    //设置title end
	    HSSFRow row = sheet.createRow(1); 
	    HSSFCell cell;
	    for(int i=0;i<per.length;i++){
	    	cell = row.createCell(i); 
	    	String[] strs = per[i].split("@@");
	    	String head = "";
	    	if(strs.length>0)
	    		head = strs[0];
	    	if(strs.length>1)
	    		keys.add(strs[1]);
    	    cell.setCellValue(new HSSFRichTextString(head)); 
	    }
	    //设置头end
	    int i = 2;
	    int n = 0;
	    for(Map<String, Object> map : list){
	    	row = sheet.createRow(i);
			HSSFCellStyle style = styles.get(0);
			Float val = 0f;
			try {
				val = Float.valueOf(map.get("mbl")+"");
			} catch (Exception e) {}
			if(val>=1){
				style = styles.get(1);
				System.out.println("--红");
			}else if(val>=0.8){
				style = styles.get(2);
				System.out.println("--黄");
			}else if(val>=0.6){
				style = styles.get(3);
				System.out.println("--蓝");
			}else if(val >=0.3){
				style = styles.get(4);
				System.out.println("--绿");
			}
	    	for(String key : keys){
	    		cell = row.createCell(n); 
	    		//style.setFillForegroundColor(HSSFColor.WHITE.index);
	    		if("mbl".equals(key)){
	    			cell.setCellValue(new DecimalFormat("#.0").format(val>1?100:val*100)+"%"); 
	    		}else{
	    			Object _val = map.get(key);
	    			cell.setCellValue(new HSSFRichTextString(_val==null?"":_val+"")); 
	    		}
    			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    		cell.setCellStyle(style);
	    		n++;
	    	}
	    	i++;
	    	n=0;
	    }
	    try {
			workbook.write(out);
			out.flush();
			byte[] buf= out.toByteArray();
			return new ByteArrayInputStream(buf,0,buf.length);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    return null;
	}
	
	/**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
     */
	
	 @SuppressWarnings({ "unchecked", "deprecation" })
	 public OutputStream exportExcel(String titleName, String[] headers,
            Collection<?> dataset, OutputStream out, String pattern) {
		    // 声明一个工作薄
	        HSSFWorkbook workbook = new HSSFWorkbook();
	       
	        // 生成一个表格
	        HSSFSheet sheet = workbook.createSheet("sheet1");
	        // 设置表格默认列宽度为15个字节
	        sheet.setDefaultColumnWidth((short) 15);
	        // 选择列的字体样式
	        HSSFFont font = workbook.createFont();
	        font.setColor(HSSFColor.VIOLET.index);//选择列字体颜色
	        font.setFontHeightInPoints((short) 12);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //字体粗细
	        // 内容列的字体样式
	        HSSFFont font2 = workbook.createFont();
	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cellStyle.setFont(font2);// 把字体应用到当前的样式
	        // 选择列样式
	        HSSFCellStyle style = workbook.createCellStyle();
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setFont(font); //把字体应用到当前的样式
	        // 内容列的样式
	        HSSFCellStyle style2 = workbook.createCellStyle();
	        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	        style2.setFont(font2);// 把字体应用到当前的样式
	        
	        // 声明一个画图的顶级管理器
	        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
	        // 定义注释的大小和位置,详见文档
//	        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,0, 0, 0, (short) 4, 2, (short) 6, 5));
	        // 设置注释内容
//	        comment.setString(new HSSFRichTextString("This is a JASON test data!"));
	        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.图片有效。
//	        comment.setAuthor("JASON");

	        // 产生表格标题行
	        HSSFRow row = sheet.createRow(0);
	        row.setHeightInPoints(17.00f);  //设置格子高度
	        for (short i = 0; i < headers.length; i++) {
	            HSSFCell cell = row.createCell(i);
	            cell.setCellStyle(style);  //设置标题样式
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	            cell.setCellValue(text);
	        }
//	        HSSFFont font3 = workbook.createFont();
//          font3.setColor(HSSFColor.BLUE.index);
	        
	        // 遍历集合数据，产生数据行
	        Iterator<?> it = dataset.iterator();
	        int index = 0;
	        while (it.hasNext()) {
	        	index++;
	            row = sheet.createRow(index);
	            T t = (T) it.next();
	            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
	            Field[] fields = t.getClass().getDeclaredFields();
	            for (int i = 0; i < fields.length; i++) {
	            	row.setHeightInPoints(16.00f);
	            	HSSFCell cell = row.createCell(i);
	                cell.setCellStyle(style2);
	                Field field = fields[i];
	                String fieldName = field.getName();
	                String getMethodName = "get"
	                        + fieldName.substring(0, 1).toUpperCase()
	                        + fieldName.substring(1);
	                try {
	                	 Class<? extends Object> tCls = t.getClass();
	                     Method getMethod = tCls.getMethod(getMethodName,
	                             new Class[] {});
	                     Object value = getMethod.invoke(t, new Object[] {});
	                     // 判断值的类型后进行强制类型转换
	                     String textValue = null;
	                     if(value != null){
		                     if (value instanceof Boolean) {
		                         boolean bValue = (Boolean) value;
		                         textValue = "男";
		                         if (!bValue) {
		                             textValue = "女";
		                         }
		                     } else if (value instanceof Date) {
		                         Date date = (Date) value;
		                         SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		                         textValue = sdf.format(date);
		                     } else if (value instanceof byte[]) {
		                         // 有图片时，设置行高为60px;
		                         row.setHeightInPoints(60);
		                         // 设置图片所在列宽度为80px,注意这里单位的一个换算
		                         sheet.setColumnWidth(i, (short) (35.7 * 80));
		                         // sheet.autoSizeColumn(i);
		                         byte[] bsValue = (byte[]) value;
		                         HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
		                                 1023, 255, (short) 6, index, (short) 6, index);
		                         anchor.setAnchorType(2);
		                         patriarch.createPicture(anchor, workbook.addPicture(
		                                 bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
		                     } else {
		                         // 其它数据类型都当作字符串简单处理
		                         textValue = value.toString();
		                        
		                         
		                     }
	                     }
	                     // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
	                     if (textValue != null) {
	                    	 System.out.println(textValue);
	                         Pattern p = Pattern.compile("^-?(\\d+)|-?(\\d+(.?)\\d+)$");
	                         Matcher matcher = p.matcher(textValue);
	                         if (matcher.matches()) {
	                             // 是数字当作double处理
	                             cell.setCellStyle(cellStyle);
	                             cell.setCellValue(Double.parseDouble(textValue));
	                         } else {
//	                        	 HSSFRichTextString richString = new HSSFRichTextString(
//	                                     textValue);
	                             //richString.applyFont(font3);
	                             cell.setCellValue(textValue);
	                         }
	                     }
					} catch (SecurityException e) {
	                    e.printStackTrace();
	                } catch (NoSuchMethodException e) {
	                    e.printStackTrace();
	                } catch (IllegalArgumentException e) {
	                    e.printStackTrace();
	                } catch (IllegalAccessException e) {
	                    e.printStackTrace();
	                } catch (InvocationTargetException e) {
	                    e.printStackTrace();
	                } finally {
	                    // 清理资源
	                }
	            }
			}
	      try {
	            workbook.write(out);
	            out.flush();
	            //out.close();
	        } catch (IOException e) {
	        	//e.printStackTrace();  //这里如果用户点击取消导出，就会报异常,还未解决。目前先不catch
	        }
	      return out;
	 }
	 
	 public static HttpHeaders httpHeaderExcelFileAttachment(final String fileName,
		        final int fileSize) {
		    String encodedFileName = fileName.replace('"', ' ').replace(' ', '_');

		    HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		    responseHeaders.setContentLength(fileSize);
		    responseHeaders.set("Content-Disposition", "attachment");
		    responseHeaders.add("Content-Disposition", "filename=\"" + encodedFileName + '\"');
		    return responseHeaders;
		}
}
