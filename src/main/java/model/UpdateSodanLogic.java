package model;

import dao.SodanDAO;

public class UpdateSodanLogic {
  public boolean execute(Sodan sodan) { 
    SodanDAO dao = new SodanDAO();
    boolean bln =  dao.sodanUpdate(sodan);
    if (bln) {
		return true;
    } else {
    	return false;
    }
  }
}