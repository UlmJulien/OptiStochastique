import java.io.*;
import java.net.*;
class UDPClient 
{    
	public static void main(String args[]) throws Exception    
	{       
//		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
//		DatagramSocket clientSocket = new DatagramSocket();
//		InetAddress IPAddress = InetAddress.getByName("localhost");
//		byte[] sendData = new byte[1024];
//		byte[] receiveData = new byte[1024];
//		String sentence = inFromUser.readLine();
//		sendData = sentence.getBytes();
//		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
//		clientSocket.send(sendPacket);
////		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
////		clientSocket.receive(receivePacket);
////		String modifiedSentence = new String(receivePacket.getData());
////		System.out.println("FROM SERVER:" + modifiedSentence);
//		clientSocket.close();
		
		
//		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		File fichier =  new File("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/test.txt") ;
		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/test.txt")) ;
				
		 // création d'un objet à sérializer
		TestStreamServ m =  new TestStreamServ("Surcouf",  "Robert") ;

		 // sérialisation de l'objet
		oos.writeObject(m) ;

		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		String sentence = oos.toString();
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
		clientSocket.send(sendPacket);
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("FROM SERVER:" + modifiedSentence);
		clientSocket.close();
		 // fermeture du flux dans le bloc finally
		oos.close();
	}
} 