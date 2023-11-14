<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<jsp:include page="common/header.jsp" />
	
	<div class="innerOuter">
		<br><br>

		<h4>게시글 Top 5</h4>
		<br>
		<div align="right"><a href="list.bo" class="table table-hover" align="center">더보기</a></div>
		<br>

		<table id="boardList" class="table table-hover" align="center">
			<thead>
				<tr>
					<th>글번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
					<th>첨부파일</th>
				</tr>
			</thead>
			<tbody>
				<!-- 현재 조회수가 가장 높은 상위 5개의 게시글 조회해서 뿌리기(ajax이용해서)-->
			</tbody>
		</table>
	</div>
	
	<!-- 게시글 조회수 높은 순으로 5개 가져오는 ajax -->
	<script>
		$(function(){
			topBoardList();
		})
		
		function topBoardList(){
			$.ajax({
				url: "topList.bo" ,
				success: function(data){
					drawBoardList(data);
				},
				error: function(){
					console.log("top5 ajax 실패")
				}
			})
		}

		function drawBoardList(data){
			for (let rowData of data) {
				const tr = document.createElement('tr');
				tr.innerHTML = "<td>" + rowData.boardNo + "</td>" +
								"<td>" + rowData.boardTitle + "</td>" +
								"<td>" + rowData.boardWriter + "</td>" +
								"<td>" + rowData.count + "</td>" +
								"<td>" + rowData.createDate + "</td>" +
								"<td>" + (rowData.originName ? "★" : "") + "</td>";

				tr.onclick =  function(){
					location.href = "detail.bo?bno=" + rowData.boardNo;
				}
				
				document.querySelector("#boardList>tbody").appendChild(tr);
			}
		}
	</script>

	<jsp:include page="common/footer.jsp" />
	
</body>
</html>