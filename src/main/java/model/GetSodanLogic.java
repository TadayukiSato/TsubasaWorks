package model;

import dao.SodanDAO;

public class GetSodanLogic {
	public Sodan execute(int id) {
		SodanDAO dao = new SodanDAO();
		Sodan sodan = dao.sodanFindById(id);
		return sodan;
	}
}