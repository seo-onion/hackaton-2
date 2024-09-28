package dbp.hackathon.event;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QRCodeService {

    private final RestTemplate restTemplate;

    public QRCodeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generarQRCode(String data) {
        String qrApiUrl = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + data;

        // La URL del código QR generado
        return qrApiUrl;
    }
}

