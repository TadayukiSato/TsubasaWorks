package servlet;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Login;
import model.LoginLogic;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String vew_action = request.getParameter("vew_action");
		
		// リクエストパラメータvew_actionの値によって処理を分岐
		if (Objects.isNull(vew_action) || StringUtils.isBlank(vew_action)) { 
			// 初回遷移 OR ログイン画面へボタンクリック時
			// ログイン画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		} else if (vew_action.equals("logOut") ) {
			// ログアウトボタンクリック時
			// ログアウト結果画面にフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/logout.jsp");
			dispatcher.forward(request, response);
		} else if (vew_action.equals("loginErr") ) {
			// ログイン失敗時
			// エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "ユーザーIDが未登録、又は<br>パスワード不一致の為<br>ログイン出来ませんでした。");
			
			// セッションが残っている場合は破棄
			HttpSession session = request.getSession();
			if (session != null) {
			    session.invalidate();
			}
			
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/logout.jsp");
			dispatcher.forward(request, response);
		} else if (vew_action.equals("homeBack") ) {
			//ログイン成功後ホームボタンが押されメニュー画面に戻る時
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/mainMenu.jsp");
			dispatcher.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pass = request.getParameter("pass");

	    // ログイン処理の実行
		Login login = new Login(userId, pass);
		LoginLogic bo = new LoginLogic();
		boolean result = bo.execute(login);
		
		// ログイン処理の成否によって処理を分岐
		if (result) { // ログイン成功時
			// セッションスコープにユーザーIDを保存
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/mainMenu.jsp");
			dispatcher.forward(request, response);
		} else { // ログイン失敗時
			
			// セッションが残っている場合は破棄
			HttpSession session = request.getSession();
			if (session != null) {
			    session.invalidate();
			}
			
			// エラーメッセージをリクエストスコープに保存
			request.setAttribute("errorMsg", "ユーザーIDが未登録、又は<br>パスワード不一致の為<br>ログイン出来ませんでした。");
			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/logout.jsp");
			dispatcher.forward(request, response);
		}
	}

}
