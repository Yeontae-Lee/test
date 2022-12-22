package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDAO {
	// 데이터베이스 작업에 사용되는 객체 타입 변수 선언(멤버변수이므로 기본값으로 자동 초기화)
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// ----------------------------------------------------------------------------------
	// 데이터베이스 작업을 위해 필요한 연동 작업(준비, 1단계, 2단계)을 수행한 후
	// 연결 정보를 저장하는 Connection 객체를 외부로 리턴하는 getConnection() 메서드 정의
	// => 파라미터 : 없음,  리턴타입 : java.sql.Connection(con)
	public Connection getConnection() {
		// 0. DB 연결에 필요한 문자열 변수 선언
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/funweb";
		String dbUser = "root";
		String dbPassword = "1234";

		try {
			// 1단계. 드라이버 클래스 로드
			Class.forName(driver);

			// 2단계. DB 연결
			con = DriverManager.getConnection(url, dbUser, dbPassword);
		} catch (ClassNotFoundException e) {
			// Class.forName(driver); 메서드 실행 과정에서 발생할 수 있는 예외
			System.out.println("드라이버 클래스 로드 실패!");
			e.printStackTrace();
		} catch (SQLException e) {
			// DriverManager.getConnection(url, dbUser, dbPassword); 메서드 실행 과정에서 발생할 수 있는 예외
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		}
		
		// Connection 객체 리턴
		return con;
	}
	// ----------------------------------------------------------------------------------
	// DB 자원 반환 작업을 수행할 close() 메서드 정의 => 메서드 오버로딩 활용
	// 1. Connection 객체를 반환할 close() 메서드 정의
	// => 파라미터 : Connection 타입(con)  리턴값 없음
	public void close(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 2. PreparedStatement 객체를 반환할 close() 메서드 정의
	// => 파라미터 : PreparedStatement 타입(pstmt)  리턴값 없음
	public void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 3. ResultSet 객체를 반환할 close() 메서드 정의
	// => 파라미터 : ResultSet 타입(rs)  리턴값 없음
	public void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// ----------------------------------------------------------------------------------
	// 글쓰기 작업을 수행할 insertBoard() 메서드 정의
	// => 파라미터 : BoardDTO 객체    리턴타입 : int(insertCount)
	public int insertBoard(BoardDTO board) {
		int insertCount = 0;
		
		// DB 연결
		con = getConnection();
		
		try {
			// 새 글 작성 시 부여할 새 글의 번호 계산을 위해
			// 기존 게시물(레코드)의 번호(num) 중 가장 큰 번호 알아내기
			String sql = "SELECT MAX(num) FROM board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			// 현재 게시물의 가장 큰 번호 + 1 값을 정수형 변수 num 에 저장하기
			// => 단, 기존 게시물이 하나도 없을 경우 기본값으로 사용할 값(1)을 미리 저장하기
			int num = 1;
			
			// 현재 게시물 최대 번호값이 존재할 경우(즉, 다음 레코드가 존재할 경우)
			// 해당 번호 + 1 값을 num 변수에 저장하기
			if(rs.next()) { // 다음 레코드(최대값)가 존재할 경우
//				num = rs.getInt("MAX(num)") + 1;
				num = rs.getInt(1) + 1;
			}
			
//			System.out.println("새 글 번호 : " + num);
			
			// 새 글 번호를 포함하여 전달받은 데이터를 board 테이블에 추가(= 글쓰기)
			// => 글번호는 계산된 새 글 번호(num) 사용, 작성일 : now() 함수, 조회수 : 0
			sql = "INSERT INTO board VALUES(?,?,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getPass());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return insertCount;
	}
	
}




















