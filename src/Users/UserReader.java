package Users;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserReader {
	
	
	public UserReader(){
		
	}
	
	public static List<User> getAllUsers(){
		List<User> userList = new ArrayList<User>();
		FileInputStream fis = null;
        BufferedReader reader = null;
        
        try 
        {
            fis = new FileInputStream("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/users/users.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
          
            System.out.println("...Loading all users from database");
          
            String line = reader.readLine();
            while(line != null)
            {
            	//TODO REFACTOR PORT
                userList.add(new User(truncPort(line)));
                line = reader.readLine();
            }  
            System.out.println("Users loaded " + userList.size());
          
        } catch (FileNotFoundException ex) {
        	System.out.println("Error UserReader : " + ex);
        } catch (IOException ex) {
        	System.out.println("Error UserReader : " + ex);
          
        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {
            	System.out.println("Error UserReader : " + ex);
            }
        }
        
        return userList;

	}
	
	public static List<User> getAllUsersButMe(User user){
		List<User> userList = getAllUsers();
		for(User usr : userList){
			if(usr.getPort().compareTo(user.getPort())==0){
				userList.remove(usr);
				break;
			}
		}
		return userList;
	}
	
	public static String truncPort(String hostport){
		String[] truncate = hostport.split(":");
//		System.out.println("TRUNCATE FUNCTION => RETURNS " + truncate[truncate.length-1]);
		return truncate[truncate.length-1];
	}

}
