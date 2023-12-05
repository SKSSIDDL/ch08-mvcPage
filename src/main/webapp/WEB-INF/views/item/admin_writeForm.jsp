<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#write_form').submit(function(){
			if($('input[type="radio"]:checked').length <1){ <%-- 배열로 인식해서 길이 check --%>
				alert('상품표시여부를 지정하세요');
				return false;
			}
			
			let items = document.querySelectorAll('.input-check');
			for(let i=0; i<items.length; i++){
				if(items[i].value.trim()==''){ //비교
				let label = document.querySelector('label[for="'+items[i].id+'"]');
				alert(label.textContent + ' 항목은 필수 입력');
				items[i].value=''; //대입
				items[i].focus();
				return false;
				}
			}
		});
	});
</script>
</head>
<body>
	<div class="page-main">
		 <jsp:include page="/WEB-INF/views/common/header.jsp"/>
		 <div class="content-main">
		 	<h2>상품 등록</h2>
		 	<form action="adminWrite.do" method="post" enctype="multipart/form-data" id="write_form">
		 		<ul>
		 			<li>
		 				<label>상품표시여부</label>
		 				<input type="radio" name="status" value="1" id="status1">미표시
		 				<input type="radio" name="status" value="2" id="status2">표시
		 			</li>
		 			<li>
		 				<label for="name">상품명</label>
		 				<input type="text" class="input-check" name="name" id="name" maxlength="10">
		 			</li>
		 			<li>
		 				<label for="price">가격</label>
		 				<input type="number" class="input-check" name="price" id="price" min="1" max="99999999">
		 			</li>
		 			<li>
		 				<label for="quantity">수량</label>
		 				<input type="number" class="input-check" name="quantity" id="quantity" min="0" max="99999">
		 			</li>
		 			<li>
		 				<label for="photo1">상품사진1</label>
		 				<input type="file" class="input-check" name="photo1" id="photo1" accept="image/gif,image/png,image/jpeg">
		 			</li>
		 			<li>
		 				<label for="photo2">상품사진2</label>
		 				<input type="file" class="input-check" name="photo2" id="photo2" accept="image/gif,image/png,image/jpeg">
		 			</li>
		 			<li>
		 				<label for="detail">상품설명</label>
		 				<textarea rows="5" cols="30" class="input-check" name="detail" id="detail"></textarea>
		 			</li>
		 		</ul>
		 		<div class="align-center">
		 			<input type="submit" value="등록">
		 			<input type="button" value="목록" onclick="location.href='adminList.do'">
		 		</div>
		 	</form>
		 </div>
	</div>
</body>
</html>