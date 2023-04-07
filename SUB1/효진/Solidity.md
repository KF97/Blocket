### Solidity 변수 및 데이터 타입

| 데이터 타입 | 키워드 | 사용 | 범위 |
| ------ | ------ | ------ | ------ |
| Boolean | bool | True or False | True, False |
| Uint | uint8 | 부호가 없는 정수 표현 | 0~255 |
|  | uint16 | 부호가 없는 정수 표현 | 0~65,535 |
|  | uint24 | 부호가 없는 정수 표현 | 0~16,777,216 |
|  | uint256 | 부호가 없는 정수 표현 | 0~2^256 |
|  | uint | 부호가 없는 정수 표현 | 0~4,294,967,295 |
| String | string |	문자열 | - |
| Address |	address | 주소 잔액 조회 | - |
|  | address | payable | 이더리움 전송 | - |
| Array | DataType[] | 배열 선언 | - |
|  | push | 가장 마지막 데이터 추가 | - |
|  | pop | 가장 마지막 데이터를 가져오고 제거 | - |
|  | length | 배열의 길이 | - |
|  | struct | 구조체 선언 | - |
| Map | Mapping (key type=>value type) | 맵 | - |

### Solidity 함수 및 옵션
```
Function 함수명() 옵션 {
    기능 정의
}
```
- view/pure를 사용하여 데이터 조회 시 수수료를 지불하지 않음
- view는 변수 값을 조회하여 반환이 가능하나 pure는 변수에 접근이 불가능함
- view/pure가 붙은 함수는 데이터 변경이 불가능함

| View | Pure |
| ------ | ------ |
| 상태 변수 값 변경 | 상태 변수 읽기 |
| 이벤트 발생 | this.balance 혹은 <주소>.balance로 접근 |
| 다른 계약 생성 | Block, tx, msg 중 하나의 멤버 변수에 접근 (msg.sig와 msg.data) 제외 |
| selfdestruct 사용 (해당 계약 계정을 삭제하고 모든 잔액을 지정된 주소로 이동) | pure로 정의되어 있지 않은 어떠한 함수라도 호출 |
| 이더리움 전송 | 특정 OPCODE를 포함한 인라인 어셈블리 사용 |
| view 혹은 pure로 선언되지 않은 어떠한 함수라도 호출 | - |
| 로우 레벨 호출 | - |
| 특정 OP Code를 포함한 인라인 어셈블리 사용 | - |

### 함수의 호출 범위 (가시성)
- `public` : 모든 영역 (상속받은 컨트랙트, 외부 컨트랙브, 내부)에서 함수 호출 가능
- `private` : 해당 함수를 포함한 컨트랙트 내부에서만 호출 가능
- `external` : 다른 컨트랙트에서 호출하거나 트랜잭션으로만 호출 가능
- `internal` : private와 비슷하지만 internal은 상속받은 컨트랙트에서도 호출 가능

### 생성자
- constructor 키워드를 사용
- 함수와 동일한 방법으로 사용
- 스마트 컨트랙트를 배포할 때 가장 처음 호출되는 함수
- return 키워드 사용 불가능

### Payable
- 외부로부터 이더리움을 받았을 때 호출하도록 하는 키워드
- payable이 붙어 있으면 이더리움을 받았을 때 호출하라는 의미
- payable이 붙은 경우 함수는 external이어야 함
- `msg.sender` : 함수를 호출한 지갑 주소를 가져옴
- `msg.value` : 전송된 이더리움 개수

### 함수 변경자
- 함수 변경자는 함수를 실행할 때 미리 실행되는 함수
- modifier 키워드 사용
- 특정 지갑 주소만 함수를 호출할 수 있도록 설정할 때 사용

### 예외처리
- require, assert를 이용

### 이벤트
- event 키워드를 사용
- 스마트 컨트랙트에서 특정한 일이 발생했을 때 외부에 알려주는 역할

### Address 타입의 자료형
- Address 타입의 자료형은 balance와 transfer 사용 가능
- Balance는 특정 지갑 주소의 이더 보유량을 조회할 때 사용
- Transfer는 스마트 컨트랙트가 보유중인 이더리움을 다른 지갑 주소로 전송 가능

### 조건문과 반복문
- 조건문: if / if else / else
- 반복문: for

### 상속
- is 키워드 사용
- public, internal일 때 사용 가능

### delegatedcall, call
- 다른 컨트랙트를 호출하기 위해 사용

### ERC-20
- Ethereum Request for Comments (ERC)
- ERC-20 기반으로 토큰을 생성할 수 있음
