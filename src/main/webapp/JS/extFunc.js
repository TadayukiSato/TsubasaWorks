function clearFormAll() {

	document.getElementById("post_action").value = "";

	for (var i = 0; i < document.forms.length; ++i) {
		clearForm(document.forms[i]);
	}
	
	document.getElementById("kainushi_idx").selectedIndex = 0;
	document.getElementById("kainushi_name").value = "";
	document.getElementById("sodan_date_from").value = "";
	document.getElementById("sodan_date_to").value = "";
	document.getElementById("bird_type").value = "";
	document.getElementById("bird_name").value = "";
	document.getElementById("onayami_gaiyo").selectedIndex = 0;
	document.getElementById("sodan_naiyo").value = "";
	document.getElementById("staff_name").value = "";
	document.getElementById("upd_date_from").value = "";
	document.getElementById("upd_date_to").value = "";
	document.getElementById("pdf_umu").selectedIndex = 0;
	let tbl = document.getElementById("data_list_tbl");
	let rw = tbl.rows.length;
	while (rw - 1 > 0) {
		tbl.deleteRow(rw - 1);
		rw = tbl.rows.length;
	}

	document.getElementById("sel_cnt").value = "0";
	document.getElementById("all_cnt").value = "0";
	//「表示一覧件数」はクリアしない様に有効無効化で制御するように変更
	//document.getElementById("ichiran_kensu").value = "10";
	document.getElementById("ichiran_kensu").readOnly = false;
	
	document.getElementById("start_point").value = "0";
	document.getElementById("end_point").value = "0";
	document.getElementById("bak_page-btn").disabled = true;
	document.getElementById("now_page").value = "0";
	document.getElementById("nxt_page-btn").disabled = true;
	document.getElementById("last_page").value = "0";
	
	// getpost()に飛んだ時にどこから飛んだか判るように
	// Formのhidden項目であるpost_actionに保存する
	document.getElementById("post_action").value = "frmClear";
	//Formのsubmitを実行（POST送信）
	document.querySelector('#frm').submit();
}

function clearForm(form) {
	for (var i = 0; i < form.elements.length; ++i) {
		clearElement(form.elements[i]);
	}
}

function clearElement(element) {
	switch (element.type) {
		case "hidden":
		case "submit":
		case "reset":
		case "button":
		case "image":
			return;
		case "file":
			return;
		case "text":
		case "password":
		case "textarea":
			element.value = "";
			return;
		case "checkbox":
		case "radio":
			element.checked = false;
			return;
		case "select-one":
		case "select-multiple":
			//element.selectedIndex = 0;
			return;
		default:
	}
}

function fncbakpage() {
	
	//判定に必要なFormの現在ページを取得
	var nowPage = parseInt(document.getElementById("now_page").value);
	
	if (nowPage > 1) {
		
		var startPoint;
		var endPoint;
		
		//計算に必要な値を取得
		var ichiranKensu = parseInt(document.getElementById("ichiran_kensu").value);
		var selCnt = parseInt(document.getElementById("sel_cnt").value);
		
		// getpost()に飛んだ時にどこから飛んだか判るように
		// Formのhidden項目であるpost_actionに保存する
		document.getElementById("post_action").value = "bakPage";
		
		nowPage = nowPage - 1;	//ページ数 - 1
		// 再計算した結果をFormのnow_pageに戻す
		document.getElementById("now_page").value = nowPage.toString();
		
		//前ページ処理後のデータ表示開始位置を再計算
		startPoint = (nowPage * ichiranKensu) - ichiranKensu + 1;
		// 再計算した結果をFormのstart_pointに戻す
		document.getElementById("start_point").value = startPoint.toString();
		
		//前ページ処理後のデータ表示終了位置を再計算
		endPoint = nowPage * ichiranKensu;
		if (endPoint > selCnt){	//計算の結果抽出数を超えたら抽出数に戻す
			endPoint = selCnt;
		}
		// 再計算した結果をFormのend_pointに戻す
		document.getElementById("end_point").value = endPoint.toString();
		
		//Formのsubmitを実行（POST送信）
		document.querySelector('#frm').submit();
	
	}
}

function fncnxtpage() {
	
	//判定に必要なFormの現在ページ最大ページを取得
	var lastPage = parseInt(document.getElementById("last_page").value);
	var nowPage = parseInt(document.getElementById("now_page").value);
	
	//現在ページが最大ページを超えたら処理しない(ボタン押せないはず)
	if (nowPage < lastPage) {

		var startPoint;
		var endPoint;
		
		//計算に必要な値を取得
		var ichiranKensu = parseInt(document.getElementById("ichiran_kensu").value);
		var selCnt = parseInt(document.getElementById("sel_cnt").value);
		
		// getpost()に飛んだ時にどこから飛んだか判るように
		// Formのhidden項目であるpost_actionに保存する
		document.getElementById("post_action").value = "nxtPage";
		
		nowPage = nowPage + 1;	//ページ数 + 1
		// 再計算した結果をFormのnow_pageに戻す
		document.getElementById("now_page").value = nowPage.toString();
		
		//前ページ処理後のデータ表示開始位置を再計算
		startPoint = (nowPage * ichiranKensu) - ichiranKensu + 1;
		// 再計算した結果をFormのstart_pointに戻す
		document.getElementById("start_point").value = startPoint.toString();
		
		//前ページ処理後のデータ表示終了位置を再計算
		endPoint = nowPage * ichiranKensu;
		if (endPoint > selCnt){	//計算の結果抽出数を超えたら抽出数に戻す
			endPoint = selCnt;
		}
		// 再計算した結果をFormのend_pointに戻す
		document.getElementById("end_point").value = endPoint.toString();
		
		//Formのsubmitを実行（POST送信）
		document.querySelector('#frm').submit();
		
	}

}