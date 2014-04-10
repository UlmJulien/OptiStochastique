
public class TestThread extends Thread {
	
	private String str = "Hello World";
	
		public void run () {
		    long start = System.currentTimeMillis();
		    // boucle tant que la durée de vie du thread est < à 5 secondes
		    while( System.currentTimeMillis() < ( start + (1000 * 2))) {
		      // traitement
		      System.out.println(this.str);
		}
	}
}
