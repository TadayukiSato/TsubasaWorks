<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Account" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

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
	<c:when test="${not empty userId}">
		<h3>メインメニュー</h3>
		<h4>ようこそ<c:out value="${userId}" />さん</h4>
		<!-- service section-->
		<section id="services">
			<div class="container">
				<div class="row">
					<div class="col-lg-4 col-sm-6 pt-4">
						<div class="service card-effect">
							<div class="iconbox">
								<i class='bx bxs-phone-call'></i>
							</div>
							<h5>飼育相談システム</h5>
							<span>電話での飼育相談を行う際に使用します。</span>
							<a href="SodanServlet?vew_action=init&sodan_id="></a>
						</div>
					</div>
					<div class="col-lg-4 col-sm-6 pt-4">
						<div class="service card-effect">
							<div class="iconbox">
								<i class='bx bx-clipboard'></i>
							</div>
							<h5>保護依頼管理システム</h5>
							<span>引取り(保護)依頼の記録を管理します。</span>
							<br>
							<span style="color: red;">【こちらは未実装です。】</span>
							<a href="#"></a>
						</div>
					</div>
					<div class="col-lg-4 col-sm-6 pt-4">
						<div class="service card-effect">
							<div class="iconbox">
								<i class='bx bx-injection'></i>
							</div>
							<h5>飼育カルテシステム</h5>
							<span>飼育鳥のお世話の記録を管理します。</span>
							<br>
							<spanspan style="color: red;">【こちらは未実装です。】</span>
							<a href="#"></a>
						</div>
					</div>
				</div>
			</div>
		</section>
		<br>
		<a href="MenuServlet?vew_action=logOut">
			<button type="button" class="btn btn-secondary ms-2">ログアウト</button>
		</a>
	</c:when>
    <c:otherwise>
		<jsp:include page="header.jsp" />
		<h5>ログインに失敗しました</h5>
		<a href="MenuServlet?vew_action=loginErr">
			<button type="button" class="btn btn-secondary ms-2">ログイン画面へ</button>
		</a>
	</c:otherwise>
</c:choose>
<!-- Option 2: Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>