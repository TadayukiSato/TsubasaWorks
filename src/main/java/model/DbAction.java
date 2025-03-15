/*	Entityクラス（javabeans）
 * （機能名）	：	飼育相談画面
 * （使用目的）	：	画面遷移先の内容を保持
 *					検索画面　⇒　詳細(確認)画面・更新画面・削除画面
 */
package model;

import java.io.Serializable;

public class DbAction implements Serializable {

	private String dbAction;

	public DbAction() { }
	public DbAction(String dbAction) {
		this.dbAction = dbAction;
	}
	public String getDbAction() { return dbAction; }
	public void setDbAction(String dbAction) { this.dbAction = dbAction; }
}