package dbp.hackathon.event;

import dbp.hackathon.Ticket.Ticket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class EventCreateTicket extends ApplicationEvent {

    private String email;
    private Ticket ticket;

    public EventCreateTicket(Object source, String email, Ticket ticket) {
        super(source);
        this.email = email;
        this.ticket = ticket;
    }

    public String getEmail() {
        return email;
    }

    public Ticket getTicket() {
        return ticket;
    }
}