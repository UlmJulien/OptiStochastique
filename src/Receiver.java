import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Receiver
{
	public ServerSocket serverSocket = null;
    public Socket socket = null;
    public InputStream is = null;
    public FileOutputStream fos = null;
    public BufferedOutputStream bos = null;
    
	public void setup ()
	{
		try {
	        this.serverSocket = new ServerSocket(44444);
	    } catch (IOException ex) {
	        System.out.println("Can't setup server on this port number. ");
	    }
	}
	
	public void process () {
	    int bufferSize = 0;

	    try {
	        this.socket = serverSocket.accept();
	    } catch (IOException ex) {
	        System.out.println("Can't accept client connection. ");
	    }

	    try {
	        this.is = socket.getInputStream();

	        bufferSize = socket.getReceiveBufferSize();
	        System.out.println("Buffer size: " + bufferSize);
	    } catch (IOException ex) {
	        System.out.println("Can't get socket input stream. ");
	    }

	    try {
	        this.fos = new FileOutputStream("/home/relou/Bureau/cours_master_m2/Optimisation Stochastique/Project/OptiStochastique/data/testRcv.txt");
	        this.bos = new BufferedOutputStream(fos);

	    } catch (FileNotFoundException ex) {
	        System.out.println("File not found. ");
	    }

	    byte[] bytes = new byte[bufferSize];

	    int count;

	    try {
		    while ((count = is.read(bytes)) > 0) {
		        this.bos.write(bytes, 0, count);
		    }
	    } catch (IOException ex) {
	    	System.out.println("Error while reading input.");
	    }
	}
	
	public void terminate()
	{
	    try {
	    	this.bos.flush();
	    	this.bos.close();
	    	this.is.close();
	    	this.socket.close();
	    	this.serverSocket.close();
	    } catch (IOException ex) {
	    	System.out.println("Error while closing thread");
	    }
	}
}