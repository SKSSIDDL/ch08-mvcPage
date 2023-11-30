package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//동일한 작성자가 작성한 것인지 확인
		MultipartRequest multi = FileUtil.createFile(request);
		int board_num = Integer.parseInt(multi.getParameter("board_num"));
		//filename 읽어오기(getParameter 아님 주의!)
		String filename = multi.getFilesystemName("filename");
		
		BoardDAO dao = BoardDAO.getInstance();
		//수정전 데이터 반환
		BoardVO db_board = dao.getBoard(board_num);
		//작성자가 같은지 확인
		if(user_num != db_board.getMem_num()) { //불일치(파일 업로드 된 것을 지우기)
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		//로그인한 작성자 회원번호 일치(update진행)
		BoardVO board = new BoardVO();
		board.setBoard_num(board_num);
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		board.setIp(request.getRemoteAddr());
		board.setFilename(filename); //파일이 없으면 무반응이라 문제 없음(dao 파일 삭제 참고)
		
		//글 수정
		dao.updateBoard(board);
		if(filename != null) { //새 파일로 교체할 때 원래 파일을 제거
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		//JSP경로 반환
		return "redirect:/board/detail.do?board_num=" + board_num;
	}

}
