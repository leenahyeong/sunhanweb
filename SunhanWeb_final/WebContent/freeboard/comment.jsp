<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
</head>
<body>
	<!-- 댓글 접고열기 갯수 -->
	<div class="comment_head">
		<i class="fas fa-angle-up" onclick="collapse()"></i> <span>댓글</span> <span
			class="comment_color">${cmt_count}</span>
	</div>

	<!-- 댓글보는곳+작성 -->
	<div id="comment_form" class="block">
		<!-- 댓글작성 -->
		<c:if test="${!empty loginUserID}">
			<form method="post" name="fbcmtForm">
				<div class="auto">
					<input type="hidden" name="cmt_bno">
					<input type="hidden" name="cmt_id" value="${loginUserID}">
					<input type="hidden" name="cmt_name" value="${loginUser.name}">
					<textarea class="comment_write outline" name="cmt_content"
						placeholder="댓글을 입력해주세요."></textarea>
					<input type="button" class="comment_btn" onclick="cmt_add()"
						value="작성">
				</div>
			</form>
		</c:if>

		<c:if test="${cmt_count ne 0}">
			<c:forEach var="list" items="${cmt_list}">
				
				<div class="s_comment">
					
					<span>${list.cmt_name}</span>
					<div class="inline">
						<c:if test="${list.cmt_id eq loginUserID}">
							<input type="button" value="수정"
								onclick="cmt_updateForm(${list.cmt_cno})" />
							<input type="button" value="삭제"
								onclick="cmt_delete(${list.cmt_cno})" />
						</c:if>
					</div>
					<span>${list.cmt_content}</span>
					<span>
						<fmt:formatDate value="${list.cmt_regdate}"
							pattern="yyyy-MM-dd HH:mm" />
					</span>
				</div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>
