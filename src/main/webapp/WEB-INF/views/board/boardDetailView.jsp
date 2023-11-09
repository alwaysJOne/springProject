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
                    <td colspan="3">게시글제목</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>admin</td>
                    <th>작성일</th>
                    <td>2022-02-01</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
						
							<!-- case1 -->
                        		<a href="" download="">이미지.jpg</a>
							<!-- case2 -->
								첨부파일이 없습니다.
		
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">게시글내용입니다</p></td>
                </tr>
            </table>
            <br>

   			
   			<!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
            <div align="center">
                <a class="btn btn-primary" onclick="">수정하기</a>
                <a class="btn btn-danger" onclick="">삭제하기</a>
            </div>
            <br><br>
           
            
             <form action="" method="post" id="postForm">
           		<input type="hidden" name=bno value="7">
           		<input type="hidden" name="filePath" value="이미지.jpg">
             </form>
            
          
            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                	
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" readonly cols="55" rows="2" style="resize:none; width:100%;">로그인 후 이용 가능합니다.</textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary disabled">등록하기</button></th>
                    </tr>
                		
                    
                     <tr> 
                        <th colspan="2">
                            <textarea class="form-control" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary" onclick="addReply();">등록하기</button></th>
                    </tr>
                            
                
                    
                    
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글남깁니다</td>
                        <td>2022-05-10</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>test샘플</td>
                        <td>2022-08-10</td>
                    </tr>
                </thead>
                <tbody>
                  
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
</body>
</html>