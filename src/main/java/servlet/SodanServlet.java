/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
 * 飼育相談サーブレット（コントローラー）
 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.AlertMsg;
import model.CreateSodanLogic;
import model.DbAction;
import model.DeleteSodanLogic;
import model.ExtCon;
import model.GetSodanCntAllLogic;
import model.GetSodanCntSelLogic;
import model.GetSodanListLogic;
import model.GetSodanLogic;
import model.Sodan;
import model.UpdateSodanLogic;

// 設定内容等の定義
final class Config {
	// アップロードしたPDFファイルを保存するディレクトリの絶対パス
	public static final String STORAGE = 
			"G:\\マイドライブ\\workspace\\TsubasaWorks\\src\\main\\webapp\\images\\";
	// 画像保存のフォルダ名
	public static final String F_NM = "images/";
}
@WebServlet("/SodanServlet")
@MultipartConfig()	//画像ファイルアップロード対応
public class SodanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*
* 【doGet】
*	メニュー画面(mainMenu.jsp)から飼育相談検索画面(sodanSrch.jsp)にフォワード
*	【飼育相談システム】ボタン押下時：vew_action = "init" & sodan_id = ""
*
*	飼育相談検索画面(sodanSrch.jsp)のから相談操作画面(sodanOpe.jsp)にフォワード
*	【新規相談】ボタン押下時：vew_action = "new" & sodan_id=""
*
*  飼育相談検索画面(sodanSrch.jsp)の相談一覧の各データから飼育相談操作画面(sodanOpe.jsp)へフォワード
* 	【詳細】ボタン押下時：vew_action = "dtl" & sodan_id = "各データのsodan_id(主キー)"
* 	【編集】ボタン押下時：vew_action = "upd" & sodan_id = "各データのsodan_id(主キー)"
* 	【削除】ボタン押下時：vew_action = "del" & sodan_id = "各データのsodan_id(主キー)"
*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// リクエストパラメータの文字コード設定
		request.setCharacterEncoding("UTF-8");
		
		// メニュー画面からのGETによるリクエストパラメータの取得
		String vew_action = request.getParameter("vew_action");		// 操作アクション 
		
		//遷移先の画面判定
		if (vew_action.equals("init")) {
			//メニューからの初期遷移時
			
			// javabeans からForm項目インスタンス生成
			ExtCon extCon = new ExtCon();
			// インスタンスにFormの各抽出条件項目を初期化として空白を設定
			extCon.setPost_action("");
			extCon.setKainushi_idx("");
			extCon.setKainushi_name("");
			extCon.setSodan_date_from("");
			extCon.setSodan_date_to("");
			extCon.setBird_type("");
			extCon.setBird_name("");
			extCon.setOnayami_gaiyo("");
			extCon.setSodan_naiyo("");
			extCon.setStaff_name("");
			extCon.setUpd_date_from("");
			extCon.setUpd_date_to("");
			extCon.setPdf_umu("");
			
			//初期表示時は検索実行しない
			int selCnt = 0;
			extCon.setSel_cnt(String.valueOf(selCnt));
			
			// 相談リスト全データ件数を取得しインスタンス設定
			GetSodanCntAllLogic getSodanCntAllLogic = new GetSodanCntAllLogic();
			int allCnt  = getSodanCntAllLogic.execute();
			extCon.setAll_cnt(String.valueOf(allCnt));
			
			// 一覧表示件数の初期値「10」をインスタンス設定
			int ichiranKensu =10;
			extCon.setIchiran_kensu(String.valueOf(ichiranKensu));
			
			// データ表示開始位置の初期値設定しインスタンスに設定
			int startPoint = 0;
			extCon.setStart_point(String.valueOf(startPoint));
			
			// データ表示終了位置の初期値設定しインスタンスに設定
			int endPoint = 0;
			extCon.setEnd_point(String.valueOf(endPoint));
			
			// 現在ページの初期値設定
			// 全データ件数が0件なら0を、それ以外は1をインスタンスに設定
			int nowPage;
			if (allCnt == 0) {
				nowPage = 0;
			} else {
				nowPage = 1;
			}
			extCon.setNow_page(String.valueOf(nowPage));
			
			// 空の飼育相談リスト生成し、リクエストスコープに保存
			List<Sodan> sodanList = new ArrayList<Sodan>();
			request.setAttribute("sodanList", sodanList);
			
			// 前頁フラグ(前頁ボタンの有効化･無効化)を設定しインスタンスに設定
			String backPageFlg= "disabled";;
			extCon.setBack_page_flg(backPageFlg);
			
			//最終ページ数の初期値設定
			// 全データ件数が0件なら0を、それ以外は1をインスタンスに設定
			int lastPage;
			if (allCnt == 0) {
				lastPage = 0;
			} else {
				lastPage = 1;
			}
			extCon.setLast_page(String.valueOf(lastPage));
			
			// 次頁フラ(次頁ボタンの有効化･無効化)グを設定しインスタンスに設定
			String nextPageFlg= "disabled";
			extCon.setNext_page_flg(nextPageFlg);
			
			// 表示一覧件数制御フラグを(表示件数の選択可否を制御)設定しインスタンスに設定
			String ichiran_kensu_flg = "";
			extCon.setIchiran_kensu_flg(ichiran_kensu_flg);
			
			//インスタンスをリクエストスコープに保存
			request.setAttribute("extCon",extCon);
			
			// 相談検索画面(sodanSrch.jsp)にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sodanSrch.jsp");
			dispatcher.forward(request, response);
		
		} else if(vew_action.equals("new")) { 
			// 検索画面の「新規相談」ボタン押下時
			
			// 空の相談インスタンスを生成しリクエストスコープに保存
			Sodan sodan = new Sodan();
			request.setAttribute("sodan", sodan);
			
			//vew_actionをリクエストスコープのDbActionに保存し遷移先のJSPファイルへ引き渡す
			DbAction dbAction = new DbAction(vew_action); //内容"new"をそのまま渡す
			request.setAttribute("dbAction", dbAction);
			
			// 相談の新規追加として操作画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sodanOpe.jsp");
			dispatcher.forward(request, response);
			
		} else { 
			//相談一覧の各データ「詳細」「編集」「削除」押下時に操作画面に遷移
			
			String sodan_id = request.getParameter("sodan_id");	// 相談ID（主キー）
			
			// String型のリクエストパラメータのsodan_idをint型として取得し直しsodanIdに格納
			int sodanId = Integer.parseInt(sodan_id);
			
			// ユニークキー(sodanId)と一致する飼育相談レコードを取得して、リクエストスコープに保存
			GetSodanLogic getSodanLogic = new GetSodanLogic();
			Sodan sodan = getSodanLogic.execute(sodanId);
			request.setAttribute("sodan", sodan);
			
			//リクエストスコープにDbActionを保存し遷移先のJSPファイルへ引き渡す
			DbAction dbAction = new DbAction(vew_action); //リクエストパラメータの内容をそのまま渡す
			request.setAttribute("dbAction", dbAction);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sodanOpe.jsp");
			dispatcher.forward(request, response);
			
		}
	}

/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*
*【doPost】
*  飼育相談検索画面(sodanSrch.jsp)から飼育相談サーブレット(SodanServlet.java)にsubmit(POST送信)
*  	【検索ボタン押下時】	：fromの<input hidden>項目 post_action = ""
*	【前頁(◀)ボタン押下時】	：fromの<input hidden>項目 post_action = "bakPage"
*	【次頁(▶)ボタン押下時】	：fromの<input hidden>項目 post_action = "nxtPage"
*	【クリアボタン押下時】	：fromの<input hidden>項目 post_action = "frmClear"
*
*  
*  飼育相談操作画面(sodanOpe.jsp)から飼育相談サーブレット(SodanServlet.java)にsubmit(POST送信)
*  	【登録ボタン押下時】：<input hidden id="post_action" value = "new">
*  							：<input hidden id="sodan_id" value = "リクエストスコープのsodan_id">
*  	【更新ボタン押下時】：<input hidden id="post_action" value = "upd">
*  							：<input hidden id="sodan_id" value = "リクエストスコープのsodan_id">
*  	【削除ボタン押下時】：<input hidden id="post_action" value = "del">
*  							：<input hidden id="sodan_id" value = "リクエストスコープのsodan_id">
*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// リクエストパラメータの取得の文字コード設定
		request.setCharacterEncoding("UTF-8");
		// リクエストパラメータ(form内容)格納変数の定義と初期化
										// 検索		: 操作
		String post_action = "";		// hidden	: hidden
		String last_page = "";			// hidden	: ×
		String up_file_path = "";		//	×		: 〇
		String sodan_date = "";			//	×		: 〇
		String sodan_date_from = "";	//	〇		: ×
		String sodan_date_to = "";		//	〇		: ×
		String sodan_time_from = "";	//	×		: 〇
		String sodan_time_to = "";		//	×		: 〇
		String kainushi_idx = "";		//	〇		: 〇
		String kainushi_name = "";		//	〇		: 〇
		String home_area = "";			//	〇		: ×
		String clinic_name = "";		//	×		: 〇
		String bird_type = "";			//	〇		: 〇
		String bird_sex = "";			//	×		: 〇
		String bird_name = "";			//	〇		: 〇
		String bird_age = "";			//	×		: 〇
		String basic_info = "";			//	×		: 〇
		String onayami_gaiyo = "";		//	〇		: ×
		String cbx_kaikata = "";		//	×		: 〇
		String cbx_byouki = "";			//	×		: 〇
		String cbx_hatsujyou = "";		//	×		: 〇
		String cbx_syokuji = "";		//	×		: 〇
		String cbx_kebiki = "";			//	×		: 〇
		String cbx_torening = "";		//	×		: 〇
		String cbx_fojing = "";			//	×		: 〇
		String cbx_mondai_kodo = "";	//	×		: 〇
		String cbx_sonota = "";			//	×		: 〇
		String sodan_naiyo = "";		//	〇		: 〇
		String staff_name = "";			//	〇		: 〇
		String upd_date_from = "";		//	〇		: ×
		String upd_date_to = "";		//	〇		: ×
		String pdf_umu = "";			//	〇		: ×
		String sel_cnt = "";			// readonly	: －
		String ichiran_kensu = "";		//	〇		: ×
		String start_point = "";		// readonly	: －
		String end_point = "";			// readonly	: －
		String now_page = "";			// readonly	: －
		String sodan_id = "";			// ×		: hidden
		
		// リクエストパラメータの取得(検索・前頁・次頁・追加・更新・削除の全処理共通)
		post_action = request.getParameter("post_action");
		kainushi_idx = request.getParameter("kainushi_idx");
		kainushi_name = request.getParameter("kainushi_name");
		bird_type = request.getParameter("bird_type");
		bird_name = request.getParameter("bird_name");
		sodan_naiyo = request.getParameter("sodan_naiyo");
		staff_name = request.getParameter("staff_name");
		
		if (post_action.equals("new") || 
			post_action.equals("upd") || 
			post_action.equals("del")) {
			
			// リクエストパラメータの取得(追加・更新・削除の各処理)
			up_file_path = request.getParameter("up_file_path");
			sodan_date = request.getParameter("sodan_date");
			sodan_time_from = request.getParameter("sodan_time_from");
			sodan_time_to = request.getParameter("sodan_time_to");
			kainushi_idx = request.getParameter("kainushi_idx");
			home_area = request.getParameter("home_area");
			clinic_name = request.getParameter("clinic_name");
			bird_sex = request.getParameter("bird_sex");
			bird_age = request.getParameter("bird_age");
			basic_info = request.getParameter("basic_info");
			cbx_kaikata = request.getParameter("cbx_kaikata");
			cbx_byouki = request.getParameter("cbx_byouki");
			cbx_hatsujyou = request.getParameter("cbx_hatsujyou");
			cbx_syokuji = request.getParameter("cbx_syokuji");
			cbx_kebiki = request.getParameter("cbx_kebiki");
			cbx_torening = request.getParameter("cbx_torening");
			cbx_fojing = request.getParameter("cbx_fojing");
			cbx_mondai_kodo = request.getParameter("cbx_mondai_kodo");
			cbx_sonota = request.getParameter("cbx_sonota");
			
			if (post_action.equals("new")){	//　データベース追加処理
				
				// 飼育相談を作成して新規追加
				Sodan sodan = new Sodan();
				sodan.setKainushiIdx(kainushi_idx);
				sodan.setKainushiName(kainushi_name);
				sodan.setSodanDate(Date.valueOf(sodan_date));
				
				DateTimeFormatter fmt_Hms = DateTimeFormatter.ofPattern("HH:mm:ss");
				DateTimeFormatter fmt_Hm = DateTimeFormatter.ofPattern("HH:mm");
				
				LocalTime lt_sodan_time_from;
				if (Objects.isNull(sodan_time_from) || StringUtils.isBlank(sodan_time_from)) { 
					sodan.setSodanTimeFrom(null);
				} else if (sodan_time_from.length() == 5) {
					lt_sodan_time_from = LocalTime.parse(sodan_time_from, fmt_Hm);
					sodan.setSodanTimeFrom(Time.valueOf(lt_sodan_time_from));
				} else if (sodan_time_from.length() == 8) {
					lt_sodan_time_from = LocalTime.parse(sodan_time_from, fmt_Hms);
					sodan.setSodanTimeFrom(Time.valueOf(lt_sodan_time_from));
				}
				
				LocalTime lt_sodan_time_to;
				if (Objects.isNull(sodan_time_to) || StringUtils.isBlank(sodan_time_to)) { 
					sodan.setSodanTimeTo(null);
				} else if (sodan_time_to.length() == 5) {
					lt_sodan_time_to = LocalTime.parse(sodan_time_to, fmt_Hm);
					sodan.setSodanTimeTo(Time.valueOf(lt_sodan_time_to));
				} else if (sodan_time_to.length() == 8) {
					lt_sodan_time_to = LocalTime.parse(sodan_time_to, fmt_Hms);
					sodan.setSodanTimeTo(Time.valueOf(lt_sodan_time_to));
				}
				
				sodan.setHomeArea(home_area);
				sodan.setClinicName(clinic_name);
				sodan.setBirdType(bird_type);
				sodan.setBirdSex(bird_sex);
				sodan.setBirdAge(bird_age);
				sodan.setBirdName(bird_name);
				sodan.setBasicInfo(basic_info);
				sodan.setSodonNaiyo(sodan_naiyo);
				
				if (Objects.isNull(cbx_kaikata) || StringUtils.isBlank(cbx_kaikata)) { 
					sodan.setCbxKaikata((byte)0);
				} else {
					sodan.setCbxKaikata(Byte.valueOf(cbx_kaikata));
				}
				
				if (Objects.isNull(cbx_byouki) || StringUtils.isBlank(cbx_byouki)) { 
					sodan.setCbxByouki((byte)0);
				} else {
					sodan.setCbxByouki(Byte.valueOf(cbx_byouki));
				}
				
				if (Objects.isNull(cbx_hatsujyou) || StringUtils.isBlank(cbx_hatsujyou)) { 
					sodan.setCbxHatsujyou((byte)0);
				} else {
					sodan.setCbxHatsujyou(Byte.valueOf(cbx_hatsujyou));
				}
				
				if (Objects.isNull(cbx_syokuji) || StringUtils.isBlank(cbx_syokuji)) { 
					sodan.setCbxSyokuji((byte)0);
				} else {
					sodan.setCbxSyokuji(Byte.valueOf(cbx_syokuji));
				}
				
				if (Objects.isNull(cbx_kebiki) || StringUtils.isBlank(cbx_kebiki)) { 
					sodan.setCbxKebiki((byte)0);
				} else {
					sodan.setCbxKebiki(Byte.valueOf(cbx_kebiki));
				}
				
				if (Objects.isNull(cbx_torening) || StringUtils.isBlank(cbx_torening)) { 
					sodan.setCbxTorening((byte)0);
				} else {
					sodan.setCbxTorening(Byte.valueOf(cbx_torening));
				}
				
				if (Objects.isNull(cbx_fojing) || StringUtils.isBlank(cbx_fojing)) { 
					sodan.setCbxFojing((byte)0);
				} else {
					sodan.setCbxFojing(Byte.valueOf(cbx_fojing));
				}
				
				if (Objects.isNull(cbx_mondai_kodo) || StringUtils.isBlank(cbx_mondai_kodo)) { 
					sodan.setCbxMondaikodo((byte)0);
				} else {
					sodan.setCbxMondaikodo(Byte.valueOf(cbx_mondai_kodo));
				}
				
				if (Objects.isNull(cbx_sonota) || StringUtils.isBlank(cbx_sonota)) { 
					sodan.setCbxSonota((byte)0);
				} else {
					sodan.setCbxSonota(Byte.valueOf(cbx_sonota));
				}
				
				sodan.setStaffName(staff_name);
				
				Part part = request.getPart("up_file_path");
				String fileName = getFielName(part);
				
				if (Objects.isNull(fileName) || StringUtils.isBlank(fileName)){
					sodan.setUpFilePath("");
				} else {
					// ファイルアップロードのファイル名用の現在日時を取得
					LocalDateTime dt = LocalDateTime.now();
					String up_dt = "up_" + dt.format(DateTimeFormatter.ofPattern("YYMMddHHmmssSSS_"));
					
					Path filePath = Paths.get(Config.STORAGE + File.separator + up_dt + fileName);
					
					// アップしたファイルを取得して、保存
					InputStream in = part.getInputStream();
					Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
					sodan.setUpFilePath(Config.F_NM + up_dt + fileName);
				}
				
				request.setAttribute("sodan", sodan);
				
				CreateSodanLogic createSodanLogic = new CreateSodanLogic();
				boolean bln = createSodanLogic.execute(sodan);
				
				//データ追加処理の結果によってアラートメッセージを変更する。
				AlertMsg alertMsg;
				if (bln) {
					alertMsg = new AlertMsg("NewOk");
				} else {
					alertMsg = new AlertMsg("NewNg");
				}
				request.setAttribute("alertMsg", alertMsg);
				
			} else if (post_action.equals("upd")){	//　データベース更新処理
				
				// リクエストパラメータの取得(更新処理)
				sodan_id = request.getParameter("sodan_id");	// 相談ID（主キー）
				
				// String型のリクエストパラメータのsodan_idをint型として取得し直しsodanIdに格納
				int sodanId = Integer.parseInt(sodan_id);
				
				// ユニークキー(sodanId)と一致する飼育相談レコードを取得して、リクエストスコープに保存
				GetSodanLogic getSodanLogic = new GetSodanLogic();
				Sodan sodan = getSodanLogic.execute(sodanId);
				
				sodan.setKainushiIdx(kainushi_idx);
				sodan.setKainushiName(kainushi_name);
				
				DateTimeFormatter fmt_Hms = DateTimeFormatter.ofPattern("HH:mm:ss");
				DateTimeFormatter fmt_Hm = DateTimeFormatter.ofPattern("HH:mm");
				
				LocalTime lt_sodan_time_from;
				if (Objects.isNull(sodan_time_from) || StringUtils.isBlank(sodan_time_from)) { 
					sodan.setSodanTimeFrom(null);
				} else if (sodan_time_from.length() == 5) {
					lt_sodan_time_from = LocalTime.parse(sodan_time_from, fmt_Hm);
					sodan.setSodanTimeFrom(Time.valueOf(lt_sodan_time_from));
				} else if (sodan_time_from.length() == 8) {
					lt_sodan_time_from = LocalTime.parse(sodan_time_from, fmt_Hms);
					sodan.setSodanTimeFrom(Time.valueOf(lt_sodan_time_from));
				}
				
				LocalTime lt_sodan_time_to;
				if (Objects.isNull(sodan_time_to) || StringUtils.isBlank(sodan_time_to)) { 
					sodan.setSodanTimeTo(null);
				} else if (sodan_time_to.length() == 5) {
					lt_sodan_time_to = LocalTime.parse(sodan_time_to, fmt_Hm);
					sodan.setSodanTimeTo(Time.valueOf(lt_sodan_time_to));
				} else if (sodan_time_to.length() == 8) {
					lt_sodan_time_to = LocalTime.parse(sodan_time_to, fmt_Hms);
					sodan.setSodanTimeTo(Time.valueOf(lt_sodan_time_to));
				}
				
				sodan.setHomeArea(home_area);
				sodan.setClinicName(clinic_name);
				sodan.setBirdType(bird_type);
				sodan.setBirdSex(bird_sex);
				sodan.setBirdAge(bird_age);
				sodan.setBirdName(bird_name);
				sodan.setBasicInfo(basic_info);
				sodan.setSodonNaiyo(sodan_naiyo);
				
				if (Objects.isNull(cbx_kaikata) || StringUtils.isBlank(cbx_kaikata)) { 
					sodan.setCbxKaikata((byte)0);
				} else {
					sodan.setCbxKaikata(Byte.valueOf(cbx_kaikata));
				}
				
				if (Objects.isNull(cbx_byouki) || StringUtils.isBlank(cbx_byouki)) { 
					sodan.setCbxByouki((byte)0);
				} else {
					sodan.setCbxByouki(Byte.valueOf(cbx_byouki));
				}
				
				if (Objects.isNull(cbx_hatsujyou) || StringUtils.isBlank(cbx_hatsujyou)) { 
					sodan.setCbxHatsujyou((byte)0);
				} else {
					sodan.setCbxHatsujyou(Byte.valueOf(cbx_hatsujyou));
				}
				
				if (Objects.isNull(cbx_syokuji) || StringUtils.isBlank(cbx_syokuji)) { 
					sodan.setCbxSyokuji((byte)0);
				} else {
					sodan.setCbxSyokuji(Byte.valueOf(cbx_syokuji));
				}
				
				if (Objects.isNull(cbx_kebiki) || StringUtils.isBlank(cbx_kebiki)) { 
					sodan.setCbxKebiki((byte)0);
				} else {
					sodan.setCbxKebiki(Byte.valueOf(cbx_kebiki));
				}
				
				if (Objects.isNull(cbx_torening) || StringUtils.isBlank(cbx_torening)) { 
					sodan.setCbxTorening((byte)0);
				} else {
					sodan.setCbxTorening(Byte.valueOf(cbx_torening));
				}
				
				if (Objects.isNull(cbx_fojing) || StringUtils.isBlank(cbx_fojing)) { 
					sodan.setCbxFojing((byte)0);
				} else {
					sodan.setCbxFojing(Byte.valueOf(cbx_fojing));
				}
				
				if (Objects.isNull(cbx_mondai_kodo) || StringUtils.isBlank(cbx_mondai_kodo)) { 
					sodan.setCbxMondaikodo((byte)0);
				} else {
					sodan.setCbxMondaikodo(Byte.valueOf(cbx_mondai_kodo));
				}
				
				if (Objects.isNull(cbx_sonota) || StringUtils.isBlank(cbx_sonota)) { 
					sodan.setCbxSonota((byte)0);
				} else {
					sodan.setCbxSonota(Byte.valueOf(cbx_sonota));
				}
				
				sodan.setStaffName(staff_name);
				
				Part part = request.getPart("up_file_path");
				String fileName = getFielName(part);
				
				if (Objects.isNull(fileName) || StringUtils.isBlank(fileName)){
					sodan.setUpFilePath(up_file_path);
				} else {
					// ファイルアップロードのファイル名用の現在日時を取得
					LocalDateTime dt = LocalDateTime.now();
					String up_dt = "up_" + dt.format(DateTimeFormatter.ofPattern("YYMMddHHmmssSSS_"));
					
					Path filePath = Paths.get(Config.STORAGE + File.separator + up_dt + fileName);
					
					// アップしたファイルを取得して、保存
					InputStream in = part.getInputStream();
					Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
					sodan.setUpFilePath(Config.F_NM + up_dt + fileName);
				}
				
				request.setAttribute("sodan", sodan);
				
				UpdateSodanLogic updateSodanLogic = new UpdateSodanLogic();
				boolean bln = updateSodanLogic.execute(sodan);
				
				//データ更新処理の結果によってアラートメッセージを変更する。
				AlertMsg alertMsg;
				if (bln) {
					alertMsg = new AlertMsg("UpdOk");
				} else {
					alertMsg = new AlertMsg("UpdNg");
				}
				request.setAttribute("alertMsg", alertMsg);
				
			} else if (post_action.equals("del")){	//　データベース削除処理
				
				if (Objects.isNull(up_file_path) || StringUtils.isBlank(up_file_path)){
				
				} else {
					
					String fileName = up_file_path.replace(Config.F_NM, "");
					
					Path filePath = Paths.get(Config.STORAGE + File.separator + fileName);
					
					Files.delete(filePath);
				}
				// リクエストパラメータの取得(削除処理)
				sodan_id = request.getParameter("sodan_id");	// 相談ID（主キー）
				// String型のリクエストパラメータのsodan_idをint型として取得し直しsodanIdに格納
				int sodanId = Integer.parseInt(sodan_id);
				DeleteSodanLogic deleteSodanLogic = new DeleteSodanLogic();
				boolean bln = deleteSodanLogic.execute(sodanId);
				
				//データ削除処理の結果によってアラートメッセージを変更する。
				AlertMsg alertMsg;
				if (bln) {
					alertMsg = new AlertMsg("DelOk");
				} else {
					alertMsg = new AlertMsg("DelNg");
				}
				request.setAttribute("alertMsg", alertMsg);
			
			}
			
			// javabeans からForm項目インスタンス生成
			ExtCon extCon = new ExtCon();
			// インスタンスにFormの各抽出条件項目は初期化で空白を設定
			extCon.setPost_action("");
			extCon.setKainushi_idx("");
			extCon.setKainushi_name("");
			extCon.setSodan_date_from("");
			extCon.setSodan_date_to("");
			extCon.setBird_type("");
			extCon.setBird_name("");
			extCon.setOnayami_gaiyo("");
			extCon.setSodan_naiyo("");
			extCon.setStaff_name("");
			extCon.setUpd_date_from("");
			extCon.setUpd_date_to("");
			extCon.setPdf_umu("");
			
			//初期表示時は検索実行しない
			int selCnt = 0;
			extCon.setSel_cnt(String.valueOf(selCnt));
			
			// 相談リスト全データ件数を取得しインスタンス設定
			GetSodanCntAllLogic getSodanCntAllLogic = new GetSodanCntAllLogic();
			int allCnt  = getSodanCntAllLogic.execute();
			extCon.setAll_cnt(String.valueOf(allCnt));
			
			// 一覧表示件数の初期値「10」をインスタンス設定
			int ichiranKensu =10;
			extCon.setIchiran_kensu(String.valueOf(ichiranKensu));
			
			// データ表示開始位置の初期値設定
			// 検索結果件数が0件なら0を、それ以外は1をインスタンスに設定
			int startPoint = 0;
			extCon.setStart_point(String.valueOf(startPoint));
			
			// データ表示終了位置の初期値設定
			int endPoint = 0;
			extCon.setEnd_point(String.valueOf(endPoint));
			
			// 現在ページの初期値設定
			// 全データ件数が0件なら0を、それ以外は1をインスタンスに設定
			int nowPage;
			if (allCnt == 0) {
				nowPage = 0;
			} else {
				nowPage = 1;
			}
			extCon.setNow_page(String.valueOf(nowPage));
			
			// 空の飼育相談リスト生成し、リクエストスコープに保存
			List<Sodan> sodanList = new ArrayList<Sodan>();
			request.setAttribute("sodanList", sodanList);
			
			// 前頁フラグを設定しインスタンスに設定
			String backPageFlg= "disabled";;
			extCon.setBack_page_flg(backPageFlg);
			
			//最終ページ数を計算しインスタンスに設定
			int lastPage;
			if (allCnt == 0) {
				lastPage = 0;
			} else {
				lastPage = 1;
			}
			extCon.setLast_page(String.valueOf(lastPage));
			
			// 次頁フラグを設定しインスタンスに設定
			String nextPageFlg= "disabled";
			extCon.setNext_page_flg(nextPageFlg);
			
			// 表示一覧件数制御フラグを設定しインスタンスに設定
			String ichiran_kensu_flg = "";
			extCon.setIchiran_kensu_flg(ichiran_kensu_flg);
			
			//インスタンスをリクエストスコープに保存
			request.setAttribute("extCon",extCon);
			
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sodanSrch.jsp");
			dispatcher.forward(request, response);
			
		} else {
			
			// リクエストパラメータの取得(検索・前頁・次頁・クリアの各処理共通)
			last_page = request.getParameter("last_page");
			sodan_date_from = request.getParameter("sodan_date_from");
			sodan_date_to = request.getParameter("sodan_date_to");
			onayami_gaiyo = request.getParameter("onayami_gaiyo");
			upd_date_from = request.getParameter("upd_date_from");
			upd_date_to = request.getParameter("upd_date_to");
			pdf_umu = request.getParameter("pdf_umu");
			sel_cnt = request.getParameter("sel_cnt");
			ichiran_kensu = request.getParameter("ichiran_kensu");
			start_point = request.getParameter("start_point");
			end_point = request.getParameter("end_point");
			now_page = request.getParameter("now_page");
			
			// javabeans からFormの抽出条件項目インスタンス生成
			ExtCon extCon = new ExtCon();
			// インスタンスにFormの各抽出条件項目(リクエストパラメータ)の内容を設定
			//extCon.setPost_action(post_action);
			extCon.setKainushi_idx(kainushi_idx);
			extCon.setKainushi_name(kainushi_name);
			extCon.setSodan_date_from(sodan_date_from);
			extCon.setSodan_date_to(sodan_date_to);
			extCon.setBird_type(bird_type);
			extCon.setBird_name(bird_name);
			extCon.setOnayami_gaiyo(onayami_gaiyo);
			extCon.setSodan_naiyo(sodan_naiyo);
			extCon.setStaff_name(staff_name);
			extCon.setUpd_date_from(upd_date_from);
			extCon.setUpd_date_to(upd_date_to);
			extCon.setPdf_umu(pdf_umu);
			
			// フォームに入力された条件で抽出条件を編集します！
			String jyoken = "";	//SQLに設定する条件設定用文字列の初期化
			// formの「索引」に条件が設定されたか判定
			if (Objects.isNull(kainushi_idx) || StringUtils.isBlank(kainushi_idx)) {
			} else {
				// SQLの条件を編集(完全一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				}
				jyoken = jyoken + "KAINUSHI_IDX = '" + kainushi_idx + "'";
			}
	        // formの「飼い主名」に条件が設定されたか判定
			if (Objects.isNull(kainushi_name) || StringUtils.isBlank(kainushi_name)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "KAINUSHI_NAME LIKE '%" + kainushi_name + "%'";
			}
			// formの「相談日(開始)」に条件が設定されたか判定
			if (Objects.isNull(sodan_date_from) || StringUtils.isBlank(sodan_date_from)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "DATE_FORMAT(SODAN_DATE, '%Y-%m-%d') >= DATE_FORMAT('" + sodan_date_from + "', '%Y-%m-%d')";
			}
			// formの「相談日(終了)」に条件が設定されたか判定
			if (Objects.isNull(sodan_date_to) || StringUtils.isBlank(sodan_date_to)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "DATE_FORMAT(SODAN_DATE, '%Y-%m-%d') <= DATE_FORMAT('" + sodan_date_to + "', '%Y-%m-%d')";
			}
			// formの「鳥の種類」に条件が設定されたか判定
			if (Objects.isNull(bird_type) || StringUtils.isBlank(bird_type)) {
			} else {
				// SQLの条件を編集(完全一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "BIRD_TYPE = '" + bird_type + "'";
			}
			// formの「鳥の名前」に条件が設定されたか判定
			if (Objects.isNull(bird_name) || StringUtils.isBlank(bird_name)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "BIRD_NAME LIKE '%" + bird_name + "%'";
			}
			// formの「お悩み概要」に条件が設定されたか判定
			if (Objects.isNull(onayami_gaiyo) || StringUtils.isBlank(onayami_gaiyo)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				switch (onayami_gaiyo) {
				case "飼い方":
					jyoken = jyoken + "CBX_KAIKATA = 1";
					break;
				case "病気":
					jyoken = jyoken + "CBX_BYOUKI = 1";
					break;
				case "発情":
					jyoken = jyoken + "CBX_HATSUJYOU = 1";
					break;
				case "食事":
					jyoken = jyoken + "CBX_SYOKUJI = 1";
					break;
				case "毛引き":
					jyoken = jyoken + "CBX_KEBIKI = 1";
					break;
				case "ﾄﾚｰﾆﾝｸﾞ":
					jyoken = jyoken + "CBX_TORENING = 1";
					break;
				case "ﾌｫｰｼﾞﾝｸﾞ":
					jyoken = jyoken + "CBX_FOJING = 1";
					break;
				case "問題行動":
					jyoken = jyoken + "CBX_MONDAIKODO = 1";
					break;
				case "その他":
					jyoken = jyoken + "CBX_SONOTA = 1";
					break;
				}
			}
			// formの「相談内容」に条件が設定されたか判定
			if (Objects.isNull(sodan_naiyo) || StringUtils.isBlank(sodan_naiyo)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "SODAN_NAIYO LIKE '%" + sodan_naiyo + "%'";
			}
			// formの「担当者名」に条件が設定されたか判定
			if (Objects.isNull(staff_name) || StringUtils.isBlank(staff_name)) {
			} else {
				// SQLの条件を編集(完全一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "STAFF_NAME = '" + staff_name + "'";
			}
			// formの「ﾃﾞｰﾀ更新日(開始)」に条件が設定されたか判定
			if (Objects.isNull(upd_date_from) || StringUtils.isBlank(upd_date_from)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "DATE_FORMAT(UPDATE_TIME, '%Y-%m-%d') >= DATE_FORMAT('" + upd_date_from + "', '%Y-%m-%d')";
			}
			
			// formの「ﾃﾞｰﾀ更新日(終了)」に条件が設定されたか判定
			if (Objects.isNull(upd_date_to) || StringUtils.isBlank(upd_date_to)) {
			} else {
				// SQLの条件を編集(部分一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				jyoken = jyoken + "DATE_FORMAT(UPDATE_TIME, '%Y-%m-%d') <= DATE_FORMAT('" + upd_date_to + "', '%Y-%m-%d')";
			}
			
			// formの「画像有無」に条件が設定されたか判定
			if (Objects.isNull(pdf_umu) || StringUtils.isBlank(pdf_umu)) {
			} else {
				// SQLの条件を編集(完全一致)
				if (jyoken == "") {
					jyoken = "WHERE ";
				} else {
					jyoken = jyoken + " AND ";
				}
				switch (pdf_umu) {
				case "有":
					jyoken = jyoken + "UP_FILE_PATH != ''";
					break;
				case "無":
					jyoken = jyoken + "UP_FILE_PATH = ''";
					break;
				}
			}
			
			// 相談リスト全データ件数を取得しインスタンス設定
			GetSodanCntAllLogic getSodanCntAllLogic = new GetSodanCntAllLogic();
			int allCnt  = getSodanCntAllLogic.execute();
			extCon.setAll_cnt(String.valueOf(allCnt));
			
			// formの「現在ページ」を再設定
			int nowPage;
			if (Objects.isNull(now_page) || StringUtils.isBlank(now_page)) {
				nowPage = 0;
			} else {
				if (Objects.isNull(post_action) || StringUtils.isBlank(post_action)) {
					nowPage = 1;
				} else {
					nowPage = Integer.parseInt(now_page);
				}
			}
			
			// formの「一覧表示件数」に条件が設定されたか判定
			int ichiranKensu =  Integer.parseInt(ichiran_kensu);
			extCon.setIchiran_kensu(String.valueOf(ichiranKensu));
			
			if (post_action.equals("frmClear")) {
				
				nowPage = 0;
				
				// 空の飼育相談リスト生成し、リクエストスコープに保存
				List<Sodan> sodanList = new ArrayList<Sodan>();
				request.setAttribute("sodanList", sodanList);
				
			} else {
				//抽出条件により飼育相談リストを取得して、リクエストスコープに保存
				GetSodanListLogic getSodanListLogic = new GetSodanListLogic();
				List<Sodan> sodanList = getSodanListLogic.execute(jyoken, nowPage, ichiranKensu);
				request.setAttribute("sodanList", sodanList);
			}
			
			// 検索結果件数を取得しインスタンスに設定
			int selCnt;
			if (Objects.isNull(post_action) || StringUtils.isBlank(post_action)) {
				GetSodanCntSelLogic getSodanCntSelLogic = new GetSodanCntSelLogic();
				selCnt  = getSodanCntSelLogic.execute(jyoken);
			} else {
				if (post_action.equals("frmClear")) {
					selCnt = 0;
				} else {
					selCnt = Integer.parseInt(sel_cnt);
				}
			}
			extCon.setSel_cnt(String.valueOf(selCnt));
			
			// formの「データ表示開始位置」をインスタンスに設定
			int startPoint;
			if (Objects.isNull(post_action) || StringUtils.isBlank(post_action)) {
				if (selCnt == 0) {
					startPoint = 0;	//抽出した結果０件なら「データ表示開始位置」も０に戻す
					nowPage = 0;	//抽出した結果０件なら「現在ページ」も０に戻す
				} else {
					startPoint = 1;
				}
			} else {
				if (post_action.equals("frmClear")) {
					startPoint = 0;
				} else {
					startPoint = Integer.parseInt(start_point);
				}
			}
			extCon.setNow_page(String.valueOf(nowPage));	//「現在ページ」は抽出後に確定
			extCon.setStart_point(String.valueOf(startPoint));
			
			// formの「データ表示終了位置」をインスタンスに設定
			int endPoint;
			if (Objects.isNull(post_action) || StringUtils.isBlank(post_action)) {
				if (selCnt == 0) {
					endPoint = 0;	//抽出した結果０件なら「データ表示終了位置」も０に戻す
				} else {
					if (selCnt < ichiranKensu) {
						endPoint = selCnt;
					} else {
						endPoint = ichiranKensu;
					}
				}
			} else {
				if (post_action.equals("frmClear")) {
					endPoint = 0;
				} else {
					endPoint = Integer.parseInt(end_point);
				}
			}
			extCon.setEnd_point(String.valueOf(endPoint));
			
			// 前頁フラグを設定しインスタンスに設定
			String backPageFlg;
			if (nowPage <= 1) {
				backPageFlg = "disabled";
			} else {
				backPageFlg = "";
			}
			extCon.setBack_page_flg(backPageFlg);
			
			//抽出された件数から最終ページ数を計算しインスタンスに設定
			int lastPage;
			if (Objects.isNull(post_action) || StringUtils.isBlank(post_action)) {
				//抽出された件数から最終ページ数を計算
				double dbl = selCnt * 10;	//double型の誤差を考慮し10倍しておく
				dbl = dbl / ichiranKensu;
				dbl = dbl / 10;				//double型の誤差を考慮し10割り戻す
				lastPage = (int)Math.ceil(dbl);
			} else {
				if (post_action.equals("frmClear")) {
					lastPage = 0;
				} else {
					//Formの最終ページを再設定
					lastPage = Integer.parseInt(last_page);
				}
			}
			extCon.setLast_page(String.valueOf(lastPage));
			
			// 次頁フラグを設定しインスタンスに設定
			String nextPageFlg;
			if (nowPage < lastPage) {
				nextPageFlg = "";
			} else {
				nextPageFlg = "disabled";
			}
			extCon.setNext_page_flg(nextPageFlg);
			
			// 表示一覧件数制御フラグを設定しインスタンスに設定
			String ichiran_kensu_flg;
			if (selCnt == 0) {
				ichiran_kensu_flg = "";
			} else {
				ichiran_kensu_flg = "readonly";
			}
			extCon.setIchiran_kensu_flg(ichiran_kensu_flg);
			
			extCon.setPost_action("");
			
			//インスタンスをリクエストスコープに保存
			request.setAttribute("extCon",extCon);
			
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sodanSrch.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}

/*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*
*	
*	飼育相談操作画面(sodanOpe.jsp)のFormの<enctype="multipart/form-data">で送信された
*	アップロード画像のファイル名を取得する。
*	(処理概要)
*	headerは、以下の内容になっているので、ここからfilenameである「XXXXX.pdf」を取得
*	form-data; name="file"; filename="XXXXX.pdf"
*
*＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝*/
    private String getFielName(Part part) {
        String header = part.getHeader("content-disposition");
        String[] split = header.split(";");
        String fileName =
                Arrays.asList(split).stream()
                        .filter(s -> s.trim().startsWith("filename"))
                        .collect(Collectors.joining());
        return fileName.substring(fileName.indexOf("=") + 1).replace("\"", "");
    }

}
