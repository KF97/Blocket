
## Req. 2. 이더리움 계정 생성 (eth0, eth1)

### Req. 2-1 계정 생성

#### 계정 생성

```
personal.newAccount(PASSWORD)
```

#### 계정 확인

```
eth.accounts
```

![req2-1_계정생성](/uploads/05a538f235296b0d71f338891899f43b/req2-1_계정생성.PNG)

![req2-1_계정생성_eth1](/uploads/120a0bd115ac080c879e55981236e344/req2-1_계정생성_eth1.PNG)

#### keystore 파일 확인

    ![req2-1_keystore파일확인](/uploads/355da300e62a6d37aa2ca6a85c4a7ace/req2-1_keystore파일확인.PNG)
    ![req2-1_keystore파일확인_eth1](/uploads/c03d515519fd9ee54f6ba3e5c1e8ff24/req2-1_keystore파일확인_eth1.PNG)

### Req. 2-2 코인베이스(Coinbase) 설정

```
 eth.coinbase
```

![req2-2_코인베이스_설정](/uploads/aeb64f20383f8d960ee651ff2a6d2025/req2-2_코인베이스_설정.PNG)
![req2-2_코인베이스_설정_eth1](/uploads/9eb40191c0e09499c7edb897d310aa1a/req2-2_코인베이스_설정_eth1.PNG)

#### 코인베이스 계정 주소 변경

```bash
miner.setEtherbase("계정주소")
```

### Req. 2-3 마이닝(Mining) 시작

-

#### 마이닝(Mining) 시작

- 트랜잭션 생성을 위한 이더 채굴

```
 miner.start(1)
```

![작req2-3_마이닝_시작](/uploads/9957917a9fd1c868607b60506c9bffa0/작req2-3_마이닝_시작.PNG)

#### 마이닝(Mining) 진행 상태 확인

```bash
eth.mining
```

![req2-3_마이닝_상태_확인](/uploads/176b442b6136871153428f2404e94c06/req2-3_마이닝_상태_확인.PNG)

#### 마이닝(Mining) 종료

```bash
miner.stop()
```

### Req. 2-4 마이닝(Mining) 결과 확인

#### 계정별 잔액 확인

```bash
eth.getBalance(eth.accounts[0])
```

![req2-4_잔액확인](/uploads/b59c1511fb71e2fc37884e299797ae3c/req2-4_잔액확인.PNG)

#### 생성된 블록 수 조회

```bash
eth.blockNumber
```

#### 블록 상세정보 조회

```bash
eth.getBlock(eth.blockNumber)
```

![req2-4_블록의_상세정보_조회](/uploads/9edba6c888fec258c1579f453f3972f5/req2-4_블록의_상세정보_조회.PNG)

