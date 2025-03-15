package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import model.Sodan;

public class SodanDAO {
	// データベース接続に使用する情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/tsubasa_db";
	private final String DB_USER = "root";
	private final String DB_PASS = "";

	public List<Sodan> sodanFind(String jyoken, int nowPage, int vewCnt) {
		List<Sodan> sodanList = new ArrayList<Sodan>();
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベースへ接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql;
			// 抽出条件の有無を判定しSELECT文を準備
			if (Objects.isNull(jyoken) || StringUtils.isBlank(jyoken)) { 
				sql = "SELECT * FROM SHIKUSODAN ORDER BY KAINUSHI_IDX ASC, KAINUSHI_NAME ASC, SODAN_DATE ASC";
			} else {
				sql = "SELECT * FROM SHIKUSODAN " + jyoken + " ORDER BY KAINUSHI_IDX ASC, KAINUSHI_NAME ASC, SODAN_DATE ASC";
			}
			int startPoint = 0;
			
	        if (nowPage > 1) {
	        	startPoint = (nowPage * vewCnt) - vewCnt;
	        }
	        sql = sql + " LIMIT " + Integer.toString(startPoint) + " , " + Integer.toString(vewCnt);
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// 抽出条件有りでSELECTを実行
			ResultSet rs = pStmt.executeQuery();
			// SELECT文の結果をArrayListに格納s
			while (rs.next()) {
				int sodanId = rs.getInt("SODAN_ID");
				String kainushiIdx = rs.getString("KAINUSHI_IDX");
				String kainushiName = rs.getString("KAINUSHI_NAME");
				Date sodanDate = rs.getDate("SODAN_DATE");
				Time sodanTimeFrom = rs.getTime("SODAN_TIME_FROM");
				Time sodanTimeTo = rs.getTime("SODAN_TIME_TO");
				String homeArea = rs.getString("HOME_AREA");
				String clinicName = rs.getString("CLINIC_NAME");
				String birdType = rs.getString("BIRD_TYPE");
				String birdSex = rs.getString("BIRD_SEX");
				String birdAge = rs.getString("BIRD_AGE");
				String birdName = rs.getString("BIRD_NAME");
				String basicInfo = rs.getString("BASIC_INFO");
				String sodanNaiyo = rs.getString("SODAN_NAIYO");
				Byte cbxKaikata = rs.getByte("CBX_KAIKATA");
				Byte cbxByouki = rs.getByte("CBX_BYOUKI");
				Byte cbxHatsujyou = rs.getByte("CBX_HATSUJYOU");
				Byte cbxSyokuji = rs.getByte("CBX_SYOKUJI");
				Byte cbxKebiki = rs.getByte("CBX_KEBIKI");
				Byte cbxTorening = rs.getByte("CBX_TORENING");
				Byte cbxFojing = rs.getByte("CBX_FOJING");
				Byte cbxMondaikodo = rs.getByte("CBX_MONDAIKODO");
				Byte cbxSonota = rs.getByte("CBX_SONOTA");
				String staffName = rs.getString("STAFF_NAME");
				String upFilePath = rs.getString("UP_FILE_PATH");
				Timestamp insertTime = rs.getTimestamp("INSERT_TIME");
				Timestamp updateTime = rs.getTimestamp("UPDATE_TIME");
				Sodan sodan = new Sodan(sodanId, kainushiIdx, kainushiName, 
						sodanDate, sodanTimeFrom, sodanTimeTo, homeArea, clinicName, 
						birdType, birdSex, birdAge, birdName, basicInfo, sodanNaiyo, 
						cbxKaikata, cbxByouki, cbxHatsujyou, cbxSyokuji, cbxKebiki, 
						cbxTorening, cbxFojing, cbxMondaikodo, cbxSonota, staffName, 
						upFilePath, insertTime, updateTime);
				sodanList.add(sodan);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return sodanList;
	}

	public Sodan sodanFindById(int id) {
		Sodan sodan = null;
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベースへ接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// SELECT文を準備
			String sql = "SELECT * FROM SHIKUSODAN WHERE SODAN_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				int sodanId = rs.getInt("SODAN_ID");
				String kainushiIdx = rs.getString("KAINUSHI_IDX");
				String kainushiName = rs.getString("KAINUSHI_NAME");
				Date sodanDate = rs.getDate("SODAN_DATE");
				Time sodanTimeFrom = rs.getTime("SODAN_TIME_FROM");
				Time sodanTimeTo = rs.getTime("SODAN_TIME_TO");
				String homeArea = rs.getString("HOME_AREA");
				String clinicName = rs.getString("CLINIC_NAME");
				String birdType = rs.getString("BIRD_TYPE");
				String birdSex = rs.getString("BIRD_SEX");
				String birdAge = rs.getString("BIRD_AGE");
				String birdName = rs.getString("BIRD_NAME");
				String basicInfo = rs.getString("BASIC_INFO");
				String sodanNaiyo = rs.getString("SODAN_NAIYO");
				Byte cbxKaikata = rs.getByte("CBX_KAIKATA");
				Byte cbxByouki = rs.getByte("CBX_BYOUKI");
				Byte cbxHatsujyou = rs.getByte("CBX_HATSUJYOU");
				Byte cbxSyokuji = rs.getByte("CBX_SYOKUJI");
				Byte cbxKebiki = rs.getByte("CBX_KEBIKI");
				Byte cbxTorening = rs.getByte("CBX_TORENING");
				Byte cbxFojing = rs.getByte("CBX_FOJING");
				Byte cbxMondaikodo = rs.getByte("CBX_MONDAIKODO");
				Byte cbxSonota = rs.getByte("CBX_SONOTA");
				String staffName = rs.getString("STAFF_NAME");
				String upFilePath = rs.getString("UP_FILE_PATH");
				Timestamp insertTime = rs.getTimestamp("INSERT_TIME");
				Timestamp updateTime = rs.getTimestamp("UPDATE_TIME");
				sodan = new Sodan(sodanId, kainushiIdx, kainushiName, 
						sodanDate, sodanTimeFrom, sodanTimeTo, homeArea, clinicName, 
						birdType, birdSex, birdAge, birdName, basicInfo, sodanNaiyo, 
						cbxKaikata, cbxByouki, cbxHatsujyou, cbxSyokuji, cbxKebiki, 
						cbxTorening, cbxFojing, cbxMondaikodo, cbxSonota, staffName, 
						upFilePath, insertTime, updateTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return sodan;
	}

	public boolean sodanCreate(Sodan sodan) {
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// INSERT文の準備(SODAN_IDは自動連番なので指定しなくてよい）
			String sql = "INSERT INTO SHIKUSODAN ("
				+ "KAINUSHI_IDX" + ", "
				+ "KAINUSHI_NAME" + ", "
				+ "SODAN_DATE" + ", "
				+ "SODAN_TIME_FROM" + ", "
				+ "SODAN_TIME_TO" + ", "
				+ "HOME_AREA" + ", "
				+ "CLINIC_NAME" + ", "
				+ "BIRD_TYPE" + ", "
				+ "BIRD_SEX" + ", "
				+ "BIRD_AGE" + ", "
				+ "BIRD_NAME" + ", "
				+ "BASIC_INFO" + ", "
				+ "SODAN_NAIYO" + ", "
				+ "CBX_KAIKATA" + ", "
				+ "CBX_BYOUKI" + ", "
				+ "CBX_HATSUJYOU" + ", "
				+ "CBX_SYOKUJI" + ", "
				+ "CBX_KEBIKI" + ", "
				+ "CBX_TORENING" + ", "
				+ "CBX_FOJING" + ", "
				+ "CBX_MONDAIKODO" + ", "
				+ "CBX_SONOTA" + ", "
				+ "STAFF_NAME" + ", "
				+ "UP_FILE_PATH ) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?);";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// INSERT文中の「?」に使用する値を設定しSQLを完成
			pStmt.setString(1, sodan.getKainushiIdx());
			pStmt.setString(2, sodan.getKainushiName());
			pStmt.setDate(3, sodan.getSodanDate());
			pStmt.setTime(4, sodan.getSodanTimeFrom());
			pStmt.setTime(5, sodan.getSodanTimeTo());
			pStmt.setString(6, sodan.getHomeArea());
			pStmt.setString(7, sodan.getClinicName());
			pStmt.setString(8, sodan.getBirdType());
			pStmt.setString(9, sodan.getBirdSex());
			pStmt.setString(10, sodan.getBirdAge());
			pStmt.setString(11, sodan.getBirdName());
			pStmt.setString(12, sodan.getBasicInfo());
			pStmt.setString(13, sodan.getSodonNaiyo());
			pStmt.setByte(14, sodan.getCbxKaikata());
			pStmt.setByte(15, sodan.getCbxByouki());
			pStmt.setByte(16, sodan.getCbxHatsujyou());
			pStmt.setByte(17, sodan.getCbxSyokuji());
			pStmt.setByte(18, sodan.getCbxKebiki());
			pStmt.setByte(19, sodan.getCbxTorening());
			pStmt.setByte(20, sodan.getCbxFojing());
			pStmt.setByte(21, sodan.getCbxMondaikodo());
			pStmt.setByte(22, sodan.getCbxSonota());
			pStmt.setString(23, sodan.getStaffName());
			pStmt.setString(24, sodan.getUpFilePath());
			
			// INSERT文を実行（resultには追加された行数が代入される）
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sodanUpdate(Sodan sodan) {
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// UPDATE文の準備
			String sql = "UPDATE SHIKUSODAN SET "
					+ "KAINUSHI_IDX = ?, "
					+ "KAINUSHI_NAME = ?, "
					+ "SODAN_DATE = ?, "
					+ "SODAN_TIME_FROM = ?, "
					+ "SODAN_TIME_TO = ?, "
					+ "HOME_AREA = ?, "
					+ "CLINIC_NAME = ?, "
					+ "BIRD_TYPE = ?, "
					+ "BIRD_SEX = ?, "
					+ "BIRD_AGE = ?, "
					+ "BIRD_NAME = ?, "
					+ "BASIC_INFO = ?, "
					+ "SODAN_NAIYO = ?, "
					+ "CBX_KAIKATA = ?, "
					+ "CBX_BYOUKI = ?, "
					+ "CBX_HATSUJYOU = ?, "
					+ "CBX_SYOKUJI = ?, "
					+ "CBX_KEBIKI = ?, "
					+ "CBX_TORENING = ?, "
					+ "CBX_FOJING = ?, "
					+ "CBX_MONDAIKODO = ?, "
					+ "CBX_SONOTA = ?, "
					+ "STAFF_NAME = ?, "
					+ "UP_FILE_PATH = ? "
					+ "WHERE SODAN_ID = ?"
					+ ";";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// UPDATE文中の「?」に使用する値を設定しSQLを完成
			pStmt.setString(1, sodan.getKainushiIdx());
			pStmt.setString(2, sodan.getKainushiName());
			pStmt.setDate(3, sodan.getSodanDate());
			pStmt.setTime(4, sodan.getSodanTimeFrom());
			pStmt.setTime(5, sodan.getSodanTimeTo());
			pStmt.setString(6, sodan.getHomeArea());
			pStmt.setString(7, sodan.getClinicName());
			pStmt.setString(8, sodan.getBirdType());
			pStmt.setString(9, sodan.getBirdSex());
			pStmt.setString(10, sodan.getBirdAge());
			pStmt.setString(11, sodan.getBirdName());
			pStmt.setString(12, sodan.getBasicInfo());
			pStmt.setString(13, sodan.getSodonNaiyo());
			pStmt.setByte(14, sodan.getCbxKaikata());
			pStmt.setByte(15, sodan.getCbxByouki());
			pStmt.setByte(16, sodan.getCbxHatsujyou());
			pStmt.setByte(17, sodan.getCbxSyokuji());
			pStmt.setByte(18, sodan.getCbxKebiki());
			pStmt.setByte(19, sodan.getCbxTorening());
			pStmt.setByte(20, sodan.getCbxFojing());
			pStmt.setByte(21, sodan.getCbxMondaikodo());
			pStmt.setByte(22, sodan.getCbxSonota());
			pStmt.setString(23, sodan.getStaffName());
			pStmt.setString(24, sodan.getUpFilePath());
			pStmt.setInt(25, sodan.getSodanId());
			
			// UPDATE文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean sodanDelete(int id) {
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// SELECT文を準備
			String sql = "DELETE FROM SHIKUSODAN WHERE SODAN_ID = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1, id);
			
			// DELETE文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public int sodanCntAll() {
		int allCnt = 0;
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// SELECT文を準備
			String sql = "SELECT COUNT(*) FROM SHIKUSODAN";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				allCnt = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return allCnt;
		}
		return allCnt;
	}
	
	public int sodanCntSel(String jyoken) {
		int cntSel = 0;
		// JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		// データベース接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			// SELECT文を準備
			String sql = "SELECT COUNT(*) FROM SHIKUSODAN" + " " + jyoken;
			PreparedStatement pStmt = conn.prepareStatement(sql);
			// SELECTを実行
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				cntSel = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return cntSel;
		}
		return cntSel;
	}
	
}
