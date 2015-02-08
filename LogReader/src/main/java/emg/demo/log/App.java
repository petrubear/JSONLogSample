package emg.demo.log;

import emg.demo.log.es.ElasticQueryExecutor;
import emg.demo.log.mq.LogMessageReader;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            LogMessageReader reader = new LogMessageReader();
            String item = reader.nextItem();
            String id = "";
            if (item.length() > 0) {
                id = item.substring(item.indexOf("ID: ") + 4);
                System.out.println(id);
            }
            if (id.length() > 0) {
                ElasticQueryExecutor elastic = new ElasticQueryExecutor();
                elastic.ExecuteQuery("mdc.correlationID:" + id, "logdemo");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
