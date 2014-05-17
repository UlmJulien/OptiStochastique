import java.io.*;
import java.net.*;
class UDPServer {
	public static void main(String args[]) throws Exception       
	{         
		DatagramSocket serverSocket = new DatagramSocket(9876); 
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)                
		{                   
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			
			File f =  new File("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/testRes.txt") ;

			// ouverture d'un flux sur un fichier
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(f)) ;
					
			// désérialization de l'objet
			TestStreamServ m = (TestStreamServ)ois.readObject() ;
			System.out.println(m) ;
			ois.close();
			
//			String sentence = new String( receivePacket.getData());
//			System.out.println("RECEIVED: " + sentence);
//			InetAddress IPAddress = receivePacket.getAddress();
//			int port = receivePacket.getPort();
//			String capitalizedSentence = sentence.toUpperCase();
//			sendData = capitalizedSentence.getBytes();
//			DatagramPacket sendPacket =                   
//					new DatagramPacket(sendData, sendData.length, IPAddress, port);
//			serverSocket.send(sendPacket);
			}
		}
	}