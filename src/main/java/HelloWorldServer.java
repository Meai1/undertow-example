import static io.undertow.Handlers.resource;

import java.io.File;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.hornetq.jms.server.embedded.EmbeddedJMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.util.Headers;

public class HelloWorldServer {
	  final static Logger logger = LoggerFactory.getLogger(MyHttpHandler.class);

	  // -agentlib:TakipiAgent
	  // https://app.takipi.com/
	  
    public static void main(final String[] args) {
    	
    	try
        {
           EmbeddedJMS jmsServer = new EmbeddedJMS();
           jmsServer.start();
           
           ConnectionFactory cf = (ConnectionFactory)jmsServer.lookup("ConnectionFactory");
           Topic topic = (Topic)jmsServer.lookup("/topic/chat");

           Connection connection = null;
           try
           {
              connection = cf.createConnection();
              Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

              MessageProducer producer = session.createProducer(topic);
              MessageConsumer consumer = session.createConsumer(topic);

              // use JMS bytes message with UTF-8 String to send a text to Stomp clients
              String text = "message sent from a Java application at " + new Date();
              //BytesMessage message = session.createBytesMessage();
              //message.writeBytes(text.getBytes("UTF-8"));
              TextMessage message = session.createTextMessage(text);
              System.out.println("Sent message: " + text);
              System.out.println("Open up the chat/index.html file in a browser and press enter");
              System.in.read();
              //producer.send(message);

              connection.start();

              while(true)
              {
	              message = (TextMessage)consumer.receive();
	              System.out.println("Received message: " + message.getText());
              }
           }
           finally
           {
              if (connection != null)
              {
                 connection.close();
              }

              jmsServer.stop();
              System.out.println("Stopped the JMS Server");
           }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
    	
    	logger.info("HI");
        Undertow server = Undertow.builder()
                .addListener(8089, "localhost")
                .setHandler(new MyHttpHandler(resource(new FileResourceManager(new File("/home/pc/workspace/undertow-example/htdocs"), 100))
                        .setDirectoryListingEnabled(true))).build();
        server.start();
    }                       

}