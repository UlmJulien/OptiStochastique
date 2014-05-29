package Daemons;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import Fiches.MonitorDirectory;
import Security.ClientSession;
 
public class Server {
    private DatagramSocket socket = null;
    private FileEvent fileEvent = null;
    private String destPath = null;
    private ClientSession currSession = null;
    private Client c = null;
 
    public Server(ClientSession session) {
    	
    	this.destPath = session.getPathToRecv();
    	this.currSession = session;
    	
    	try {
			this.socket = new DatagramSocket(Integer.parseInt(session.getSelf().getPort()));
		} catch (SocketException e) {
			e.printStackTrace();
		}
    }
 
    public void createAndListenSocket() {
        try {
            byte[] incomingData = new byte[1024 * 1000 * 50];
            while (true) {
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                socket.receive(incomingPacket);
                byte[] data = incomingPacket.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                fileEvent = (FileEvent) is.readObject();
                int port = fileEvent.getPortToReply();
                
                if(fileEvent.getIsBroadcast())
                {
                	MonitorDirectory md = new MonitorDirectory(currSession);
                	
                	List<File> fileToSend = md.getMyFiles(currSession.getPathToRecv(), Integer.toString(port));
                	
                	try {
    					this.c = new Client(port);
    				} catch (SocketException e) {
    					e.printStackTrace();
    				} catch (UnknownHostException e) {
    					e.printStackTrace();
    				}
                	
                	for (int i = 0; i<fileToSend.size(); i++)
        		    { 
        		    	c.createConnection(fileToSend.get(i).toString(), currSession.getSelf().getPort(), false);
        		    }
                	c.close();
                }
                else 
                {
                	if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                        System.out.println("Some issue happened while packing the data @ client side");
                        System.exit(0);
                    }
                	createAndWriteFile();   // writing the file to hard disk
                } 
            }
 
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    public void createAndWriteFile() {
        String outputFile = destPath + fileEvent.getFilename();
        File dstFile = new File(outputFile);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");
 
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }
}