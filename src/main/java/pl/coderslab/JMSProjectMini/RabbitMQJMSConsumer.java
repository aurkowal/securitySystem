package pl.coderslab.JMSProjectMini;

import javax.jms.*;

import org.apache.qpid.jms.JmsConnectionFactory;



public class RabbitMQJMSConsumer {

    public static void main(String[] args) throws Exception {

        JmsConnectionFactory factory =
                new JmsConnectionFactory("amqp://localhost:5672");
        factory.setUsername("guest");
        factory.setPassword("guest");

        System.out.println("BEFORE createConnection");
        Connection connection = factory.createConnection();
        connection.start();
        System.out.println("AFTER start");

        Session session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination =
                session.createQueue("myQueue");

        System.out.println("destination ok");
        MessageConsumer consumer =
                session.createConsumer(destination);

        System.out.println("Waiting for message...");

        // ✅ SYNCHRONICZNY ODBIÓR (PEWNY)
        Message message = consumer.receive();

        if (message instanceof TextMessage textMessage) {
            System.out.println("Received message by consumer: " + textMessage.getText());
        }

        consumer.close();
        session.close();
        connection.close();
    }
}
