import Fiches.*;

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
	  }
	 
	  public static void main(String[] args) {
	    new Main();
	  }

}
