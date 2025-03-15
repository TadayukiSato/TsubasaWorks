package test;

import dao.SodanDAO;
import model.Sodan;

public class SodanDAOTest {
	public static void main(String[] args) {
		testdbSodanGetById(); // ユーザーが見つかる場合のテスト
	}
	public static void testdbSodanGetById() {
		int Id = 18;
		SodanDAO dao = new SodanDAO();
		Sodan shikuSodan = dao.sodanFindById(Id);
		System.out.println(shikuSodan.getKainushiName());
	}
}
