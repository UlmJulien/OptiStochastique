import java.io.*;

public class testMain {

	public static void main(String[] args) {
		File fichier =  new File("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/test.txt") ;

		try {
		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
				
		 // création d'un objet à sérializer
		TestStreamServ m =  new TestStreamServ("Surcouf",  "Robert") ;

		 // sérialization de l'objet
		oos.writeObject(m) ;

		 // fermeture du flux dans le bloc finally
		oos.close();
		}
		catch (Exception e) {
			
		}
		
		try {
			File f =  new File("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/testRes.txt") ;

			// ouverture d'un flux sur un fichier
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(fichier)) ;
					
			// désérialization de l'objet
			TestStreamServ m = (TestStreamServ)ois.readObject() ;
			System.out.println(m) ;
			ois.close();
		}
		catch (Exception e) {
			
		}
	}

}
