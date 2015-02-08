package emg.demo.log.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.UUID;

/**
 * Created by edison on 2/7/15.
 */
public class LogMessageProducer implements Runnable{

    private static Logger LOGGER = LoggerFactory.getLogger(LogMessageProducer.class);
    @Override
    public void run() {
        try {
            String localID = UUID.randomUUID().toString();
            MDC.put("correlationID", localID);

            //ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("LogDemo.queue");

            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String text = String.format("Message From: %s ID: %s", Thread.currentThread().getName(), localID);
            TextMessage message = session.createTextMessage(text);

            producer.send(message);
            session.close();

            connection.close();
            LOGGER.info("MESSAGE SENT FROM: {}", Thread.currentThread().getName());

            MDC.remove("correlationID");
        } catch (Exception ex){
            LOGGER.error("Error on MessageProducer: {}", ex.getMessage(), ex);
        }
    }
}
