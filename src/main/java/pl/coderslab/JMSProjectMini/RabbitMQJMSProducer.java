package pl.coderslab.JMSProjectMini;

import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.qpid.jms.JmsConnectionFactory;


import javax.jms.*;
import org.apache.qpid.jms.JmsConnectionFactory;


public class RabbitMQJMSProducer {

    public static void main(String[] args) {
        try {
            System.out.println("START PRODUCER");


            JmsConnectionFactory factory =
                    new JmsConnectionFactory("amqp://localhost:5672");
            factory.setUsername("guest");
            factory.setPassword("guest");


            Connection connection = factory.createConnection();
            connection.start();
            System.out.println("CONNECTED");

            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            System.out.println("session ok");

            Destination destination =
                    session.createQueue("myQueue");

            System.out.println("destination ok");


            MessageProducer producer =
                    session.createProducer(destination);

            System.out.println("producer ok");

            TextMessage message =
                    session.createTextMessage("Hello RabbitMQ JMS!");

            producer.send(message);
            System.out.println("MESSAGE SENT ✅");

            producer.close();
            session.close();
            connection.close();

            System.out.println("END PRODUCER");
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
