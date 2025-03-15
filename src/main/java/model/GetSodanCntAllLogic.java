package model;

import dao.SodanDAO;

public class GetSodanCntAllLogic {
	public int execute() { 
		SodanDAO dao = new SodanDAO();
		int allCnt = dao.sodanCntAll();
		return allCnt;
	}
}