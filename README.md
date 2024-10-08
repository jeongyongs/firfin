# firfin

### 목 차

1. [요구사항](#1-요구사항)
2. [ERD](#2-erd)
3. [사용법](#3-사용법)
4. [개선 사항](#4-개선-사항)
5. [스키마 보기](src/main/resources/schema.sql)
6. [API 명세 보기](doc/API.md)
7. [고민거리 보기](doc/%EA%B3%A0%EB%AF%BC%EA%B1%B0%EB%A6%AC.md)
8. [문제점 보기](doc/%EB%AC%B8%EC%A0%9C%EC%A0%90.md)
9. [위험요소 보기](doc/%EC%9C%84%ED%97%98%EC%9A%94%EC%86%8C.md)

## 1. 요구사항

- 유 저
    - 각 유저는 현재 소지한 금액을 저장하고 있습니다.
    - 각 유저는 가입 또는 탈퇴 중 하나의 상태를 가집니다.
    - 각 유저는 보유할 수 있는 최대 금액이 정해져있습니다.
        - 유저마다 다를 수 있습니다.
    - 각 유저는 건당, 일일, 월간 결제 한도를 가집니다.
- 머니 결제 및 페이백 API 를 설계 및 구현합니다.
    - 사용자 인증은 고려하지 않습니다.
        - 파라미터에 user_id로 대체합니다.
    - 머니 결제 API
        - 소지한 금액 및 건당, 일일, 월간 결제 한도 내에서 결제 가능합니다.
        - 어떤 상황에서든 결제는 반드시 1건당 1회가 일어나야합니다.
        - 결제 실패 시 실패 시점과 원인을 알 수 있어야합니다.
        - 동일한 결제 요청이 중복해서 발생할 수 있습니다.
        - 동일한 결제 요청이 동시에 발생할 수 있습니다.
        - 실행시간은 5초의 타임아웃을 가집니다.
    - 머니 결제 취소 API
        - 동일한 결제 취소 요청이 중복해서 발생할 수 있습니다.
        - 동일한 결제 취소 요청이 동시에 발생할 수 있습니다.
        - 실행시간은 5초의 타임아웃을 가집니다.
    - 페이백 API
        - 실행시간은 5초의 타임아웃을 가집니다.
    - 페이백 취소 API
        - 실행시간은 5초의 타임아웃을 가집니다.
- DB Schema 는 MySQL 기준으로 설계합니다.
    - 별도의 쿼리로 작성하여 첨부합니다.
- 파라미터 설명을 담은 API 명세를 작성합니다.
- 프로젝트의 각 단계에서 발생할 수 있는 문제와 해결방안을 정리한 문서를 작성합니다.
    - 위험요소를 식별합니다.
    - 비용적 측면과 성능적 측면을 고려합니다.
- 완성하지 못한 기능은 고민거리 등 이유를 정리한 문서를 작성합니다.

## 2. ERD

![erd.png](doc/img/erd.png)

## 3. 사용법

서버는 `12345` 포트를 사용합니다.

기본 설정으로 `dev` 프로필을 사용합니다.

### 3.1. dev

H2 내장 데이터베이스를 사용합니다.

`schema.sql`와 `data.sql`을 사용하여 서버 실행 시 자동으로 스키마를 생성하고 더미 데이터를 삽입합니다.<br>
[스키마 보기](src/main/resources/schema.sql) | [데이터 보기](src/main/resources/data.sql)

H2 데이터베이스 기본 콘솔을 제공합니다. `/h2-console`

[application-dev.properties](src/main/resources/application-dev.properties)

### 3.2. prod

MySQL 데이터베이스를 사용합니다.

스키마가 자동으로 생성되지 않습니다.

[application-prod.properties](src/main/resources/application-prod.properties)

### 3.3. 로 깅

INFO 레벨 로그는 콘솔에 출력됩니다.

예외가 발생할 경우 `./log/exception/exception.log`에 기록됩니다.

로그는 매일 자정에 롤 아웃되며 `./log/exception/history`에 날짜별로 기록됩니다.

최대 로그 파일 크기 1GB, 30일 동안 유지됩니다.

## 4. 개선 사항

### 2024.08.28(수)

#### 결제 API 성능 개선

1. DB payments 인덱싱
   - (user_id, payment_status, create_at) 인덱스 적용

2. 일일 / 월간 사용 금액 연산 로직 변경
   - Stream API → DB `SUM()` 집계 함수 사용

> 평균 455 ms → **30 ms** 로 개선 [자세히](https://velog.io/@jeongyong/API-%EC%84%B1%EB%8A%A5-%ED%96%A5%EC%83%81%EC%9D%84-%EC%9C%84%ED%95%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8)
