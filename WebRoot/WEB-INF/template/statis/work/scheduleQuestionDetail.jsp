<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
    <title>个人进度详情列表</title>
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/reset.pchlin.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/js/jedate/skin/jedate.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/sys/css/workManage.css">
  </head>
  <body>
    <!-- begin 个人进度详情列表 -->
    <div class="wa_box sq_detail_list">
        <div class="wa_table_oper">
            <div  class="table_name">
                <span><a href="<%=path %>/work/scheduleClass.jhtml">进度查询</a></span>
                <span><a href="<%=path %>/work/scheduleClassDetail.jhtml?lessonId=${lessonId }&courseId=${courseId }">班级进度</a></span>
                <span class="current">个人进度</span>
            </div>
        </div>
        <table class="wa_table">
            <caption>${studentName}作业详情</caption>
        	<colgroup>
                <col style="width: 8%">
                <col style="width: 40%">
                <col style="width: 7%">
                <col style="width: 7%">
                <col style="width: 7%">
            </colgroup>
            <thead>
                <tr>
                    <th>题号</th>
                    <th>名称</th>
                    <th>题型</th>
                    <th>对错</th>
                    <th>作答用时</th>
                    <th>答案</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach items="${resultCardInfoList}" var="card">
	            		<tr>
	            			<c:choose>
	            				<c:when test="${card.childResultCardInfoList!=null && fn:length(card.childResultCardInfoList) > 0}"><td class="show_list"><span class="show_btn"></span>${card.sortNo}</td></c:when>
	            				<c:otherwise><td>${card.sortNo}</td></c:otherwise>
	            			</c:choose>
		                    <td>${card.brief}</td>
		                    <td>
								<c:if test="${card.questionType==0 }">组合题</c:if> <c:if
								test="${card.questionType==1 }">单选题</c:if> <c:if
								test="${card.questionType==2 }">多选题</c:if> <c:if
								test="${card.questionType==3 }">填空题</c:if> <c:if
								test="${card.questionType==4 }">连线题</c:if> <c:if
								test="${card.questionType==5 }">语音题</c:if> <c:if
								test="${card.questionType==6 }">判断题</c:if>
							</td>
		                    <td><c:if test="${card.state==2 }">对</c:if> <c:if
								test="${card.state!=2 }">错</c:if></td>
		                    <td><c:if test="${card.questionType!=0 && card.useTime!=null}"> ${card.useTime}s</c:if></td>
		                    <td class="answer">
								<c:choose>
									<c:when test="${card.questionType==5 }">
										<c:forEach items="${card.answerObj}" var="answer" varStatus="index">
										<audio src="${answer.answer}" controls></audio>
										</c:forEach>
									</c:when>
									<c:otherwise><c:forEach items="${card.answerObj}" var="answer" varStatus="index">
										<c:if test="${index.index!=0 }">,</c:if>${answer.answer}
									</c:forEach> </c:otherwise>
								</c:choose>
							</td>
	                	</tr>
	                	<c:if test="${card.childResultCardInfoList!=null && fn:length(card.childResultCardInfoList) > 0}">
	                		<tr class="inside_table">
			                    <td colspan="6">
			                        <table class="wa_table">
							        	<colgroup>
							                <col style="width: 8%">
							                <col style="width: 40%">
							                <col style="width: 7%">
							                <col style="width: 7%">
							                <col style="width: 7%">
							            </colgroup>
			                            <tbody>
			                            	<c:forEach items="${card.childResultCardInfoList}" var="childCard">
				                                <tr>
								                    <td>${childCard.appSortNo}</td>
								                    <td>${childCard.brief}</td>
								                    <td>
														<c:if test="${childCard.questionType==0 }">组合题</c:if> <c:if
														test="${childCard.questionType==1 }">单选题</c:if> <c:if
														test="${childCard.questionType==2 }">多选题</c:if> <c:if
														test="${childCard.questionType==3 }">填空题</c:if> <c:if
														test="${childCard.questionType==4 }">连线题</c:if> <c:if
														test="${childCard.questionType==5 }">语音题</c:if> <c:if
														test="${childCard.questionType==6 }">判断题</c:if>
													</td>
								                    <td><c:if test="${childCard.state==2 }">对</c:if> <c:if
														test="${childCard.state!=2 }">错</c:if></td>
								                    <td><c:if test="${childCard.questionType!=0 && childCard.useTime!=null }"> ${childCard.useTime}s</c:if></td>
								                    <td class="answer" style="width: 234px">
														<c:choose>
															<c:when test="${childCard.questionType==5 }"><c:forEach items="${childCard.answerObj}" var="answer" varStatus="index">
															<audio src="http://teaching.skyedu99.com/AppTask/${answer.answer}" controls></audio>
															</c:forEach></c:when>
															<c:otherwise><c:forEach items="${childCard.answerObj}" var="canswer" varStatus="index1">
																<c:if test="${index1.index!=0 }">,</c:if>${canswer.answer}
															</c:forEach> </c:otherwise>
														</c:choose>
													</td>
							                	</tr>
			                                </c:forEach>
			                            </tbody>
			                        </table>
			                    </td>
			                </tr>
	                	</c:if>
            	</c:forEach>
            </tbody>
        </table>
    </div>
    <!-- end 个人进度详情列表 -->

    <script type="text/javascript" src="<%=path %>/sys/js/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/jedate/jquery.jedate.min.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/scheduleQuestionDetail.js"></script>
    <script type="text/javascript" src="<%=path %>/sys/js/public.js"></script>
  </body>
</html>