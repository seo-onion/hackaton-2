package dbp.hackathon.Ticket;

import dbp.hackathon.event.EventCreateTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;  // Añadir el publisher de eventos

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest request) {
        Ticket newTicket = ticketService.createTicket(request.getEstudianteId(), request.getFuncionId(), request.getCantidad());

        if (newTicket != null) {
            System.out.println("Ticket creado con éxito, publicando evento...");
            eventPublisher.publishEvent(new EventCreateTicket(this, newTicket.getEstudiante().getEmail(), newTicket));
        } else {
            System.out.println("Error creando el ticket");
        }

        return ResponseEntity.ok(newTicket);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.findById(id);
        if (ticket == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<Iterable<Ticket>> getTicketsByEstudianteId(@PathVariable Long estudianteId) {
        Iterable<Ticket> tickets = ticketService.findByEstudianteId(estudianteId);
        return ResponseEntity.ok(tickets);
    }

    @PatchMapping("/{id}/changeState")
    public ResponseEntity<?> changeTicketState(@PathVariable Long id) {
        try {
            ticketService.changeState(id);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }
}
