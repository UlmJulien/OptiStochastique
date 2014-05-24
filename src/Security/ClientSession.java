package Security;

import java.util.List;

import Users.User;
import Users.UserReader;

public class ClientSession {
	
	private User self = null;
	private List<User> userList = null;
	private Boolean isLoggedIn = null;
	
	public ClientSession(){
		isLoggedIn = false;
	}
	
	public ClientSession(String port){
		self = new User(port);
		userList = UserReader.getAllUsersButMe(self);
		isLoggedIn = true;
	}
	
	
	
	//GET/SET
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
}
