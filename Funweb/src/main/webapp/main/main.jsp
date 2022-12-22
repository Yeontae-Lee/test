<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main/main.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/front.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"/>
		<!-- 헤더 들어가는곳 -->
		  
		<div class="clear"></div>   
		<!-- 본문들어가는 곳 -->
		<!-- 메인 이미지 -->
		<div id="main_img"><img src="../images/main_img.jpg"></div>
		<!-- 본문 내용 -->
		<article id="front">
		  	<div id="solution">
		  		<div id="hosting">
		  			<h3>Web Hosting Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  		<div id="security">
		  		  	<h3>Web Security Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  		<div id="payment">
		  			<h3>Web Payment Solution</h3>
					<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
		  		</div>
		  	</div>
		  	<div class="clear"></div>
			<div id="sec_news">
				<h3><span class="orange">최근 공지사항</span></h3>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin 
					quis anteProin quis anteProin quis anteProin 
					quis ante......</dd>
				</dl>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin 
					quis anteProin quis anteProin quis anteProin 
					quis ante......</dd>
				</dl>
			</div>
		
			<div id="news_notice">
		  		<h3 class="brown">News &amp; Notice</h3>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin 
					quis anteProin quis anteProin quis anteProin 
					quis ante......</dd>
				</dl>
				<dl>
					<dt>Vivamus id ligula....</dt>
					<dd>Proin quis ante Proin quis anteProin 
					quis anteProin quis anteProin quis anteProin 
					quis ante......</dd>
				</dl>
		  	</div>
	  	</article>
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>










