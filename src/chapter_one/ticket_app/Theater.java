package chapter_one.ticket_app;

public class Theater {
    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    /**
     * 초기 버전. 쓸모없는 중복이 다수 보인다
     * @param audience 관중
     */
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

    public void cleanedEnter(Audience audience){
        Ticket ticket = ticketSeller.getTicketOffice().getTicket();
        if (!audience.getBag().hasInvitation()){
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
        }
        audience.getBag().setTicket(ticket);
    }
}
