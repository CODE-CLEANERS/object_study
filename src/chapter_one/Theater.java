package chapter_one;

public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    /**
     * 초기 버전. 쓸모없는 중복이 다수 보인다
     * @param audience 관중
     */
    // TODO: 2023-12-06 이 매서드를 리팩토링 해보기 (다른 클래스 또한 무제한으로 변경하거나 확장해도 무방함)
    public void enter(Audience audience){
        if (audience.getBag().hasInvitation()){
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
            return;
        }
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        // TODO: 2023-12-06 이 부분에서 ticket.getFee() 가 아닌 ticket.getCreditCard().pay() 의 형식으로 매서드가 바뀐다면?
        // TODO: 변경에 유연한 코드를 만들기 위해서는 어떻게 해야할까?
        audience.getBag().minusAmount(ticket.getFee());
        ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        audience.getBag().setTicket(ticket);
    }
}