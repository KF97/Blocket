# Solidity 파고들기

생성일: 2021년 9월 1일 오후 4:05

Solidity에서 Contracts는 OOP에서 클래스와 유사하다. 각각의 컨트랙트는 state variables, functions, function Modifier, Events, Struct Types, Enum Type을 선언할 수 있다. 또한 컨트랙트는 다른 컨트랙트를 상속받을 수 있다.

또한 컨트랙트에는 라이브러리, 인터페이스로 불리는 특별한 종류가 존재한다.

## State Variables

컨트랙트 스토리지에 영구적으로 저장되는 변수이다. 

```java
pragma solidity >=0.4.0 <0.6.0;

contract SimpleStorage {
    uint storedData; // State variable
    // ...
}
```

## Functions

Functions는 컨트랙트 내에서 실행가능한 코드의 단위를 뜻한다. 

```java
pragma solidity >=0.4.0 <0.6.0;

contract SimpleAuction {
    function bid() public payable {
        // ...
    }
}
```

→ 함수 내부 혹은 외부에서 다른 함수를 호출할 수 있으며 have different lavels of visibility towards other contracts

## 😕Function Modifiers

Function Modifiers는 선언적 방법으로 함수의 의미를 수정하기 위해 사용될 수 있다.

```java
pragma solidity >=0.4.22 <0.6.0;

contract Purchase {
    address public seller;
    
    modifier onlySeller() {
        require(
            msg.sender == seller,
            "Only seller can call this."
        );
        _;
    }
    
    function abort() public view onlySeller { // Modifier usage
        // ...
    }
}
```

### 보충

[solidity - Modifier(함수변경자)](https://caileb.tistory.com/141)

function modifier : 함수 변경자

Modifier는 함수의 동작을 변경시키기 위해 사용된다. 이 말의 의미는, Modifier를 사용하면 함수를 실행시키기 전과 실행시킨 후에 특정한 기능을 할 수 있도록 만들 수가 있다는 것이다. 따라서, **Modifier를 사용하면 사전에 어떤 조건에 부합되는지 확인이 가능하다**.

ex) 함수를 실행시키기 전에, 스마트 컨트랙트를 배포한 사람의 계정과 동일한 계정인지를 확인할 수도 있다.   

```java
pragma solidity >=0.5.0 <0.7.0;

contract joker {
    uint public data = 0;
    
    modifier check { // Modifier 정의
        data++;
        _;
    }
    
    function getWithCheck() check public { // Modifier를 함수에 적용
        // ...
    }
}
```

- Modifier를 작성할 때 `_` 를 사용하게 된다. `_` 는 함수를 실행하는 시점을 나타내는 것이다. 따라서 함수를 실행시키기 위해 행동을 제한하려는 어떤 코드를 추가하기를 원한다면 modifier 안에 `_` 를 기준으로 작성하면 된다. 다음의 예시를 살펴보자.

    ```java
    pragma solidity >=0.5.0 <0.7.0;
    
    contract joker {
        uint public mutex = 0;
        uint public exeCnt = 0;
        
        modifier check {
            mutex++;
            _;
            mutex--;
        }
        
        function getWithCheck() check public {
            if (mutex == 1)
                exeCnt = exeCnt + 1;
        }
    }
    
    contract caller {
        event log(uint data1, uint data2);
        
        function func() public {
            joker k = new joker();
            
            emit log(k.mutex(), k.exeCnt()); // data1:0, data2:0
            k.getWithCheck();
            emit log(k.mutex(), k.exeCnt()); // data1:0, data2:1
            k.getWithCheck();
            emit log(k.mutex(), k.exeCnt()); // data1:0, data2:0
            k.getWithCheck();
    }
    ```

    ![Untitled](Solidity 파고들기 e3f158fffd2e44668eaf87e7b852a411.assets/Untitled.png)

    위 코드는 `getWithCheck` 라는 함수에 check라는 Modifier를 추가시켰다. check는 함수를 호출시키기 전에 mutex값을 1 증가시키고, 호출완료된 후에는 mutex 값을 1 감소시키는 modifier이다. 위 코드는 check modifier를 getWithCheck() 함수에 추가했기 때문에, getWithCheck 함수를 실행시키기 전에 mutex라는 값을 증가시키고, getWithCheck 함수에 있는 내용을 모두 실행하고, getWithCheck 함수가 종료된 후에는 mutex라는 값을 감소시키는 것이다. 참고로 modifier를 사용하지 않고 함수 하나를 추가해서 위와 동일한 처리를 할 수도 있다.

    ## Events

    ## Struct Types

    ## Enum Types