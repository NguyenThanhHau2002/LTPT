package utils;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

//Include the following static imports before your class definition
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ConnectDB {
	private MongoClient client;
	private MongoDatabase database;
	
//	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	
	public ConnectDB() {
//		ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
//		this.client = MongoClients.create(connectionString);
		this.client = MongoClients.create("mongodb://localhost:27017/");
		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		database = client.getDatabase("sample_training").withCodecRegistry(pojoCodecRegistry);;
	}
	
	public MongoDatabase getDataBDatabase() {
		return database;
	}
}
