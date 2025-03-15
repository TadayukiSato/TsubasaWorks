package model;
import dao.SodanDAO;

public class CreateSodanLogic {
  public boolean execute(Sodan sodan) { 
    SodanDAO dao = new SodanDAO();
    boolean bln =  dao.sodanCreate(sodan);
    if (bln) {
		return true;
    } else {
    	return false;
    }
  }
}