package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
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
	// 데이터베이스 작업을 위한 메서드 정의
	// 회원 가입 작업을 수행하는 insertMember() 메서드 정의
	// => 파라미터 : MemberDTO(member)   리턴타입 : int(insertCount)
	public int insertMember(MemberDTO member) {
		int insertCount = 0;
		
		// getConnection() 메서드를 호출하여 연결된 데이터베이스 관리 객체(Connection) 리턴
		con = getConnection();
		
		try {
			// 3단계. SQL 구문 작성 및 전달
			// member 테이블에 아이디, 패스워드, 이름, 가입일, 이메일, 폰번호, 주소, 전화번호 추가
			String sql = "INSERT INTO member VALUES(?,?,?,now(),?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPass());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getMobile());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getPhone());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			// 예외 발생 여부와 관계없이 수행할 작업 기술 - DB 자원 반환
			close(pstmt);
			close(con);
		}

		// 처리 결과 리턴
		return insertCount;
	}
	
	
	// 로그인 판별 작업을 수행하는 checkUser() 메서드 정의
	// => 파라미터 : MemberDTO(member)   리턴타입 : boolean(isLoginSuccess)
	public boolean checkUser(MemberDTO member) {
		boolean isLoginSuccess = false;
		
		try {
			// Connection 객체 리턴받기
			con = getConnection();
			
			// 3단계. SQL 구문 작성 및 전달
			// member 테이블의 id, pass 컬럼 데이터와 일치하는 레코드를 조회
			String sql = "SELECT * FROM member WHERE id=? AND pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPass());
			
			// 4단계. SQL 구문 실행 및 결과 처리
			rs = pstmt.executeQuery();
			
			// ResultSet 객체에 저장된 레코드가 있을 경우 로그인 성공, 아니면 실패
			if(rs.next()) { // 레코드 있을 경우(= 로그인 성공)
				isLoginSuccess = true;
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			// 예외 발생 여부와 관계없이 수행할 작업 기술 - DB 자원 반환
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return isLoginSuccess;
	}
	
}


















