package pl.coderslab.JMSProjectMini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;


@Component
public class MessageReceiver {

    @RabbitListener(queues = "moja_kolejka")
    public void receiveMessage(String jsonMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            MessageRepresentation message = objectMapper.readValue(jsonMessage, MessageRepresentation.class);

            System.out.println("Otrzymano wiadomość: " + message.getContent());
            System.out.println("Autor: " + message.getSender());

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}