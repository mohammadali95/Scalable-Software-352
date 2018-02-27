package DataStructures;

public class Conversation {

	private String port;
	private String host;
	private String name;
	
	public Conversation(String host, String port, String name) {
		this.port = port;
		this.host = host;
		this.name = name;
	}
	
	public String getPort() {return port;}
	
	public String getHost() {return host;}
	
	public String getName() {return name;}
	
	public void setPort(String port) {this.port = port;}
	
	public void setHost(String host) {this.host = host;}
	
	public void setName(String name) {this.name = name;}

}
