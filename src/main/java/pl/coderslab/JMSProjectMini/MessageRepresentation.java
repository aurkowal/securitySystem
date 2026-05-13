package pl.coderslab.JMSProjectMini;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRepresentation {

    @JsonProperty("message")
    private String content;
    @JsonProperty("author")
    private String sender;
    private int id;

    public MessageRepresentation() {
    }

    public MessageRepresentation(String content, String sender, int id) {
        this.content = content;
        this.sender = sender;
        this.id = id;
    }

    // Getters and setters

}
