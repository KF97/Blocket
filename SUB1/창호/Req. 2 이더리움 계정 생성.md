# Sub PJT I (블록체인)

- 이더리움 계정 생성
    - eth0 신규 계정 생성

    ```json
    // 여기서는 ssafy1을 패스워드로 지정했다
    personal.newAccount("password")
    "0x08b71bf7b200d3547b1a797e335cd8465cf2824d"
    ```

    - 코인베이스 (Coinbase) 설정

    ```json
    miner.setEtherbase("0x08b71bf7b200d3547b1a797e335cd8465cf2824d")
    // true가 리턴됨
    ```

    - 마이닝 (Mining) 작업

    ```json
    // 마이닝 시작
    miner.start(1)
    
    // 마이닝 진행 상태 확인
    eth.mining
    
    // 마이닝 중단
    miner.stop()
    
    // 마이닝 결과 확인
    eth.getBalance(eth.accounts[0])
    ```

    - eth1 enode 확인

    ![https://user-images.githubusercontent.com/69533427/131914570-59f18b77-cf4d-47a0-8952-a94a14dfa8f1.png](https://user-images.githubusercontent.com/69533427/131914570-59f18b77-cf4d-47a0-8952-a94a14dfa8f1.png)

    - eth0 노드에 eth1 노드 등록
        - vagrant에서 설정할 때, eth1의 ip 주소를 192.168.50.11로 설정했으므로 위의 enode값에서의 ip주소를 변경해줘야 한다
        - 이번 테스트 환경에서는 eth0에서만 RPC API를 사용하므로 eth0에서 eth1노드를 등록한다.

    ```powershell
    admin.addPeer("enode://e9b0e9f37655064f2ea1a9fe3488f6c739b028927bba7c29048b410f9c274401e8721b0e02b37ef77ee6dc3cb7c769637182aa8adf1411420d5676611352d214@192.168.50.11:30303?discport=0")
    ```

    - peerCount가 1늘었음을 확인할 수 있고 admin.peers 배열에서 방금 연결한 eth1가 remoteAddress에 들어와 있으면 제대로 등록된 것이다.

    ```json
    > net.peerCount
    1
    > admin.peers
    [{
        caps: ["eth/65", "eth/66", "snap/1"],
        enode: "enode://e9b0e9f37655064f2ea1a9fe3488f6c739b028927bba7c29048b410f9c274401e8721b0e02b37ef77ee6dc3cb7c769637182aa8adf1411420d5676611352d214@192.168.50.11:30303?discport=0",
        id: "2996e3b880ff8ecdaadbfae385892b607979dd6973c936980126548a581f5c01",
        name: "Geth/v1.10.8-stable-26675454/linux-amd64/go1.16.4",
        network: {
          inbound: false,
          localAddress: "192.168.50.10:57394",
          remoteAddress: "192.168.50.11:30303",
          static: true,
          trusted: false
        },
        protocols: {
          eth: {
            difficulty: 16,
            head: "0x8e6b4372b7a12c80cd3c6ba97f4d11732dedac61d7f6d20b3a34001366bbd55e",
            version: 66
          },
          snap: {
            version: 1
          }
        }
    }]
    ```

    ![https://user-images.githubusercontent.com/69533427/131915544-8c948d2b-0404-4a81-8725-369ab0124a5d.png](https://user-images.githubusercontent.com/69533427/131915544-8c948d2b-0404-4a81-8725-369ab0124a5d.png)

    - eth1에서 블록이 제대로 쌓여가는지 확인하기 위해서는 eth1에 생성된 마지막 블럭의 hash값과 eth0에서 해당 블록의 hash값의 동일 여부를 확인하면 된다

    ```json
    > web3.eth.getBlock('latest')
    {
      difficulty: 133575,
      extraData: "0xd883010a08846765746888676f312e31362e34856c696e7578",
      gasLimit: 9616779,
      gasUsed: 0,
      hash: "0x27934e47d4ddc49ad9a3bcb8dbd83ccef5a5159fc59e37964989af0b6848b2e2",
      logsBloom: "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
      miner: "0x08b71bf7b200d3547b1a797e335cd8465cf2824d",
      mixHash: "0x801173117e12c856045c53b686ba84e8b63dbc060ff219de6ccff008ab5e0efb",
      nonce: "0x30426eaa130635c4",
      number: 40,
      parentHash: "0x62739174216f7afea9336cc9024d4bf0a8f5b10878f7877bf8e8c531a1be2079",
      receiptsRoot: "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
      sha3Uncles: "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
      size: 536,
      stateRoot: "0xeba9b23e1ae74ff67b39d4ab73f2eda46f583cd10be2a7d9520aeeb1d77e7641",
      timestamp: 1630614884,
      totalDifficulty: 5292844,
      transactions: [],
      transactionsRoot: "0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421",
      uncles: []
    }
    ```

    ![https://user-images.githubusercontent.com/69533427/131915616-6ad891ba-acc1-434b-a947-40ec0f4b236b.png](https://user-images.githubusercontent.com/69533427/131915616-6ad891ba-acc1-434b-a947-40ec0f4b236b.png)

    - 마지막 블록 번호가 40번이고 해당 hash값은"0x27....b2e2" 이다.  eth0에서 다음과 같이 확인한다

    ```json
    > web3.eth.getBlock(40).hash
    "0x27934e47d4ddc49ad9a3bcb8dbd83ccef5a5159fc59e37964989af0b6848b2e2"
    ```

    ![https://user-images.githubusercontent.com/69533427/131915694-f9c3d7bf-aa20-4d09-8e1a-731eb5624e7b.png](https://user-images.githubusercontent.com/69533427/131915694-f9c3d7bf-aa20-4d09-8e1a-731eb5624e7b.png)

    - eth0과 eth1이 정상적으로 연결된 것을 확인할 수 있다.
    - eth1 신규 계정 생성 (geth 콘솔을 나가서 진행)

    ```json
    geth --datadir ~/dev/eth_localdata account new
    
    // 패스워드 입력하면 address 나옴 (여기서는 ssafy2)
    
    // 다음과 같이 keystore 파일을 확인할 수 있다.
    vagrant@eth1:~/dev/eth_localdata/keystore$ ls
    UTC--2021-09-02T21-22-53.787585681Z--65a3e4c26aae570e715ac724fc374c7831d5e70b
    
    // geth 콘솔을 구동해서 확인하면 다음과 같이 계정이 생성된 것을 알 수 있다.
    > eth.accounts
    ["0x65a3e4c26aae570e715ac724fc374c7831d5e70b"]
    ```

