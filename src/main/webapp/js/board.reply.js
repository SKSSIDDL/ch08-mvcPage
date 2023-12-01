$(function(){
	//페이지 처리
	let rowCount = 10; //한 화면에 10개 보여지도록
	let currentPage;
	let count;
	
	//댓글 목록
	function selectList(pageNum){
		//pageNum currentPage에 보관
		currentPage = pageNum;
		
		//로딩 이미지 노출
		$('#loading').show();
		
		$.ajax({
			url:'listReply.do',
			type:'post',
			data:{pageNum:pageNum, rowCount:rowCount, board_num:$('#board_num').val()},
			//위에 페이지 처리를 위해 만들어둔 pageNum, rowCount가 입력됨(key:value의 쌍)
			dataType:'json',
			success:function(param){
				//로딩 이미지 감추기
				$('#loading').hide();
				count = param.count;
				
				//첫 페이지를 불러올 때 기존 데이터를 지우고 불러오기
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				
				$(param.list).each(function(index,item){
					let output = '<div class="item">';
					output += '<h4>' + item.id + '<h4>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
			},
			error:function(){
				//로딩 이미지 감추기
				$('#loading').hide();
				alert('네트워크 오류 발생');
			}
		});
	}
	
	//페이지 처리 이벤트 연결(다음 댓글 보기 버튼 클릭시 데이터 추가)
	
	//댓글 등록
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#re_content').val('').focus();
			return false;
		}
		
		//form 이하의 태그에 입력한 데이터를 모두 읽어오기(queryString 형식)
		let form_data = $(this).serialize();
		
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result =='success'){
					//정상적으로 등록되면 폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글을 다시 호출
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
		//기본 이벤트 제거
		event.preventDefault();
	});
	
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
	
	//textarea에 내용 입력시 글자수 체크(등록, 수정에 사용)
	$(document).on('keyup','textarea',function(){ //이벤트 명, 이벤트 발생 태그, function
		//입력한 글자수 구하기
		let inputLength = $(this).val().length;
		
		if(inputLength >300){//300자를 넘어선 경우
			$(this).val($(this).val().substring(0,300)); //데이터를 읽어와서 잘라내고 다시 textarea에 넣기-300자 이상 잘라내기
		}else{//300자 이하인 경우
			let remain = 300 - inputLength;
			remain += '/300'; //300자까지 가능하다고 알려줌
			if($(this).attr('id') == 're_content'){ 
				//등록폼 글자수 체크
				$('#re_first .letter-count').text(remain);
			}else{ 
				//수정폼 글자수 체크
				$('#mre_first .letter-count').text(remain);
			}
		}
	});
	
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		
	});
	
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	//댓글 수정폼 초기화
	function initModifyForm(){
		
	}
	
	//댓글 수정
	$(document).on('submit','#mre_form',function(){
		
	});
	
	//댓글 삭제(버튼 누르면 바로 삭제)
	$(document).on('click','.delete-btn',function(){
		
	});
	
	//초기 데이터(목록) 호출
	selectList(1); //1페이지 호출
	
});