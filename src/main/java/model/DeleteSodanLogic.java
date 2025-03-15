package model;

import dao.SodanDAO;

public class DeleteSodanLogic {
  public boolean execute(int Id) { 
    SodanDAO dao = new SodanDAO();
    boolean bln =  dao.sodanDelete(Id);
    if (bln) {
		return true;
    } else {
    	return false;
    }
  }
}