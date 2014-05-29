package Daemons;

import java.io.*;
import java.net.*;

import Fiches.MonitorDirectory;
 
public class Client {
    private DatagramSocket socket = null;
    private FileEvent event = null;
    private String sourceFilePath = null;
    private String hostName = "localHost";
    private InetAddress IPAddress = null;
    private int port;
 
 
    public Client(int port) throws SocketException, UnknownHostException {
    	this.socket = new DatagramSocket();
    	IPAddress = InetAddress.getByName(hostName);
    	this.port = port;
    }
 
    public void createConnection(String path, String sessionPort, Boolean broadcast) {
    	this.sourceFilePath = path;
    	
        try {
            byte[] incomingData = new byte[1024];
            event = getFileEvent(broadcast);
            event.setPortToReply(Integer.parseInt(sessionPort));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(event);
            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
            socket.send(sendPacket);
            if(broadcast)
            {
            	System.out.println("Broadcast sent from "+sessionPort+" to "+port);
            }
            else
            {
            	System.out.println(this.sourceFilePath+" sent from "+sessionPort+" to "+port);
            }

 
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public FileEvent getFileEvent(Boolean broadcast) {
        FileEvent fileEvent = new FileEvent();
        String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
        String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
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
        
        if(broadcast)
        	fileEvent.setIsBroadcast(true);
        return fileEvent;
    }
    
    public void close() {
    	this.socket.close();
    }
}