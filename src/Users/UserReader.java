package Users;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserReader {
	
	private List<User> UserList = null;
	
	public UserReader(){
		UserList = new ArrayList<User>();
	}
	
	public List<User> getUserList(){
		return UserList;
	}
	
	public void getAllUsers(){
		
		FileInputStream fis = null;
        BufferedReader reader = null;
        
        try 
        {
            fis = new FileInputStream("/home/gui/ProjetOpti/OptiStochastique/users/users.txt");
            reader = new BufferedReader(new InputStreamReader(fis));
          
            System.out.println("...Loading all users from database");
          
            String line = reader.readLine();
            while(line != null)
            {
                UserList.add(new User(line));
                line = reader.readLine();
            }           
          
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

	}

}
