# 매장 테이블 예약 서비스

# API 설명

## 1. 사용자

<details> 
<summary>회원가입</summary>

경로 및 요청 

`POST` `http://localhost:8080/user/register`

```
{
    "loginId": "user10",
    "password": "user10@#$",
    "name": "유저10",
    "phone": "01011112222"
}
```

결과
```
{
    "id": 3,
    "loginId": "user10",
    "password": "$2a$10...",
    "name": "유저10",
    "phone": "01011112222",
    "role": "ROLE_USER",
    "createdAt": "2024-06-10 02:05:06",
    "updatedAt": "2024-06-10 02:05:06"
}
```
</details>

<details> 
<summary>로그인</summary>
경로 및 요청 

`POST` `http://localhost:8080/user/login`

```
{
    "loginId": "user10",
    "password": "user10@#$"
}
```

결과 (토큰값)
```
eyJhbGciO...
```
</details>

## 2. 파트너(점장)

<details> 
<summary>회원가입</summary>

경로 및 요청 

`POST` `http://localhost:8080/partner/register`

```
{
    "loginId": "partner33",
    "password": "partner33@#$",
    "name": "33번파트너"
}
```

결과
```
{
    "id": 4,
    "loginId": "partner33",
    "password": "$2a$1...",
    "name": "33번파트너",
    "role": "ROLE_PARTNER",
    "createdAt": "2024-06-10 02:18:40",
    "updatedAt": "2024-06-10 02:18:40"
}
```
</details>

<details> 
<summary>로그인</summary>

경로 및 요청 

`POST` `http://localhost:8080/partner/login`

```
{
    "loginId": "partner33",
    "password": "partner33@#$"
}
```

결과 (토큰값)
```
eyJhbGci...
```
</details>

## 3. 매장

<details> 
<summary>등록</summary>

경로 및 요청

`POST` `http://localhost:8080/store/register`

`Authorization`: `Bearer eyJhbGci...`

```
{
  "storeName": "매장명",
  "storeAddress": "서울시 서초구 강남대로 999",
  "storePhone": "0299998888",
  "storeIntroduction": "매장 소개글입니다."
}
```

결과
```
{
    "partnerId": 4,
    "storeId": 4,
    "storeName": "매장명",
    "storeAddress": "서울시 서초구 강남대로 999",
    "storePhone": "0299998888",
    "storeIntroduction": "매장 소개글입니다.",
    "createdAt": "2024-06-10 02:35:22",
    "updatedAt": "2024-06-10 02:35:22"
}
```
</details>

<details> 
<summary>수정</summary>

경로 및 요청

`PUT` `http://localhost:8080/store/update/4`

`Authorization`: `Bearer eyJhbGci...`

```
{
  "storeName": "수정된 매장명",
  "storeAddress": "서울시 서초구 강남대로 999",
  "storePhone": "0211116666",
  "storeIntroduction": "수정된 매장 소개글입니다."
}
```

결과
```
{
    "storeName": "수정된 매장명",
    "storeAddress": "서울시 서초구 강남대로 999",
    "storePhone": "0211116666",
    "storeIntroduction": "수정된 매장 소개글입니다.",
    "createDate": "2024-06-10 02:35:22",
    "updateDate": "2024-06-10 02:36:33"
}
```
</details>

<details> 
<summary>삭제</summary>

경로 및 요청

`GET` `http://localhost:8080/store/delete/5`

`Authorization`: `Bearer eyJhbGci...`

결과
```
store_id: 5 -> 삭제완료
```
</details>

<details> 
<summary>검색</summary>

경로 및 요청

`GET` `http://localhost:8080/store/list`

```
{
    "searchContents": ""
}
```

결과
```
{
    "content": [
        {
            "storeId": 4,
            "storeName": "수정된 매장명",
            "storeAddress": "서울시 서초구 강남대로 999",
            "storePhone": "0211116666",
            "storeIntroduction": "수정된 매장 소개글입니다."
        },
        {
            "storeId": 5,
            "storeName": "test10",
            "storeAddress": "test address 10",
            "storePhone": "01011112222",
            "storeIntroduction": "teset introduction 10"
        },
        {
            "storeId": 6,
            "storeName": "test20",
            "storeAddress": "test address 20",
            "storePhone": "0299997777",
            "storeIntroduction": "teset introduction 20"
        }
    ],
    "pageable": 
        ...
}
```

경로 및 요청

`GET` `http://localhost:8080/store/list?searchType=NAME`

`Params` `searchType = NAME`

```
{
    "searchContents": "수정"
}
```

결과
```
{
    "content": [
        {
            "storeId": 4,
            "storeName": "수정된 매장명",
            "storeAddress": "서울시 서초구 강남대로 999",
            "storePhone": "0211116666",
            "storeIntroduction": "수정된 매장 소개글입니다."
        }
    ],
    "pageable": 
        ...
}
```
</details>

## 4. 예약

<details> 
<summary>등록</summary>

경로 및 요청

`POST` `http://localhost:8080/reservation/register`

`Authorization`: `Bearer eyJhbGci...`

```
{
    "storeId": 4,
    "people": 10,
    "date": "2024-06-10",
    "time": "18"
}
```

결과
```
{
    "storeId": 4,
    "storeName": "수정된 매장명",
    "username": "유저10",
    "userPhone": "01011112222",
    "people": 10,
    "status": "REQUEST",
    "reservationAt": "2024-06-10 18:00",
    "createdAt": "2024-06-10 02:58:22"
}
```
</details>

<details> 
<summary>승인</summary>

경로 및 요청

`PUT` `http://localhost:8080/reservation/approve?reservationId=8`

`Authorization`: `Bearer eyJhbGci...`

결과
```
{
    "id": 8,
    "storeId": 4,
    "storeName": "수정된 매장명",
    "username": "유저10",
    "userPhone": "01011112222",
    "people": 10,
    "status": "APPROVE",
    "reservationAt": "2024-06-10 18:00",
    "createdAt": "2024-06-10 02:58:22"
}
```
</details>

<details> 
<summary>거절</summary>

경로 및 요청

`PUT` `http://localhost:8080/reservation/refuse?reservationId=8`

`Authorization`: `Bearer eyJhbGci...`

결과
```
{
    "id": 8,
    "storeId": 4,
    "storeName": "수정된 매장명",
    "username": "유저10",
    "userPhone": "01011112222",
    "people": 10,
    "status": "REFUSE",
    "reservationAt": "2024-06-10 18:00",
    "createdAt": "2024-06-10 02:58:22"
}
```
</details>

<details> 
<summary>도착완료</summary>

경로 및 요청

`PUT` `http://localhost:8080/reservation/arrived?reservationId=8`

`Authorization`: `Bearer eyJhbGci...`

결과 (예약시간 10분전일때만 아래와 같은 정상적인 응답을 보냄)
```
{
    "id": 8,
    "storeId": 4,
    "storeName": "수정된 매장명",
    "username": "유저10",
    "userPhone": "01011112222",
    "people": 10,
    "status": "ARRIVED",
    "reservationAt": "2024-06-10 18:00",
    "createdAt": "2024-06-10 02:58:22"
}
```
</details>

<details> 
<summary>이용완료</summary>

경로 및 요청

`PUT` `http://localhost:8080/reservation/complete?reservationId=8`

`Authorization`: `Bearer eyJhbGci...`

결과
```
{
    "id": 8,
    "storeId": 4,
    "storeName": "수정된 매장명",
    "username": "유저10",
    "userPhone": "01011112222",
    "people": 10,
    "status": "COMPLETE",
    "reservationAt": "2024-06-10 18:00",
    "createdAt": "2024-06-10 02:58:22"
}
```
</details>

<details> 
<summary>매장별 예약 확인</summary>

경로 및 요청

`GET` `http://localhost:8080/reservation/list?storeId=4`

`Authorization`: `Bearer eyJhbGci...`

결과
```
{
    "content": [
        {
            "id": 8,
            "storeId": 4,
            "storeName": "수정된 매장명",
            "username": "유저10",
            "userPhone": "01011112222",
            "people": 10,
            "status": "COMPLETE",
            "reservationAt": "2024-06-10 18:00",
            "createdAt": "2024-06-10 02:58:22"
        },
        {
            "id": 9,
            "storeId": 4,
            "storeName": "수정된 매장명",
            "username": "유저11",
            "userPhone": "01033335555",
            "people": 2,
            "status": "REQUEST",
            "reservationAt": "2024-06-10 20:00",
            "createdAt": "2024-06-10 03:17:51"
        }
    ],
    "pageable": 
        ...
}
```
</details>

## 5. 리뷰

<details> 
<summary>등록</summary>

경로 및 요청

`POST` `http://localhost:8080/review/register`

`Authorization`: `Bearer eyJhbGci...`

```
{
    "reservationId": 8,
    "contents": "review register test",
    "rating": 5
}
```

결과
```
{
    "reservationId": 8,
    "storeName": "수정된 매장명",
    "contents": "review register test",
    "rating": 5,
    "createdAt": "2024-06-10 03:19:57"
}
```
</details>

<details> 
<summary>수정</summary>

경로 및 요청

`PUT` `http://localhost:8080/review/update/3`

`Authorization`: `Bearer eyJhbGci...`

```
{
    "contents": "update review",
    "rating": 1
}
```

결과
```
{
    "reservationId": 8,
    "storeName": "수정된 매장명",
    "contents": "update review",
    "rating": 1,
    "createdAt": "2024-06-10 03:19:57",
    "updatedAt": "2024-06-10 03:21:52"
}
```
</details>

<details> 
<summary>삭제(파트너)</summary>

경로 및 요청

`DELETE` `http://localhost:8080/review/delete/4`

`Authorization`: `Bearer eyJhbGci...`

결과
```
review_id: 4 -> 삭제완료
```
</details>

<details> 
<summary>삭제(유저)</summary>

경로 및 요청

`DELETE` `http://localhost:8080/review/delete/3`

`Authorization`: `Bearer eyJhbGci...`

결과
```
review_id: 3 -> 삭제완료
```
</details>

<details> 
<summary>삭제(파트너)</summary>

경로 및 요청

`DELETE` `http://localhost:8080/review/delete/4`

`Authorization`: `Bearer eyJhbGci...`

결과
```
review_id: 4 -> 삭제완료
```
</details>

<details> 
<summary>매장별 리뷰 찾기</summary>

경로 및 요청

`GET` `http://localhost:8080/review/list?storeId=4`

결과
```
{
    "content": [
        {
            "reservationId": 8,
            "storeName": "수정된 매장명",
            "contents": "user10 review",
            "rating": 5,
            "createdAt": "2024-06-10 03:36:13",
            "updatedAt": "2024-06-10 03:36:13"
        },
        {
            "reservationId": 9,
            "storeName": "수정된 매장명",
            "contents": "user11 review",
            "rating": 1,
            "createdAt": "2024-06-10 03:36:34",
            "updatedAt": "2024-06-10 03:36:34"
        }
    ],
    "pageable": 
        ...
}
```
</details>