package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "RegisterServlet", urlPatterns = { "/RegisterServlet" })
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String userID = request.getParameter("userID");
		String userPW1 = request.getParameter("userPW1");
		String userPW2 = request.getParameter("userPW2");
		String userName = request.getParameter("userName");
		String userAge = request.getParameter("userAge");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		String userProfile = request.getParameter("userProfile");
		
		if(userID == null || userPW1 == null || userPW2 == null ||
				userName == null || userAge == null || userGender == null ||
				userEmail == null || userID.equals("") || userPW1.equals("") || userPW2.equals("") ||
				userName.equals("") || userAge.equals("") || userGender.equals("") || userEmail.equals("")) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent", "빈칸을 모두 채워주세요");
			response.sendRedirect("index.jsp");
			return;
		}
		
		if(!userPW1.equals(userPW2)) {
			request.getSession().setAttribute("messageType","오류 메세지");
			request.getSession().setAttribute("messageContent", "비밀번호가 다릅니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		int result = new userDAO().register(userID, userPW1, userName, userAge, userGender, userEmail, userProfile);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID); //자동으로 회원가입 후 로그인 되도록 함
			request.getSession().setAttribute("messageType", "성공 메세지");
			request.getSession().setAttribute("messageContent", "회원가입에 성공하였습니다.");
			response.sendRedirect("index.jsp");
			return;
		} else {
			request.getSession().setAttribute("messageType", "오류 메세지");
			request.getSession().setAttribute("messageContent", "이미 존재하는 회원입니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	}
}
