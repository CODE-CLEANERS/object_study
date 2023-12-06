# 1. 객체, 설계

> "소프트웨어 모듈은
> 1. 동작 가능하고 
> 2. 간단하게 변경 가능해야 하고 
> 3. 특별한 훈련 없이도 개발자가 쉽게 읽고 이해해야 한다." - Robert C Matin -

```java
class Thaeter{
...
  public void enter(Audience audience){
        if (audience.getBag().hasInvitation()){
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        audience.getBag().minusAmount(ticket.getFee());
        ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
...
}
```

해당 로직을 말로 옮겨보면

1. 소극장은 관객의 가방을 열어 그 안에 초대장이 들어 있는지 살핀다.
2. 있으면 판매원은 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮긴다.
3. 가방 안에 초대장이 들어 있지 않다면 관람객의 가방에서 티켓 금액만큼의 현금을 꺼내 매표소에 적립한 후에
4. 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮긴다.

- 문제는 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재라는 점.

### 1. 관람객의 입장
- 소극장(Theater) 라는 제 3자가 관람객의 가방을 마음대로 열어본다.

### 2. 판매원
- 소극장이 판매원의 허락 없이 매표소에 보관 중인 티켓과 현금에 마음대로 접근 할 수 있다.
- 티켓을 꺼내 관람객의 가방에 집어놓고 돈을 매표소에 적립하는 일을 판매원이 아닌 소극장이 수행한다.

### 세부적인 내용을 알아야 한다는 문제 (공감이 많이된다...)
- 이 코드를 이해하기 위해서는
  1. Audience 가 Bag을 가지고
  2. Bag 안에는 현금과 티켓이 있고
  3. TicketSeller 가 TicketOffice 에서 티켓을 판매하고
  4. TicketOffice 안에 돈과 티켓이 보관되어 있다
     는 사실을 전부 기억해야 한다.

- 더불어 Audience 와 TicketSeller 를 변경할 경우 Theater 도 변경해야 한다는 문제가 있다.(Theater 측에서 매우 의존적으로 해당 녀석들을 꺼내보니까)


### 문제의 해결을 위해서는?

객체들을 __자율적인 존재__ 로 만들면 된다.

---

## 캡슐화

> __"개념적으로나 물리적으로 객체 내부의 세부적인 사항을 감추는 것을 캡슐화라고 부른다."__

### 캡슐화의 목적

__변경하기 쉬운 객체를 만드는 것이다.__

- 캡슐화를 통해 객체 내부로의 접근을 제한하면 객체와 객체 사이의 결합도를 낮출 수 있다. 즉 객체를 더 쉽게 변경할 수 있게 된다.

위 코드를 변경해보자.

### 1. TicketSeller 클래스에 sellTo 매서드 추가

```java
    public void sellTo(Audience audience){
        if (audience.getBag().hasInvitation()){
            Ticket ticket = ticketOffice.getTicket();
            // Theater 측에서는 Ticketseller.getTicketOffice().getTicket() 형태였다.
            audience.getBag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketOffice.getTicket();
        audience.getBag().minusAmount(ticket.getFee());
        ticketOffice.plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
```

### 2. Theater 클래스 매소드의 변화

```java
public void cleanedEnter(Audience audience){
        ticketSeller.sellTo(audience); // 오직 TicketSeller 의 인터페이스에 의존한다.
    }
```

- TicketSeller 가 내부에 TicketOffice 인스턴스를 포함한다는 사실은 __구현(implementation)__ 영역에 속한다.


## 인터페이스만 공개하라

> __객체를 인터페이스와 구현으로 나누고, 인터페이스만을 공개하는 것__ 은
- 객체 사이의 결합도를 낮추고
- 변경하기 쉬운 코드를 작성하기 위해
  따라야하는 가장 기본적인 설계 원칙이다.

## 무엇이 바뀐것인가?

> __객체가 자신의 구성(implementation) 을 외부에 노출하지 않았다.__ 그리고 이를 통해 객체는 스스로의 책임을 더 단단히 수행하게 되었다.

### 캡슐화와 응집도

> 핵심은 객체 내부의 상태를 캡슐화하고, 객체 간에 오직 __메시지__ 를 통해서만 상호작용하도록 만드는 것이다.
>
> 객체는 다른 객체의 내부 구성(implementation)을 알 필요도 없고, 알아서도 안된다.
>
> 단지 다른 객체에게 요청(메시지) 를 보내고, 요청을 받은 객체는 자기가 가지는 구성(implementation)을 사용해서 그 메시지에 응답하면 된다.


- 밀접하게 연관된 작업만을 수행하고 연관성 없는 작업은 다른 객체에게 위임하는 객체를 __응집도__가 높다고 표현한다.
- 응집도를 높이기 위해서는 객체 스스로 자신의 데이터(상태, 구현...)를 책임져야 한다.
- __객체는 스스로의 데이터를 스스로 처리하는 자율적인 존재여야 한다.__

### 설계와 트레이드오프

위 리팩토링과 같은 방법으로 매서드를 리팩토링했다.

```java
    public void sellTo(Audience audience){
        ticketOffice.plusAmount(audience.buy(ticketOffice.getTicket()));
    }
    
    public void sellToV2(Audience audience){
        ticketOffice.sellTicketTo(audience);
    }
```

- 매서드는 좀 더 응집력있어졌지만, 한 가지 단점이 있다
- 바로 ticketOffice 측에서 Audience 를 알게 되었다(인자로 받는다)는 점이다. 즉, 결합도가 올라갔다

이런 경우 어떤 선택을 해야할까?

> 상황에 따라 다르다. 즉 완벽한 설계는 존재하지 않으며, 상황에 따라 판단하는 능력이 필요하다.

---



---


---

## 영화관, 티켓 어플리케이션 초기 구현에서 내가 파악한 문제점들 (저자의 문제제기를 보기 전)

### 문제가 되는 로직

```java
public void enter(Audience audience){
        if (audience.getBag().hasInvitation()){
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        audience.getBag().minusAmount(ticket.getFee());
        ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
```

1. 객체에서 A.getB().getC().act(); 와 같은 형태가 보인다. 객체가 단순히 상태를 리턴하는 데에 멈추고 있다.
- 그렇다면 이 로직들을 어떻게 정리해야할까?
- 예를 들어 Audience.getBag().setAmount(); 라는 코드는 어떻게 변화해야 할까? 누가 책임을 져야할까?
    - 단순히 Audience.setAmount() 라는 매서드로 줄이기만 하면 OK일까?
    - 아주 간단한 로직이 A-> B-> C 의 요청 체인으로 이루어져야만 할까?
    - 그래야한다면 해당 방식의 장점은 무엇일까?

2. 중복되는 로직이 발견된다.
```java
Ticket ticket = ticketSeller.getTicketOffice().getTicket();
```
- 위 로직은 if문 밖으로 빼도 되는 것 아닐까?
- 좀 더 클린하게 변경도 가능하지 않을까? 한번 먼저 해보자. (매서드의 변경 없이)

```java
public void cleanedEnter(Audience audience){
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        if (!audience.getBag().hasInvitation()){
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        }
        audience.getBag().setTicket(ticket);
    }
```

- 매서드로 빼야하는 부분이 보이지만 그래도 이정도면 원래 버전보다는 깔끔한 듯 하다(중복이 좀 줄었으니)
