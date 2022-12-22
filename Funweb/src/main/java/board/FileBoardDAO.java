package board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileBoardDAO {
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
	// 글쓰기(파일 업로드 포함) 작업을 수행할 insertFileBoard() 메서드 정의
	// => 파라미터 : FileBoardDTO 객체    리턴타입 : int(insertCount)
	public int insertFileBoard(FileBoardDTO fileBoard) {
		int insertCount = 0;
		
		// DB 연결
		con = getConnection();
		
		try {
			// 새 글 작성 시 부여할 새 글의 번호 계산을 위해
			// 기존 게시물(레코드)의 번호(num) 중 가장 큰 번호 알아내기
			String sql = "SELECT MAX(num) FROM file_board";
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
			sql = "INSERT INTO file_board VALUES(?,?,?,?,?,?,?,now(),0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, fileBoard.getName());
			pstmt.setString(3, fileBoard.getPass());
			pstmt.setString(4, fileBoard.getSubject());
			pstmt.setString(5, fileBoard.getContent());
			pstmt.setString(6, fileBoard.getFile());
			pstmt.setString(7, fileBoard.getOriginal_file());
			
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
	
	// 게시물 전체 갯수 조회를 위한 getListCount() 메서드 정의
	// => 파라미터 : 없음   리턴타입 : int(listCount)
	public int getListCount() {
		int listCount = 0;
		
		con = getConnection();
		
		try {
			// 원하는 컬럼의 갯수를 조회하려면 COUNT(컬럼명 또는 *) 함수 사용
			String sql = "SELECT COUNT(*) FROM file_board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1); // 또는 "COUNT(*)" 지정
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return listCount;
	}
	
//	// 게시물 목록 조회를 위한 selectFileBoardList() 메서드 정의
//	public ArrayList<FileBoardDTO> selectFileBoardList() {
//		ArrayList<FileBoardDTO> fileBoardList = null;
//		
//		// DB 연결
//		con = getConnection();
//		
//		try {
//			// 전체 게시물 목록 조회(번호 기준 내림차순 정렬)
//			String sql = "SELECT * FROM file_board ORDER BY num DESC";
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			fileBoardList = new ArrayList<FileBoardDTO>();
//			
//			while(rs.next()) {
//				// 1개 레코드 정보를 저장할 FileBoardDTO 객체 생성 후 데이터 저장
//				// => 단, 파일명은 목록 출력 대상에 포함되지 않으므로 저장 생략 가능
//				FileBoardDTO fileBoard = new FileBoardDTO();
//				fileBoard.setNum(rs.getInt("num"));
//				fileBoard.setName(rs.getString("name"));
////				fileBoard.setPass(rs.getString("pass"));
//				fileBoard.setSubject(rs.getString("subject"));
////				fileBoard.setContent(rs.getString("content"));
////				fileBoard.setFile(rs.getString("file"));
////				fileBoard.setOriginal_file(rs.getString("original_file"));
//				fileBoard.setDate(rs.getDate("date"));
//				fileBoard.setReadcount(rs.getInt("readcount"));
//				
//				// 모든 레코드를 저장할 ArrayList 객체에 FileBoardDTO 객체를 추가
//				fileBoardList.add(fileBoard);
//			}
//			
//		} catch (SQLException e) {
//			System.out.println("SQL 구문 오류 발생!");
//			e.printStackTrace();
//		} finally {
//			close(rs);
//			close(pstmt);
//			close(con);
//		}
//		
//		return fileBoardList;
//	}
	
	// 게시물 목록 조회를 위한 selectFileBoardList() 메서드 정의 => 페이징 처리 추가
	public ArrayList<FileBoardDTO> selectFileBoardList(int page, int limit) {
		ArrayList<FileBoardDTO> fileBoardList = null;
		
		// DB 연결
		con = getConnection();
		
		try {
			// 현재 페이지에서 조회할 레코드의 첫번째 행번호 계산
			// 1페이지일 때 0, 2페이지일 때 10, 3페이지일 때 20
			// => (현재페이지번호 - 1) * 페이지 당 게시물 수
			int startRow = (page - 1) * limit;
			System.out.println("시작 행번호 : " + startRow);
			
			// 전체 게시물 목록 조회(번호 기준 내림차순 정렬)
			// => LIMIT 절 뒤의 파라미터는 시작 행번호, 레코드 수를 지정
			String sql = "SELECT * FROM file_board ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			fileBoardList = new ArrayList<FileBoardDTO>();
			
			while(rs.next()) {
				// 1개 레코드 정보를 저장할 FileBoardDTO 객체 생성 후 데이터 저장
				// => 단, 파일명은 목록 출력 대상에 포함되지 않으므로 저장 생략 가능
				FileBoardDTO fileBoard = new FileBoardDTO();
				fileBoard.setNum(rs.getInt("num"));
				fileBoard.setName(rs.getString("name"));
//					fileBoard.setPass(rs.getString("pass"));
				fileBoard.setSubject(rs.getString("subject"));
//					fileBoard.setContent(rs.getString("content"));
//					fileBoard.setFile(rs.getString("file"));
//					fileBoard.setOriginal_file(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				// 모든 레코드를 저장할 ArrayList 객체에 FileBoardDTO 객체를 추가
				fileBoardList.add(fileBoard);
			}
			
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return fileBoardList;
	}
	
	// 글번호에 해당하는 게시물 1개 정보를 조회하는 selectFileBoard() 메서드 정의
	// => 파라미터 : 글번호(num)    리턴타입 : FileBoardDTO(fileBoard)
	public FileBoardDTO selectFileBoard(int num) {
		FileBoardDTO fileBoard = null;
		
		con = getConnection();
	
		try {
			String sql = "SELECT * FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fileBoard = new FileBoardDTO();
				fileBoard.setName(rs.getString("name"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setContent(rs.getString("content"));
				fileBoard.setFile(rs.getString("file"));
				fileBoard.setOriginal_file(rs.getString("original_file"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
			}
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		} 
		
		return fileBoard;
	}
	
}




















