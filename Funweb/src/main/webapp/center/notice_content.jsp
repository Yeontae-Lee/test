<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_content.jsp</title>
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
			<h1>Notice Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td></td>
					<td>글쓴이</td>
					<td></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td></td>
					<td>조회수</td>
					<td></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3"></td>
				</tr>
			</table>
			<div id="table_search">
				<input type="button" value="글수정" class="btn" onclick="#"> 
				<input type="button" value="글삭제" class="btn" onclick="#"> 
				<input type="button" value="글목록" class="btn" onclick="#">
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


