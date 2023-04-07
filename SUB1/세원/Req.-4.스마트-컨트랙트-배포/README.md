
## Req. 4. 스마트 컨트랙트 배포

### Req. 4-1 eth0 노드 확인

#### VirtualBox 또는 Vagrant에서 eth0 VM에 대한 포트 포워딩 확인

- Host 8545 -> Guest 8545
  ![Req4-1_포트포워딩_확인](/uploads/5cd0d4fc743e2698cf977ea47ee0c0a3/Req4-1_포트포워딩_확인.PNG)

### Req. 4-2 Metamask 설정

#### Metamask 설치 및 실행

- [MetaMask](https://metamask.io/)

![Req4-2_metamask](/uploads/14e72569aced164dc49c6b516bafcbcb/Req4-2_metamask.PNG)

#### Metamask의 Custom RPC 옵션 설정

![Req4-3_Custom_RPC_chainId](/uploads/239e9e932993e03f2dfc7d0307663859/Req4-3_Custom_RPC_chainId.PNG)

![Req4-3_Custom_RPC](/uploads/b48d2722cc24dcad90a95b7e4c393fc1/Req4-3_Custom_RPC.PNG)

### Req. 4-3 스마트 컨트랙트 배포(Remix)

#### Remix 접속

- [Remix](https://remix.ethereum.org/)

#### 기본 제공 예제 중 1개를 선택하여 코드 내용 확인

![Req4-3_sol_check](/uploads/ee476d37c8a3d7cf4e335244287e9dc3/Req4-3_sol_check.PNG)

#### 로컬 이더리움 네트워크와 연동 및 Compile 및 Deploy 수행

![Req4-3_sol](/uploads/c6935349b6e7777d6b427a167ba05f55/Req4-3_sol.PNG)

### Req. 4-4 블록 정보 조회

#### 스켈레톤 프로젝트의 이더리움 네트워크 정보를 맞게 수정

- admin.wallet 수정

  ```json
  {
    "address": "1897a446d860070a7439d67f9cd29cb48fe9a495",
    "crypto": {
      "cipher": "aes-128-ctr",
      "ciphertext": "1fd0828230eca5d8debbea42bf603d81346b0b5fc19ed95b1ad4a74880e1b32c",
      "cipherparams": {
        "iv": "a27ac1937793e4a3ae0ab6883c6c922f"
      },
      "kdf": "scrypt",
      "kdfparams": {
        "dklen": 32,
        "n": 262144,
        "p": 1,
        "r": 8,
        "salt": "6a6d6ba40cc2ff7c8b67f553aeae0a022c93928e2c7ed5051b29442e96335831"
      },
      "mac": "3e7bb46e9777b1a7abc659bf37b008cfff8b9d34ca778f5d51c41e4f5b4d4434"
    },
    "id": "3b3ba345-7a93-4cf7-8b7a-88e4517a44b8",
    "version": 3
  }
  ```

- index.js 수정

  ```javascript
  const API_BASE_URL = "http://127.0.0.1:8080";
  const BLOCKCHAIN_URL = "http://127.0.0.1:8545";
  const BLOCKCHAIN_WEBSOCKET_URL = "ws://127.0.0.1:8545";
  const ITEM_INVENTORY_CONTRACT_ADDRESS = "";
  const CASH_CONTRACT_ADDRESS = "";
  const PURCHASE_RECORD_CONTRACT_ADDRESS = "";
  ```

#### 스켈레톤 프로젝트 구동

```bash
npm install
npm run serve
```

- 웹 브라우저에 접속하여 확인
