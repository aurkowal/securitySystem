package pl.coderslab.UserSystem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(String userData){
        rabbitTemplate.convertAndSend("nowa_kolejka", userData);
    }

}
