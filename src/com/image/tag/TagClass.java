package com.image.tag;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.image.tag.utils.FileUtil;
import com.image.tag.utils.ImageTagUtils;



public class TagClass {
	
	public static String BASE_PATH = "upload" + File.separator + "imageTag" + File.separator + "file" + File.separator + "76" + 
			File.separator + "bookId" + File.separator + "bookName" + File.separator + "temp" + File.separator;
	
	public static String analysisBookHtml(int bookId, String bookName, List<Map<String, Object>> lessonList) {
		String htmlNameOut = null;
		try {
			String basePath = ImageTagUtils.getHomePath() + File.separator +BASE_PATH.replaceFirst("bookId", String.valueOf(bookId))
			.replaceFirst("bookName", bookName);
			String htmlPath = basePath + "html";
			String audioPath = basePath + "sounds" + File.separator;
			String imgPath = basePath + "img" + File.separator;
			
			List<String> htmlFileName = FileUtil.getDirAllFile(htmlPath);
			if (htmlFileName == null || htmlFileName.size() == 0) {
				return null;
			}
			Document indexDoc = Jsoup.parse(new File(basePath + File.separator + "index.html"), "UTF-8");
			Elements lessonLi = indexDoc.getElementsByTag("li");
			for (int i = 0; i < lessonLi.size(); i++) {
				Element element = lessonLi.get(i);
				String attr = element.attr("class");
				String sort = StringUtils.replace(attr, "pic", "");
				String htmlName = StringUtils.substring(element.child(0).attr("href"), 5);
				Map<String, Object> lesson = getLesson(lessonList, "orderNo", sort);
				if (lesson != null) {
					lesson.put("htmlName", htmlName);
				}
			}
			
			Collections.sort(htmlFileName, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					int num1 = Integer.parseInt(StringUtils.substring(o1, 1, StringUtils.indexOf(o1, ".")));
					int num2 = Integer.parseInt(StringUtils.substring(o2, 1, StringUtils.indexOf(o2, ".")));
					return num1 - num2;
				}
			});
			
			float globalWidth = 0, globalHeight = 0;
			
			List<Lesson> tagLessonList = new ArrayList<Lesson>();
			Map<String, Object> currLesson = lessonList.get(0);
			List<ImagePage> pageList = new ArrayList<ImagePage>();
			for (int i = 0; i < htmlFileName.size(); i++) {
				htmlNameOut = htmlFileName.get(i);
				
				if (i != 0) {
					Map<String, Object> tempLesson = getLesson(lessonList, "htmlName", htmlFileName.get(i));
					if (tempLesson != null) { //不为空，说明当页为新课次的起始页
						int lessonId = Integer.parseInt(String.valueOf(currLesson.get("id")));
						String lessonName = String.valueOf(currLesson.get("name"));
						Lesson lesson = new Lesson(lessonId, lessonName, pageList);
						tagLessonList.add(lesson);
						currLesson = tempLesson;
						pageList = new ArrayList<ImagePage>();
					}
				}
				
				Document doc = Jsoup.parse(new File(htmlPath + File.separator + htmlFileName.get(i)), "UTF-8");
			 
			 	Elements imgElem = doc.getElementsByTag("img");
				String src = imgElem.eq(0).attr("src");
				float divWidth = Integer.parseInt(imgElem.eq(0).attr("width"));
				float divHeight = Integer.parseInt(imgElem.eq(0).attr("height"));
				String imageName = StringUtils.substring(src, StringUtils.lastIndexOf(src, "/")+1);
				
				File file = new File(imgPath + imageName);
				
				Image image = ImageIO.read(file);
				float imageWidth = image.getWidth(null);
				float imageHeight = image.getHeight(null);
				if (i==0) { //全局宽度， 高度取第一张图片的宽高;
					globalWidth = imageWidth;
					globalHeight = imageHeight;
				}
					
				int sortNo = 0;
				List<PointTag> rectList = new ArrayList<PointTag>();
				Elements tagElem = doc.getElementsByTag("area");
				for (Element element : tagElem) {
					if (StringUtils.equals("rect", element.attr("shape"))) {  //规则的四边形
						List<Point> pointList = new ArrayList<Point>();
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x1 = Float.parseFloat(points[0]);
						float y1 = Float.parseFloat(points[1]);
						float x2 = Float.parseFloat(points[2]);
						float y2 = Float.parseFloat(points[3]);
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						Shape shape = new Polygon(pointList);
						String audioName = element.attr("_data");
						if (audioName.contains(",")) { //出现两段音频的，合并音频，产生新的音频文件
							String outFileName = "audio_"+ (int)(Math.random()*10) + "_" + System.currentTimeMillis() + ".mp3";
							boolean b = FileUtil.mergeFile(audioPath, outFileName, audioName.split(","));
							if (b) {
								audioName = outFileName;
							}
						} else {
							audioName += ".mp3";
						}
						PointTag pointTag = new PointTag(2, ++sortNo, shape, audioName, "");
						rectList.add(pointTag);
					} else if (StringUtils.equals("circle", element.attr("shape"))) {//圆
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x = Float.parseFloat(points[0]);
						float y = Float.parseFloat(points[1]);
						float radius = Float.parseFloat(points[2]);
						Point point = new Point(x, y);
						Shape shape = new Circle(point, radius, radius);
						String audioName = element.attr("_data");
						if (audioName.contains(",")) { //出现两段音频的，合并音频，产生新的音频文件
							String outFileName = "audio_"+ (int)(Math.random()*10) + "_" + System.currentTimeMillis() + ".mp3";
							boolean b = FileUtil.mergeFile(audioPath, outFileName, audioName.split(","));
							if (b) {
								audioName = outFileName;
							}
						} else {
							audioName += ".mp3";
						}
						PointTag pointTag = new PointTag(1, ++sortNo, shape, audioName, "");
						rectList.add(pointTag);
					} else { //不规则图形
						List<Point> pointList = new ArrayList<Point>();
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x1 = Float.parseFloat(points[0]);
						float y1 = Float.parseFloat(points[1]);
						float x2 = x1, y2 = y1;
						if (points.length >= 4) {
							x2 = Float.parseFloat(points[2]) + 20;
							y2 = Float.parseFloat(points[3]) + 20;
						}
						x2 = x2-x1<20 ? x2+20 : x2;
						y2 = y2-y1<20 ? y2+20 : y2;
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						Shape shape = new Polygon(pointList);
						String audioName = element.attr("_data");
						PointTag pointTag = new PointTag(2, ++sortNo, shape, "", audioName);
						rectList.add(pointTag);
					}
				}
				ImagePage imagePage = new ImagePage(i+1, imageName, rectList);
				pageList.add(imagePage);
			}
			
			int lessonId = Integer.parseInt(String.valueOf(currLesson.get("id")));
			String lessonName = String.valueOf(currLesson.get("name"));
			Lesson lesson = new Lesson(lessonId, lessonName, pageList);
			tagLessonList.add(lesson);
			
			Book book = new Book(bookId, globalWidth > globalHeight ? 1 : 2, globalWidth, globalHeight, tagLessonList);
			return JSONObject.toJSONString(book);
		} catch (Exception e) {
			System.out.println(htmlNameOut);
			e.printStackTrace();
		}
		return null;
	}
	
	private static Map<String, Object> getLesson(List<Map<String, Object>> list, String mapKey, String mapVal) {
		Map<String, Object> returnMap = null;
		for (Map<String, Object> map : list) {
			if (mapVal.equals(String.valueOf(map.get(mapKey)))) {
				returnMap = map;
				break;
			}
		}
		return returnMap;
	}
	
	
	/**
	 * @param type 类型： 1:book, 2:lesson
	 * @param json
	 * @param bookId
	 * @param bookName
	 * @param lessonId
	 * @param lessonName
	 * @return
	 */
	public static String analysisLessonHtml(String json, int bookId, String bookName, int lessonId, String lessonName) {
		String htmlNameOut = null;
		try {
			String period = "76";
			String basePath = ImageTagUtils.getHomePath() + File.separator +BASE_PATH.replaceFirst("bookId", String.valueOf(bookId))
					.replaceFirst("bookName", bookName) + lessonId;
			String formalImageDirPath = ImageTagUtils.getHomePath() + 
					ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 1, true);
			boolean formalImageDirIsExists = (new File(formalImageDirPath)).exists();
			
			String formalAudioDirPath = ImageTagUtils.getHomePath() + 
					ImageTagUtils.getImageTagFileUploadPath(bookId, period, bookName, 2, true);
			boolean formalAudioDirIsExists = (new File(formalAudioDirPath)).exists();
			
			String htmlPath = basePath + File.separator + "html";
			String audioPath = basePath + File.separator + "sounds" + File.separator;
			String imgPath = basePath + File.separator + "img" + File.separator;
			
			List<String> htmlFileName = FileUtil.getDirAllFile(htmlPath);
			Collections.sort(htmlFileName, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					int num1 = Integer.parseInt(StringUtils.substring(o1, 1, StringUtils.indexOf(o1, ".")));
					int num2 = Integer.parseInt(StringUtils.substring(o2, 1, StringUtils.indexOf(o2, ".")));
					return num1 - num2;
				}
			});
			
			if (htmlFileName == null || htmlFileName.size() == 0) {
				return null;
			}
			float globalWidth = 0, globalHeight = 0;
			
			
			List<ImagePage> pageList = new ArrayList<ImagePage>();
			for (int i = 0; i < htmlFileName.size(); i++) {
				htmlNameOut = htmlFileName.get(i);
				
				Document doc = Jsoup.parse(new File(htmlPath + File.separator + htmlFileName.get(i)), "UTF-8");
			 
			 	Elements imgElem = doc.getElementsByTag("img");
				String src = imgElem.eq(0).attr("src");
				float divWidth = Integer.parseInt(imgElem.eq(0).attr("width"));
				float divHeight = Integer.parseInt(imgElem.eq(0).attr("height"));
				String imageName = StringUtils.substring(src, StringUtils.lastIndexOf(src, "/")+1);
				if (formalImageDirIsExists && (new File(formalImageDirPath+imageName).exists())) {
					//正式文件夹存在且正式文件夹存在同名的文件， 重命名文件。
					imageName = FileUtil.renameFile(imgPath, imageName, 
							"image_"+ (i+1) + "_" + System.currentTimeMillis() + StringUtils.substring(imageName, imageName.indexOf(".")));
				}
				File file = new File(imgPath + imageName);
				
				Image image = ImageIO.read(file);
				float imageWidth = image.getWidth(null);
				float imageHeight = image.getHeight(null);
				if (i==0) { //全局宽度， 高度取第一张图片的宽高;
					globalWidth = imageWidth;
					globalHeight = imageHeight;
				}
					
				int sortNo = 0;
				List<PointTag> rectList = new ArrayList<PointTag>();
				Elements tagElem = doc.getElementsByTag("area");
				for (Element element : tagElem) {
					if (StringUtils.equals("rect", element.attr("shape"))) {  //规则的四边形
						List<Point> pointList = new ArrayList<Point>();
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x1 = Float.parseFloat(points[0]);
						float y1 = Float.parseFloat(points[1]);
						float x2 = Float.parseFloat(points[2]);
						float y2 = Float.parseFloat(points[3]);
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						Shape shape = new Polygon(pointList);
						String audioName = element.attr("_data");
						if (audioName.contains(",")) { //出现两段音频的，合并音频，产生新的音频文件
							String outFileName = "audio_"+ (int)(Math.random()*10) + "_" + System.currentTimeMillis() + ".mp3";
							boolean b = FileUtil.mergeFile(audioPath, outFileName, audioName.split(","));
							if (b) {
								audioName = outFileName;
							}
						} else {
							audioName += ".mp3";
							if (formalAudioDirIsExists && (new File(formalAudioDirPath+audioName).exists())) {
								//正式文件夹存在且正式文件夹存在同名的文件， 重命名文件。
								audioName = FileUtil.renameFile(audioPath, audioName, 
										"audio_"+ (int)(Math.random()*10) + "_" + System.currentTimeMillis() + StringUtils.substring(audioName, audioName.indexOf(".")));
							}
						}
						PointTag pointTag = new PointTag(2, ++sortNo, shape, audioName, "");
						rectList.add(pointTag);
					} else if (StringUtils.equals("circle", element.attr("shape"))) {//圆
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x = Float.parseFloat(points[0]);
						float y = Float.parseFloat(points[1]);
						float radius = Float.parseFloat(points[2]);
						Point point = new Point(x, y);
						Shape shape = new Circle(point, radius, radius);
						String audioName = element.attr("_data");
						if (audioName.contains(",")) { //出现两段音频的，合并音频，产生新的音频文件
							String outFileName = "audio_"+ (int)(Math.random()*10) + "_" + System.currentTimeMillis() + ".mp3";
							boolean b = FileUtil.mergeFile(audioPath, outFileName, audioName.split(","));
							if (b) {
								audioName = outFileName;
							}
						} else {
							audioName += ".mp3";
						}
						PointTag pointTag = new PointTag(1, ++sortNo, shape, audioName, "");
						rectList.add(pointTag);
					} else { //不规则图形
						List<Point> pointList = new ArrayList<Point>();
						String[] points = StringUtils.split(element.attr("coords"), ",");
						float x1 = Float.parseFloat(points[0]);
						float y1 = Float.parseFloat(points[1]);
						float x2 = x1, y2 = y1;
						if (points.length >= 4) {
							x2 = Float.parseFloat(points[2]) + 20;
							y2 = Float.parseFloat(points[3]) + 20;
						}
						x2 = x2-x1<20 ? x2+20 : x2;
						y2 = y2-y1<20 ? y2+20 : y2;
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y1*imageHeight/divHeight)/2));
						pointList.add(new Point((x1*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						pointList.add(new Point((x2*imageWidth/divWidth)/2, (y2*imageHeight/divHeight)/2));
						Shape shape = new Polygon(pointList);
						String audioName = element.attr("_data");
						PointTag pointTag = new PointTag(2, ++sortNo, shape, "", audioName);
						rectList.add(pointTag);
					}
				}
				ImagePage imagePage = new ImagePage(i+1, imageName, rectList);
				pageList.add(imagePage);
			}
			Lesson lesson = new Lesson(lessonId, lessonName, pageList);
			
			Book book = null;
			if (StringUtils.isNotEmpty(json)) {
				System.out.println(json);
				JSONObject jsonObject = JSONObject.parseObject(json);
				System.err.println(jsonObject);
				book = JSONObject.toJavaObject(jsonObject, Book.class);
				System.out.println(JSONObject.toJSONString(book));
				List<Lesson> lessonList = book.getLessonList();
				if (lessonList == null) {
					lessonList = new ArrayList<Lesson>();
				}
				lessonList.add(lesson);
				book.setLessonList(lessonList);
			} else {
				List<Lesson> lessonList = new ArrayList<Lesson>();
				lessonList.add(lesson);
				book = new Book(bookId, globalWidth > globalHeight ? 1 : 2, globalWidth, globalHeight, lessonList);
			}
			return JSONObject.toJSONString(book);
		} catch (Exception e) {
			System.out.println(htmlNameOut);
			e.printStackTrace();
		}
		return null;
	}

}

class Point {
	
	private float x;
	
	private float y;
	
	public Point() {
		super();
	}
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}

interface Shape{
	
	public List<Point> getPoints();
	
	public void setPoints(List<Point> points);
	
	public Point getPoint();
	
	public void setPoint(Point point);
	
	public float getWidthRadius();
	
	public void setWidthRadius(float widthRadius); 
	
	public float getHeightRadius();
	
	public void setHeightRadius(float heightRadius);
}

class Polygon implements Shape{
	
	private List<Point> points;
	
	public Polygon() {
		
	}
	
	public Polygon(List<Point> points) {
		this.points = points;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void setPoints(List<Point> points) {
		this.points = points;
	}

	@Override
	public Point getPoint() {
		return null;
	}

	@Override
	public void setPoint(Point point) {
		
	}

	@Override
	public float getWidthRadius() {
		return 0;
	}

	@Override
	public void setWidthRadius(float widthRadius) {
		
	}

	@Override
	public float getHeightRadius() {
		return 0;
	}

	@Override
	public void setHeightRadius(float heightRadius) {
		
	}

}

class Circle implements Shape{
	
	private Point point;
	
	private float widthRadius;
	
	private float heightRadius;
	
	public Circle() {
		super();
	}	
	
	public Circle(Point point, float widthRadius, float heightRadius) {
		super();
		this.point = point;
		this.widthRadius = widthRadius;
		this.heightRadius = heightRadius;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public float getWidthRadius() {
		return widthRadius;
	}

	public void setWidthRadius(float widthRadius) {
		this.widthRadius = widthRadius;
	}

	public float getHeightRadius() {
		return heightRadius;
	}

	public void setHeightRadius(float heightRadius) {
		this.heightRadius = heightRadius;
	}

	@Override
	public List<Point> getPoints() {
		return null;
	}

	@Override
	public void setPoints(List<Point> points) {
		
	}

}

class PointTag{
	
	private int type;
	
	private int sortNo;
	
	private Shape shape;
	
	private String audioName;
	
	private String describe;

	public PointTag() {
		super();
	}

	public PointTag(int type, int sortNo, Shape shape, String audioName, String describe) {
		super();
		this.type = type;
		this.sortNo = sortNo;
		this.shape = shape;
		this.audioName = audioName;
		this.describe = describe;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}

class ImagePage{
	
	private int pageNumber;
	
	private String imageName;
	
	private List<PointTag> rectList;
	
	public ImagePage() {
		super();
	}	

	public ImagePage(int pageNumber, String imageName, List<PointTag> rectList) {
		super();
		this.pageNumber = pageNumber;
		this.imageName = imageName;
		this.rectList = rectList;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public List<PointTag> getRectList() {
		return rectList;
	}

	public void setRectList(List<PointTag> rectList) {
		this.rectList = rectList;
	}

}

class Lesson{
	
	private int lessonId;
	
	private String lessonName;
	
	private List<ImagePage> pageList;
	
	public Lesson() {
		super();
	}

	public Lesson(int lessonId, String lessonName, List<ImagePage> pageList) {
		super();
		this.lessonId = lessonId;
		this.lessonName = lessonName;
		this.pageList = pageList;
	}

	public int getLessonId() {
		return lessonId;
	}

	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public void setPageList(List<ImagePage> pageList) {
		this.pageList = pageList;
	}

	public String getLessonName() {
		return lessonName;
	}

	public List<ImagePage> getPageList() {
		return pageList;
	}
}

class Book{
	
	private int bookId; 
	
	private int type;
	
	private float width;
	
	private float height;
	
	private List<Lesson> lessonList;
	
	public Book() {
		super();
	}
	
	public Book(int bookId, int type, float width, float height, List<Lesson> lessonList) {
		super();
		this.bookId = bookId;
		this.type = type;
		this.width = width;
		this.height = height;
		this.lessonList = lessonList;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getType() {
		return type;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public List<Lesson> getLessonList() {
		return lessonList;
	}

	public void setLessonList(List<Lesson> lessonList) {
		this.lessonList = lessonList;
	}
	
}