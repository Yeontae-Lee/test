<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 한글 처리
request.setCharacterEncoding("UTF-8");

// String name = request.getParameter("name");
// String pass = request.getParameter("pass");
// String subject = request.getParameter("subject");
// String content = request.getParameter("content");

// BoardDTO 객체에 데이터 저장
// BoardDTO board = new BoardDTO();
// board.setName(name);
// board.setPass(pass);
// board.setSubject(subject);
// board.setContent(content);
%>

<%-- 자바빈 객체를 활용한 파라미터 저장(BoardDTO 클래스 활용(board)) --%>
<jsp:useBean id="board" class="board.BoardDTO"></jsp:useBean>
<jsp:setProperty name="board" property="*" />

<%
// BoardDAO 객체의 insertBoard() 메서드를 호출하여 게시물 등록 작업 수행
// => 파라미터 : BoardDTO 객체    리턴타입 : int(insertCount)
BoardDAO boardDAO = new BoardDAO();
int insertCount = boardDAO.insertBoard(board);

// 게시물 작성 성공 시 notice.jsp 페이지로 포워딩
// 게시물 작성 실패 시 자바스크립트 사용하여 "글쓰기 실패" 출력 후 이전페이지로 돌아가기
if(insertCount > 0) {
	response.sendRedirect("./notice.jsp");
} else {
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
}

%>






