package Users;

public class User {
	private String Ip = null;
	
	public User(){
		Ip = "No ip : error";
	}
	
	public User(String userip){
		Ip = userip;
	}
	
	//GET/SET
	public String getIp(){
		return Ip;
	}
	
	public void setIp(String ip){
		Ip = ip;
	}

}
