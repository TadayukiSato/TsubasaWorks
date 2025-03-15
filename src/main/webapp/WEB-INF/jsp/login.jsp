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
<h3>ログイン画面</h3>
<form action="MenuServlet" method="post">
	<div style="border-radius: 15px;background-color:aliceblue;width:95%;" class="container-fluid border border-info mt-2 mx-2 me-3 py-2">
		<input type="hidden" id="userid" name="vew_action" value="menu">
		ユーザーID：<input type="text" name="userId" class="form-control"><br>
		パスワード：<input type="password" name="pass" class="form-control"><br>
		<input type="submit" style="width: 80px;" class="btn btn-primary ms-2" value="ログイン">
		<input type="reset" style="width: 80px;" class="btn btn-dark ms-2" value="クリア">
	</div>
</form>
<!-- Option 2: Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>