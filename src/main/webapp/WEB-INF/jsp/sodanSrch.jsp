<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Account, model.Sodan, model.ExtCon, model.AlertMsg, java.util.List, java.util.Objects, java.sql.Timestamp, java.sql.Date, java.text.SimpleDateFormat, org.apache.commons.lang3.StringUtils" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- リクエストスコープに保存された飼育相談リストを取得 -->
<% List<Sodan> sodanList = (List<Sodan>) request.getAttribute("sodanList"); %>
<!-- リクエストスコープに保存されたFormに入力された抽出条件等を取得 -->
<% ExtCon extCon = (ExtCon) request.getAttribute("extCon");  %>
<!-- リクエストスコープに保存されたAlertMsgを取得 -->
<% AlertMsg alertMsg = (AlertMsg) request.getAttribute("alertMsg");  %>

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
<title>飼育相談検索画面</title>

<script type="text/javascript" src="JS/extFunc.js"></script>

<script type="text/javascript">

function showAlertNewOk(){
		alert("データを追加しました。");
		return;
}

function showAlertNewNg(){
		alert("データの追加に失敗しました。");
		return;
}

function showAlertUpdOk(){
		alert("データを更新しました。");
		return;
}

function showAlertUpdNg(){
		alert("データの更新に失敗しました。");
		return;
}

function showAlertDelOk(){
		alert("データを削除しました。");
		return;
}

function showAlertDelNg(){
		alert("データの削除に失敗しました。");
		return;
}
</script>

</head>

<c:choose>
	<c:when test="${alertMsg.alertMsg == 'NewOk'}">
		<body onload="showAlertNewOk()">
	</c:when>
	<c:when test="${alertMsg.alertMsg == 'NewNg'}">
		<body onload="showAlertNewNg()">
	</c:when>
	<c:when test="${alertMsg.alertMsg == 'UpdOk'}">
		<body onload="showAlertUpdOk()">
	</c:when>
	<c:when test="${alertMsg.alertMsg == 'UpdNg'}">
		<body onload="showAlertUpdNg()">
	</c:when>
	<c:when test="${alertMsg.alertMsg == 'DelOk'}">
		<body onload="showAlertDelOk()">
	</c:when>
	<c:when test="${alertMsg.alertMsg == 'DelNg'}">
		<body onload="showAlertDelNg()">
	</c:when>
	<c:otherwise>
		<body>
	</c:otherwise>
</c:choose>	

<nav style="background-color:skyblue;" class="fixed-top navbar navbar-expand-sm ">
	<span style="font-size: 1rem;">　</span>
	<a href="MenuServlet?vew_action=homeBack"><i class="bi bi-house" style="font-size: 1.5rem; color: cornflowerblue;"></i></a>
	<span style="font-size: 1rem;">　</span>
	<span style="font-size: 1rem; color: $black;"><i class="bi bi-chevron-double-right" style="font-size: 1.5rem; color: $black;"></i></span>
	<span style="font-size: 1rem;">　</span>
	<span style="font-size: 1.5rem; color: $black;">飼育相談検索</span>
	<span style="font-size: 1rem;">　</span>
	<a style="font-size: 1rem; align-items: center;" class="btn btn-outline-primary" href="SodanServlet?vew_action=new&sodan_id=" role="button">新規相談</a>
</nav>

<%--
<% for(Sodan sodan : sodanList) {%>
 <p><%=sodan.getKainushiName() %>：<%=sodan.getBirdName() %></p>
<% } %>

<% for (Sodan sodan : sodanList) {%>
	<p><%=sodan.getContent()%></p>
<% } %>
<p>${cntAll}</p>
--%>

<form id="frm" action="SodanServlet" name="sodan_main_form" method="post">

	<input hidden id="post_action" name="post_action" value="${extCon.post_action}">
	<input hidden id="last_page" name="last_page" value="${extCon.last_page}">

	<div style="border-radius: 15px;background-color:aliceblue;width:95%;" class="container-fluid border border-info mt-2 mx-2 me-3 py-2">
		<div class="row">
			<div class="p-1 mx-1">
				<div class="input-group">
					<span class="h5 mt-1 ms-1">検索条件　</span>
					<input style="width: 80px;" type="submit" class="btn btn-primary ms-2" name="btn_ext" value="検索">
					<input style="width: 80px;" type="button" class="btn btn-secondary ms-1" value="クリア" onClick="clearFormAll();">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-4">
				<div class="input-group">
					<span class="input-group-text">索引</span>
					<select style="text-align: reft; width: 100px;" id="kainushi_idx" name="kainushi_idx" class="form-select">
						<option <c:if test="${empty extCon.kainushi_idx}">selected</c:if> disabled value="">選択...</option>
						<c:forEach var="moji" items="${gojyuon}">
							<c:choose>
								<c:when test="${moji == 'あ行' or moji == 'か行' or moji == 'さ行' or moji == 'た行' or moji == 'な行' or 
										moji == 'は行' or moji == 'ま行' or moji == 'や行' or moji == 'ら行' or moji == 'わ行'}">
									<optgroup label="${moji}">
								</c:when>
								<c:otherwise>
									<option <c:if test="${extCon.kainushi_idx == moji}">selected</c:if> value="${moji}">${moji}</option>
								</c:otherwise>
							</c:choose>	
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="p-1 mx-sm-1 mx-md-0 col-11 col-sm-11 col-md-7 col-lg-7">
				<div class="input-group">
					<span class="input-group-text">飼い主名</span>
					<input type="text" class="form-control" id="kainushi_name" name="kainushi_name" value="${extCon.kainushi_name}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-6 col-lg-6">
				<div class="input-group">
					<span class="input-group-text">相談日</span>
					<input class="form-control" type="date" id="sodan_date_from" name="sodan_date_from" value="${extCon.sodan_date_from}">
					<span class="pt-2 px-1">～</span>
					<input class="form-control" type="date" id="sodan_date_to" name="sodan_date_to" value="${extCon.sodan_date_to}">
				</div>
			</div>
			<div class="p-1 mx-sm-1 mx-md-0 col-11 col-sm-11 col-md-5 col-lg-5">
				<div class="input-group">
					<span class="input-group-text">鳥の種類</span>
					<input class="form-control" list="datalistOptions" id="bird_type" name="bird_type" value="${extCon.bird_type}" placeholder="Type to search...">
					<datalist id="datalistOptions">
						<c:forEach var="birdtype" items="${chousyu}">
							<option value="${birdtype}">
						</c:forEach>
					</datalist>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-6 col-lg-6">
				<div class="input-group">
					<span class="input-group-text">鳥の名前</span>
					<input type="text" class="form-control" id="bird_name" name="bird_name" value="${extCon.bird_name}">
				</div>
			</div>
			<div class="p-1 mx-sm-1 mx-md-0 col-11 col-sm-11 col-md-5 col-lg-5">
				<div class="input-group">
					<span class="input-group-text">お悩み概要</span>
					<select id="onayami_gaiyo" name="onayami_gaiyo" class="form-select">
						<option <c:if test="${empty extCon.onayami_gaiyo}">selected</c:if> value=""></option>
						<option <c:if test="${extCon.onayami_gaiyo == '飼い方'}">selected</c:if> value="飼い方">飼い方</option>
						<option <c:if test="${extCon.onayami_gaiyo == '病気'}">selected</c:if> value="病気">病気</option>
						<option <c:if test="${extCon.onayami_gaiyo == '発情'}">selected</c:if> value="発情">発情</option>
						<option <c:if test="${extCon.onayami_gaiyo == '食事'}">selected</c:if> value="食事">食事</option>
						<option <c:if test="${extCon.onayami_gaiyo == '毛引き'}">selected</c:if> value="毛引き">毛引き</option>
						<option <c:if test="${extCon.onayami_gaiyo == 'ﾄﾚｰﾆﾝｸﾞ'}">selected</c:if> value="ﾄﾚｰﾆﾝｸﾞ">ﾄﾚｰﾆﾝｸﾞ</option>
						<option <c:if test="${extCon.onayami_gaiyo == 'ﾌｫｰｼﾞﾝｸﾞ'}">selected</c:if> value="ﾌｫｰｼﾞﾝｸﾞ">ﾌｫｰｼﾞﾝｸﾞ</option>
						<option <c:if test="${extCon.onayami_gaiyo == '問題行動'}">selected</c:if> value="問題行動">問題行動</option>
						<option <c:if test="${extCon.onayami_gaiyo == 'その他'}">selected</c:if> value="その他">その他</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-11">
				<div class="input-group">
					<span class="input-group-text">相談内容</span>
					<input type="text" style="width: 300px;" class="form-control" id="sodan_naiyo" name="sodan_naiyo" value="${extCon.sodan_naiyo}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-11 col-sm-11 col-md-4 col-lg-5">
				<div class="input-group">
					<span class="input-group-text">担当者名</span>
					<input class="form-control" list="staff_name_list" id="staff_name" name="staff_name" value="${extCon.staff_name}" placeholder="Type to search..." >
					<datalist id="staff_name_list">
						<c:forEach var="staffname" items="${syokuin}">
							<option value="${staffname}">
						</c:forEach>
					</datalist>
				</div>
			</div>
			<div class="p-1 mx-sm-1 mx-md-0 col-11 col-sm-11 col-md-7 col-lg-6">
				<div class="input-group">
					<span class="input-group-text">ﾃﾞｰﾀ更新日</span>
					<input class="form-control" type="date" id="upd_date_from" name="upd_date_from" value="${extCon.upd_date_from}">
					<span class="pt-2 px-1">～</span>
					<input class="form-control" type="date" id="upd_date_to" name="upd_date_to" value="${extCon.upd_date_to}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="p-1 mx-1 col-6 col-sm-5 col-md-3 col-lg-3">
				<div class="input-group">
					<span class="input-group-text">画像有無</span>
					<select style="width: 80px;" id="pdf_umu" name="pdf_umu" class="form-select">
						<option <c:if test="${empty extCon.pdf_umu}">selected</c:if> value=""></option>
						<option <c:if test="${extCon.pdf_umu == '有'}">selected</c:if> value="有">有</option>
						<option <c:if test="${extCon.pdf_umu == '無'}">selected</c:if> value="無">無</option>
					</select>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container-fluid py-2">
		<h5 class="row mt-1 ms-1" style="display: inline-block;">飼育相談一覧</h5>
		<span style="font-weight: bold;"> 　【 </span>
		<input readonly id="sel_cnt" name="sel_cnt" style="width: 40px; text-align:center; display: inline-block;" type="text" class="form-control form-control-sm readonly_color" value="${extCon.sel_cnt}">
		<span style="font-weight: bold;"> 件 / 全 </span>
		<input readonly id="all_cnt" name="all_cnt" style="width: 40px; text-align:center; display: inline-block;" type="text" class="form-control form-control-sm readonly_color" value="${extCon.all_cnt}">
		<span style="font-weight: bold;"> 件中】</span>
		<span style="font-weight: bold;"> (表示：</span>
		<c:choose>
			<c:when test="${extCon.ichiran_kensu_flg == 'readonly'}">
				<select readonly id="ichiran_kensu" name="ichiran_kensu" style="width: 85px; display: inline-block;" class="form-select form-select-sm">
					<option <c:choose><c:when test="${extCon.ichiran_kensu == 10}">selected</c:when><c:otherwise>disabled</c:otherwise></c:choose> value="10">10件</option>
					<option <c:choose><c:when test="${extCon.ichiran_kensu == 50}">selected</c:when><c:otherwise>disabled</c:otherwise></c:choose> value="50">50件</option>
					<option <c:choose><c:when test="${extCon.ichiran_kensu == 100}">selected</c:when><c:otherwise>disabled</c:otherwise></c:choose> value="100">100件</option>
				</select>
			</c:when>
			<c:otherwise>
				<select id="ichiran_kensu" name="ichiran_kensu" style="width: 85px; display: inline-block;" class="form-select form-select-sm">
					<option <c:if test="${extCon.ichiran_kensu == 10}">selected</c:if> value="10">10件</option>
					<option <c:if test="${extCon.ichiran_kensu == 50}">selected</c:if> value="50">50件</option>
					<option <c:if test="${extCon.ichiran_kensu == 100}">selected</c:if> value="100">100件</option>
				</select>
			</c:otherwise>
		</c:choose>	
		<span style="font-weight: bold;"> </span>
		<input readonly  id="start_point" name="start_point" style="width: 40px; text-align:center; display: inline-block;" type="text" class="form-control form-control-sm readonly_color" value="${extCon.start_point}">
		<span style="font-weight: bold;"> 件目 ～ </span>
		<input readonly  id="end_point" name="end_point" style="width: 40px; text-align:center; display: inline-block;" type="text" class="form-control form-control-sm readonly_color" value="${extCon.end_point}">
		<span style="font-weight: bold;"> 件目)</span>
		<div class="btn-group btn-group-sm" role="group">
			<button <c:out value="${extCon.back_page_flg}" /> id="bak_page-btn" type="button" class="btn btn-outline-primary" onclick="fncbakpage()"><i class='bx bxs-left-arrow'></i></button>
			<input readonly id="now_page" name="now_page" style="width: 40px; text-align:center; display: inline-block;" type="text" class="form-control form-control-sm readonly_color" value="${extCon.now_page}">
			<button <c:out value="${extCon.next_page_flg}" /> id="nxt_page-btn" type="button" class="btn btn-outline-primary" onclick="fncnxtpage()"><i class='bx bxs-right-arrow'></i></button>
		</div>
		<div class="table-responsive">
			<table id="data_list_tbl" class="table table-striped table-sm">
				<thead>
					<tr>
						<th>索引</th>
						<th>飼い主名</th>
						<th>相談日</th>
						<th>鳥の種類</th>
						<th>鳥の名前</th>
						<th>お悩み概要</th>
						<th>相談内容</th>
						<th>担当者名</th>
						<th>ﾃﾞｰﾀ更新日</th>
						<th>画像</th>
						<th class="text-center" colspan="4">操作</th>
					</tr>
				</thead>
				<tbody>
					<% for (int i = 0; i < sodanList.size(); i++) { %>
						<tr>
							<td><%=sodanList.get(i).getKainushiIdx()%></td>
							<td><%=sodanList.get(i).getKainushiName()%></td>
							<td><%=sodanList.get(i).getSodanDate()%></td>
							<td><%=sodanList.get(i).getBirdType()%></td>
							<td><%=sodanList.get(i).getBirdName()%></td>
							<td>
								<% if (sodanList.get(i).getCbxKaikata() == 1){%>飼い方 <%}%>
								<% if (sodanList.get(i).getCbxByouki() == 1){%>病気 <%}%>
								<% if (sodanList.get(i).getCbxHatsujyou() == 1){%>発情 <%}%>
								<% if (sodanList.get(i).getCbxSyokuji() == 1){%>食事 <%}%>
								<% if (sodanList.get(i).getCbxKebiki() == 1){%>毛引き <%}%>
								<% if (sodanList.get(i).getCbxTorening() == 1){%>ﾄﾚｰﾆﾝｸﾞ <%}%>
								<% if (sodanList.get(i).getCbxFojing() == 1){%>ﾌｫｰｼﾞﾝｸﾞ<%}%>
								<% if (sodanList.get(i).getCbxMondaikodo() == 1){%>問題行動 <%}%>
								<% if (sodanList.get(i).getCbxSonota() == 1){%>その他<%}%>
							</td>
							<td>
								<%	String sodonNaiyo = sodanList.get(i).getSodonNaiyo();
									if(sodonNaiyo.length() >= 10){
										sodonNaiyo = sodonNaiyo.substring(0, 10) + "…";
								}%>
								<%=sodonNaiyo%>
							</td>
							<td><%=sodanList.get(i).getStaffName()%></td>
							<%
							Date date = new Date(sodanList.get(i).getUpdateTime().getTime());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							%>
							<td><%=sdf.format(date)%></td>
							<% 
							String str = sodanList.get(i).getUpFilePath();
							if (StringUtils.isEmpty(str) || StringUtils.isBlank(str)) { %>
								<td> ― </td>
							<% } else { %>
								<td>
									<a href="<%=sodanList.get(i).getUpFilePath()%>" target="_blank">🔗</a>
								</td>
							<% } %>
							
							<td><a href="SodanServlet?vew_action=dtl&sodan_id=<%=sodanList.get(i).getSodanId()%>">詳細 </a></td>
							<td><a href="SodanServlet?vew_action=upd&sodan_id=<%=sodanList.get(i).getSodanId()%>">編集 </a></td>
							<td><a href="SodanServlet?vew_action=del&sodan_id=<%=sodanList.get(i).getSodanId()%>">削除 </a></td>
						</tr>
					<% } %>
				</tbody>
			</table>
		</div>
	</div>
</form>
<!-- Option 2: Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>