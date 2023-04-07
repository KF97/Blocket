# SMART CONTRACT

생성일: 2021년 9월 1일 오전 1:39

[](https://ethereum.org/ko/developers/docs/smart-contracts/)

## WHAT IS A SMART CONTRACT?

**🤔 특정 조건을 만족하면 일정한 결과를 반환하는 자동 계약 프로그램을 뜻하기도 한다.**

간단히 말해, 이더리움 블록체인에서 실행하는 프로그램을 가리킨다. 코드와 데이터의 집합체이며 이더리움 블록체인(공용 데이터베이스라고 생각하면 편하다.)에서 특정 주소(address)에 위치한다. 

스마트 컨트랙트는 이더리움 계정의 한 유형이다. 이는 

- balance를 가지고 있으며 (즉 이더리움 잔액이 존재한다.)
- 네트워크를 통해 트랜잭션을 보낼 수 있다는 뜻이다.

그러나 일반 사용자에 의해 제어되지 않으며, 대신에 네트워크 상에 존재하고 프로그래밍된 의도대로 실행된다. 사용자 계정(EOA)은 스마트 컨트랙트에서 정의된 함수(function, 또는 기능)를 실행하는 트랜잭션을 제출(submitting)함으로서 스마트 컨트랜트와 상호작용할 수 있다.

스마트 컨트랙트는 일반적인 계약서와 마찬가지로 규칙(rules)을 정의할 수 있으며, 이를 자동적으로 코드를 통해 실행한다(and automatically enforce them via the code). 

기본적으로 스마트 컨트랙트는 삭제되지 않으며 상호작용 또한 되돌릴 수 없다. (smart contracts can not be deleted by default, and interactions with them are irreversible)

## A DIGITAL VENDING MACHINE

스마트 컨트랙트에 대해 가장 적절한 예시는 바로 자판기일 것이다. 올바른 입력(inputs)을 하면, 확실한 결과(output)가 보장된다. 자판기에서 돈을 넣고 버튼을 누르면(input) 해당 제품(output)을 얻는 것과 동일한 것이다.

**🤔 또는 전자 화폐의 잔액이 일정 액수 이하면 신용카드로 자동으로 충전하는 서비스도 스마트 계약의 예시라고 할 수 있다.**

```python
money + snack selection = snack dispensed (돈과 함께 스낵을 선택하면 = 스낵이 제공된다. )
```

위와 같은 로직은 자판기에서 프로그래밍되어 있는 로직일 것이다. 

스마트 컨트랙트는 자판기와 마찬가지로, 이와 같은 로직으로 프로그래밍되어 있다. 여기 자판기가 스마트 컨트랙트처럼 보이게 하는지에 대한 간단한 예시를 소개한다. 

```java
pragma solidity 0.6.11;

// VendingMachine smart Contract를 통해 우리는 컵케이크를 100이라는 비용을 통해 구매할 수 있다. 
contract VendingMachine {

    // Declare state variables of the contract
		// 계약서의 상태 변수를 정의한다. 
    address public owner;
    mapping (address => uint) public cupcakeBalances;

    // When 'VendingMachine' contract is deployed:
    // VendingMachine 계약서가 사용될 때
    // 1. set the deploying address as the owner of the contract
    // 1. 사용주소(deployng address)를 계약서의 주인(the owner of the contract)으로서 설정한다. 
    // 2. set the deployed smart contract's cupcake balance to 100
    // 2. 사용된 스마트 계약서의 컵케이크 가격을 100으로 설정한다. 
    constructor() public {
        owner = msg.sender;
        cupcakeBalances[address(this)] = 100;
    }

    // Allow the owner to increase the smart contract's cupcake balance
    // 계약서의 주인만이 판매할 컵케이크의 가격을 수정할 수 있다.
    function refill(uint amount) public {
        require(msg.sender == owner, "Only the owner can refill.");
        cupcakeBalances[address(this)] += amount;
    }

    // Allow anyone to purchase cupcakes
    // 누구나 컵케이크를 구매할 수 있도록 한다. 
    function purchase(uint amount) public payable {
        require(msg.value >= amount * 1 ether, "You must pay at least 1 ETH per cupcake");
        require(cupcakeBalances[address(this)] >= amount, "Not enough cupcakes in stock to complete this purchase");
        cupcakeBalances[address(this)] -= amount;
        cupcakeBalances[msg.sender] += amount;
    }
}
```

## PERMISSIONLESS

모든 유저가 스마트 계약서를 작성할 수 있고, 사용할 수 있다. 필요한 것은 스마트 컨트랙트 언어를 통해 어떻게 코드를 작성하는지 배우는 것과, 계약서를 사용하기 위해 필요한 충분한 ETH를 갖고 있는 것이다.

스마트 컨트랙트를 사용하는 것은 기술적으로 트랜잭션의 형태이며, 따라서 ETH Transfer를 할 때와 마찬가지로 일정 Gas(일종의 수수료)를 지불해야 한다. 다만 스마트 컨트랙트를 사용하기 위해 필요한 Gas는 ETH Transfer에 필요한 Gas에 비해 훨씬 비싸다.