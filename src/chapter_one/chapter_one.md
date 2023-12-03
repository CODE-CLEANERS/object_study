# 챕터 1 - 객체 설계

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

---

## 책에서 제시한 문제점들

