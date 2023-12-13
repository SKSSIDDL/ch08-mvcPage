$(function(){
	/* ============================================
	 * 장바구니 상품 주문 수령 변경
	 * ============================================*/
	$('.cart-modify').on('click',function(){
		//장바구니 수량 알아내기(부모(td)->자식(input)을 찾음)
		let input_quantity = $(this).parent().find('input[name="order_quantity"]');
		if(input_quantity.val()==''){
			alert('수량을 입력하세요');
			input_quantity.focus();
			return;
		}
		if(input_quantity.val()<1){
			alert('상품의 최소 수량은 1입니다');
								//태그에 명시한 value 값을 읽어옴(변경전 원래값)
			input_quantity.val(input_quantity.attr('value'));
			return;
		}
		//서버와 통신
		$.ajax({
			url:'modifyCart.do',
			type:'post',
			data:{cart_num:$(this).attr('data-cartnum'), 
				  item_num:$(this).attr('data-itemnum'), 
				  order_quantity:input_quantity.val()}, //커스텀 데이터 속성은 언더바x, 하이픈 사용
			dataType: 'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인 후 사용하세요');
				}else if(param.result == 'noSale'){
					alert('판매가 중지되었습니다');
					location.href='list.do';
				}else if(param.result == 'noQuantity'){
					alert('상품의 수량이 부족합니다');
					location.href='list.do';
				}else if(param.result == 'success'){
					alert('상품의 개수가 수정되었습니다');
					location.href='list.do';
				}else{
					alert('장바구니 상품 개수 수정 오류');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
	
	/* ============================================
	 * 장바구니 상품 삭제
	 * ============================================*/
	$('.cart-del').on('click', function(){
		//서버와 통신
		$.ajax({
			url:'deleteCart.do',
			type:'post',
			data:{cart_num:$(this).attr('data-cartnum')},
			dataType:'json',
			success:function(param){
				if(param.result =='logout'){
					alert('로그인 후 사용하세요');
				}else if(param.result == 'success'){
					alert('선택하신 상품을 삭제했습니다');
					location.href='list.do';
				}else{
					alert('장바구니 상품 삭제 오류');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	});
});