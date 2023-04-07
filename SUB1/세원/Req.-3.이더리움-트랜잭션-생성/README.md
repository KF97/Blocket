
## Req. 3. 이더리움 트랜잭션(Transaction) 생성

### Req. 3-1 트랜잭션(Transaction) 생성

#### 트랜잭션(Transaction)

- 외부 소유 계정(EOA)에 의해 서명된 메시지
- 이더리움 네트워크에 의해 전송되고 이더리움 블록체인에 기록

- 논스(nonce) : 발신 EOA에 의해 발행되어 메시지 재사용을 방지하는 데 사용되는 일련번호

- 가스 가격(gas price) : 발신자가 지급하는 가스의 가격(웨이)

- 가스 한도(gas limit) : 이 트랜잭션을 위해 구입할 가스의 최대량

- 수신자(recipient) : 목적지 이더리움 주소

- 값(value) : 목적지에 보낼 이더의 양

- 데이터(data) : 가변 길이 바이너리 데이터 페이로드

- v,r,s : EOA의 ECDSA 디지털 서명의 세 가지 구성요소

#### 계정 간 이더(ETH) 전송 트랜잭션 생성

- 계정 잠금 해제

  ```bash
  personal.unlockAccount(eth.accounts[0])
  ```

  ![req3-1_트랙잭션_전_unlock](/uploads/9c214f0cddfab7926d15db6d4c2be228/req3-1_트랙잭션_전_unlock.PNG)

- 트랜잭션 전 금액 확인

  ![req3-1_트랙잭션_전_금액확인](/uploads/65eeabd4bad30fac12c1ccbd122df0be/req3-1_트랙잭션_전_금액확인.PNG)

- 트랜잭션 생성 및 해시 값 확인

  ![req3-1_트랙잭션_생성](/uploads/dac6cae008efdee95a624b7210f9eeaa/req3-1_트랙잭션_생성.PNG)

#### 트랜잭션 상태 조회

```bash
eth.getTransaction("트랜잭션 해시 값")
```

![req3-2_트랜잭션_상태_조회](/uploads/550ef35e07ed9dfbc1cdc25780d15137/req3-2_트랜잭션_상태_조회.PNG)

### Req. 3-2 트랜잭션(Transaction) 결과 확인

#### 마이닝 재시작 (일정 시간 수행)

```bash
miner.start()
```

#### 트랜잭션 상태 조회

```bash
eth.getTransaction("트랜잭션 해시 값")
```

#### 트랜잭션 처리 완료 시 마이닝 중단

```bash
miner.stop()
```

![req3-3_트랜잭션_상태조회](/uploads/5cd229680e338d34d1d0537ea8bf4cad/req3-3_트랜잭션_상태조회.PNG)
![req3-2_트랜잭션_상태_조회3](/uploads/86d202846fad376811bee69909d332de/req3-2_트랜잭션_상태_조회3.PNG)

