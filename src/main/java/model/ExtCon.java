/*	Entityクラス（javabeans）
 * （機能名）	：	飼育相談検索画面
 * （使用目的）	：	Formの抽出条件、その他各項目
 */
package model;

import java.io.Serializable;

public class ExtCon implements Serializable {
	
	private String post_action;			//ポストアクション
	private String kainushi_idx;		// 索引
	private String kainushi_name;		//飼い主名
	private String sodan_date_from; 	//相談日(開始)
	private String sodan_date_to;		//相談日(終了)
	private String bird_type;			//鳥の種類
	private String bird_name;			//鳥の名前
	private String onayami_gaiyo;		//お悩み概要
	private String sodan_naiyo;			//相談内容
	private String staff_name;			//担当者名
	private String upd_date_from;		//ﾃﾞｰﾀ更新日(開始)
	private String upd_date_to;			//ﾃﾞｰﾀ更新日(終了)
	private String pdf_umu;				//画像有無
	private String sel_cnt;				//抽出データ件数
	private String all_cnt;				//全データ件数
	private String ichiran_kensu;		//一覧表示件数
	private String start_point;			//データ表示開始位置
	private String end_point;			//データ表示終了位置
	private String now_page;			//現在ページ
	private String back_page_flg;		//前頁ボタン制御フラグ
	private String last_page;			//最終ページ
	private String next_page_flg;		//次頁ボタン制御フラグ
	private String ichiran_kensu_flg;	//一覧表示件数
	
	public ExtCon() {	}
	
	public ExtCon(
			String post_action, 
			String kainushi_idx, 
			String kainushi_name, 
			String sodan_date_from, 
			String sodan_date_to, 
			String bird_type, 
			String bird_name, 
			String onayami_gaiyo, 
			String sodan_naiyo, 
			String staff_name, 
			String upd_date_from, 
			String upd_date_to, 
			String pdf_umu, 
			String sel_cnt, 
			String all_cnt, 
			String ichiran_kensu, 
			String start_point, 
			String end_point, 
			String now_page, 
			String back_page_flg, 
			String last_page, 
			String next_page_flg,
			String ichiran_kensu_flg) 
	{
		this.post_action = post_action;
		this.kainushi_idx = kainushi_idx;
		this.kainushi_name = kainushi_name;
		this.sodan_date_from = sodan_date_from;
		this.sodan_date_to = sodan_date_to;
		this.bird_type = bird_type;
		this.bird_name = bird_name;
		this.onayami_gaiyo = onayami_gaiyo;
		this.sodan_naiyo = sodan_naiyo;
		this.staff_name = staff_name;
		this.upd_date_from = upd_date_from;
		this.upd_date_to = upd_date_to;
		this.pdf_umu = pdf_umu;
		this.sel_cnt = sel_cnt;
		this.all_cnt = all_cnt;
		this.ichiran_kensu = ichiran_kensu;
		this.start_point = start_point;
		this.end_point = end_point;
		this.now_page = now_page;
		this.back_page_flg = back_page_flg;
		this.last_page = last_page;
		this.next_page_flg = next_page_flg;
		this.ichiran_kensu_flg = ichiran_kensu_flg;

	}
	
	public String getPost_action() { return post_action; }
	public void setPost_action(String post_action) { this.post_action = post_action; }
	public String getKainushi_idx() { return kainushi_idx; }
	public void setKainushi_idx(String kainushi_idx) { this.kainushi_idx = kainushi_idx; }
	public String getKainushi_name() { return kainushi_name; }
	public void setKainushi_name(String kainushi_name) { this.kainushi_name = kainushi_name; }
	public String getSodan_date_from() { return sodan_date_from; }
	public void setSodan_date_from(String sodan_date_from) { this.sodan_date_from = sodan_date_from; }
	public String getSodan_date_to() { return sodan_date_to; }
	public void setSodan_date_to(String sodan_date_to) { this.sodan_date_to = sodan_date_to; }
	public String getBird_type() { return bird_type; }
	public void setBird_type(String bird_type) { this.bird_type = bird_type; }
	public String getBird_name() { return bird_name; }
	public void setBird_name(String bird_name) { this.bird_name = bird_name; }
	public String getOnayami_gaiyo() { return onayami_gaiyo; }
	public void setOnayami_gaiyo(String onayami_gaiyo) { this.onayami_gaiyo = onayami_gaiyo; }
	public String getSodan_naiyo() { return sodan_naiyo; }
	public void setSodan_naiyo(String sodan_naiyo) { this.sodan_naiyo = sodan_naiyo; }
	public String getStaff_name() { return staff_name; }
	public void setStaff_name(String staff_name) { this.staff_name = staff_name; }
	public String getUpd_date_from() { return upd_date_from; }
	public void setUpd_date_from(String upd_date_from) { this.upd_date_from = upd_date_from; }
	public String getUpd_date_to() { return upd_date_to; }
	public void setUpd_date_to(String upd_date_to) { this.upd_date_to = upd_date_to; }
	public String getPdf_umu() { return pdf_umu; }
	public void setPdf_umu(String pdf_umu) { this.pdf_umu = pdf_umu; }
	public String getSel_cnt() { return sel_cnt; }
	public void setSel_cnt(String sel_cnt) { this.sel_cnt = sel_cnt; }
	public String getAll_cnt() { return all_cnt; }
	public void setAll_cnt(String all_cnt) { this.all_cnt = all_cnt; }
	public String getIchiran_kensu() { return ichiran_kensu; }
	public void setIchiran_kensu(String ichiran_kensu) { this.ichiran_kensu = ichiran_kensu; }
	public String getStart_point() { return start_point; }
	public void setStart_point(String start_point) { this.start_point = start_point; }
	public String getEnd_point() { return end_point; }
	public void setEnd_point(String end_point) { this.end_point = end_point; }
	public String getNow_page() { return now_page; }
	public void setNow_page(String now_page) { this.now_page = now_page; }
	public String getBack_page_flg() { return back_page_flg; }
	public void setBack_page_flg(String back_page_flg) { this.back_page_flg = back_page_flg; }
	public String getLast_page() { return last_page; }
	public void setLast_page(String last_page) { this.last_page = last_page; }
	public String getNext_page_flg() { return next_page_flg; }
	public void setNext_page_flg(String next_page_flg) { this.next_page_flg = next_page_flg; }
	public String getIchiran_kensu_flg() { return ichiran_kensu_flg; }
	public void setIchiran_kensu_flg(String ichiran_kensu_flg) { this.ichiran_kensu_flg = ichiran_kensu_flg; }
	
}
