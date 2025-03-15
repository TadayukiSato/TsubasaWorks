<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<!-- オリジナルのCSS  -->
<link href="css/common.css" rel="stylesheet" type="text/css" media="all">
<link href="css/style.css" rel="stylesheet" type="text/css" media="all">
<!-- boxicons cdn  -->
<link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
<title>TSUBASA WORKS</title>
</head>
<body>
<jsp:include page="header.jsp" />
<c:choose>
	<c:when test="${not empty errorMsg}">
		<h5> ${errorMsg} </h5>
	</c:when>
	<c:otherwise>
		<h5>ログアウトしました。</h5>
	</c:otherwise>
</c:choose>
<br>
<a href="MenuServlet"> 
	<button type="button" class="btn btn-secondary ms-2">ログイン画面へ</button>
</a>
<!-- Option 2: Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>