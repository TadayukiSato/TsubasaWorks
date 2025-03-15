package model;

import java.util.List;

import dao.SodanDAO;

public class GetSodanListLogic {
	public List<Sodan> execute(String extCon, int nowPage, int vewCnt) {
		SodanDAO dao = new SodanDAO();
		List<Sodan> sodanList = dao.sodanFind(extCon, nowPage, vewCnt);
		return sodanList;
	}
}