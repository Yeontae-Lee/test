<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_write.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
	// 세션에 저장된 아이디를 id 변수에 저장
	// 단, 세션 아이디가 없을 경우(= null 일 경우) "회원만 접근 가능합니다" 출력 후 이전페이지로 돌아가기
	String id = (String)session.getAttribute("sessionId");
	if(id == null) {
		%>
		<script>
			alert("회원만 접근 가능합니다.");
			history.back();
		</script>
		<%
	}
	
	if(request.getParameter("num") == null) {
		%>
		<script>
			alert("잘못된 접근 입니다.");
			history.back();
		</script>
		<%
	}
	// FileBoardDAO 객체의 selectFileBoard() 메서드를 호출하여 글번호에 해당하는 게시물 조회
	int num = Integer.parseInt(request.getParameter("num"));
	
	FileBoardDAO fileBoardDAO = new FileBoardDAO();
	FileBoardDTO fileBoard = fileBoardDAO.selectFileBoard(num);
	%>	
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
			<h1>Driver Write</h1>
			<!-- 파일 업로드 기능을 동작시키려면 form 태그에 
					enctype 속성값으로 "multipart/form-data" 값 지정해야함 -->
			<form action="./driver_writePro.jsp" method="post" enctype="multipart/form-data">
				<table id="notice">
					<tr>
						<td>작성자</td>
						<td><input type="text" name="name" value="<%=fileBoard.getName()%>" readonly="readonly"></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pass" required="required"></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject" value="<%=fileBoard.getSubject()%>" required="required"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea rows="10" cols="20" name="content" required="required"><%=fileBoard.getContent()%></textarea></td>
					</tr>
					<tr>
						<td>기존 파일</td>
						<td><%=fileBoard.getOriginal_file() %></td>
					</tr>
					<tr>
						<td>파일(기존 파일 덮어쓰기)</td>
						<td><input type="file" name="file" value="<%=fileBoard.getOriginal_file() %>" required="required"></td>
					</tr>
				</table>

				<div id="table_search">
					<input type="submit" value="글쓰기" class="btn">
				</div>
			</form>
			<div class="clear"></div>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


