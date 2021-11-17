package server;
import client.clientit;

public class chatter {

	public String name;
	public clientit client;
	
	//constructor
	public chatter(String name, clientit client){
		this.name = name;
		this.client = client;
	}

	
	//getters and setters
	public String getName(){
		return name;
	}
	public clientit getClient(){
		return client;
	}
	
	
}