package emg.demo.log;



import emg.demo.log.mq.LogMessageProducer;
import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger LOGGER = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {

        String defaultID = UUID.randomUUID().toString();
        MDC.put("correlationID", defaultID);
        LOGGER.info("Sample Start");

        for(int i = 0; i<10; i++){
            LOGGER.info("Iteration: {}", i);
            thread(new LogMessageProducer(), false);
        }

        MDC.remove("correlationID");
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
}
