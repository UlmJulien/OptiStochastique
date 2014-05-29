import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import Security.*;
import Users.User;
import Users.UserReader;
import Fiches.*;
import IHM.AuthFrame;

public class Main {

	public Main() {
		
		List<User> userList = UserReader.getAllUsers();
		
		for(int i=0; i<userList.size(); i++){
			System.out.println(userList.get(i).getPort());
		}

	    AuthFrame mainframe = new AuthFrame(userList);
	    mainframe.run();
	    
//	    CryptoMessageRSA crypto = new CryptoMessageRSA();
//	    String cr = CryptoMessageRSA.crypterMessage("kookoo", crypto.getClePublic().getPublicExponent(), crypto.getClePublic().getModulus());
//	    String dcr = crypto.decrypterMessage(cr);
//	    System.out.println("CRYPTE : " + cr);
//	    System.out.println("DECRYPTE : " + dcr);
		
//	    if (mainframe.getClientSession().getIsLoggedIn()) 
//	    {
	    	
//	    }
	    
	  }
	 
	  public static void main(String[] args) {
	    new Main();
	  }

}
