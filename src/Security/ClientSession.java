package Security;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import Daemons.Client;
import Users.User;
import Users.UserReader;

public class ClientSession {
	
	private User self = null;
	private List<User> userList = null;
	private Boolean isLoggedIn = null;
	private String PathToRecv = null;
	private String PathToSend = null;
	
	public ClientSession(){
		isLoggedIn = false;
	}
	
	public ClientSession(String port){
		self = new User(port);
		userList = UserReader.getAllUsersButMe(self);
		isLoggedIn = true;
		this.PathToRecv = new File("").getAbsolutePath() + "/users/" + port + "/patients/";
		this.PathToSend = new File("").getAbsolutePath() + "/users/" + port + "/toSend/";
	}	
	
	//GET/SET
	
	public String getPathToRecv() {
		return PathToRecv;
	}

	public void setPathToRecv(String pathToRecv) {
		PathToRecv = pathToRecv;
	}

	public String getPathToSend() {
		return PathToSend;
	}

	public void setPathToSend(String pathToSend) {
		PathToSend = pathToSend;
	}
	
	public User getSelf() {
		return self;
	}

	public void setSelf(User self) {
		this.self = self;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public Boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(Boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	public void print(){
		System.out.println("CURRENT SESSION");
		System.out.println("User logged in : " + getIsLoggedIn() + " (" + getSelf().getPort() + ")");
		for(User usr : userList){
			System.out.println("UserList elem : " + usr.getPort());
		}
	}
	
	public void checkForCrash(String port) {
		String absolutePathToDir = new File("").getAbsolutePath();
		if(new File(absolutePathToDir + "/users/" + port).exists() == false){
			new File(absolutePathToDir + "/users/" + port + "/patients").mkdirs();
			new File(absolutePathToDir + "/users/" + port + "/toSend").mkdirs();
			
			for(User u:userList)
			{
				try {
					Client c = new Client(Integer.parseInt(u.getPort()));
					c.createConnection(PathToSend, self.getPort(), true);
					System.out.println("SENDING BROADCAST "+self.getPort());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
}
