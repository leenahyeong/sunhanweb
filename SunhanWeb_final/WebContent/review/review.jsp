<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>선한영향력</title>
<!-- Vendor CSS Files -->
<link href="resoures/css/bootstrap.min.css" rel="stylesheet">
<link href="resoures/css/owl.carousel.min.css" rel="stylesheet">
<link href="/your-path-to-fontawesome/css/fontawesome.css"
   rel="stylesheet">
<!-- Template Main CSS File -->
<link href="resoures/cssmain/style.css" rel="stylesheet">
<link href="resoures/cssmain/custom.css?ver=2" rel="stylesheet">
<link rel="stylesheet"
   href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />
<script src="resoures/js/jquery.min.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

<link href="resoures/cssmain/starrr.css?ver=1.1" rel="stylesheet">
<script src="resoures/js/starrr2.js?ver=1.8"></script>

<style>
.main-nav li {
   min-width: 150px;
}

.s_comment span {
   font-weight: 300 !important;
}

.r_name a {
   color: #3a3a3a !important;
    font-size: 16px !important;
   font-weight: 450 !important;
}

ion-icon{
   vertical-align: initial;
    color: #3a3a3a;
    font-size: 18px;
}

.s_comment {
   border: 1px solid #ddd !important;
   padding: 10px;
   margin-top: 40px;
   margin-bottom: 0;
}

.comment_color {
   vertical-align: initial !important;
   font-size: 26px;
}

.r_name {
   font-size: 15px;
   padding: 0 0 5px 0;
}

.comment_btn {
   border: 1px solid #d2d2d2 !important;
   border-left: none !important;
   background: #f9f9f9 !important;
   font-size: 14px !important;
   font-weight: 400 !important;
}

.comment_btn:hover{
   color: #333;
}

.inline .star_date {
   margin: 0 10px;
}

.inline {
   margin-left: 0 !important;
}

.re {
   background: #f6f6f6;
   margin-top: 0px;
   border-top: none !important;
}

.pd20 {
   padding: 20px 0 0 0;
}

</style>


</head>
<body>
   <%@include file="../header.jsp"%>

   <main id="main" class="container-flude">
      <section class="auto cover_img">
         <div class="jumbotron jumbotron-fluid">
            <div class="container">
               <h1 class="display-4">
                  <a href="review.do?userid=${loginUserID}" class="fff">리뷰</a>
               </h1>
               <p class="lead">모든 리뷰를 확인할 수 있습니다.</p>
            </div>
         </div>
      </section>

      <div class="container real_c">
         <section class="auto search_sec">
            <form>
               <div class="float-right searchbar"></div>
            </form>
         </section>

         <section class="auto">
            <h3>검색조건</h3>

            <form name="searchForm">
               <div class="card-body search_div">
                  <table class="" id="search_table">
                     <tbody>
                        <tr>
                           <c:choose>
                              <c:when test="${loginUser.admin eq 0}">
                                 <th width="20%">가게명검색</th>
                              </c:when>
                              <c:otherwise>
                                 <th>유저아이디검색</th>
                              </c:otherwise>
                           </c:choose>
                           <input type="hidden" class="search_text" name="userid" value="${loginUserID}">
                           <td><input type="text" class="search_text"
                              name="search_userid" value="${keyword}"></td>
                        </tr>

                        <tr>
                           <th>날짜</th>
                           <td><input class="datepicker search_text"
                              name="start_date" value="${start_date}"> <span>-</span>
                              <input class="search_text datepicker" name="end_date"
                              value="${end_date}"></td>
                        </tr>

                        <tr class="center">
                           <td colspan="2"><input type="submit" value="검색"
                              class="search_btn" id="searchBtn"></td>
                        </tr>
                     </tbody>
                  </table>
               </div>
            </form>


         </section>

         <section class="auto">
            <div>
               <div class="comment_head">
                  <c:choose>
                     <c:when test="${loginUser.admin eq 0}">
                        <h3 class="inline">내가 작성한 리뷰</h3>
                     </c:when>
                     <c:otherwise>
                        <h3 class="inline">내 가게 리뷰</h3>
                     </c:otherwise>
                  </c:choose>
                  <span class="comment_color">${count.get(0)}</span>
                  <div class="" style="margin:3px 0">
                  <h5 class="inline c_999">사장님 댓글</h5>
                  <span class="c_999 vertical_initial">${count.get(1)}</span>
                  </div>
               </div>

               <c:forEach var="lists" items="${list}" varStatus="status">
                  <ul class="s_comment auto">
                     <c:choose>
                        <c:when test="${lists.review_depth eq 0}">
                           <li class="r_name">
                           <c:choose>
                              <c:when test="${loginUser.admin eq 0}">
                                 <a href="store.do?userid=${lists.review_storeid}">${lists.review_shopname}</a>
                              </c:when>
                              <c:otherwise>
                                 <a href="chating.jsp?toID=${lists.review_userid}">${lists.review_userid} <i class="far fa-comment-dots"></i></a>
                              </c:otherwise>
                           </c:choose>
                           </li>
      
                           <li>
                              <div style="margin: 0" class="left inline">
                                 <div class="starrr" id="starrr${status.count}"></div>
                                 <input type="hidden" name="score" value="${lists.review_score}" class="star2_input" readonly/>
                                 <script type="text/javascript">          
                                    $('.starrr').starrr({
                                       readOnly : true,
                                       rating : '${lists.review_score}',
                                       max : 5
                                    });
                                 </script>
                                 <span class="star_date"><fmt:formatDate value="${lists.review_date}" pattern="yyyy-MM-dd" /></span>
                              </div>
                              <c:if test="${lists.review_userid eq loginUserID}">
                                 <div class="float-right">
                                    <input type="button" value="수정" onclick="review_updateForm(${lists.review_no})">
                                    <form name="deleteform" method="post" class="inline" action="reviewdelete.do?review_no=${lists.review_no}">
                                       <input type="button" class="review_del_btn" value="삭제">
                                    </form>
                                 </div>
                              </c:if>
                           </li>
      
                           <li class="pd20"><p>${lists.review_content}</p></li>
                           
                           <c:if test="${loginUserID eq lists.review_storeid}">
                           <li>
                              <form method="post" name="re_form${status.count}" action="reviewadd.do" id="form${status.count}" class="fff">   
                                 <input type="button" class="rebtn" value="답글남기기">
                                 <div class="hide" id="hide${status.count}">
                                    <textarea class="comment_write outline" name="review_content" placeholder="리뷰를 남겨주세요."></textarea>
                                 
                                    <input type="button" class="comment_btn" value="작성">
                                 </div>                              
                              </form>
                           </li>
                           </c:if>
                        </c:when>
                        
                        <c:otherwise>
                        <span class="material-icons">subdirectory_arrow_right</span>
                           <li class="r_name inline">
                           <c:choose>
                              <c:when test="${loginUser.admin eq 0}">
                                 <a href="chating.jsp?toID=${lists.review_storeid}">사장님 <i class="far fa-comment-dots"></i></a>
                              </c:when>
                              
                              <c:otherwise>
                                 <a href="store.do?userid=${loginUserID}">사장님</a>
                              </c:otherwise>
                           </c:choose>
                           <span class="star_date">
                              <fmt:formatDate value="${lists.review_date}" pattern="yyyy-MM-dd" /></span>
                           </li>
                           <li>
                              <c:if test="${lists.review_storeid eq loginUserID}">
                                 <div class="float-right">
                                    <input type="button" value="수정" onclick="review_updateForm(${lists.review_no})">
                                    <form name="deleteform" action="reviewdelete.do?review_no=${lists.review_no}" method="post" class="inline">
                                       <input type="button" class="review_del_btn" value="삭제">
                                    </form>
                                 </div>
                              </c:if>
                           </li>
                           <li class="pd20"><p>${lists.review_content}</p></li>
                           
                           
                           
                        </c:otherwise>
                     </c:choose>               
                  </ul>

               </c:forEach>
            </div>
         </section>

         <div class="paging">
            <ul>
               <c:if test="${startPage > 1}">
                  <li><a
                     href="review.do?userid=${loginUserID}&page=${startPage-1}">
                        <span aria-hidden="true">&laquo;</span>
                  </a></li>
               </c:if>

               <c:forEach var="paging" begin="${startPage}" end="${endPage}">
                  <c:if test="${paging == spage}">
                     <li class="p_active"><a
                        href="review.do?userid=${loginUserID}&page=${paging}">${paging}</a>
                     </li>
                  </c:if>

                  <c:if test="${paging != spage}">
                     <li><a
                        href="review.do?userid=${loginUserID}&page=${paging}">${paging}</a></li>
                  </c:if>
               </c:forEach>

               <c:if test="${endPage != maxPage}">
                  <li><a
                     href="review.do?userid=${loginUserID}&page=${endPage+1}">
                        <span aria-hidden="true">&raquo;</span>
                  </a></li>
               </c:if>

            </ul>
         </div>

      </div>
   </main>


   <%@include file="../footer.jsp"%>

   <!-- Vendor JS Files -->
   <script src="resoures/js/bootstrap.bundle.min.js"></script>
   <script src="resoures/js/mobile-nav.js"></script>
   <script src="resoures/js/owl.carousel.min.js"></script>
   <script src="https://kit.fontawesome.com/26e5b48a3a.js"
      crossorigin="anonymous"></script>
   <script src="https://unpkg.com/ionicons@5.0.0/dist/ionicons.js"></script>

   <!-- 달력구현에 필요한 -->
   <script
      src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
   <script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>


   <!-- Template Main JS File -->
   <script src="resoures/js/custom.js"></script>
   <script src="resoures/js/datepicker-ko.js?ver=1.1"></script>

   <script type="text/javascript">
   $(document).ready(function() {
      var id;
      // 답글작성폼 열리는
      $(".rebtn").on("click", function() { 
         id = $(this).next().attr('id');
         document.getElementById(id).style.display = "block";
      });
      
      // 후원자가 작성버튼 눌렀을 때
      $(".comment_btn").on("click", function() { 
         var content = $(this).prev().attr('value');
   
         if (content == "" || content == " ") {
            alert("리뷰를 입력해주세요.");
            content.focus();
            return false;
         } else {
            $(this).closest('form').attr('action');
            $(this).closest('form').submit();
         }
      });
      
      
      $('.cover_img').css({'background-image' : 'url(resoures/images/jb/jb_review.png)'});
      $('.datepicker').datepicker({});
      $('.material-icons').parent().addClass('re');

      // 검색조건 유효성
      var searchBtn = document.getElementById("searchBtn");
      
      searchBtn.onclick = function() {
         var start_date = document.searchForm.start_date.value;
         var end_date = document.searchForm.end_date.value;

         if (start_date.length != 0 && end_date.length != 0) {
            console.log("둘다 널아님!");
            // 시작날짜가 더 클때
            var start_arr = start_date.split('-');
            start_arr = new Date(start_arr[0],start_arr[1], start_arr[2]);
            
            var end_arr = end_date.split('-');
            end_arr = new Date(end_arr[0], end_arr[1], end_arr[2]);

            if ((end_arr - start_arr) < 0) {
               alert("날짜 조건이 맞지 않습니다.");
               return false;
            }

            document.searchForm.submit();
            
         } else if (start_date.length != 0 && end_date == 0) {
            alert("날짜를 입력해주세요.");
            document.searchForm.end_date.focus();
            return false;
            
         } else if (start_date.length == 0 && end_date.length != 0) {
            alert("날짜를 입력해주세요.");
            document.searchForm.start_date.focus();
            return false;
            
         } else {
            console.log("둘다 비었으니 조건x");
            document.searchForm.submit();
         }

      };
   });
   
   function review_updateForm(review_no){
      window.name = "parentForm";
      window.open("reviewupdate.do?review_no=" +review_no, "reviewupdateForm",
         "width=550, height=300, left=500, top=100, resizable=no, scrollbars=no");
   }
   
   /* function review_delete(no) {
      var str;
      str = confirm("삭제하시겠습니까?");
      if (str) {
         document.deleteform.action = "reviewdelete.do?review_no="+no;
         document.deleteform.submit();
      } else {
         return false;
      }
   } */
   
   // 후원자가 작성버튼 눌렀을 때
   $(".review_del_btn").on("click", function() { 
        // var 변수명에 '$' <- 붙이면 object 담는 변수가 됨;
        //var $form2 = $(this).closest('input').attr('name');
        
      var str;
      str = confirm("삭제하시겠습니까?");
      
      if (str) {
         $(this).closest('form').attr('action');
         $(this).closest('form').submit();
      } else {
         return false;
      }
    });
   
   </script>

</body>
</html>