package pl.coderslab.OrderSystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendOrderStatus(Order order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOrder = objectMapper.writeValueAsString(order);
            rabbitTemplate.convertAndSend("order_queue", jsonOrder);
            System.out.println("Order status sent: " + order.getStatus());
        } catch (JsonProcessingException e) {
            System.err.println("Error converting Order to JSON: " + e.getMessage());
        }
    }
}