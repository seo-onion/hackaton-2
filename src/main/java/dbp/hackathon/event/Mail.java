package dbp.hackathon.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class Mail {

    public Mail() {

    }

    @Getter
    @Setter
    public static class HtmlTemplate {
        private String template;
        private Map<String, Object> props;

        // Getters y setters

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Map<String, Object> getProps() {
            return props;
        }

        public void setProps(Map<String, Object> props) {
            this.props = props;
        }
    }

    private String from;
    private String to;
    private String subject;
    private HtmlTemplate htmlTemplate;

    public Mail(String from, String to, String subject, HtmlTemplate htmlTemplate) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.htmlTemplate = htmlTemplate;
    }
}