<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Question</title>
</head>
<body>
	<h1><c:out value="${question.text }" /></h1>
	<a href="/">Dashboard</a>
	<h2>Tags:</h2>
	<c:forEach var="tag" items="${question.getTags() }">
		<button><c:out value="${tag.getSubject() }" /></button>
	</c:forEach>
	
	<h2>Answers:</h2>
	<ul>
		<c:forEach var="answer" items="${question.getAnswers() }">
			<li><c:out value="${answer.text }" /></li>
		</c:forEach>
	</ul>
	
	<h2>Add your answer:</h2>
		<form:form action="/questions/${questionId}/answers" method="post" modelAttribute="answer">
			<div class="form-group">
				<form:label path="text">Answer:</form:label>
				<form:errors class="text-danger" path="text" />
				<form:input path="text" class="form-control" />
			</div>
			<button>Answer it!</button>
		</form:form>
</body>
</html>