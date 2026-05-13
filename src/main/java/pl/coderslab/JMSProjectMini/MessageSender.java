package pl.coderslab.JMSProjectMini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;


@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendJsonMessage(MessageRepresentation message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);

            rabbitTemplate.convertAndSend("moja_kolejka", jsonMessage);

            System.out.println("Wiadomość wysłana: " + jsonMessage);
        } catch (JsonProcessingException e) {
            System.err.println("Błąd konwersji do JSON: " + e.getMessage());
        }
    }
}