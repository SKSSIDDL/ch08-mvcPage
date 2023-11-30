package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardFavVO;
import kr.controller.Action;

public class WriteFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		//로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			
			BoardFavVO favVO = new BoardFavVO();
			favVO.setBoard_num(board_num);
			favVO.setMem_num(user_num);
			
			BoardDAO dao = BoardDAO.getInstance();
			//좋아요 등록 여부
			BoardFavVO db_fav = dao.selectFav(favVO);
			
			if(db_fav != null) {//좋아요가 등록되어 있는 경우
				//좋아요 삭제
				dao.deleteFav(db_fav);
				mapAjax.put("status", "noFav");
			}else {//좋아요 등록이 되어있지 않은 경우
				dao.insertFav(favVO);
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(board_num)); //변경했다면 count값도 넘겨줘야함
		}
		
		//JSON문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
