package Users;

public class User {
	private String host = null;
	private String port = null;
	
	public User(){
		host = "localhost";
		port = "";
	}
	
	public User(String port){
		this.port = port;
	}
	
	

	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
}
