package chapter_one.ticket_app;

public class Audience {
    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Bag getBag() {
        return bag;
    }

    public Long buy(Ticket ticket){
        if (bag.hasInvitation()){
            bag.setTicket(ticket);
            return 0L;
        }
        bag.setTicket(ticket);
        bag.minusAmount(ticket.getFee());
        return ticket.getFee();
    }

    public Long buyV2(Ticket ticket){
        return bag.hold(ticket);
    }
}
