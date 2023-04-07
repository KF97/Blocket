# Sub PJT I (블록체인)

- 이더리움 트랜잭션 생성
    - 계정 간 이더(ETH) 전송 트랜잭션 생성 (eth0 geth console에서)

    ```json
    // 트랜잭션 생성을 위한 계정 보안 해제 (Unlock) 
    personal.unlockAccount(eth.accounts[0])
    
    // 트랜잭션 오브젝트 구조
    // from: eth0의 0번 계좌, to : eth1의 0번 계좌 
    tx = { from: eth.accounts[0], to: eth.accounts[1], value: 1e17,
    gas: 90e3, gasPrice: 20e9, nonce:0}
    
    // 트랜잭션 보내기
    eth.sendTransaction(tx)
    "0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a"
    ```

    - 트랜잭션 hash값 확인

    ```powershell
    eth.getTransaction("0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a")
    
    {
      blockHash: null,
      blockNumber: null,
      from: "0x08b71bf7b200d3547b1a797e335cd8465cf2824d",
      gas: 90000,
      gasPrice: 20000000000,
      hash: "0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a",
      input: "0x",
      nonce: 0,
      r: "0x19f4ad5b6ce766d0695d58ae3ce2f262eefa7ec6f19ad5307f30fc7889082dd8",
      s: "0x5545048c7b511e27bf90feb7e901ea2f04150d0c754a578cabcd5522d1ae3e44",
      to: "0x65a3e4c26aae570e715ac724fc374c7831d5e70b",
      transactionIndex: null,
      type: "0x0",
      v: "0x755",
      value: 100000000000000000
    }
    ```

    - 현재 블록 hash값과 number의 값이 null이다.
    - 마이닝을 진행하고 일정 시간 후에 다시 트랜잭션 상태를 조회해보자.

    ```powershell
    // 마이닝 시작
    miner.start(1)
    
    // 트랜잭션 상태 조회
    eth.getTransaction("0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a")
    
    {
      blockHash: "0x365882e361716a67029ceb8cba0c1c8084c6ce19e63ed55520f573466abdcdb4",
      blockNumber: 41,
      from: "0x08b71bf7b200d3547b1a797e335cd8465cf2824d",
      gas: 90000,
      gasPrice: 20000000000,
      hash: "0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a",
      input: "0x",
      nonce: 0,
      r: "0x19f4ad5b6ce766d0695d58ae3ce2f262eefa7ec6f19ad5307f30fc7889082dd8",
      s: "0x5545048c7b511e27bf90feb7e901ea2f04150d0c754a578cabcd5522d1ae3e44",
      to: "0x65a3e4c26aae570e715ac724fc374c7831d5e70b",
      transactionIndex: 0,
      type: "0x0",
      v: "0x755",
      value: 100000000000000000
    }
    
    // 마이닝 종료
    miner.stop()
    ```

    - 블록값을 확인할 수 있게 됐다. 마이닝된 블록값을 확인해보면..

    ```powershell
    // 마이닝된 블록값을 확인
    eth.getBlock("0x365882e361716a67029ceb8cba0c1c8084c6ce19e63ed55520f573466abdcdb4")
    
    {
      difficulty: 131072,
      extraData: "0xd883010a08846765746888676f312e31362e34856c696e7578",
      gasLimit: 9607389,
      gasUsed: 21000,
      hash: "0x365882e361716a67029ceb8cba0c1c8084c6ce19e63ed55520f573466abdcdb4",
      logsBloom: "0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
      miner: "0x08b71bf7b200d3547b1a797e335cd8465cf2824d",
      mixHash: "0xeee363ba6ab3ee9a2ebdbb92107ce25c8b46ee5ae52563cb8e00208b68c88581",
      nonce: "0x770f03101330cc06",
      number: 41,
      parentHash: "0x27934e47d4ddc49ad9a3bcb8dbd83ccef5a5159fc59e37964989af0b6848b2e2",
      receiptsRoot: "0xaff51b8a581a9895c780be8479f0123a9656a376c6067ac7c2d7200bd92370b0",
      sha3Uncles: "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347",
      size: 652,
      stateRoot: "0x230b7fba5bae9aa76c812b4d1fea6000bfe2518244e43af25c6855c6d191d9a7",
      timestamp: 1630631818,
      totalDifficulty: 5423916,
      transactions: ["0xe7ac02afebcf96ea391e7abec60ab24eb8c98ec0ca3c7ff39ac23947fd74ff0a"],
      transactionsRoot: "0x4daffaf115822958ac763022a0aec279d9bf7c5a6d71ec2ddf5ed89ac00bda1e",
      uncles: []
    }
    ```

    - 마지막으로 eth1의 geth 콘솔에서 잔액을 확인하면 정상적으로 들어왔음을 알 수 있다.

    ```powershell
    > web3.fromWei(eth.getBalance(eth.accounts[0]))
    0.1
    ```