package com.teach.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.w3c.dom.Document;


/**
 * word 转换成html 2017-2-27
 */
public class WordToHtml {
	
	public static Map<String, Object> wordToHtml(String path){
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isEmpty(path)) {
			map.put("success", false);
			map.put("message", "文件路径为空");
		}
		if (StringUtils.containsIgnoreCase(path, ".docx")) {
			return word2007ToHtml(path);
		} else {
			return word2003ToHtml(path);
		}
	}

	private static Map<String, Object> word2003ToHtml(String path){
		Map<String, Object> map = new HashMap<String, Object>();
		File wordFile = new File(path);
		File htmlFile = new File(wordFile.getParentFile(), wordFile.getName() + "_html.html");
		try{
			// 原word文档
			InputStream input = new FileInputStream(wordFile);
	
			HWPFDocument wordDocument = new HWPFDocument(input);
//			List<Picture> list = wordDocument.getPicturesTable().getAllPictures();
//			if (list != null && list.size() > 0) {
//				map.put("success", false);
//				map.put("message", "文档中含有图片，转换出错!");
//				return map;
//			}
			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
			// 解析word文档
			wordToHtmlConverter.processDocument(wordDocument);
			Document htmlDocument = wordToHtmlConverter.getDocument();
	
			// 生成html文件地址
			OutputStream outStream = new FileOutputStream(htmlFile);
	
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(outStream);
	
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer serializer = factory.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
	
			serializer.transform(domSource, streamResult);
			outStream.close();
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "转换出错: "+ e.getMessage());
			return map;
		}
		map.put("success", true);
		map.put("path", htmlFile.getPath());;
		return map;
	}

	private static Map<String, Object> word2007ToHtml(final String wordPath) {
		Map<String, Object> map = new HashMap<String, Object>();
		File wordFile = new File(wordPath);
		File htmlFile = new File(wordFile.getParentFile(), wordFile.getName() + "_html.html");
		try {
			final InputStream inputStream = new FileInputStream(wordFile);// 输入流
			final XWPFDocument document = new XWPFDocument(inputStream);// 读取word文档
			inputStream.close();// 关闭输入流
			final XHTMLOptions options = XHTMLOptions.create();// 创建选项
//			List<XWPFPictureData> list = document.getAllPictures();
//			if (list != null && list.size() > 0) {
//				map.put("success", false);
//				map.put("message", "文档中含有图片，转换出错!");
//				return map;
//			}
			final OutputStream outputStream = new FileOutputStream(htmlFile);// 输出流
			XHTMLConverter.getInstance().convert(document, outputStream, options);// word文档转html
			System.out.println("html:" + htmlFile.getAbsolutePath());
			outputStream.close();// 关闭输出流
			// document.close();// 关闭文档
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "转换出错: "+ e.getMessage());
			return map;
		}
		map.put("success", true);
		map.put("path", htmlFile.getPath());
		return map;
	}

	public static void main(String[] args) {
		String str = "C:\\Users\\skyedu_beyond\\Desktop\\2018年春季1年级尖子班Chapter4教案.doc";
		Map<String, Object> map = wordToHtml(str);
		System.out.println(map);
	}
	
}