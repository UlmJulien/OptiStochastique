import java.util.List;

import Users.User;
import Users.UserReader;
import Fiches.*;
import IHM.AuthFrame;

public class Main {

	public Main() {
		FEC testFiche = new FEC();
		
		System.out.println(testFiche.getTest());
//	    // création d'une instance du thread
//	    TestThread thread = new TestThread();
//	    // Activation du thread
//	    thread.start();
//	    System.out.println("end thread");
//	    // tant que le thread est en vie...
//	    while( thread.isAlive() ) {
//	      // faire un traitement...
//	      System.out.println("Ligne affichée par le main");
//	      try {
//	        // et faire une pause
//	        thread.sleep(10000);
//	      }
//	      catch (InterruptedException ex) {}
//	    }
		
		UserReader ur = new UserReader();
		ur.getAllUsers();
		List<User> userList = ur.getUserList();
		
		for(int i=0; i<userList.size(); i++){
			System.out.println(userList.get(i).getIp());
		}
		

	    AuthFrame mainframe = new AuthFrame(userList);
	    mainframe.run();
	    MonitorDirectory md = new MonitorDirectory();
	    md.checkChanges();
	  }
	 
	  public static void main(String[] args) {
	    new Main();
	  }

}
