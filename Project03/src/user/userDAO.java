package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public userDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/USERCHART";
			String dbID = "root";
			String dbPW = "3355253a";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ȸ�� �α��� ���
	public int login(String userID, String userPW) {
		String SQL = "SELECT * FROM USER WHERE userID = ?";
		pstmt = null;
		rs = null;

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("userPW").equals(userPW)) {
					return 1; // �α��� ����
				}
				return 2; // ��й�ȣƲ��
			} else {
				return 0; // ����� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public int registerCheck(String userID) {
		String SQL = "SELECT * FROM USER WHERE userID = ?";
		pstmt = null;
		rs = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if (rs.next() || userID.equals("") || userID == null) {
				return 0; // �̹� ����
			} else {
				return 1; // ���� ����
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // DB ����
	}

	public int register(String userID, String userPW1, String userName, String userAge, String userGender,
			String userEmail, String userProfile) {
		String SQL = "INSERT INTO USER VALUES (?,?,?,?,?,?,?)";
		pstmt = null;
		rs = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPW1);
			pstmt.setString(3, userName);
			pstmt.setString(4, userAge);
			pstmt.setString(5, userGender);
			pstmt.setString(6, userEmail);
			pstmt.setString(7, userProfile);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}
