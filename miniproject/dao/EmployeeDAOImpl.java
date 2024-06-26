package dao;

import util.OracleConnector;
import vo.EmployeeVO;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDAOImpl implements EmployeeDAO{

	@Override
	public int insert(EmployeeVO evo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int nCnt = 0;

		try {
			conn = OracleConnector.getConnection();
			String sql = "INSERT INTO EMPLOYEE (EMPLOYEE_ID, NAME, DEP, JOB, BIRTHDAY, ADDRESS, EMAIL, PASSWORD, TEL, DELETE_YN, CREATED_DATE)"
					+ " VALUES (?, ?, ?, '평사원', STR_TO_DATE(?, '%Y-%m-%d'), ?, ?, ?, ?, 'N', SYSDATE())";

			pstmt = conn.prepareStatement(sql);

			pstmt.clearParameters(); // clearParameters() 함수 항상 사용하기
			pstmt.setString(1, evo.getEmployeeId());
			pstmt.setString(2, evo.getName());
			pstmt.setString(3, evo.getDep());
			pstmt.setString(4, evo.getBirthday());
			pstmt.setString(5, evo.getAddress());
			pstmt.setString(6, evo.getEmail());
			pstmt.setString(7, evo.getPassword());
			pstmt.setString(8, evo.getTel());

			nCnt = pstmt.executeUpdate();

		}catch(Exception e) {
			System.out.println("EmployeeDAOImpl.Insert () 함수에서 입력 중 오류 발생  >>> : " +e.getMessage () );
		}
		return nCnt;
	}


	@Override
	public EmployeeVO loginCheck(EmployeeVO evo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		EmployeeVO result = null;

		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT * FROM EMPLOYEE "
					+ "WHERE EMAIL = ? AND PASSWORD = ?");
			pstmt =  conn.prepareStatement(sql);

			pstmt.setString(1, evo.getEmail());
			pstmt.setString(2, evo.getPassword());

			rsRs = pstmt.executeQuery();

			if (rsRs != null) {

				while (rsRs.next()) {
					result = new EmployeeVO();

					result.setEmployeeId(rsRs.getString(1));
					result.setName(rsRs.getString(2));
					result.setDep(rsRs.getString(3));
					result.setJob(rsRs.getString(4));
					result.setBirthday(rsRs.getString(5));
					result.setAddress(rsRs.getString(6));
					result.setEmail(rsRs.getString(7));
					result.setPassword(rsRs.getString(8));
					result.setTel(rsRs.getString(9));
					result.setDeleteYN(rsRs.getString(10));
					result.setCreatedDate(rsRs.getString(11));
				}
			}
		} catch (Exception e) {
			System.out.println("EmployeeDAOImpl.loginCheck() 조회 중 에러가 발생 >>> : " + e);
		}

		return result;
	}

	@Override
	public EmployeeVO selectById(long employeeId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		EmployeeVO result = null;

		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT * FROM EMPLOYEE "
					+ "WHERE EMPLOYEE_ID = ?");
			pstmt =  conn.prepareStatement(sql);

			pstmt.setLong(1, employeeId);

			//ResultSet 에 파일 받아오기
			rsRs = pstmt.executeQuery();

			if (rsRs != null) {

				while (rsRs.next()) {
					result = new EmployeeVO();

					result.setEmployeeId(rsRs.getString(1));
					result.setName(rsRs.getString(2));
					result.setDep(rsRs.getString(3));
					result.setJob(rsRs.getString(4));
					result.setBirthday(rsRs.getString(5));
					result.setAddress(rsRs.getString(6));
					result.setEmail(rsRs.getString(7));
					result.setPassword(rsRs.getString(8));
					result.setTel(rsRs.getString(9));
					result.setDeleteYN(rsRs.getString(10));
					result.setCreatedDate(rsRs.getString(11));
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}


	@Override
	public EmployeeVO selectLast() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		EmployeeVO result = null;

		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEE");
			pstmt =  conn.prepareStatement(sql);

			rsRs = pstmt.executeQuery();

			if (rsRs != null) {

				while (rsRs.next()) {
					result = new EmployeeVO();

					if (rsRs.getString(1) != null) {
						result.setEmployeeId(rsRs.getString(1));
					} else {
						result.setEmployeeId("0");
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}


	@Override
	public boolean isExistsByEmail(String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsRs = null;
		EmployeeVO result = null;

		try {
			conn = OracleConnector.getConnection();

			String sql = ("SELECT * FROM EMPLOYEE"
					+ " WHERE EMAIL = ?");

			pstmt =  conn.prepareStatement(sql);
			pstmt.setString(1, email);

			rsRs = pstmt.executeQuery();

			if (rsRs != null) {
				while (rsRs.next()) {
					if (email.equals(rsRs.getNString(7))) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
