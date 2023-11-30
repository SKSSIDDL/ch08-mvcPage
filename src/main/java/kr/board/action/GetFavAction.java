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

public class GetFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		Map<String,Object> mapAjax = new HashMap<String,Object>(); //타입이 다르기 떄문에 object 사용
		
		//로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		BoardDAO dao = BoardDAO.getInstance();
		if(user_num == null) {//로그인 안된 경우
			mapAjax.put("status", "noFav"); //String 타입
		}else {//로그인 된 경우
			BoardFavVO boardFav = dao.selectFav(new BoardFavVO(board_num,user_num));
			
			if(boardFav!=null) {//좋아요 표시
				mapAjax.put("status", "yesFav");
			}else {//좋아요 미표시
				mapAjax.put("status", "noFav");
			}
		}
		mapAjax.put("count", dao.selectFavCount(board_num)); 
		//Integer 타입 - 로그인 여부에 따라 if와 else 둘다 사용되기 때문에 밖으로 뺌(좋아요 개수)
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP  경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
