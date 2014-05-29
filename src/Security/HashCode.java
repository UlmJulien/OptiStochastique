package Security;

import java.util.Random;


public class HashCode {

	public HashCode() {
		
	}
	
	public int hash (int ClientId) {
		return (ClientId * 42 + ClientId);
	}
	
	public int hashRandom (int hash) {
		Random r = new Random();
		int result = 0 + r.nextInt(hash);
		return result;
	}
	
}
