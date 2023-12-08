package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class UserDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품번호 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));
		
		ItemDAO dao = ItemDAO.getInstance();
		ItemVO item = dao.getItem(item_num);
		
		//내용 줄바꿈 처리(관리자만 입력하기 떄문에 useBrHtml, 사용자가 등록한다면 조금 더 제한을 둬야함)
		item.setDetail(StringUtil.useBrHtml(item.getDetail()));
		
		request.setAttribute("item", item);
		
		return "/WEB-INF/views/item/user_detail.jsp";
	}

}
