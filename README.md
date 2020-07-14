# 선한영향력(Good Influence)
<br>
<left>
 
![main](https://user-images.githubusercontent.com/68145824/87403307-ab509380-c5f7-11ea-8273-336234ba696f.png)

'선한영향력'은 결식아동과 결식아동들을 후원하고자 하는 매장들을 연결해주고자 하여 만든 웹 어플리케이션입니다.
<br>아동들은 후원 매장에 대한 정보를 얻을 수 있으며, 방문 전 예약을 하여 손쉽게 방문할 수 있습니다. 
<br>
<br>

프로젝트 기간 :
2020.04.07 ~ 2020.07.02
<br>
프로젝트 인원 :
2명

<br>

## 사용 기술
<br>
디자인

- HTML, CSS, JS 프레임워크인 Bootstrap 사용

<br>
언어

 - JAVA (JSP+SERVLET)
 
<br>
데이터베이스

 - MYSQL
 
<br>
서버

 - AWS

<br>


## 시현(본인이 구현한 기능)

### 1. 게시판 

- 썸머노트 에디터 사용 (AJAX로 이미지 업로드)

- 댓글(CRUD) AJAX로 구현

  #### 1.1 공지사항

  >![notice](https://user-images.githubusercontent.com/68145824/87397214-f31eed00-c5ee-11ea-867b-1ea3b3ac0db5.gif)

  <br>
  
  #### 1.2 갤러리

  - 이미지를 리사이징하여 썸네일 이미지 크기를 줄여서 저장
  <br>
  
    >![photo](https://user-images.githubusercontent.com/68145824/87397232-fc0fbe80-c5ee-11ea-8fb5-dc6f986fd4e5.gif)

  <br>

  #### 1.3 자유게시판

  - MYSQL의 ON DELETE CASCADE를 제약조건으로 하여 해당 글이 삭제되면 해당 글에 달린 댓글도 같이 삭제됨
  <br>
  
  >![freeboard](https://user-images.githubusercontent.com/68145824/87397363-32e5d480-c5ef-11ea-972c-43ad3e2ca86b.gif)

<br>

### 2. 예약

- 후원아동만 예약 가능하며, 방문예정시간 1시간 전이나 후원자가 이미 예약처리(예약승인, 거절)를 했을 경우 수정 불가

  #### 2.1 아동(예약, 예약수정, 예약취소)

  >![reserve_child](https://user-images.githubusercontent.com/68145824/87397823-e8188c80-c5ef-11ea-80a0-fb018fd2aa8a.gif)

  <br>

   #### 2.2 후원자(예약거절)

  >![reserve_refuse](https://user-images.githubusercontent.com/68145824/87397895-067e8800-c5f0-11ea-9381-8113fabb9eaa.gif)

  <br>

   #### 2.3 후원자(예약승인, 방문완료)

  >![reserve_success](https://user-images.githubusercontent.com/68145824/87397901-08e0e200-c5f0-11ea-823d-38072f7eb139.gif)

<br>

### 3. 리뷰

- 방문완료한 아동만 작성 가능, 별점 라이브러리(starrr) 사용

  #### 3.1 아동이 리뷰 작성 시

  - MYSQL의 ON DELETE CASCADE를 제약조건으로 하여 해당 글이 삭제되면 해당 글에 달린 댓글도 같이 삭제됨
  <br>
  
  >![review_child](https://user-images.githubusercontent.com/68145824/87397909-0bdbd280-c5f0-11ea-8026-7c77d90a84c7.gif)

  <br>

  #### 3.2 후원자가 리뷰 작성 시

  >![review_store](https://user-images.githubusercontent.com/68145824/87397912-0d0cff80-c5f0-11ea-8543-d1b999b7ed5a.gif)

