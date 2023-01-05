# shopping
+ 원모어백 웹사이트 클론코딩(백엔드)

## :calendar: 기간
+ 2022.11.02 ~ 2022.12.19

## :seedling: 사용 기술
+ Java 11
+ Spring boot
+ Spring Data JPA
+ Gradle
+ H2
+ QueryDSL
+ Mockito
+ JUnit%

## :pencil2: ERD 설계
![onemorebag_수정](https://user-images.githubusercontent.com/70851874/210693956-8b497cbd-96a9-4eb0-99da-bf28e16a877f.png)

## :bulb: 기능 목록
+ 회원 기능
  + 회원 등록
  + 회원 조회
  + 회원 수정
  + 회원 탈퇴
+ 상품 기능
  + 상품 등록(admin)
  + 상품 목록 조회
  + 상품 조회
  + 상품 수정(admin)
  + 상품 삭제(admin)
  + 상품 검색
+ 주문 기능
  + 상품 주문
  + 주문 조회
  + 주문 취소
+ 상품 좋아요
  + 좋아요
  + 좋아요 취소
+ 기타 요구사항
  + 상품 재고 관리 필요
  + 상품은 카테고리 및 브랜드로 구분할 수 있음
  + 상품 주문시 배송 정보를 입력할 수 있음
  + 회원은 상품에 좋아요를 누를 수 있음
  + 상품 등록, 수정, 삭제는 관리자만 가능
  + 배송 상태는 관리자만 수정 가능

## :white_check_mark: TODOLIST
<details>
<summary>TODOLIST</summary>
<div markdown="1">

- 22.11.02
  - 프로젝트 생성
  - BaseEntity

- 22.11.07
  - Member, Delivery, itemLike, Order

- 22.11.09
  - Order_Item, Item, Item_File
  - *category, brand -> item 안에 구현하는 걸로 수정

- 22.11.11
  - category(enum class)
  - brand는 enum -> String 으로 변경
  - 양방향, 단방향, 연관관계 메서드 최종수정
  - MemberRepository, ItemRepository, OrderRepository

- 22.11.13
  - MemberService
  - FileStore, FileDto, FileApiController
  - item = orderItem erd 일대다(0~∞ -> 1~∞) 수정

- 22.11.14-19: likelion hackathon

- 22.11.22
  - ItemService, OrderService
  - ItemLikeRepository, ItemLikeService

- 22.11.25
  - @Login, LoginMember, LoginArgumentResolver, LoginIntercpetor, SessionConst, WebConfig
  - MemberController, MemberApiController, MemberSaveForm, LoginForm

- 22.11.27
  - MyPageApiController(마이페이지조회)
  - OrderSaveForm, OrderController
  - *비고: OrderController에 hasErrors() model 추가

- 22.11.29
  - ItemResponse, ItemApiController(상품목록조회, 상품상세조회, 상품검색)
  - ItemCustomRepository, ItemCustomRepositoryImpl, ItemSearchDto, ItemSearchResponse

- 22.12.01
  - ItemSaveForm, ItemUpdateForm
  - ItemController, ItemApiController(상품등록, 상품수정, 상품삭제)
  - MemberController(회원목록조회)
  - MyPageApiController(주문 내역 조회)

- 22.12.02
  - ItemApiController에 좋아요 넘겨주기(상품목록조회, 상품상세조회,상품검색)
  - 상품목록조회 MainController로 옮기기("/")
  - ItemApiController(상품 좋아요)
  - MyPageApiController(좋아요 목록 조회, 회원정보수정 조회)
  - MyPageController(회원정보수정)

- 22.12.05
  - MemberTest

- 22.12.06
  - MemberServiceTest

- 22.12.08
  - ItemTest, ItemFileTest, FileStoreTest

- 22.12.11
  - OrderTest, OrderServiceTest

- 22.12.12
  - OrderFindServiceTest(특정회원 주문목록 조회되는지 확인)

- 22.12.13
  - ItemRepositoryTest, ItemLikeRepositoryTest

- 22.12.15
  - ItemSeviceTest, ItemLikeServiceTest

- 22.12.18
  - (상품검색 기능수정)
  - ItemRepositoryTest
  - ItemService
  - *item에 itemfile이 1:N인데 페이징과 컬렉션 최적화를 위해 item과 itemfile을 따로 조회함

- 22.12.19
  - ItemSeviceTest (상품검색테스트구현)

</div>
</details>

## :white_check_mark: URI 문서
<details>
<summary>URI 문서</summary>
<div markdown="1">
  
 - 메인페이지
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|기본(/)|메인페이지 조회(상품 목록 조회)|api|

- 이미지
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/imgs/{originalFileName}|이미지 파일 조회|-|

- 회원 기본
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/member/signup|회원등록 페이지 조회|-|
  |POST|/member/signup|회원 등록|form|
  |GET|/member/login|로그인 페이지 조회|-|
  |POST|/member/login|로그인|form|
  |POST|/member/logout|로그아웃|form|

- 회원 상세
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/mypage|회원 마이페이지 조회|api|
  |GET|/mypage/orders|주문내역 페이지 조회|api|
  |DELETE|/mypage/orders|주문 수정(취소)|api|
  |GET|/mypage/likes|좋아요 목록 조회|api|
  |GET|/mypage/modify|회원정보수정 페이지 조회|api|
  |POST|/mypage/modify|회원정보수정|form|
  
- 상품
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/item|상품 목록 조회|api|
  |GET|/item/{itemId}|상품 상세 조회|api|
  |POST|/item/{itemId}|상품 좋아요|api|
  |GET|/item/search|상품 검색|api|

- 관리자
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/item/add|상품 등록 페이지 조회|-|
  |POST|/item/add|상품 등록|form|
  |GET|/item/{itemId}/modify|상품 수정 페이지 조회|api|
  |POST|/item/{itemId}/modify|상품 수정|form|
  |DELETE|/item/{itemId}|상품 삭제|api|
  |GET|/member|회원 목록 조회|api|
  |DELETE|/member|회원 삭제|-|
  
- 주문
  |Method|URI|Description|Type|
  |------|---|---|---|
  |GET|/order/{itemId}|주문 페이지 조회|-|
  |GET|/order/{itemId}|상품 주문|form|

</div>
</details>  
