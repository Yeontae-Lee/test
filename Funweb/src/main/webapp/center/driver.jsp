<%@page import="java.util.ArrayList"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_center"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="./notice.jsp">Notice</a></li>
				<li><a href="#">Public News</a></li>
				<li><a href="./driver.jsp">Driver Download</a></li>
				<li><a href="#">Service Policy</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>Driver Download</h1>
			<table id="notice">
				<tr>
					<th class="tno">No.</th>
					<th class="ttitle">Title</th>
					<th class="twrite">Writer</th>
					<th class="tdate">Date</th>
					<th class="tread">Read</th>
				</tr>
				<%
				FileBoardDAO fileBoardDAO = new FileBoardDAO();
				
				// ----------------- 페이징 처리를 위한 추가 부분 ----------------
				int pageNum = 1; // 현재 페이지를 저장할 변수
				
				// request 객체를 통해 전달받은 페이지 번호(page 파라미터)가 있을 경우(null 이 아닐 경우)
				// => 전달받은 page 파라미터 값을 pageNum 변수에 저장
				if(request.getParameter("page") != null) {
					pageNum = Integer.parseInt(request.getParameter("page"));
				}
// 				out.print("현재 페이지 번호 : " + pageNum);
				
				// 게시물 목록을 가져오기 전 전체 게시물 목록 갯수 조회를 위한 getListCount() 메서드 호출
				int listCount = fileBoardDAO.getListCount(); // 전체 게시물 수
				int listLimit = 10; // 페이지 당 보여줄 게시물 수
				int pageLimit = 10; // 페이지 당 보여줄 페이지 수
				
				// 페이징 처리를 위한 계산 작업
				// 1. 전체 페이지 수 계산(총 게시물 수 / 페이지 당 게시물 수 + 0.9)
				// => 총 게시물 수 / 페이지 당 게시물 수를 실수로 연산하려면 double 타입 형변환
				// => 계산된 결과값을 다시 정수형으로 변환
// 				int maxPage = (int)((double)listCount / listLimit + 0.9);
				
				// Math.ceil() 메서드를 사용하여 올림 처리할 경우
				int maxPage = (int)Math.ceil((double)listCount / listLimit);	
				
				// 2. 현재 페이지에서 보여줄 시작 페이지 번호(1, 11, 21 등의 시작 번호) 계산
				int startPage = ((int)((double)pageNum / pageLimit + 0.9) - 1) * pageLimit + 1;
				
				// 3. 현재 페이지에서 보여줄 끝 페이지 번호(10, 20, 30 등의 끝 번호) 계산
				int endPage = startPage + pageLimit - 1;
				
				// 4. 끝 페이지가 현재 페이지에서 표시할 최대 페이지 수보다 클 경우
				//    끝 페이지 번호를 총 페이지 수로 대체
				if(endPage > maxPage) {
					endPage = maxPage;
				}
				// ---------------------------------------------------------------
				
				// FileBoardDAO 객체의 selectFileBoardList() 메서드를 호출하여 게시물 목록 리턴받기
				// => 파라미터 : 없음    리턴타입 : ArrayList<FileBoardDTO>
// 				ArrayList<FileBoardDTO> fileBoardList = fileBoardDAO.selectFileBoardList();
				
				// 게시물 목록 가져올 때 페이지에서 표시할 목록 갯수만큼만 조회할 경우
				// => 파라미터로 현재 페이지번호와 페이지 당 게시물 수 전달
				ArrayList<FileBoardDTO> fileBoardList = fileBoardDAO.selectFileBoardList(pageNum, listLimit);
				
				// ArrayList 객체가 null 이 아니고, 저장된 객체가 1개 이상일 때만 목록 출력
				if(fileBoardList != null && fileBoardList.size() > 0) {
					for(FileBoardDTO fileBoard : fileBoardList) {
						%>
						<tr onclick="location.href='./driver_content.jsp?num=<%=fileBoard.getNum() %>&page=<%=pageNum%>'">
							<td><%=fileBoard.getNum() %></td>
							<td class="left"><%=fileBoard.getSubject() %></td>
							<td><%=fileBoard.getName() %></td>
							<td><%=fileBoard.getDate() %></td>
							<td><%=fileBoard.getReadcount() %></td>
						</tr>
						<%
					}
				} else {
				%>
				<!-- 게시물 목록이 없을 경우 -->
				<tr>
					<td colspan="5">작성된 게시물이 없습니다.</td>
				</tr>
				<%} %>
			</table>
			<div id="table_search">
				<input type="button" value="글쓰기" class="btn" 
						onclick="location.href='./driver_write.jsp'">
			</div>
			<div id="table_search">
				<form action="#" method="post">
					<input type="text" name="search" class="input_box">
					<input type="submit" value="Search" class="btn">
				</form>
			</div>
			
			<!-- 페이지 목록 출력하는 곳 -->
			<div class="clear"></div>
			<div id="page_control">
				<!-- 
				이전페이지 버튼은 현재 페이지 번호가 시작페이지 번호보다 클 때만 링크 표시하며
				현재 페이지번호 - 1 값을 파라미터로 전달
				-->
				<%if(pageNum == startPage) { %>
					Prev&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<%} else { %>
					<a href="./driver.jsp?page=<%=pageNum - 1%>">Prev</a>
				<%} %>
				
				<!-- 페이지 목록은 시작 페이지번호부터 끝 페이지 번호까지 차례대로 표시 -->
				<%for(int i = startPage; i <= endPage; i++) { %>
					<!-- 페이지 번호 클릭 시 driver.jsp 페이지로 페이지번호(page)를 전달 -->
					<!-- 단, 현재 페이지 번호는 하이퍼링크 없이 표시 -->
					<%if(i == pageNum) { %>
						<%=i %>
					<%} else { %>
						<a href="./driver.jsp?page=<%=i%>"><%=i %></a>
					<%} %>
				<%} %>
				
				<!-- 
				다음페이지 버튼은 현재 페이지 번호가 끝페이지 번호보다 작을 때만 링크 표시하며
				현재 페이지번호 + 1 값을 파라미터로 전달
				-->
				<%if(pageNum == endPage) { %>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Next
				<%} else { %>
					<a href="./driver.jsp?page=<%=pageNum + 1%>">Next</a>
				<%} %>
			</div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>









