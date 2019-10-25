<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();	
%>
<!DOCTYPE html>
<html lang="en" class="ppt">
    <head>
        <meta charset="utf-8">
        <title>PPT编辑器</title>
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/flexible.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/barstyle.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/wordArt.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/reset.pchlin.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/jquery-ui.min.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/animate.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/smartMenu.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/evol-colorpicker.min.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/video-js.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/upload_ppt.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/start.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/index.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/editor.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/screen.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/ppt/shapCss.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/fonts/fontCss.css">
        <link rel="stylesheet" type="text/css" href="<%=path%>/sys/teach/css/common.css">
    </head>
    <style type="text/css">
        .haveFun {
            background: #a995fe;
            width: 0;
            height: 0;
            position: absolute;
            opacity: 0.5;
            cursor: move;
        }
    </style>
    <body>
	    <input type="hidden" id="id" value="${courseware.id}">
	    <input type="hidden" id="lessonId" value="${param.lessonId}">
    <div class="body" style="width: 100%;height: 100%">
    <!--  begin 菜单栏 -->
    <div class="header">
        <div class="menu_box">
            <div class="fileTitle">Skyedu蓝天教育</div>
            <div class="menu_nav">
                <span class="listRight_1 nav active ui-draggable">开始</span>
                <span class="listRight_1 nav ui-draggable">插入</span>
                <span class="listRight nav ui-draggable">动画</span>
                <span class="listRight_1 nav ui-draggable">设计</span>
<!--                 <span class="listRight_1 nav ui-draggable">功能测试</span> -->
                <span class="listRight_1 nav ui-draggable uploadCourseware">上传</span>
            </div>
            <div class="menu_cnt">
                <div class="lookPpt">预览</div>
                <div class="savePpt">保存</div>
                <div class="tabs tabs_1 active">
                    <ul style="width: 550px;float:left;">
                        <li class="font_set">
                            <div class="tabs_cnt">
                                <div class="fontChoose">
                                    <select class="font_style" name="fontstyle">
                                        <option class="hide" style="font-family:宋体">宋体</option>
                                        <option style="font-family:宋体">宋体</option>
                                        <option style="font-family:楷体">楷体</option>
                                        <option style="font-family:黑体">黑体</option>
                                        <option style="font-family:仿宋">仿宋</option>
                                        <option style="font-family:楷体">楷体</option>
                                        <option style="font-family:儷黑">儷黑</option>
                                        <option style="font-family:儷宋">儷宋</option>
                                        <option style="font-family:隶书">隶书</option>
                                        <option style="font-family:幼圆">幼圆</option>
                                        <option style="font-family:新宋体">新宋体</option>
                                        <option style="font-family:標楷體">標楷體</option>
                                        <option style="font-family:細明體">細明體</option>
                                        <option style="font-family:华文黑体">华文黑体</option>
                                        <option style="font-family:华文楷体">华文楷体 </option>
                                        <option style="font-family:华文宋体">华文宋体</option>
                                        <option style="font-family:华文仿宋">华文仿宋</option>
                                        <option style="font-family:华文细黑">华文细黑</option>
                                        <option style="font-family:方正舒体">方正舒体</option>
                                        <option style="font-family:新細明體">新細明體</option>
                                        <option style="font-family:华文细黑">华文细黑</option>
                                        <option style="font-family:华文楷体">华文楷体</option>
                                        <option style="font-family:华文宋体">华文宋体</option>
                                        <option style="font-family:华文中宋">华文中宋</option>
                                        <option style="font-family:华文仿宋">华文仿宋</option>
                                        <option style="font-family:方正舒体">方正舒体</option>
                                        <option style="font-family:方正姚体">方正姚体</option>
                                        <option style="font-family:华文彩云">华文彩云</option>
                                        <option style="font-family:华文琥珀">华文琥珀</option>
                                        <option style="font-family:华文隶书">华文隶书</option>
                                        <option style="font-family:华文行楷">华文行楷</option>
                                        <option style="font-family:华文新魏">华文新魏</option>
                                        <option style="font-family:蘋果儷中黑">蘋果儷中黑</option>
                                        <option style="font-family:蘋果儷細宋">蘋果儷細宋</option>
                                        <option style="font-family:微軟正黑體">微軟正黑體</option>
                                        <option style="font-family:微软雅黑体">微软雅黑体</option>
                                        <option style="font-family:仿宋_GB2312">仿宋_GB2312</option>
                                        <option style="font-family:楷体_GB2312">楷体_GB2312</option>
                                        <option style="font-family:Angsana New">Angsana New</option>
                                        <option style="font-family:Arial">Arial</option>
                                        <option style="font-family:Arial Black">Arial Black</option>
                                        <option style="font-family:Batang">Batang</option>
                                        <option style="font-family:Book Antiqua">Book Antiqua</option>
                                        <option style="font-family:Bookman Old Style">Bookman Old Style</option>
                                        <option style="font-family:Calibri">Calibri</option>
                                        <option style="font-family:Cambria">Cambria</option>
                                        <option style="font-family:Candara">Candara</option>
                                        <option style="font-family:Century">Century</option>
                                        <option style="font-family:Century Gothic">Century Gothic</option>
                                        <option style="font-family:Century Schoolbook">Century Schoolbook</option>
                                        <option style="font-family:Comic Sans MS">Comic Sans MS</option>
                                        <option style="font-family:Consolas">Consolas</option>
                                        <option style="font-family:Constantia">Constantia</option>
                                        <option style="font-family:Corbel">Corbel</option>
                                        <option style="font-family:Cordia New">Cordia New</option>
                                        <option style="font-family:Courier New">Courier New</option>
                                        <option style="font-family:DaunPenh">DaunPenh</option>
                                        <option style="font-family:Dotum">Dotum</option>
                                        <option style="font-family:FangSong">FangSong</option>
                                        <option style="font-family:Franklin Gothic Book">Franklin Gothic Book</option>
                                        <option style="font-family:Franklin Gothic Medium">Franklin Gothic Medium</option>
                                        <option style="font-family:Garamond">Garamond</option>
                                        <option style="font-family:Gautami">Gautami</option>
                                        <option style="font-family:Georgia">Georgia</option>
                                        <option style="font-family:Gill Sans MT">Gill Sans MT</option>
                                        <option style="font-family:Gulim">Gulim</option>
                                        <option style="font-family:GungSuh">GungSuh</option>
                                        <option style="font-family:Helvetica">Helvetica</option>
                                        <option style="font-family:HGGothicE">HGGothicE</option>
                                        <option style="font-family:HGGyoshotai">HGGyoshotai</option>
                                        <option style="font-family:HGPMinchoE">HGPMinchoE</option>
                                        <option style="font-family:HYGothic-Extra">HYGothic-Extra</option>
                                        <option style="font-family:HYMyeongJo-Extra">HYMyeongJo-Extra</option>
                                        <option style="font-family:Iskoola Pota">Iskoola Pota</option>
                                        <option style="font-family:KaiTi">KaiTi</option>
                                        <option style="font-family:Kalinga">Kalinga</option>
                                        <option style="font-family:Kartika">Kartika</option>
                                        <option style="font-family:Latha">Latha</option>
                                        <option style="font-family:LiSu">LiSu</option>
                                        <option style="font-family:Lucida Sans">Lucida Sans</option>
                                        <option style="font-family:Lucida Sans Unicode">Lucida Sans Unicode</option>
                                        <option style="font-family:Malgun Gothic">Malgun Gothic</option>
                                        <option style="font-family:Mangal">Mangal</option>
                                        <option style="font-family:Meiryo">Meiryo</option>
                                        <option style="font-family:Microsoft JhengHei">Microsoft JhengHei</option>
                                        <option style="font-family:Microsoft YaHei">Microsoft YaHei</option>
                                        <option style="font-family:MingLiU">MingLiU</option>
                                        <option style="font-family:MingLiU_HKSCS">MingLiU_HKSCS</option>
                                        <option style="font-family:MS Gothic">MS Gothic</option>
                                        <option style="font-family:MS Mincho">MS Mincho</option>
                                        <option style="font-family:MS PGothic">MS PGothic</option>
                                        <option style="font-family:MS PMincho">MS PMincho</option>
                                        <option style="font-family:Nyala">Nyala</option>
                                        <option style="font-family:PMingLiU">PMingLiU</option>
                                        <option style="font-family:PMingLiU-ExtB">PMingLiU-ExtB</option>
                                        <option style="font-family:Raavi">Raavi</option>
                                        <option style="font-family:Rockwell">Rockwell</option>
                                        <option style="font-family:Segoe">Segoe</option>
                                        <option style="font-family:Segoe Light">Segoe Light</option>
                                        <option style="font-family:Segoe UI">Segoe UI</option>
                                        <option style="font-family:Segoe UI Light">Segoe UI Light</option>
                                        <option style="font-family:Shruti">Shruti</option>
                                        <option style="font-family:SimHei">SimHei</option>
                                        <option style="font-family:SimSun">SimSun</option>
                                        <option style="font-family:SimSun-ExtB">SimSun-ExtB</option>
                                        <option style="font-family:STKaiti">STKaiti</option>
                                        <option style="font-family:STXingkai">STXingkai</option>
                                        <option style="font-family:STXinwei">STXinwei</option>
                                        <option style="font-family:Sylfaen">Sylfaen</option>
                                        <option style="font-family:Tahoma">Tahoma</option>
                                        <option style="font-family:Times">Times</option>
                                        <option style="font-family:Times New Roman">Times New Roman</option>
                                        <option style="font-family:Trebuchet MS">Trebuchet MS</option>
                                        <option style="font-family:Tunga">Tunga</option>
                                        <option style="font-family:TW Cen MT">TW Cen MT</option>
                                        <option style="font-family:Verdana">Verdana</option>
                                        <option style="font-family:Vrinda">Vrinda</option>
                                    </select>
                                    <select class="font_size" name="fontsize">
                                        <option class="hide">1</option>
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                        <option>6</option>
                                        <option>7</option>
                                    </select>
                                </div>
                                <div class="fontStyle">
                           			<a href="javascript:;" name="symbol" title="符号"></a>
                                    <a href="javascript:;" name="underline" title="下滑线"></a>
                                    <a href="javascript:;" name="bold" title="加粗"></a>
                                    <a href="javascript:;" name="italic" title="斜体"></a>
                                    <a href="javascript:;" name="strikethrough" title="删除线"></a>
                                    <a href="javascript:;" name="removeformat" title="清除字体格式"></a>
                                    <a href="javascript:;" name="fontcolor" title="字体颜色">
                                        <span class="mark"></span>
                                    <span class="select">
                                        <input type="submit" id="fontcolor">
                                    </span>
                                    </a>
                                    <a style="margin-left: 15px" href="javascript:;" name="chooseView" title="选择窗格"></a>
                                    <a style="min-width: 25px;min-height: 25px" href="javascript:;" name="superLink" title="超链接"></a>
                                </div>
                            </div>
                        </li>
                        <li class="par_set">
                            <div class="tabs_cnt">
                                <a href="javascript:;" name="alignleft" title="左对齐"></a>
                                <a href="javascript:;" name="aligncenter" title="居中"></a>
                                <a href="javascript:;" name="alignright" title="右对齐"></a>
                                <a href="javascript:;" name="alignjustify" title="分散对齐&#13;字符之间需用空格隔开"></a>
                                <a href="javascript:;">
                                    <select class="line_height" title="段落间距" name="lineheight">
                                        <option class="hide">1.0</option>
                                        <option>1.0</option>
                                        <option>1.5</option>
                                        <option>2.0</option>
                                        <option>2.5</option>
                                        <option>3.0</option>
                                    </select>
                                </a>
                                <a href="javascript:;">
                                    <select class="letter_spacing" title="字间距" name="letterSpacing">
                                        <option class="hide">1.0</option>
                                        <option>1</option>
                                        <option>2</option>
                                        <option>3</option>
                                        <option>4</option>
                                        <option>5</option>
                                        <option>6</option>
                                        <option>7</option>
                                        <option>8</option>
                                    </select>
                                </a>
                                <a href="javascript:;">
                                    <select class="vertical_align" title="对齐文本" name="verticalalign">
                                        <option class="hide">顶端</option>
                                        <option>顶端</option>
                                        <option>中部</option>
                                        <option>底端</option>
                                    </select>
                                </a>
                                <a href="javascript:;" name="unorderedlist" title="无序列表"></a>
                                <a href="javascript:;" name="orderedlist" title="有序列表"></a>
                                <a href="javascript:;" name="undo" title="后退操作"></a>
                            </div>
                        </li>
                    </ul>
                    <ul style="float:left;">
                        <li class="extra_tab">
                            <div class="tabs_cnt">
                                <a href="javascript:;" name="grid" title="网格线"></a>
                            </div>
                        </li>
                        <li class="box_style">
                            <div class="tabs_cnt">
                                <ul class="active">
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                    <li>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
                                        <a name="boxstyle" href="javascript:;">Abc</a>
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
<!--                                         <a name="boxstyle" href="javascript:;">Abc</a> -->
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="tabs tabs_1">
                    <ul class="break">
                        <li class="textBox">
                                <span class="span_animate">
                                    <img src="<%=path%>/sys/teach/images/ppt/putIn_1.png" alt="">
                                    <span class="putIn">添加文本框</span>
                                </span>
                        </li>
                        <li class="audio">
                            <span class="span_animate">
                                <img src="<%=path%>/sys/teach/images/ppt/putIn_2.png" alt="">             
                                <label>
                                    <span class="putIn">添加音频</span>
                                    <input type="file" name="file_audio" id="file_audio" multiple="multiple" onchange="uploadAllTypeFile(this,2)"/>
                                    <input type="file" name="file1" id="file1" multiple="multiple" />
                                </label>
                            </span>
                        </li>
                        <li class="video">
							<span class="span_animate">
                                <img src="<%=path%>/sys/teach/images/ppt/putIn_3.png" alt="">
                                <label>
                                	<span>添加视频</span>
                                	<input type="file" name="file_video" id="file_video" multiple="multiple" onchange="uploadAllTypeFile(this,4)"/>
                            	</label>
                            </span>
                        </li>
                        <li class="sheet">
                            <span class="span_animate">
                                <img src="<%=path%>/sys/teach/images/ppt/putIn_4.png" alt="">
                                <span class="putIn">添加表格</span>
                            </span>
                            <span class="sheetCont">
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                                <i></i><i></i><i></i><i></i><i></i>
                            </span>
                        </li>
                        <li class="upLoad">
                            <span class="span_animate">
                                <img src="<%=path%>/sys/teach/images/ppt/putIn_5.png" alt="">
                                <label>
                                	<span>添加图片</span>
                                	<input type="file" name="file0" id="file0" multiple="multiple" onchange="uploadAllTypeFile(this,1)"/>
                            	</label>
                            </span>
                        </li>
                  		<li class="shape">
                            <span class="span_animate">
                                <img src="<%=path%>/sys/teach/images/ppt/putIn_6.png" alt="">
                                <i class="putIn">形状</i>
                            </span>
                            <span class="icon_cont">
                                <span class="title">箭头</span>
                                <span class="iconFont_cont">
                                    <i title="转角箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantou"></use>
                                        </svg>
                                    </i>
                                    <i title="环状箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-1"></use>
                                        </svg>
                                    </i>
                                    <i title="十字箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-4arrow"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头1">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantouarrow489"></use>
                                        </svg>
                                    </i>
                                    <i title="折线箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-hj2"></use>
                                        </svg>
                                    </i>
                                    <i title="粗箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-arrow-left-thick"></use>
                                        </svg>
                                    </i>
                                    <i title="对角箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantou1"></use>
                                        </svg>
                                    </i>
                                    <i title="弯箭头1">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantouarrow476-copy"></use>
                                        </svg>
                                    </i>
                                    <i title="弯箭头2">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantouarrow496"></use>
                                        </svg>
                                    </i>
                                    <i title="弯箭头3">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jiantou14-copy-copy-copy"></use>
                                        </svg>
                                    </i>
                                    <i title="向上箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-u-arrow3-right"></use>
                                        </svg>
                                    </i>
                                    <i title="向上箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-downleft"></use>
                                        </svg>
                                    </i>
                                    <i title="圈箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-arrows-10-2"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-arrow-circle-top"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-changjiantou"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-changjiantou-copy"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-arrow1"></use>
                                        </svg>
                                    </i>
                                    <i title="箭头">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-changjiantou-copy1"></use>
                                        </svg>
                                    </i>
                                </span>

                                <span class="title">多边形</span>
                                <span class="iconFont_cont">
                                    <i title="圆形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuan"></use>
                                        </svg>
                                    </i>
                                    <i title="矩形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-icon-test"></use>
                                        </svg>
                                    </i>
                                    <i title="矩形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-icon-test1"></use>
                                        </svg>
                                    </i>
                                    <i title="梯形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liucheng"></use>
                                        </svg>
                                    </i>
                                    <i title="矩形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-bruce"></use>
                                        </svg>
                                    </i>
                                    <i title="梯形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liucheng1"></use>
                                        </svg>
                                    </i>
                                    <i title="多边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liucheng2"></use>
                                        </svg>
                                    </i>
                                    <i title="流程3">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liucheng3"></use>
                                        </svg>
                                    </i>
                                    <i title="流程4">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liuchengtu"></use>
                                        </svg>
                                    </i>
                                    <i title="梯形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liuchengtu1"></use>
                                        </svg>
                                    </i>
                                    <i title="L形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-Lxing"></use>
                                        </svg>
                                    </i>
                                    <i title="六边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liuchengtu"></use>
                                        </svg>
                                    </i>
                                    <i title="等腰梯形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liuchengtu3"></use>
                                        </svg>
                                    </i>
                                    <i title="水滴">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-shuidi"></use>
                                        </svg>
                                    </i>
                                    <i title="八边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-babianxing"></use>
                                        </svg>
                                    </i>
                                    <i title="饼形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-bingxing"></use>
                                        </svg>
                                    </i>
                                    <i title="菱形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-lingxing"></use>
                                        </svg>
                                    </i>
                                    <i title="半圆">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-banyuan"></use>
                                        </svg>
                                    </i>
                                    <i title="平行四边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-pinghangsibianxing"></use>
                                        </svg>
                                    </i>
                                    <i title="六边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-liubianxing"></use>
                                        </svg>
                                    </i>
                                    <i title="缺角矩形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-quejiaojuxing"></use>
                                        </svg>
                                    </i>
                                    <i title="三角形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-sanjiaoxing"></use>
                                        </svg>
                                    </i>
                                    <i title="十字形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-shizixing"></use>
                                        </svg>
                                    </i>
                                    <i title="五边形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-wubianxing"></use>
                                        </svg>
                                    </i>
                                    <i title="斜纹">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-xiewen"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形1">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing1"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形2">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing2"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形3">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing3"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形4">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing4"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形5">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing5"></use>
                                        </svg>
                                    </i>
                                    <i title="圆矩形6">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanjuxing6"></use>
                                        </svg>
                                    </i>
                                    <i title="圆菱形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanlingxing"></use>
                                        </svg>
                                    </i>
                                    <i title="圆柱">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-yuanzhu"></use>
                                        </svg>
                                    </i>
                                    <i title="正方体">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-zhengfangti"></use>
                                        </svg>
                                    </i>
                                    <i title="直角三角形">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-zhijiaosanjiaoxing"></use>
                                        </svg>
                                    </i>


                                </span>
                                <span class="title">符号</span>
                                <span class="iconFont_cont">
                                    <i title="加好">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jia"></use>
                                        </svg>
                                    </i>
                                    <i title="减号">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-jian"></use>
                                        </svg>
                                    </i>
                                    <i title="乘号">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-cheng"></use>
                                        </svg>
                                    </i>
                                    <i title="除号">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-chu"></use>
                                        </svg>
                                    </i>
                                    <i title="等于号">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-dengyu"></use>
                                        </svg>
                                    </i>
                                    <i title="不等于号">
                                        <svg class="icon_1" aria-hidden="true">
                                            <use xlink:href="#icon-budengyu"></use>
                                        </svg>
                                    </i>
                                </span>
                            </span>
                        </li>
                        <li class="pageTopFooter">
                            <span class="span_animate">
                                <img width="23" height="23" src="<%=path%>/sys/teach/images/ppt/iconfont/pptIcon_14.png" alt="">
                                <i style="font-style: normal" class="putIn">页眉和页脚</i>
                            </span>
                        </li>
                        <li class="pptStyle">
                            <span class="span_animate">
                                <img width="30" height="23" src="<%=path%>/sys/teach/images/ppt/pptStyle.png" alt="">
                                <i style="font-style: normal" class="putIn">幻灯片板式</i>
                            </span>
                            <div class="pptStyleCont" style="display: none;">
                                <ul>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_03.png" alt=""><i>标题幻灯片</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_05.png" alt=""><i>标题和内容</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_07.png" alt=""><i>节标题</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_12.png" alt=""><i>两栏内容</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_13.png" alt=""><i>比较</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_14.png" alt=""><i>仅标题</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_18.png" alt=""><i>空白</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_19.png" alt=""><i>内容和标题</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_20.png" alt=""><i>图片与标题</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_24.png" alt=""><i>标题和竖排文字</i></li>
                                    <li><img src="<%=path%>/sys/teach/images/ppt/1_25.png" alt=""><i>垂直排列标题文本</i></li>
                                    <span class="pptStyleIn">应用</span>
                                    <span class="pptStyleInAll">全部应用</span>
                                </ul>
                            </div>
                        </li>
                        <li class="wordArt">
                            <span class="span_animate">
                                <img width="22" height="23" src="<%=path%>/sys/teach/images/ppt/iconfont/pptIcon_13.png" alt="">
                                <i style="font-style: normal" class="putIn">艺术字</i>
                            </span>
                            <div class="wordArtCont" style="display: none;">
                                <div>
                                    <h1 class='outlinedA'>A</h1>
                                    <h1 class='outlinedA mid'>A</h1>
                                    <h1 class='outlinedB'>A</h1>
                                    <h1 class='hsl'>A</h1>
                                    <h1 class='bottom'>A</h1>
                                    <h1 class='dilate'>A</h1>
                                    <h1 class='erode'>A</h1>
                                    <h1 class='distant1'>A</h1>
                                    <h1 class='distant-top'>A</h1>
                                    <h1 class='distant-front'>A</h1>
                                    <h1 class='diff1'>A</h1>
                                    <h1 class='bevel'>A</h1>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="tabs animateTabs">
                    <div class="moreAnimateCont moreAnimateCont_1 ">
                        <ul style="height: 65px;">
                            <li><img src="<%=path%>/sys/teach/images/ppt/animateIcon/1_03.png" alt=""><i>无</i></li>
                            <li style="width: 15px;background-color: #ccc;padding-top: 5px;height: 40px;">进入</li>
                            <li type="in" animationName="bounceIn"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/10_03.png" alt=""><i>弹跳出现</i></li>
                            <li type="in" animationName="bounceInDown"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/11_03.png" alt=""><i>上至下</i></li>
                            <li type="in" animationName="bounceInLeft"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/12_03.png" alt=""><i>左至右</i></li>
                            <li type="in" animationName="bounceInRight"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/13_03.png" alt=""><i>右至左</i></li>
                            <li type="in" animationName="bounceInUp"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/14_03.png" alt=""><i>下至上</i></li>
                            <li style="width: 15px;background-color:#ccc;padding-top: 5px;height: 40px;">退出</li>
                            <li type="out" animationName="bounceOut"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/10_03.png" alt=""><i>弹跳退出</i></li>
                            <li type="out" animationName="bounceOutDown"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/11_03.png" alt=""><i>上至下</i></li>
                            <li type="out" animationName="bounceOutLeft"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/12_03.png" alt=""><i>左至右</i></li>
                            <li type="out" animationName="bounceOutRight"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/13_03.png" alt=""><i>右至左</i></li>
                            <li type="out" animationName="bounceOutUp"><img src="<%=path%>/sys/teach/images/ppt/animateIcon/14_03.png" alt=""><i>下至上</i></li>
                        </ul>
                    </div>
                </div>
                <div class="tabs animateTabs plan">
                    <div class="moreAnimateCont">
                        <ul class="theme" style="height: 65px;">
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/001.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/002.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/003.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/004.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/005.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/006.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/007.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/008.jpg" alt=""></li>
                            <li><img src="<%=path%>/sys/teach/images/ppt/theme/009.jpg" alt=""></li>
                            <li style="text-align: center;width: 66px;padding:0.039062rem 0.078125rem;line-height: 40px;font-size: 30px">无</li>
                        </ul>
                        <div class="rt" style="width: 80px;cursor:pointer;"> 
                            <label style="display: block;">
                            	<img style="display: block;margin: 10px auto 0 auto" src="<%=path%>/sys/teach/images/ppt/plan/11_03.png" alt=""><i style="font-style: normal">添加本地图片</i>
                            	<input type="file" name="file_theme" id="file_theme" style="display: none;" multiple="multiple" onchange="uploadAllTypeFile(this,7)"/>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="tabs upload_ppt">
                    <ul>
                        <li>
                            <div class="upload_file ppt_file">
                                <span>选择文件</span>
                                <form id="pptUploadForm" enctype="multipart/form-data"><input type="file"/></form>
                            </div>
                            <div class="upload_file file_info">
                                <span class="file_name"></span>
                                <span class="file_progress"><i></i></span>
                                <span class="delete_file">删除</span>
                            </div>
                        </li>
                    </ul>
                </div>
                
            </div>
        </div>
    </div>
    <!-- end 菜单栏 -->
    <!-- begin ppt略缩图列表 -->
    <div class="left wrap">
        <div class="rightClick_right qq" style="box-shadow:1px 5px 5px #ccc;width: 180px;background-color:#fff;position: absolute;display: none;"></div>
        <div class="rightClick_left qq" style="box-shadow:1px 5px 5px #ccc;width: 100px;background-color:#fff;position: absolute;display: none;">
        </div>
        <i class="addPPT">添加空白页</i>
        <ul>
            <li class="page_box">
                <i></i>
                <span class="page_index">1</span>
                <span class="closePPT">×</span>
                <div class="page border_org" name="1"><img class="thumbImg" src="" alt=""></div>
        	</li>
        </ul>
   	</div>
    <!-- begin 动画窗格 -->
    <div class="animateCont">
        <div class="animate_title">动画窗格</div>
     	<ul class="animateList"></ul>
    </div>
    <div class="chooseViewCont">
        <div class="viewCont_title">选择窗格</div>
        <ul class="viewCont_list"></ul>
    </div>
    <div class="setPptStyleCont">
        <div class="pptStyleCont_title">设置背景格式<i></i></div>
        <div class="pptStyleCont_list">
            <span style="color: #c30e0e"><i></i>填充</span>
            <form action="" method="get">
                <label>
              	    <input type="radio" checked="checked" name="pptStyleBg" id="pptStyleChunse" />
                    <span>纯色填充</span>
                </label>
                <label class="cantClick_1">
                    <input type="radio" disabled name="pptStyleBg" id="pptStyleJianbian" />
                    <span>渐变填充</span>
                </label>
                <label class="cantClick_2">
                    <input type="radio" disabled name="pptStyleBg" id="pptStyleWenli" />
                    <span>图片或纹理填充</span>
                </label>
                <label class="cantClick_3">
                    <input type="radio" disabled name="pptStyleBg" id="pptStyleTuan" />
                    <span>图案填充</span>
                </label>
            </form>
            <div style="margin: 10px 0">
                <div>
                    <span style="color:#515151;float:left;height: 32px;line-height: 32px;">颜色</span>
                    <a href="javascript:;" name="pptBgc" title="背景颜色">
                        <span class="mark"></span>
                    <span class="select">
                        <input style="width: 2px;opacity: 0" type="submit" id="pptBgc">
                    </span>
                    </a>
                </div>
                <div style="overflow:hidden;width: 100%;">
                    <span style="color:#515151;float:left;height: 32px;line-height: 32px;">透明度</span>
                    <input type="text" name="pptOpacity" value="0%" />
                    <span class="choosePptOpc">
                        <i class="pptOpacity_up"></i>
                        <i class="pptOpacity_down"></i>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <!-- end 动画窗格 -->

    <!-- end ppt略缩图列表 -->

    <!-- begin 编辑器区域 -->
    <div class="right" spellcheck="false">

        <div class="editor_box" name="1" id="domImg" animatenum="0"></div>
        <!--<div class="editor_box" name="2" style="display: none"></div>-->
        <!--<div class="editor_box" name="3" style="display: none"></div>-->
        <!--<div class="editor_box" name="4" style="display: none"></div>-->
        <!--<div class="editor_box" name="5" style="display: none"></div>-->
    </div>
    <!-- end 编辑器区域 -->
    <!-- begin PPT上传完显示缩略图并生成话述编辑 -->
	<div class="slide_outline"></div>
    <!-- end PPT上传完显示缩略图并生成话述编辑 -->
    </div>
    </body>
    <script src="<%=path%>/sys/teach/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/jquery-ui.min.js"></script>
    <script src="<%=path%>/sys/teach/js/shortcutKey.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/ddsort.min.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/evol-colorpicker.min.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/dom-to-image.min.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/video.js"></script>
    <script src="<%=path%>/sys/teach/js/ppt/music.js"></script>
    <script src="<%=path%>/sys/teach/js/jquery-PlayBar.min.js"></script>
    <script src="<%=path%>/sys/teach/js/lib/iconfont.js"></script>
    <script src="<%=path%>/sys/teach/js/layer.js"></script>
    <script src="<%=path%>/sys/teach/js/util/select.js"></script>
    <script src="<%=path%>/sys/teach/js/index.js"></script>
    <script src="<%=path%>/sys/teach/js/util/ddsort.js"></script>
    <script src="<%=path%>/sys/teach/js/util/editor.js"></script>
    <script src="<%=path%>/sys/teach/js/util/menuEvent.js"></script>
    <script src="<%=path%>/sys/teach/js/animateBtn.js"></script>
    <script src="<%=path%>/sys/teach/js/util/screen.js"></script>
    <script src="<%=path%>/sys/teach/js/ppt/ppt.js"></script>
    <script src="<%=path%>/sys/teach/js/ppt/rightClick.js"></script>
    <script src="<%=path%>/sys/teach/js/util/editPpt.js"></script>
    <script src="<%=path%>/sys/teach/js/common.js"></script>
    <script src="<%=path%>/sys/teach/js/upload_ppt.js"></script>
	<script src="<%=path%>/sys/teach/js/flexible.js"></script>
	<script>
        videojs.options.flash.swf = "video-js.swf";
    </script>
    <script>
        $(function () {
            $('.left.wrap').bind('contextmenu',function(){
                return false;
            });
            var type = '文本框';
            var $thisName = 'box'+ textBoxIndex++ +'';
            var $thisName1 = 'box'+ textBoxIndex++ +'';
            var newText1 = "<div style='left: 6%;top: 7%;width: 85%;height: 24%;' class='module textBoxIndex' className='"+$thisName+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%;font-size:0.203125rem;'></p></div></div>";
            var newText2 = "<div style='left: 6%;top: 47%;width: 85%;height: 24%;' class='module textBoxIndex' className='"+$thisName1+"' ><div class='text' contenteditable='true' style='cursor:auto;width:100%;height:auto'><p style='min-height: 16px;width: 100%;font-size:0.203125rem;'></p></div></div>";
            E.insert(newText1,type,$thisName);
            E.insert(newText2,type,$thisName1);
			
            $(document).on('contextmenu','.editor_box',function(){
                return false;
            });
            
            if(${courseware != null}){
            	var pptType = '${courseware.pptType}';
            	if(pptType == '2'){
					renderUploadPage('${courseware.uploadPptName}',${courseware.pageAboveWords == null ? "1" : courseware.pageAboveWords});
            	}else{
	    			renderPage(${courseware.content == null ? "1" : courseware.content}, ${courseware.animation == null ? "1" : courseware.animation}, ${courseware.aboveWords == null ? "1" : courseware.aboveWords}, ${courseware.thumbnail == null ? "1" : courseware.thumbnail});
            	}
    		}
        });
    </script>
</html>
