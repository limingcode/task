//坐标点
function Point(x, y){
	var point = new Object();
	point.x = x;
	point.y = y;
	point.getX = function(){
		return this.x;
	}
	point.getY = function(){
		return this.y;
	}
	return point;
}

//圆
function Circle(point, widthRadius, heightRadius){
	var circle = new Object();
	circle.point = point;
	circle.widthRadius = widthRadius;
	circle.heightRadius = heightRadius;
	circle.getPoint = function(){
		return this.point;
	}
	circle.getWidthRadius = function(){
		return this.widthRadius;
	}
	circle.getHeightRadiust = function(){
		return this.heightRadius;
	}
	return circle;
}

//多边形
function Polygon(points){
	var polygon = new Object();
	polygon.points = points;
	polygon.getPoints = function(){
		return this.points;
	}
	return polygon;
}

//标记点
function PointTag(type, sortNo, shape, audioName, describe,duration){
	var pointTag = new Object();
	pointTag.type = type;
	pointTag.sortNo = sortNo;
	pointTag.shape = shape;
	pointTag.audioName = audioName;
	pointTag.describe = describe;
	pointTag.duration = duration;
    pointTag.getDuration = function(){
        return this.Duration;
    }
	pointTag.getType = function(){
		return this.type;
	}
	pointTag.getSortNo = function(){
		return this.sortNo;
	}
	pointTag.getShape = function(){
		return this.shape;
	}
	pointTag.getAudioName = function(){
		return this.audioName;
	}
	pointTag.getDescribe = function(){
		return this.describe;
	}
	return pointTag;
}

function ImagePage(pageNumber, imageName, rectList){
	var imagePage = new Object();
	imagePage.pageNumber = pageNumber;
	imagePage.imageName = imageName;
	imagePage.rectList = rectList;
	imagePage.getPageNumber = function(){
		return this.pageNumber;
	}
	imagePage.getImageName = function(){
		return this.imageName;
	}
	imagePage.getRectList = function(){
		return this.rectList;
	}
	return imagePage;
}

function Lesson(lessonId, lessonTitle, lessonDesc, lessonPop, pageList){
	var lesson = new Object();
	lesson.lessonId = lessonId;
	lesson.lessonTitle = lessonTitle;
	lesson.lessonDesc = lessonDesc;
	lesson.lessonPop = lessonPop;
	lesson.pageList = pageList;
	lesson.getLessonId = function(){
		return this.lessonId;
	}
	lesson.getLessonTitle = function(){
		return this.lessonTitle;
	}
	lesson.getLessonDesc = function(){
		return this.description;
	}
	lesson.getLessonPop = function(){
		return this.lessonPop;
	}
	lesson.getPageList = function(){
		return this.pageList;
	}
	return lesson;
}

function Book(bookId, type, width, height, lessonList){
	var book = new Object();
	book.bookId = bookId;
	book.type = type;
	book.lessonList = lessonList;
	book.width = width;
	book.height = height;
	book.getBookId = function(){
		return this.bookId;
	}
	book.getType = function(){
		return this.type;
	}
	book.getWidth = function(){
		return this.width;
	}
	book.getHeight = function(){
		return this.height;
	}
	book.getLessonList = function(){
		return this.lessonList;
	}
	return book;
}