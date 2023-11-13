<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<style>
	table * {margin:5px;}
	table {width:100%;}
</style> 
</head>
<body>
	<jsp:include page="../common/header.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="list.bo">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${b.boardTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${b.boardWriter}</td>
                    <th>작성일</th>
                    <td>${b.createDate}</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
						<c:choose>
							<c:when test="${ not empty b.originName }">
								<!-- case1 -->
                        		<a href="${b.changeName}" download="${b.originName}">${b.originName}</a>
                        	</c:when>
                        	<c:otherwise>
								<!-- case2 -->
								첨부파일 없음
							</c:otherwise>
						</c:choose>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${b.boardContent}</p></td>
                </tr>
            </table>
            <br>

   			<c:if test="${ loginUser.userId eq b.boardWriter }">
	   			<!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
	            <div align="center">
	                <a class="btn btn-primary" onclick="postFormSubmit(1)">수정하기</a>
	                <a class="btn btn-danger" onclick="postFormSubmit(2)">삭제하기</a>
	            </div>
	            <br><br>
            </c:if>
            
             <form action="" method="post" id="postForm">
           		<input type="hidden" name="bno" value="${b.boardNo}">
           		<input type="hidden" name="filePath" value="${b.changeName}">
             </form>
             
            <script>
            	function postFormSubmit(num){
            		if(num === 1){
                        $("#postForm").attr('action', 'updateForm.bo');
            			//document.querySelector("#postForm").setAttribute('action','updateForm.bo');
            		} else {
                        $("#postForm").attr('action', 'delete.bo');
            			//document.querySelector("#postForm").setAttribute('action','delete.bo');
            		}
                    $("#postForm").submit();
                    //document.querySelector("#postForm").submit();
            	}
            </script>
            
          
            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	<c:choose>
                		<c:when test="${ empty loginUser }">
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" readonly cols="55" rows="2" style="resize:none; width:100%;">로그인 후 이용 가능합니다.</textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary disabled">등록하기</button></th>
		                    </tr>
	                	</c:when>
	                    <c:otherwise>
		                    <tr> 
		                        <th colspan="2">
		                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
		                        </th>
		                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
		                    </tr>
	                    </c:otherwise>
                    </c:choose>  
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>      
                </thead>
                <tbody>
                 
                </tbody>
            </table>
        </div>
        <br><br>

        <script>
            $(function(){
                //댓글 조회하는 함수호출
                selectReplyList();
            })

            function selectReplyList(){
                $.ajax({
                    url: "rlist.bo",
                    data: {
                        bno: ${b.boardNo}
                    },
                    success: function(list){
                        let str = "";
                        for (reply of list) {
                            str += ("<tr>" +
                                        "<th>"+ reply.replyWriter +"</th>" +
                                        "<td>"+ reply.replyContent +"</td>" +
                                        "<td>"+ reply.createDate +"</td>" +
                                    "</tr>")
                        }

                        //$("#replyArea tbody").html(str);
                        document.querySelector("#replyArea tbody").innerHTML = str;
                        document.querySelector("#rcount").innerHTML = list.length;         

                    },
                    error: function(){
						console.log("rlist.bo ajax통신 실패");
                    }
                })
            }
            
            //댓글을 추가해주는 메서드
            function addReply(){
            	$.ajax({
                    url: "rinsert.bo",
                    data: {
                        refBno: '${b.boardNo}',
            			replyWriter: '${loginUser.userId}',
            			replyContent: $("#content").val()
                    },
                    success: function(res){
                             //성공시 다시 그려주기
						if (res === "success"){
							selectReplyList();
							$("#content").val("");
						}
                    },
                    error: function(){
						console.log(" ajax통신 실패");
                    }
                })
            }

        </script>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
</body>
</html>