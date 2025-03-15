package model;

import dao.SodanDAO;

public class GetSodanCntSelLogic {
	public int execute(String jyoken) { 
		SodanDAO dao = new SodanDAO();
		int cntSel = dao.sodanCntSel(jyoken);
		return cntSel;
	}
}

