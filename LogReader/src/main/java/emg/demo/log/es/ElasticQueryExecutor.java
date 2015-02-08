package emg.demo.log.es;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

/**
 * Created by edison on 2/8/15.
 */
public class ElasticQueryExecutor {
    public void ExecuteQuery(String query, String index) throws Exception {
        if (query == null || index == null) {
            throw new InvalidArgumentException(new String[]{"query or index null"});
        }

        Node node = new NodeBuilder().clusterName("elasticsearch").node();
        Client client = node.client();

        try {
            SearchResponse response = client.prepareSearch(index)
                    .setTypes("demologs")
                    .setQuery(QueryBuilders.queryString(query))
                    .execute().actionGet();
            System.out.println("****************** response ******************");
            System.out.println(response.toString());
            System.out.println("****************** response ******************");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            client.close();
            node.close();
        }

    }
}
