package kr.item.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.item.vo.ItemVO;
import kr.util.DBUtil;

public class ItemDAO {
	//싱글턴 패턴
	private static ItemDAO instance = new ItemDAO();
	
	//내부 호출 O
	public static ItemDAO getInstance() {
		return instance;
	}
	
	//생성자(내부 호출 X)
	private ItemDAO() {}
	
	//관리자 - 상품 등록
	public void insertItem(ItemVO item)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO zitem (item_num,name,price,quantity,photo1,photo2,detail,status) "
					+ "VALUES (zitem_seq.nextval,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, item.getName());
			pstmt.setInt(2, item.getPrice());
			pstmt.setInt(3, item.getQuantity());
			pstmt.setString(4, item.getPhoto1());
			pstmt.setString(5, item.getPhoto2());
			pstmt.setString(6, item.getDetail());
			pstmt.setInt(7, item.getStatus());
			
			pstmt.executeUpdate();
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//관리자 - 상품 수정
	//관리자 - 상품 삭제
	//관리자/사용자 - 전체 상품 개수/검색 상품 개수
	//관리자/사용자 - 전체 상품 목록/검색 상품 목록
	//관리자/사용자 - 상품 상세
	
}
