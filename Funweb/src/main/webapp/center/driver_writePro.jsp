<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 한글 처리
request.setCharacterEncoding("UTF-8");

// 파일 업로드 관련 정보 처리를 위해 MultipartRequest 객체를 활용
// 1. 업로드할 파일이 저장되는 이클립스 프로젝트 상의 경로를 변수에 저장
String uploadPath = "/upload";
// 2. 업로드 할 파일의 크기를 정수형태로 지정(10MB 로 지정)
//    단, 파일 크기 지정 시 byte 단위부터 계산 과정을 차례대로 활용하면 유지보수 시 편리함
// int fileSize = 10485760; // 10MB 크기 직접 지정 시
int fileSize = 1024 * 1024 * 10; // byte(1) -> KB(1024 Byte) -> MB(1024 KB) -> 10MB 단위로 변환

// 3. 현재 프로젝트(서블릿)를 처리하는 객체를 서블릿 컨텍스트라고 하며
// 이 서블릿 컨텍스트를 객체 형태로 가져와서(request 객체의 getServletContext() 메서드 활용) 
// 프로젝트 상의 가상 업로드 경로에 대한 실제 업로드 경로를 알아내기(getRealPath() 메서드 활용)
ServletContext context = request.getServletContext();
// 4. 업로드할 파일이 저장되는 실제 경로를 저장할 변수 선언
String realPath = context.getRealPath(uploadPath); // 파라미터로 가상의 업로드 폴더명 전달
// D:\Shared\JSP\workspace_jsp\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Funweb 폴더 내에
// upload 폴더가 생성되어 있음
// => 주의! 워크스페이스 내의 프로젝트 폴더를 바로 진입하면 있는 upload 폴더는 가상의 폴더이다!
// out.print(realPath);

// 5. 게시물 작성 후 글쓰기 버튼 클릭 시 게시물 정보는 request 객체에 담겨 전달되므로
//    해당 파라미터들을 가져와서 BoardDTO 객체에 저장하기 위해서
//    request.getParameter() 메서드 대신 MultipartRequest 객체를 통해 가져와야 함
//    => 주의! MultipartRequest 객체를 사용하려면 cos.jar 라이브러리 필수!
//       (WEB-INF / lib 폴더에 복사 및 Build path 등록 필요)
// MultipartRequest 객체 생성
MultipartRequest multi = new MultipartRequest(
	request, // 1) 실제 요청 정보가 포함된 request 객체
	realPath, // 2) 실제 업로드 폴더 경로
	fileSize, // 3) 업로드 파일 크기
	"UTF-8", // 4) 한글 파일명에 대한 인코딩 방식
	new DefaultFileRenamePolicy() // 5) 중복 파일명에 대한 처리를 담당하는 객체
);

// 6. MultipartRequest 객체의 getParameter() 사용하여 폼 파라미터 데이터 가져와서 
//    FileBoardDTO 객체에 저장(주의! request.getParameter() 메서드가 아니다!)
FileBoardDTO fileBoard = new FileBoardDTO();
fileBoard.setName(multi.getParameter("name"));
fileBoard.setPass(multi.getParameter("pass"));
fileBoard.setSubject(multi.getParameter("subject"));
fileBoard.setContent(multi.getParameter("content"));
// 단, 파일 정보를 가져올 때 getParameter() 메서드 사용 불가!
// 1) 파일명을 관리하는 객체에 접근 가져오기
String fileElement = multi.getFileNames().nextElement().toString();
// 2) 1번에서 가져온 이름을 활용하여 원본 파일명과 실제 업로드 된 파일명 가져오기
String original_file = multi.getOriginalFileName(fileElement);
String file = multi.getFilesystemName(fileElement);

// out.print("실제 파일명 : " + realFile + "<br>원본 파일명 : " + original_file);
fileBoard.setOriginal_file(original_file);
fileBoard.setFile(file);

// FileBoardDAO 객체의 insertFileBoard() 메서드를 호출하여 글쓰기 작업 수행
// => 파라미터 : FileBoardDTO 객체    리턴타입 : int(insertCount)
FileBoardDAO fileBoardDAO = new FileBoardDAO();
int insertCount = fileBoardDAO.insertFileBoard(fileBoard);

// 작업 완료 후 결과 판별하여 글쓰기 성공 시 driver.jsp 페이지로 포워딩하고
// 실패 시 자바스크립트로 "파일 업로드 실패" 출력 후 이전페이지로 돌아가기
if(insertCount > 0) {
	response.sendRedirect("./driver.jsp");
} else {
	%>
	<script>
		alert("파일 업로드(글쓰기) 실패!");
		history.back();
	</script>
	<%
}
%>










