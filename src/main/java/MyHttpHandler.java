import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;

/*
import com.dkhenry.RethinkDB.RqlConnection;
import com.dkhenry.RethinkDB.RqlMethodQuery.TableCreate;
import com.dkhenry.RethinkDB.errors.RqlDriverException;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.rethinkdb.Ql2.Term;*/

import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHttpHandler implements HttpHandler {

	private ResourceHandler next;
	  final static Logger logger = LoggerFactory.getLogger(MyHttpHandler.class);

	  
	public MyHttpHandler(ResourceHandler next) {
		this.next = next;
	}

	public void handleRequest(HttpServerExchange exchange) throws Exception {
		// TODO Auto-generated method stub
		if (exchange.getRelativePath().equals("/hi")) {
			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
					"text/plain");
			exchange.getResponseSender().send("Helloj World");
			logger.info("Entering application.");

			/*boolean rvalue = false;
			try { 
				RqlConnection r = RqlConnection.connect("localhost",28015);
				TableCreate tc = r.db("test").table_create("lol");
				Term t = tc.build();
	
				r.close();
			} catch (RqlDriverException e) {
				rvalue = true; 
			}*/
			/*try {
			MongoClient mongoClient = new MongoClient("localhost");

			DB db = mongoClient.getDB("mydb");
			boolean auth = db.authenticate("ivo", "ab252525".toCharArray());
			if (auth)
			{
				Set<String> colls = db.getCollectionNames();

				for (String s : colls) {
				    System.out.println(s);
				}
			}
			}catch(Exception e)
			{
				logger.error(e.getMessage());
			}*/
		} else {
			next.handleRequest(exchange);
		}
	}

}
