# API 명세

## 1. 결제 API

### 1.1. 결제 요청 `POST` `/api/payment`

#### 1.1.1. Request

```http request
POST /api/payment HTTP/1.1
Content-Type: application/json

{
    "userId": 123,
    "transactionId": "40cbf5ce-3539-484b-9966-1b620607f047",
    "merchantId": 890,
    "amount": 22000
}
```

| 파라미터          | 타 입    | 설 명                              | 필 수 |
|---------------|--------|----------------------------------|-----|
| userId        | Long   | 유저 번호                            | O   |
| transactionId | String | 결제 트랜잭션 아이디<br>- 결제 건당 생성되는 UUID | O   |
| merchantId    | Long   | 가맹점 번호                           | O   |
| amount        | Long   | 결제 금액 (원)<br>- 최소 0원 이상          | O   |

#### 1.1.2. Response

```http request
HTTP/1.1 200 OK
Content-Type: application/json

{
    "paymentId": 4567,
    "amount": 22000,
    "createAt": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명       |
|-----------|----------|-----------|
| paymentId | Long     | 결제 승인 번호  |
| amount    | Long     | 결제 금액 (원) |
| createAt  | Datetime | 결제 승인 날짜  |

```http request
HTTP/1.1 409 Conflict
Content-Type: application/json

{
    "code": 10,
    "message": "이미 결제된 건입니다",
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명          |
|-----------|----------|--------------|
| code      | Integer  | 요청 실패 코드     |
| message   | String   | 요청 실패 원인 메시지 |
| timestamp | Datetime | 요청 실패 타임스탬프  |

| 코 드 | 내 용        | 설 명                                                                                                                                               | 응답 코드 |
|-----|------------|---------------------------------------------------------------------------------------------------------------------------------------------------|-------|
| 10  | 중복 요청      | 결제 완료 건에 결제를 요청한 경우                                                                                                                               | 409   |
| 20  | 잔액 부족      | 결제 금액보다 잔액이 부족한 경우                                                                                                                                | 400   |
| 21  | 건당 한도 초과   | 결제 금액이 건당 한도를 초과한 경우                                                                                                                              | 400   |
| 22  | 일일 한도 초과   | 일일 결제 합산 금액이 한도를 초과한 경우                                                                                                                           | 400   |
| 23  | 월간 한도 초과   | 월간 결제 합산 금액이 한도를 초과한 경우                                                                                                                           | 400   |
| 30  | 실행 시간 타임아웃 | 실행 시간이 타임아웃된 경우 (5s)                                                                                                                              | 500   |
| 40  | 잘못된 요청     | 잘못된 파라미터 타입 또는 파라미터 규칙에 맞지 않는 경우<br>- userId: null 또는 음수<br>- transactionId: null 또는 잘못된 UUID<br>- merchantId: null 또는 음수<br>- amount: null 또는 음수 | 400   |
| 41  | 유효하지 않는 유저 | 존재하지 않는 유저 또는 탈퇴한 유저일 경우                                                                                                                          | 404   |

### 1.2. 결제 취소 `POST` `/api/payment/cancel`

#### 1.2.1. Request

```http request
POST /api/payment/cancel HTTP/1.1
Content-Type: application/json

{
    "userId": 123,
    "paymentId": 4567
}
```

| 파라미터      | 타 입  | 설 명                   | 필 수 |
|-----------|------|-----------------------|-----|
| userId    | Long | 유저 번호                 | O   |
| paymentId | Long | 결제 번호<br>- 결제 승인 후 발급 | O   |

#### 1.2.2. Response

```http request
HTTP/1.1 200 OK
Content-Type: application/json

{
    "paymentId": 4567,
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명      |
|-----------|----------|----------|
| paymentId | Long     | 결제 번호    |
| timestamp | Datetime | 결제 취소 날짜 |

```http request
HTTP/1.1 409 Conflict
Content-Type: application/json

{
    "code": 10,
    "message": "이미 결제 취소된 건입니다",
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명          |
|-----------|----------|--------------|
| code      | Integer  | 요청 실패 코드     |
| message   | String   | 요청 실패 원인 메시지 |
| timestamp | Datetime | 요청 실패 타임스탬프  |

| 코 드 | 내 용        | 설 명                                                                                 | 응답 코드 |
|-----|------------|-------------------------------------------------------------------------------------|-------|
| 00  | 정보 불일치     | 유저 정보와 결제 정보가 일치하지 않는 경우                                                            | 400   |
| 10  | 중복 요청      | 결제 취소 완료 건에 결제 취소를 요청한 경우                                                           | 409   |
| 30  | 실행 시간 타임아웃 | 실행 시간이 타임아웃된 경우 (5s)                                                                | 500   |
| 40  | 잘못된 요청     | 잘못된 파라미터 타입 또는 파라미터 규칙에 맞지 않는 경우<br>- userId: null 또는 음수<br>- paymentId: null 또는 음수 | 400   |
| 41  | 유효하지 않는 유저 | 존재하지 않는 유저 또는 탈퇴한 유저일 경우                                                            | 404   |
| 42  | 유효하지 않는 결제 | 존재하지 않는 결제일 경우                                                                      | 404   |

## 2. 페이백 API

### 2.1. 페이백 지급 `POST` `/api/payback`

#### 2.1.1. Request

```http request
POST /api/payback HTTP/1.1
Content-Type: application/json

{
    "userId": 123,
    "paymentId": 4567,
    "amount": 2000
}
```

| 파라미터      | 타 입  | 설 명                     | 필 수 |
|-----------|------|-------------------------|-----|
| userId    | Long | 유저 번호                   | O   |
| paymentId | Long | 결제 번호<br>- 결제 승인 후 발급   | O   |
| amount    | Long | 결제 금액 (원)<br>- 최소 0원 이상 | O   |

#### 2.1.2. Response

```http request
HTTP/1.1 200 OK
Content-Type: application/json

{
    "paybackId": 1122,
    "amount": 2000,
    "createAt": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명       |
|-----------|----------|-----------|
| paybackId | Long     | 페이백 번호    |
| amount    | Long     | 결제 금액 (원) |
| createAt  | Datetime | 페이백 지급 날짜 |

```http request
HTTP/1.1 409 Conflict
Content-Type: application/json

{
    "code": 10,
    "message": "이미 페이백이 지급된 건입니다",
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명          |
|-----------|----------|--------------|
| code      | Integer  | 요청 실패 코드     |
| message   | String   | 요청 실패 원인 메시지 |
| timestamp | Datetime | 요청 실패 타임스탬프  |

| 코 드 | 내 용        | 설 명                                                                                                             | 응답 코드 |
|-----|------------|-----------------------------------------------------------------------------------------------------------------|-------|
| 00  | 정보 불일치     | 유저 정보와 결제 정보가 일치하지 않는 경우                                                                                        | 400   |
| 10  | 중복 요청      | 페이백 지급 완료 건에 페이백 지급을 요청한 경우                                                                                     | 409   |
| 30  | 실행 시간 타임아웃 | 실행 시간이 타임아웃된 경우 (5s)                                                                                            | 500   |
| 40  | 잘못된 요청     | 잘못된 파라미터 타입 또는 파라미터 규칙에 맞지 않는 경우<br>- userId: null 또는 음수<br>- paymentId: null 또는 음수<br>- amount: null 또는 음수일 경우 | 400   |
| 41  | 유효하지 않는 유저 | 존재하지 않는 유저 또는 탈퇴한 유저일 경우                                                                                        | 404   |
| 42  | 유효하지 않는 결제 | 존재하지 않는 결제일 경우                                                                                                  | 404   |

### 2.2. 페이백 취소 `POST` `/api/payback/cancel`

#### 2.2.1. Request

```http request
POST /api/payback/cancel HTTP/1.1
Content-Type: application/json

{
    "userId": 123,
    "paybackId": 1122
}
```

| 파라미터      | 타 입  | 설 명    | 필 수 |
|-----------|------|--------|-----|
| userId    | Long | 유저 번호  | O   |
| paybackId | Long | 페이백 번호 | O   |

#### 2.2.2. Response

```http request
HTTP/1.1 200 OK
Content-Type: application/json

{
    "payback": 1122,
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명       |
|-----------|----------|-----------|
| payback | Long     | 페이백 번호    |
| timestamp | Datetime | 페이백 취소 날짜 |

```http request
HTTP/1.1 409 Conflict
Content-Type: application/json

{
    "code": 10,
    "message": "이미 페이백 지급이 취소된 건입니다",
    "timestamp": "2024-08-13T11:20:30"
}
```

| 파라미터      | 타 입      | 설 명          |
|-----------|----------|--------------|
| code      | Integer  | 요청 실패 코드     |
| message   | String   | 요청 실패 원인 메시지 |
| timestamp | Datetime | 요청 실패 타임스탬프  |

| 코 드 | 내 용         | 설 명                                                                                 | 응답 코드 |
|-----|-------------|-------------------------------------------------------------------------------------|-------|
| 00  | 정보 불일치      | 유저 정보와 페이백 정보가 일치하지 않는 경우                                                           | 400   |
| 10  | 중복 요청       | 페이백 취소 완료 건에 페이백 취소를 요청한 경우                                                         | 409   |
| 30  | 실행 시간 타임아웃  | 실행 시간이 타임아웃된 경우 (5s)                                                                | 500   |
| 40  | 잘못된 요청      | 잘못된 파라미터 타입 또는 파라미터 규칙에 맞지 않는 경우<br>- userId: null 또는 음수<br>- paybackId: null 또는 음수 | 400   |
| 41  | 유효하지 않는 유저  | 존재하지 않는 유저 또는 탈퇴한 유저일 경우                                                            | 404   |
| 43  | 유효하지 않는 페이백 | 존재하지 않는 페이백일 경우                                                                     | 404   |
