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
			request.getSession().setAttribute("messageType","���� �޼���");
			request.getSession().setAttribute("messageContent", "��ĭ�� ��� ä���ּ���");
			response.sendRedirect("index.jsp");
			return;
		}
		
		if(!userPW1.equals(userPW2)) {
			request.getSession().setAttribute("messageType","���� �޼���");
			request.getSession().setAttribute("messageContent", "��й�ȣ�� �ٸ��ϴ�.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		int result = new userDAO().register(userID, userPW1, userName, userAge, userGender, userEmail, userProfile);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID); //�ڵ����� ȸ������ �� �α��� �ǵ��� ��
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "ȸ�����Կ� �����Ͽ����ϴ�.");
			response.sendRedirect("index.jsp");
			return;
		} else {
			request.getSession().setAttribute("messageType", "���� �޼���");
			request.getSession().setAttribute("messageContent", "�̹� �����ϴ� ȸ���Դϴ�.");
			response.sendRedirect("index.jsp");
			return;
		}
	}
}
