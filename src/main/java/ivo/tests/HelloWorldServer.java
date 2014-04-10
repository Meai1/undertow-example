package ivo.tests;

import static io.undertow.Handlers.resource;

import java.io.File;

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
    	logger.info("HI");
        Undertow server = Undertow.builder()
                .addListener(8089, "localhost")
                .setHandler(new MyHttpHandler(resource(new FileResourceManager(new File("/home/pc/workspace/undertow-example/htdocs"), 100))
                        .setDirectoryListingEnabled(true))).build();
        server.start();
    }                       

}