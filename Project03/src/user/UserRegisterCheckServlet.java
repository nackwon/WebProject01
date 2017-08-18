package user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserRegisterCheckServlet")
public class UserRegisterCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID = request.getParameter("userID");
		
		if(userID == null || userID.equals("")) {
			response.getWriter().write("-1");
		}
		response.getWriter().write(new userDAO().registerCheck(userID)+"");
		// 마지막에 "" 이것을 하는 이유는 registerCheck메소드는 숫자 0 1 -1을 반환하기 때문에 마지막에 공백을 넣어줌으로써
		// String 형으로 보여지게끔 한다.
	}
}
