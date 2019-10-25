<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body onload="javascript: document.forms[0].submit();">
	<form action="${url}" method="${method}">
		<c:forEach var="entry" items="${parameterMap }">
			<input type="hidden" name="${entry.key }" value="${entry.value }" />
		</c:forEach>
	</form>
</body>