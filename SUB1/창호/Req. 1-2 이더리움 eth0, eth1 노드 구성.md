# Sub PJT I (블록체인)

- 이더리움 노드 구성

    공통 (eth0 ,eth1)

    - 이더리움 소프트웨어는 Geth 1.9 (stable) 이상을 사용
    - Geth는 가상 머신 상에서 동작하도록 구축

    1) 이더리움 eth0 노드 구성

    - eth0 노드의 경우 RPC API를 호출할 수 있도록 활성화
    - 먼저 eth0 가상머신에 접속 `vagrant ssh eth0`
    - Geth 설치

    ```jsx
    sudo add-apt-repository -y ppa:ethereum/ethereum
    
    // install the stable version of go-ethereum:
    sudo apt-get update
    sudo apt-get install ethereum
    
    // 제대로 설치되었는지 확인
    geth version
    ```

    - 디렉토리 생성 및 genesis.json 작성

    ```jsx
    // p --parent: 상위 경로도 함께 생성되는 명령어
    mkdir -p dev/eth_localdata
    cd dev/eth_localdata
    
    // 홈 디렉토리로 
    cd ~
    vi genesis.json
    ```

    - genesis.json

    ```json
    {
        "config": {
            "chainId": 921,
            "homesteadBlock": 0,
            "eip150Block": 0,
            "eip155Block": 0,
            "eip158Block": 0
        },
        "difficulty": "0x10",
        "coinbase": "0x0000000000000000000000000000000000000000",
        "gasLimit": "9999999",
        "alloc": {},
        "extraData": "",
        "nonce": "0xdeadbeefdeadbeef",
        "mixhash": "0x0000000000000000000000000000000000000000000000000000000000000000",
        "parentHash": "0x0000000000000000000000000000000000000000000000000000000000000000",
        "timestamp": "0x00"
    }
    ```

    - 용어 설명
        - config
            - 새로 생성할 프라이빗 블록체인 네트워크의 설정을 담당하는 오브젝트.
        - chainId
            - 현재 chain을 식별하는 Network ID (여기서는 921)
            - [DAO 사건](http://wiki.hash.kr/index.php/%EB%8B%A4%EC%98%A4) 이후 [하드포크](https://steemit.com/kr/@loum/hardfork-softfork)하여 추가된 항목이다
                - [Replay Attack](http://wiki.hash.kr/index.php/%EB%A6%AC%ED%94%8C%EB%A0%88%EC%9D%B4_%EA%B3%B5%EA%B2%A9)을 막기위해 사용된다.
        - homesteadBlock
            - 블록체인의 [Release 버전](https://en.wikipedia.org/wiki/Ethereum#Milestones)을 의미
        - eip150Block, eip155Block, eip158Block
            - EIPs는 Ethereum Improvement Proposals 의 약자로, 이더리움 네트워크를 개선하는 여러 제안들을 말한다. 해당 제안의 적용이 시작되는 블록 ID를 적으면 된다.
            - 학습을 위해 처음부터 이 보안을 적용할 것이므로 0으로 설정
        - difficulty
            - 블록을 채굴(mining)할 때와 연관된 난이도 (여기서는 0x10으로 설정)
        - coinbase
            - 해당 블록에 대해서 채굴을 성공하면 얻게되는 총 보상금이 들어갈 160비트의 계좌주소
        - gasLimit
            - 거래(transaction) 시 발생하는 최대 가스량을 의미
        - alloc
            - Genesis 블록 생성 시 지정한 지갑에 할당된 양을 미리 채운다. (여기서는 비워둠)
        - extraData
            - 광부(miner)가 블록에 포함시킨 임의의 데이터 (최대 32byte, 여기서는 N/A)
        - nonce
            - mixhash 옵션과 함께 현 블록의 POW를 위해서 충분한 양의 계산을 수행했음을 증명해주는 옵션이다.
        - mixhash
            - nonce값과 결합하여 이 블록에 충분한 양의 계산이 수행되었음을 증명하는 256bit의 해시값이다. (여기서는 0)
        - parentHash
            - nonce와 mixhash를 포함한 부모 블록의 헤더에 대한 해시 값을 갖는 옵션
            - genesis block이므로 0
        - timestamp
            - 해당 블록이 시작된 시점을 Unix의 time() 함수와 동일한 아웃풋으로 나타낸다
    - genesis 블록 생성

    ```json
    geth --datadir ~/dev/eth_localdata init ~/genesis.json
    ```

    ![https://user-images.githubusercontent.com/69533427/131856823-0cf37be0-dabe-487f-8d1c-6930d363824f.png](https://user-images.githubusercontent.com/69533427/131856823-0cf37be0-dabe-487f-8d1c-6930d363824f.png)

    - eth0 노드 구성

    ```json
    geth --networkid 921 --maxpeers 2 --datadir ~/dev/eth_localdata --allow-insecure-unlock --nodiscover --port 30303 --http --http.port 8545 --http.addr 0.0.0.0 --http.corsdomain "*" --http.api admin,net,miner,eth,rpc,web3,txpool,debug,personal --miner.threads 1
    ```

    [—allow-insecure-unlock](https://stackoverflow.com/questions/59340803/geth-option-allow-insecure-unlock-what-exactly-does-it-mean)

    —nodiscover : 주위 노드들 중 나를 수동으로 등록하지 않는 이상 자동으로 스캔하여 등록하지 말라는 뜻이다.

    [—http.corsdomain](https://geth.ethereum.org/docs/rpc/server) : 웹페이지에서 해당 API에 접근을 가능하게 하기 위한 옵션

    —http.api : RPC API를 사용하기 위한 옵션

    - geth에 접속 (새로 cmd 창을 띄우고 eth0 가상머신에 접속)

    ```json
    geth attach http://0.0.0.0:8545
    ```

    2) 이더리움 eth1 노드 구성

    - eth1 가상머신에 접속 `vagrant ssh eth1`
    - Geth 설치
    - eth0과 마찬가지로 genesis 블록 생성
    - eth1 노드 구성

    ```json
    geth --networkid 921 --maxpeers 2 --datadir ~/dev/eth_localdata --allow-insecure-unlock --nodiscover --port 30303 --http --http.port 8545 --http.addr localhost --http.corsdomain "*" --miner.threads 1
    ```

    - geth에 접속 (새로 cmd 창을 띄우고 eth1 가상머신에 접속)

    ```json
    geth attach http://localhost:8545
    ```

