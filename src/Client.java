import java.io.*;
import java.net.*;

import Fiches.MonitorDirectory;
 
public class Client {
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath = "/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/src/testSrc.txt";
    private String destinationPath = "/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/dest/";
    private String hostName = "localHost";
    private InetAddress IPAddress = null;
 
 
    public Client() throws SocketException, UnknownHostException {
    	this.socket = new DatagramSocket();
    	IPAddress = InetAddress.getByName(hostName);
    }
 
    public void createConnection(String path) {
    	this.sourceFilePath = path;
    	
        try {
            byte[] incomingData = new byte[1024];
            event = getFileEvent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
            socket.send(sendPacket);
            System.out.println("File sent from client");
//            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
//            socket.receive(incomingPacket);
//            String response = new String(incomingPacket.getData());
//            System.out.println("Response from server:" + response);
            Thread.sleep(2000);
//            System.exit(0);
 
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
 
    public FileEvent getFileEvent() {
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(sourceFilePath);
        File file = new File(sourceFilePath);
        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read,
                        fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
        return fileEvent;
    }
 
    public static void main(String[] args) throws SocketException, UnknownHostException {
	    MonitorDirectory md = new MonitorDirectory();
	    md.checkChanges();
	    Client c = new Client();
	    
	    for (int i = 0; i<md.listOfPatientFiles.size(); i++)
	    {
	    	c.createConnection(md.listOfPatientFiles.get(i).toString());
	    }
    }
}