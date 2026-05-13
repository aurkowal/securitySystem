package pl.coderslab.OrderSystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {

    @RabbitListener(queues = "order_queue")
    public void receiveOrderStatus(String jsonOrder) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Order order = objectMapper.readValue(jsonOrder, Order.class);
            System.out.println("Received order status: " + order.getStatus());

        } catch (Exception e) {
            System.err.println("Error processing received order status: " + e.getMessage());
        }
    }
}
