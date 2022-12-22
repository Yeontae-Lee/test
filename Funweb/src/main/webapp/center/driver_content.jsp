<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_content.jsp</title>
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
		<%
		// FileBoardDAO 객체의 selectFileBoard() 메서드를 호출하여 글번호에 해당하는 게시물 조회
		int num = Integer.parseInt(request.getParameter("num"));
		
		FileBoardDAO fileBoardDAO = new FileBoardDAO();
		FileBoardDTO fileBoard = fileBoardDAO.selectFileBoard(num);
		%>
		<article>
			<h1>Driver Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=num %></td>
					<td>글쓴이</td>
					<td><%=fileBoard.getName() %></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=fileBoard.getDate() %></td>
					<td>조회수</td>
					<td><%=fileBoard.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=fileBoard.getSubject() %></td>
				</tr>
				<tr>
					<td>파일</td>
					<!-- 사용자가 실제 업로드할 때의 파일명(원본파일명)을 표시 -->
					<td colspan="3">
						<%=fileBoard.getOriginal_file() %>&nbsp;&nbsp;
						<!-- 
						실제 파일과 연결하여 다운로드를 위한 하이퍼링크 작성(HTML5 기능)
						하이퍼링크 태그에 download 속성 지정 시 파일 다운로드 기능 동작하며
						download 속성값에 다운로드할 파일명 지정 가능하므로 원본 파일의 이름을 속성값으로 전달
						-->
						<a href="../upload/<%=fileBoard.getFile()%>" download="<%=fileBoard.getOriginal_file() %>">
							<input type="button" value="다운로드">
						</a>
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"><%=fileBoard.getContent() %></td>
				</tr>
			</table>


			<table>
				<tr><td>댓글입니다.</td><td>admin</td><td>2022-01-24 14:21:30</td></tr>
				<tr><td>댓글222</td><td>admin</td><td>2022-01-24 14:23:30</td></tr>
				<tr><td>댓글333</td><td>hong</td><td>2022-01-24 22:23:30</td></tr>
			</table>
			
			<div>
				<textarea rows="4" cols="30"></textarea>
				<input type="button" value="댓글달기" onclick="#">
			</div>


			<div id="table_search">
				<input type="button" value="글수정" class="btn" onclick="location.href='./driver_update.jsp?num=<%=num%>&page=<%=request.getParameter("page")%>'"> 
				<input type="button" value="글삭제" class="btn" onclick="location.href='./driver.delete?num=<%=num%>&page=<%=request.getParameter("page")%>'"> 
				<input type="button" value="글목록" class="btn" onclick="location.href='./driver.jsp?page=<%=request.getParameter("page")%>'">
			</div>

			<div class="clear"></div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


