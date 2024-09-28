package dbp.hackathon.event;

import dbp.hackathon.Ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventCreateTicketListener implements ApplicationListener<EventCreateTicket> {

    private final QRCodeService qrCodeService;
    private final EmailService emailService;

    @Autowired
    public EventCreateTicketListener(QRCodeService qrCodeService, EmailService emailService) {
        this.qrCodeService = qrCodeService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(EventCreateTicket event) {
        System.out.println("Evento recibido: enviando correo a " + event.getEmail());
        // Obtener los datos del ticket desde el evento
        Ticket ticket = event.getTicket();
        String email = event.getEmail();

        // Generar el código QR
        String qrUrl = qrCodeService.generarQRCode(ticket.getId().toString());

        // Guardar el URL del código QR en el ticket
        ticket.setQr(qrUrl);

        // Enviar el correo con el código QR al estudiante
        try {
            enviarCorreoConQR(email, qrUrl);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void enviarCorreoConQR(String email, String qrUrl) throws MessagingException {
        Mail mail = new Mail();
        mail.setFrom("tu_correo@example.com");
        mail.setTo(email);
        mail.setSubject("Tu ticket y código QR");

        // Construir los datos para la plantilla Thymeleaf
        Mail.HtmlTemplate template = new Mail.HtmlTemplate();
        template.setTemplate("qrTemplate.html");  // Nombre de la plantilla Thymeleaf

        // Pasar las variables necesarias a la plantilla
        Map<String, Object> props = new HashMap<>();
        props.put("qrUrl", qrUrl);
        template.setProps(props);

        mail.setHtmlTemplate(template);

        // Enviar el correo
        emailService.sendEmail(mail);
    }
}
