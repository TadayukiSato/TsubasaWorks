<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Account, model.Sodan, model.DbAction, java.util.List, java.util.Arrays, java.util.ArrayList" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- リクエストスコープに保存された飼育相談レコードを取得 -->
<% Sodan sodan = (Sodan) request.getAttribute("sodan"); %>
<!-- リクエストスコープに保存されたデータベースの処理内容(画面遷移先)を取得 -->
<% DbAction dbAction = (DbAction) request.getAttribute("dbAction"); %>

<jsp:include page="common.jsp" />

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
	<!-- bootstrapicons cdn  -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
	<title>
		<c:choose>
			<c:when test="${dbAction.dbAction == 'dtl'}">
				飼育相談詳細画面
			</c:when>
			<c:when test="${dbAction.dbAction == 'upd'}">
				飼育相談更新画面
			</c:when>
			<c:when test="${dbAction.dbAction == 'del'}">
				飼育相談削除画面
			</c:when>
			<c:when test="${dbAction.dbAction == 'new'}">
				飼育相談新規作成
			</c:when>
			<c:otherwise>
				？？
			</c:otherwise>
		</c:choose>	
	</title>
</head>

<script type="text/javascript" src="JS/extFunc.js"></script>

<body>
<!-- ヘッダー上部にパンくずリストを表示する -->
<nav style="background-color:skyblue;" class="fixed-top navbar navbar-expand-sm ">
	<span style="font-size: 1rem;">　</span>
	<a href="MenuServlet?vew_action=homeBack"><i class="bi bi-house" style="font-size: 1.5rem; color: cornflowerblue;"></i>	</a>
	<span style="font-size: 1rem;">　</span>
	<span style="font-size: 1rem; color: $black;"><i class="bi bi-chevron-double-right" style="font-size: 1.5rem; color: $black;"></i></span>
	<span style="font-size: 1rem;">　</span>
	<a href="#" style="font-size: 1.5rem; color: $black;" onclick=history.back()>飼育相談検索</a>
	<span style="font-size: 1rem; color: $black;"><i class="bi bi-chevron-double-right" style="font-size: 1.5rem; color: $black;"></i></span>
	<span style="font-size: 1rem;">　</span>
	<span style="font-size: 1.5rem; color: $black;">
		<c:choose>
			<c:when test="${dbAction.dbAction == 'dtl'}">
				飼育相談詳細
			</c:when>
			<c:when test="${dbAction.dbAction == 'upd'}">
				飼育相談更新
			</c:when>
			<c:when test="${dbAction.dbAction == 'del'}">
				飼育相談削除
			</c:when>
			<c:when test="${dbAction.dbAction == 'new'}">
				新規飼育相談
			</c:when>
			<c:otherwise>
				？？
			</c:otherwise>
		</c:choose>	
	</span>
</nav>

<!--＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*
* bootstrap5 フォームバリデーション
*	JavaScriptにて制御
*	参照元：https://bttb.jp/bootstrap5/index.php?strkey=needs-validation
*	<form novalidate class="needs-validation">
*		<div class="input-group has-validation">
*			<input・・・required />
*			<div class="valid-feedback">ok!</div>
*			<div class="invalid-feedback">担当者名は入力必須項目です！</div>
*		</div>
*	</form>
*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝-->
<script>
(function () {
	'use strict';
	window.addEventListener('load', function () {
	// Bootstrapのフォームの入力検証スタイルを適用するフォームを取得
	var forms = document.getElementsByClassName('needs-validation');
	// ループして帰順を防ぐ
	var validation = Array.prototype.filter.call(forms, function (form) {
		form.addEventListener('submit', function (event) {
			if (form.checkValidity() === false) {
				event.preventDefault();
				event.stopPropagation();
			}
			form.classList.add('was-validated');
		}, false);
	});
      }, false);
})();
</script>

<form novalidate class="needs-validation" id="frm" enctype="multipart/form-data" action="SodanServlet" name="sodan_ope_form" method="post">

	<input hidden id="post_action" name="post_action" value="${dbAction.dbAction}">
	<input hidden id="sodan_id" name="sodan_id" value="${sodan.sodanId}">
	
	<div class="container-fluid">
		<c:choose>
			<c:when test="${dbAction.dbAction == 'dtl'}">
				<c:if test="${not empty sodan.upFilePath}">
					<div class="input-group">
						<span class="input-group-text" id="up_file_path_label">アップロードされた画像</span>
						<input 	<c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}"> readonly </c:if> id="up_file_path" name="up_file_path" type="text" class="disabled_color form-control" value="${sodan.upFilePath}">
					</div>
					<div class="ratio ratio-1x1">
						<iframe src="${sodan.upFilePath}" frameborder="0" style="border:0"></iframe>
					</div>
				</c:if>
			</c:when>
			<c:when test="${dbAction.dbAction == 'new'}">
				<div class="row ms-1">
					<div class="p-1 mx-1 col-11 col-sm-11 col-md-9 col-lg-8 col-xl-7">
						<label for="up_file_path" class="form-label">
							PDFファイルを選択して下さい(容量1Mバイト以下)<br>※データ登録後アップロード開始
						</label>
						<input id="up_file_path" name="up_file_path" class="form-control" type="file" accept="application/pdf">
					</div>
				</div>
			</c:when>
			<c:when test="${dbAction.dbAction == 'upd'}">
				<c:choose>
					<c:when test="${not empty sodan.upFilePath}">
						<div class="input-group">
							<span class="input-group-text" id="up_file_path_label">アップロードされた画像</span>
							<input 	<c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}"> readonly </c:if> id="up_file_path" name="up_file_path" type="text" class="disabled_color form-control" value="${sodan.upFilePath}">
						</div>
						<div class="ratio ratio-1x1">
							<iframe src="${sodan.upFilePath}" frameborder="0" style="border:0"></iframe>
						</div>
					</c:when>
					<c:when test="${empty sodan.upFilePath}">
						<div class="row ms-1">
							<div class="p-1 mx-1 col-11 col-sm-11 col-md-9 col-lg-8 col-xl-7">
								<label for="up_file_path" class="form-label">
									PDFファイルを選択して下さい(容量1Mバイト以下)<br>※データ登録後アップロード開始
								</label>
								<input id="up_file_path" name="up_file_path" class="form-control" type="file" accept="application/pdf">
							</div>
						</div>
					</c:when>
				</c:choose>	
			</c:when>
			<c:when test="${dbAction.dbAction == 'del'}">
				<h2>削除対象データの内容を確認してください。</h2>
				<c:if test="${not empty sodan.upFilePath}">
					<div class="input-group">
						<span class="input-group-text" id="up_file_path_label">アップロードされた画像(削除されます)</span>
						<input 	<c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}"> readonly </c:if> id="up_file_path" name="up_file_path" type="text" class="disabled_color form-control" value="${sodan.upFilePath}">
					</div>
					<div class="ratio ratio-1x1">
						<iframe src="${sodan.upFilePath}" frameborder="0" style="border:0"></iframe>
					</div>
				</c:if>
			</c:when>
		</c:choose>	
	
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-3 col-xl-3">
				<div class="input-group">
					<span class="input-group-text" id="sodan_date_label">相談日</span>
					<input	<c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="sodan_date" name="sodan_date" 	type="date" class="disabled_color form-control" value="${sodan.sodanDate}" aria-describedby="sodan_date_label" required />
					<div class="valid-feedback">ok!</div>
					<div class="invalid-feedback">相談日は入力必須項目です！</div>
				</div>
			</div>
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-7 col-lg-6 col-xl-6">
				<div class="input-group">
					<span class="input-group-text" id="sodan_time_from_label">相談時間</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="sodan_time_from" name="sodan_time_from" class="disabled_color form-control" type="time" value="${sodan.sodanTimeFrom}" aria-describedby="sodan_time_from_label">
					<span class="input-group-text" id="sodan_time_to_label">～</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="sodan_time_to" name="sodan_time_to" class="disabled_color form-control" type="time" value="${sodan.sodanTimeTo}" aria-describedby="sodan_time_to_label">
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-3 col-xl-3">
				<div class="input-group has-validation">
					<span class="input-group-text" id="knm_idx">索引</span>
					<select <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="kainushi_idx" name="kainushi_idx" style="text-align: reft; width: 100px;" class="disabled_color form-select" aria-describedby="knm_idx" required>
						<c:choose>
							<c:when test="${empty sodan.kainushiIdx}">
								<option selected value=""></option>
							</c:when>
							<c:otherwise>
								<option value=""></option>
							</c:otherwise>
						</c:choose>							
						<c:forEach var="moji" items="${gojyuon}">
							<c:choose>
								<c:when test="${moji == 'あ行' or moji == 'か行' or moji == 'さ行' or moji == 'た行' or moji == 'な行' or 
											moji == 'は行' or moji == 'ま行' or moji == 'や行' or moji == 'ら行' or moji == 'わ行'}">
									<optgroup label="${moji}">
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${sodan.kainushiIdx == moji}">
											<option selected value="${moji}">${moji}</option>
										</c:when>
										<c:otherwise>
											<option value="${moji}">${moji}</option>
										</c:otherwise>
									</c:choose>	
								</c:otherwise>
							</c:choose>	
						</c:forEach>
					</select>
					<div class="valid-feedback">ok!</div>
					<div class="invalid-feedback">索引を選択してください！</div>
				</div>
			</div>
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-7 col-lg-6 col-xl-6">
				<div class="input-group  has-validation">
					<span class="input-group-text">飼い主名</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="kainushi_name" name="kainushi_name" type="text" class="disabled_color form-control" id="kainushi_name" name="kainushi_name" value="${sodan.kainushiName}" required>
				<div class="valid-feedback">ok!</div>
				<div class="invalid-feedback">飼い主名は入力必須項目です！</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-3 col-xl-3">
				<div class="input-group">
					<span class="input-group-text">地域</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="home_area" name="home_area" type="text" class="disabled_color form-control" value="${sodan.homeArea}">
				</div>
			</div>
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-7 col-lg-6 col-xl-6">
				<div class="input-group">
					<span class="input-group-text">通っている病院</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="clinic_name" name="clinic_name" type="text" class="disabled_color form-control" value="${sodan.clinicName}">
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-5 col-lg-5">
				<div class="input-group">
					<span class="input-group-text">鳥の種類</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="bird_type" name="bird_type" class="disabled_color form-control" list="bird_type_list" placeholder="Type to search..." value="${sodan.birdType}">
					<datalist id="bird_type_list">
						<c:forEach var="birdtype" items="${chousyu}">
							<option value="${birdtype}">
						</c:forEach>
					</datalist>
				</div>
			</div>
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-5 col-lg-4">
				<div class="input-group">
					<span class="input-group-text">鳥の性別</span>
					<select <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="bird_sex" name="bird_sex" class="disabled_color form-select">
						<c:choose>
							<c:when test="${sodan.birdSex == ''}">
								<option selected value=""></option>
							</c:when>
							<c:otherwise>
								<option value=""></option>
							</c:otherwise>
						</c:choose>	
						<c:choose>
							<c:when test="${sodan.birdSex == 'オス'}">
								<option selected value="オス">オス</option>
							</c:when>
							<c:otherwise>
								<option value="オス">オス</option>
							</c:otherwise>
						</c:choose>	
						<c:choose>
							<c:when test="${sodan.birdSex == 'メス'}">
								<option selected value="メス">メス</option>
							</c:when>
							<c:otherwise>
								<option value="メス">メス</option>
							</c:otherwise>
						</c:choose>	
						<c:choose>
							<c:when test="${sodan.birdSex == '不明'}">
								<option selected value="不明">不明</option>
							</c:when>
							<c:otherwise>
								<option value="不明">不明</option>
							</c:otherwise>
						</c:choose>	
					</select>
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-4">
				<div class="input-group">
					<span class="input-group-text">鳥の年齢</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="bird_age" name="bird_age" type="text" class="disabled_color form-control" value="${sodan.birdAge}">
				</div>
			</div>
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-5 col-lg-5">
				<div class="input-group">
					<span class="input-group-text">鳥の名前</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="bird_name" name="bird_name" type="text" class="disabled_color form-control" value="${sodan.birdName}">
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11">
				<div class="input-group">
					<span class="input-group-text">基本情報</span>
					<textarea <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="basic_info" name="basic_info" class="disabled_color form-control" rows="5">${sodan.basicInfo}</textarea>
				</div>
			</div>
		</div>
		<div class="row m-1 col-11">
			<label class="form-label">お悩み概要：</label>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">飼い方：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_kaikata" name="cbx_kaikata" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxKaikata == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">病気：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_byouki" name="cbx_byouki" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxByouki == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">発情：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_hatsujyou" name="cbx_hatsujyou" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxHatsujyou == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">食事：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_syokuji" name="cbx_syokuji" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxSyokuji == 1}">checked</c:if>>
				</span>
			</div>
			 <div class="col">
				<span style="display: inline-block;" class="input-group-text">毛引き：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_kebiki" name="cbx_kebiki" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxKebiki == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">ﾄﾚｰﾆﾝｸﾞ：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_torening" name="cbx_torening" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxTorening == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">ﾌｫｰｼﾞﾝｸﾞ：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_fojing" name="cbx_fojing" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxFojing == 1}">checked</c:if>>
				</span>
			</div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">問題行動：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_mondai_kodo" name="cbx_mondai_kodo" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxMondaikodo == 1}">checked</c:if>>
				</span>
			 </div>
			<div class="col">
				<span style="display: inline-block;" class="input-group-text">その他：
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="cbx_sonota" name="cbx_sonota" class="form-check-input" type="checkbox" value="1" <c:if test="${sodan.cbxSonota == 1}">checked</c:if>>
				</span>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11">
				<div class="input-group has-validation">
					<span class="input-group-text">飼育相談内容</span>
					<textarea <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="sodan_naiyo" name="sodan_naiyo" class="disabled_color form-control" rows="10" required>${sodan.sodonNaiyo}</textarea>
					<div class="valid-feedback">ok!</div>
					<div class="invalid-feedback">飼育相談内容は入力必須項目です！</div>
				</div>
			</div>
		</div>
		<div class="row ms-1">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-7 col-lg-6 col-xl-6">
				<div class="input-group has-validation">
					<span class="input-group-text">担当者名</span>
					<input <c:if test="${dbAction.dbAction == 'dtl' || dbAction.dbAction == 'del'}">disabled</c:if> id="staff_name" name="staff_name" type="text" class="disabled_color form-control" list="staff_name_list" placeholder="Type to search..." value="${sodan.staffName}" required>
					<datalist id="staff_name_list">
						<c:forEach var="staffnamet" items="${syokuin}">
							<option value="${staffnamet}">
						</c:forEach>
					</datalist>
					<div class="valid-feedback">ok!</div>
					<div class="invalid-feedback">担当者名は入力必須項目です！</div>
				</div>
			</div>
		</div>
		<c:choose>
			<c:when test="${dbAction.dbAction == 'dtl'}">
				<div class="row mt-1 ms-1">
					<button style="width: 80px;" class="btn btn-dark ms-1" type="button" onclick=history.back()>戻る</button>
				</div>
			</c:when>
			<c:when test="${dbAction.dbAction == 'new'}">
				<div class="row mt-1 ms-1">
					<input style="width: 80px;" type="submit" class="btn btn-primary ms-1" value="登録">
					<input style="width: 80px;" type="button"class="btn btn-secondary ms-1" value="クリア" onclick="window.location.reload();">
				</div>
				<div class="row mt-1 ms-1">
					<button style="width: 80px;" class="btn btn-dark ms-1" type="button" onclick=history.back()>戻る</button>
				</div>
			</c:when>
			<c:when test="${dbAction.dbAction == 'upd'}">
				<div class="row mt-1 ms-1">
					<input style="width: 80px;" type="submit" class="btn btn-primary ms-1" value="更新">
				</div>
				<div class="row mt-1 ms-1">
					<button style="width: 80px;" class="btn btn-dark ms-1" type="button" onclick=history.back()>戻る</button>
				</div>
			</c:when>
			<c:when test="${dbAction.dbAction == 'del'}">
				<div class="row mt-1 ms-1">
					<button style="width: 80px;" type="button" class="btn btn-danger ms-1" data-bs-toggle="modal" data-bs-target="#ope_modal">削除</button>
				</div>
				<div class="row mt-1 ms-1">
					<button style="width: 80px;" class="btn btn-dark ms-1" type="button" onclick=history.back()>戻る</button>
				</div>
			</c:when>
		</c:choose>	
	</div>

<!-- ↓↓ 削除確認Modal ↓↓ -->
	<div class="modal fade" id="ope_modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="opeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				<h1 class="modal-title fs-5" id="opeModalLabel">削除確認</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<p>データを削除してよろしいですか？</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">キャンセル</button>
				<input type="submit" class="btn btn-danger" value="削除実行">
			</div>
		</div>
	</div>
<!-- ↑↑ Modal ↑↑ -->

</form>

<!-- Option 2: Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>