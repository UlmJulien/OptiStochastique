import java.util.List;

import Users.User;
import Users.UserReader;
import Fiches.*;
import IHM.AuthFrame;

public class Main {

	public Main() {
//		FEC testFiche = new FEC();
//		
//		System.out.println(testFiche.getTest());
//		
//		UserReader ur = new UserReader();
//		ur.getAllUsers();
//		List<User> userList = ur.getUserList();
//		
//		for(int i=0; i<userList.size(); i++){
//			System.out.println(userList.get(i).getIp());
//		}
//		
//
//	    AuthFrame mainframe = new AuthFrame(userList);
//	    mainframe.run();
	    MonitorDirectory md = new MonitorDirectory();
	    md.checkChanges();
	  }
	 
	  public static void main(String[] args) {
	    new Main();
	  }

}
