package svc;

// JdbcUtil 클래스의 static 메서드를 클래스명 없이 호출하기 위한 static import
import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

// Action 클래스로부터 지시를 받아 DAO 클래스와 상호작용을 통해 실제 DB 작업을 수행하는 클래스 = Service 클래스
public class BoardWriteProService {

    // 글쓰기 작업 요청을 위한 registArticle() 메서드 정의
    public boolean registArticle(BoardBean boardBean) {
//      System.out.println("BoardWriteProService - registArticle()");

        // 1. 글쓰기 작업 요청 처리 결과를 저장할 boolean 타입 변수 선언
        boolean isWriteSuccess = false;

        // 2. JdbcUtil 객체로부터 Connection Pool 에 저장된 Connection 객체 가져오기(공통)
        Connection con = getConnection(); // JdbcUtil.getConnection()

        // 3. BoardDAO 클래스로부터 BoardDAO 객체 가져오기(공통)
        BoardDAO boardDAO = BoardDAO.getInstance();

        // 4. BoardDAO 객체의 setConnection() 메서드를 호출하여 Connection 객체를 전달(공통)
        boardDAO.setConnection(con);

        // 5. BoardDAO 객체의 XXX 메서드를 호출하여 XXX 작업 수행 및 결과 리턴 받기
        // insertArticle() 메서드를 호출하여 글등록 작업 수행 및 결과 리턴받아 처리
        // => 파라미터 : BoardBean 객체, 리턴값 : int(insertCount)
        int insertCount = boardDAO.insertArticle(boardBean);

        // 리턴값에 대한 결과 처리
        if (insertCount > 0) { // 작업이 성공했을 경우
            // INSERT 작업이 성공했을 경우 트랜잭션 적용을 위해
            // JdbcUtil 클래스의 commit() 메서드를 호출하여 commit 작업 수행
            commit(con); // JdbcUtil.commit(con)

            // 작업 처리 결과를 성공으로 표시하기 위해 isWriteSuccess 를 true 로 지정
            isWriteSuccess = true;
        } else { // 작업이 실패했을 경우
            // INSERT 작업이 실패했을 경우 트랜잭션 적용 취소를 위해
            // JdbcUtil 클래스의 rollback() 메서드를 호출하여 rollback 작업 수행
            rollback(con); // JdbcUtil.rollback(con)
        }

        // 6. JdbcUtil 객체로부터 가져온 Connection 객체를 반환(공통)
        close(con); // JdbcUtil.close(con)

        // 7. 작업 처리 결과 리턴
        return isWriteSuccess;
    }

    public void registIp(String ip) {
        Connection con = getConnection(); // JdbcUtil.getConnection()

        BoardDAO boardDAO = BoardDAO.getInstance();

        boardDAO.setConnection(con);

//        int insertCount = boardDAO.insertIpInfo(ip);

//        if (insertCount > 0) {
//            commit(con);
//        } else {
//            rollback(con);
//        }

        close(con);
    }

}
