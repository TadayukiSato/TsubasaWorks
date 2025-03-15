/*	Entityクラス（javabeans）
 * （機能名）	：	飼育相談画面
 * （使用目的）	：	飼育相談データベース
 */
package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Sodan implements Serializable {
	private int sodanId;	// Primary Key
	private String kainushiIdx;
	private String kainushiName;
	private Date sodanDate;
	private Time sodanTimeFrom;
	private Time sodanTimeTo;
	private String homeArea;
	private String clinicName;
	private String birdType;
	private String birdSex;
	private String birdAge;
	private String birdName;
	private String basicInfo;
	private String sodonNaiyo;
	private Byte cbxKaikata;
	private Byte cbxByouki;
	private Byte cbxHatsujyou;
	private Byte cbxSyokuji;
	private Byte cbxKebiki;
	private Byte cbxTorening;
	private Byte cbxFojing;
	private Byte cbxMondaikodo;
	private Byte cbxSonota;
	private String staffName;
	private String upFilePath;
	private Timestamp insertTime;
	private Timestamp updateTime;
	
	public Sodan() { }

	public Sodan(int sodanId, String kainushiIdx, String kainushiName, Date sodanDate, 
			Time sodanTimeFrom, Time sodanTimeTo, String homeArea, String clinicName, 
			String birdType, String birdSex, String birdAge, String birdName, 
			String basicInfo, String sodonNaiyo, Byte cbxKaikata, Byte cbxByouki,
			Byte cbxHatsujyou, Byte cbxSyokuji, Byte cbxKebiki, Byte cbxTorening,
			Byte cbxFojing, Byte cbxMondaikodo, Byte cbxSonota, String staffName, 
			String upFilePath, Timestamp insertTime, Timestamp updateTime) {
		this.sodanId = sodanId;
		this.kainushiIdx = kainushiIdx;
		this.kainushiName = kainushiName;
		this.sodanDate = sodanDate;
		this.sodanTimeFrom = sodanTimeFrom;
		this.sodanTimeTo = sodanTimeTo;
		this.homeArea = homeArea;
		this.clinicName = clinicName;
		this.birdType = birdType;
		this.birdSex = birdSex;
		this.birdAge = birdAge;
		this.birdName = birdName;
		this.basicInfo = basicInfo;
		this.sodonNaiyo = sodonNaiyo;
		this.cbxKaikata = cbxKaikata;
		this.cbxByouki = cbxByouki;
		this.cbxHatsujyou = cbxHatsujyou;
		this.cbxSyokuji = cbxSyokuji;
		this.cbxKebiki = cbxKebiki;
		this.cbxTorening = cbxTorening;
		this.cbxFojing = cbxFojing;
		this.cbxMondaikodo = cbxMondaikodo;
		this.cbxSonota = cbxSonota;
		this.staffName = staffName;
		this.upFilePath = upFilePath;
		this.insertTime = insertTime;
		this.updateTime = updateTime;

	}
	
	public int getSodanId() { return sodanId; }
	public void setSodanId(int sodanId) { this.sodanId = sodanId; }
	public String getKainushiIdx() { return kainushiIdx; }
	public void setKainushiIdx(String kainushiIdx) { this.kainushiIdx = kainushiIdx; }
	public String getKainushiName() { return kainushiName; }
	public void setKainushiName(String kainushiName) { this.kainushiName = kainushiName; }
	public Date getSodanDate() { return sodanDate; }
	public void setSodanDate(Date sodanDate) { this.sodanDate = sodanDate; }
	public Time getSodanTimeFrom() { return sodanTimeFrom; }
	public void setSodanTimeFrom(Time sodanTimeFrom) { this.sodanTimeFrom = sodanTimeFrom; }
	public Time getSodanTimeTo() { return sodanTimeTo; }
	public void setSodanTimeTo(Time sodanTimeTo) { this.sodanTimeTo = sodanTimeTo; }
	public String getHomeArea() { return homeArea; }
	public void setHomeArea(String homeArea) { this.homeArea = homeArea; }
	public String getClinicName() { return clinicName; }
	public void setClinicName(String clinicName) { this.clinicName = clinicName; }
	public String getBirdType() { return birdType; }
	public void setBirdType(String birdType) { this.birdType = birdType; }
	public String getBirdSex() { return birdSex; }
	public void setBirdSex(String birdSex) { this.birdSex = birdSex; }
	public String getBirdAge() { return birdAge; }
	public void setBirdAge(String birdAge) { this.birdAge = birdAge; }
	public String getBirdName() { return birdName; }
	public void setBirdName(String birdName) { this.birdName = birdName; }
	public String getBasicInfo() { return basicInfo; }
	public void setBasicInfo(String basicInfo) { this.basicInfo = basicInfo; }
	public String getSodonNaiyo() { return sodonNaiyo; }
	public void setSodonNaiyo(String sodonNaiyo) { this.sodonNaiyo = sodonNaiyo; }
	public Byte getCbxKaikata() { return cbxKaikata; }
	public void setCbxKaikata(Byte cbxKaikata) { this.cbxKaikata = cbxKaikata; }
	public Byte getCbxByouki() { return cbxByouki; }
	public void setCbxByouki(Byte cbxByouki) { this.cbxByouki = cbxByouki; }
	public Byte getCbxHatsujyou() { return cbxHatsujyou; }
	public void setCbxHatsujyou(Byte cbxHatsujyou) { this.cbxHatsujyou = cbxHatsujyou; }
	public Byte getCbxSyokuji() { return cbxSyokuji; }
	public void setCbxSyokuji(Byte cbxSyokuji) { this.cbxSyokuji = cbxSyokuji; }
	public Byte getCbxKebiki() { return cbxKebiki; }
	public void setCbxKebiki(Byte cbxKebiki) { this.cbxKebiki = cbxKebiki; }
	public Byte getCbxTorening() { return cbxTorening; }
	public void setCbxTorening(Byte cbxTorening) { this.cbxTorening = cbxTorening; }
	public Byte getCbxFojing() { return cbxFojing; }
	public void setCbxFojing(Byte cbxFojing) { this.cbxFojing = cbxFojing; }
	public Byte getCbxMondaikodo() { return cbxMondaikodo; }
	public void setCbxMondaikodo(Byte cbxMondaikodo) { this.cbxMondaikodo = cbxMondaikodo; }
	public Byte getCbxSonota() { return cbxSonota; }
	public void setCbxSonota(Byte cbxSonota) { this.cbxSonota = cbxSonota; }
	public String getStaffName() { return staffName; }
	public void setStaffName(String staffName) { this.staffName = staffName; }
	public String getUpFilePath() { return upFilePath; }
	public void setUpFilePath(String upFilePath) { this.upFilePath = upFilePath; }
	public Timestamp getInsertTime() { return insertTime; }
	public void setInsertTime(Timestamp insertTime) { this.insertTime = insertTime; }
	public Timestamp getUpdateTime() { return updateTime; }
	public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

}
