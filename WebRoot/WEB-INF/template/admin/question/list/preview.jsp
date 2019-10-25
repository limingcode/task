<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html class="task">
<head lang="en">
<meta charset="UTF-8">
<title>预览</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/reset.pchlin.css">
<link rel="stylesheet" type="text/css"
	href="<%=path%>/sys/css/public.css">
<link rel="stylesheet" href="<%=path%>/sys/css/taskOnline.css" />
<link rel="stylesheet" href="<%=path%>/sys/css/swiper.min.css">
</head>
<body>
	<!-- begin 操作按钮 -->
    <div class="oper_list">
        <!-- <button class="sol_btn show_analy">显示解析</button> -->
        <button class="sol_btn show_e_anwer">显示填空题完整答案</button>
        <button class="sol_btn ff_replace">字体(粗体)</button>
        <c:if test="${questionList!=null && fn:length(questionList)==1}"> <button class="sol_btn" onclick="editor(${questionList[0].iD})">修改/查看</button></c:if>
    </div>
    <!-- end 操作按钮 -->
	<!--头部返回/时间/分数-->
	<header class="qts-hd">
		<div class="back">
			<a href="javascript:;" class="flash-link btn-back">&nbsp;</a>
		</div>
		<div class="score">
			<span>总分</span> <span>${work.score}</span>
		</div>
		<div class="time">
			<span>00:00:00</span>
		</div>
	</header>
	<div class="swiper-container">
		<div class="swiper-wrapper">
		  <c:forEach items="${questionList}" var="question">
			<!-- begin 连线题 -->
			<article class="swiper-slide">
				<div class="title">预&nbsp;&nbsp;&nbsp;&nbsp;览</div>
				<!-- begin 题干内容-->
				<div class="stem_cnt" analy="${question.analyzation }">
					<ul class="herding_text">
						<c:if
							test="${question.questionType==0 || question.questionType==1 || question.questionType==2 || question.questionType==6}">
							<c:forEach items="${question.title}" var="qModule">
								<li><c:if test="${qModule.style.align==1 }">${qModule.content }</c:if>
									<c:if
										test="${qModule.imgs!= null && fn:length(qModule.imgs) != 0}">
										<span
											<c:if test="${qModule.style.align==0 }">class="img_top"</c:if>
											<c:if test="${qModule.style.align==1 }">class="img_bottom"</c:if>
											<c:if test="${qModule.style.align==2 }">class="img_left"</c:if>
											<c:if test="${qModule.style.align==3 }">class="img_right"</c:if>>
											<c:forEach items="${qModule.imgs }" var="img">
												<img style="height:${img.height}px ;width: ${img.width}px;"
													src="<%=path %>/upload/question/${question.iD%15 }/${question.iD%16 }/${question.iD}/${img.name}"
													alt="" />
											</c:forEach> </span>
									</c:if>
									<c:if
										test="${qModule.style.align==0 || qModule.style.align==2|| qModule.style.align==3}">${qModule.content }</c:if>
								</li>
							</c:forEach>
							<li class="score">(本题分值${question.score}分)</li>
						</c:if>
						<c:if
							test="${question.questionType==3 || question.questionType==4}">
							<li>${config.tiankongTip}</li>
						</c:if>
						<c:if test="${question.questionType==5}">
							<li>${config.yuyinTip}</li>
						</c:if>
					</ul>
					<span class="getDown"></span>
				</div>
				<!-- end 题干内容-->
				<!-- begin 答题区域-->
				<div class="answerCont">
					<!--播放器控件-->
					<c:if test="${question.voiceFlag && question.voiceFlag1}">
						<div class="item">
							<div class="plyer">
	                            <span class="player_btn"></span>
	                            <audio class="audio" src="" 
	                            src-data="${question.voiceUrl }" 
	                            wait-data="${question.waitTime }" >
	                            </audio>
	                            <span class="audio_currentTime">00:00</span>
	                            <span class="audio_duration">00:00</span>
	                        </div>
						</div>
					</c:if>
					<c:forEach items="${question.childQuestion }" var="childQuestion">
						<c:choose>
							<c:when
								test="${childQuestion.questionType==1 || childQuestion.questionType==2 || childQuestion.questionType==6}">
								<div class="item" analy="${childQuestion.analyzation }">
									<div class="choose_title">
										<c:forEach items="${childQuestion.title}" var="pModule">
											<p>
												<c:if test="${pModule.style.align==1 }">${pModule.content }</c:if>
												<c:if
													test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
													<span
														<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
														<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
														<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
														<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
														<c:forEach items="${pModule.imgs }" var="img">
															<img
																style="height:${img.height}px ;width: ${img.width}px;"
																src="<%=path %>/upload/question/${childQuestion.iD%15 }/${childQuestion.iD%16 }/${childQuestion.iD}/${img.name}"
																alt="" />
														</c:forEach> </span>
												</c:if>
												<c:if
													test="${pModule.style.align==0 || pModule.style.align==2 || pModule.style.align==3}">${pModule.content }</c:if>
											</p>
										</c:forEach>
									</div>
									<div class="text">
										<ul class="herding_checkbox">
											<c:forEach items="${childQuestion.resultList}" var="result">
												<li
													<c:if test="${result.answer==0}">class="checked_out"</c:if>
													<c:if test="${result.answer==1}">class="checked_on"</c:if>><i></i>${result.head}.
													${result.content}</li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</c:when>
							<c:when test="${childQuestion.questionType==3 }">
								<div class="item" analy="${childQuestion.analyzation }">
									<div class="text">
										<c:forEach items="${childQuestion.title}" var="pModule">
											<p>
												<c:if test="${pModule.style.align==1}">${pModule.content }</c:if>
												<c:if
													test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
													<span
														<c:if test="${pModule.style.align==0 }"> class="img_top"</c:if>
														<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
														<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
														<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
														<c:forEach items="${pModule.imgs }" var="img">
															<img
																style="height:${img.height}px ;width: ${img.width}px;"
																src="<%=path %>/upload/question/${childQuestion.iD%15 }/${childQuestion.iD%16 }/${childQuestion.iD}/${img.name}"
																alt="" />
														</c:forEach> </span>
												</c:if>
												<c:if
													test="${pModule.style.align==0 || pModule.style.align==2 || pModule.style.align==3}">${pModule.content }</c:if>
											</p>
										</c:forEach>
									</div>
								</div>
							</c:when>
							<c:when test="${childQuestion.questionType==4 }">
								<div class="item" analy="${childQuestion.analyzation }">
									<ul class="task_answer pddLeft_15">
										<c:forEach items="${childQuestion.resultList}" var="result">
											<li>${result.content }</li>
										</c:forEach>
									</ul>
									<div class="text">
										<c:forEach items="${childQuestion.title}" var="pModule">
											<p>
												<c:if test="${pModule.style.align==1 }">${pModule.content }</c:if>
												<c:if
													test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
													<span
														<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
														<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
														<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
														<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
														<c:forEach items="${pModule.imgs }" var="img">
															<img
																style="height:${img.height}px ;width: ${img.width}px;"
																src="<%=path %>/upload/question/${childQuestion.iD%15 }/${childQuestion.iD%16 }/${childQuestion.iD}/${img.name}"
																alt="" />
														</c:forEach> </span>
												</c:if>
												<c:if
													test="${pModule.style.align==0 || pModule.style.align==2|| pModule.style.align==3}">${pModule.content }</c:if>
											</p>
										</c:forEach>
									</div>
								</div>
							</c:when>
							<c:when test="${childQuestion.questionType==5 }">
								<div class="item" analy="${childQuestion.analyzation }">
									<div class="text">
										<c:forEach items="${childQuestion.title}" var="pModule">
											<p>
												<c:if test="${pModule.style.align==1 }">${pModule.content }</c:if>
												<c:if
													test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
													<span
														<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
														<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
														<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
														<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
														<c:forEach items="${pModule.imgs }" var="img">
															<img
																style="height:${img.height}px ;width: ${img.width}px;"
																src="<%=path %>/upload/question/${childQuestion.iD%15 }/${childQuestion.iD%16 }/${childQuestion.iD}/${img.name}"
																alt="" />
														</c:forEach> </span>
												</c:if>
												<c:if
													test="${pModule.style.align==0 || pModule.style.align==2|| pModule.style.align==3}">${pModule.content }</c:if>
											</p>
										</c:forEach>
									</div>
									<div class="recording">
			                            <span class="rd_item rd_item1">
			                                	听语音
			                                <span class="player_btn"></span>
			                                <audio class="audio" src="" src-data="${question.voiceUrl }" wait-data="${question.waitTime }"></audio>
			                                </span>
			                            <span class="rd_item rd_item2">录音</span>
			                            <span class="rd_item rd_item3">录音回放</span>
			                        </div>

								</div>
							</c:when>
						</c:choose>
					</c:forEach>


					<c:if
						test="${question.questionType==1 || question.questionType==2 || question.questionType==6}">
						<div class="item">
							<div class="text">
								<ul class="herding_checkbox">
									<c:forEach items="${question.resultList}" var="result">
										<li
											<c:if test="${result.answer==0}">class="checked_out"</c:if>
											<c:if test="${result.answer==1}">class="checked_on"</c:if>><i></i>${result.head}.
											${result.content}</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==3 }">
						<div class="item" analy="${question.analyzation }">
							<div class="text">
								<c:forEach items="${question.title}" var="pModule">
									<p>
										<c:if test="${pModule.style.align==1}">${pModule.content }</c:if>
										<c:if
											test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
											<span
												<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
												<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
												<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
												<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
												<c:forEach items="${pModule.imgs }" var="img">
													<img style="height:${img.height}px ;width: ${img.width}px;"
														src="<%=path %>/upload/question/${question.iD%15 }/${question.iD%16 }/${question.iD}/${img.name}"
														alt="" />
												</c:forEach> </span>
										</c:if>
										<c:if
											test="${pModule.style.align==0 || pModule.style.align==2  || pModule.style.align==3}">${pModule.content }</c:if>
									</p>
								</c:forEach>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==4 }">
						<div class="item" analy="${question.analyzation }">
							<ul class="task_answer pddLeft_15">
								<c:forEach items="${question.resultList}" var="result">
									<li>${result.content }</li>
								</c:forEach>
							</ul>
							<div class="text">
								<c:forEach items="${question.title}" var="pModule">
									<p>
										<c:if test="${pModule.style.align==1 }">${pModule.content }</c:if>
										<c:if
											test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
											<span
												<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
												<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
												<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
												<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
												<c:forEach items="${pModule.imgs }" var="img">
													<img style="height:${img.height}px ;width: ${img.width}px;"
														src="<%=path %>/upload/question/${question.iD%15 }/${question.iD%16 }/${question.iD}/${img.name}"
														alt="" />
												</c:forEach> </span>
										</c:if>
										<c:if
											test="${pModule.style.align==0 || pModule.style.align==2 || pModule.style.align==3}">${pModule.content }</c:if>
									</p>
								</c:forEach>
							</div>
						</div>
					</c:if>
					<c:if test="${question.questionType==5 }">
						<div class="item" analy="${question.analyzation }">
							<div class="text">
								<c:forEach items="${question.title}" var="pModule">
									<p>
										<c:if test="${pModule.style.align==1 }">${pModule.content }</c:if>
										<c:if
											test="${pModule.imgs!= null && fn:length(pModule.imgs) != 0}">
											<span
												<c:if test="${pModule.style.align==0 }">class="img_top"</c:if>
												<c:if test="${pModule.style.align==1 }">class="img_bottom"</c:if>
												<c:if test="${pModule.style.align==2 }">class="img_left"</c:if>
												<c:if test="${pModule.style.align==3 }">class="img_right"</c:if>>
												<c:forEach items="${pModule.imgs }" var="img">
													<img style="height:${img.height}px ;width: ${img.width}px;"
														src="<%=path %>/upload/question/${question.iD%15 }/${question.iD%16 }/${question.iD}/${img.name}"
														alt="" />
												</c:forEach> </span>
										</c:if>
										<c:if
											test="${pModule.style.align==0 || pModule.style.align==2 || pModule.style.align==3}">${pModule.content }</c:if>
									</p>
								</c:forEach>
							</div>
							<div class="recording">
	                            <span class="rd_item rd_item1">
	                               	 听语音
	                                <span class="player_btn"></span>
	                                <audio class="audio" src="" src-data="${question.voiceUrl}" wait-data="${question.waitTime }"></audio>
	                                </span>
	                            <span class="rd_item rd_item2">录音</span>
	                            <span class="rd_item rd_item3">录音回放</span>
	                        </div>

						</div>
					</c:if>
				</div>
				<!-- end 答题区域-->
			</article>
			<!-- end 连线题 -->
		  </c:forEach>
		</div>
		<!-- begin 页码区域-->
		<div class="pageButton">
			<span class="leftBtn swiper-button-prev"></span> <span
				class="pageNum swiper-pagination"></span> <span
				class="rightBtn swiper-button-next"></span>
		</div>
		<!-- end 页码区域-->
	</div>
	<div>
		<span class="card-darg-btn">&nbsp;</span>
	</div>
	<script src="<%=path%>/sys/js/jquery-2.1.4.min.js"></script>
	<script src="<%=path%>/sys/js/swiper.min.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/public.js"></script>
	<script type="text/javascript" src="<%=path%>/sys/js/task_line.js"></script>
	<script type="text/javascript">
		function editor(id){
			var questionId=id; 
			window.location.href="<%=path%>/question/editorQuestion.jhtml?questionId="+questionId;
		}
	</script>
</body>
</html>