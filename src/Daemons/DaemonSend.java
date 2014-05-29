package Daemons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import Security.ClientSession;
import Security.HashCode;
import Fiches.MonitorDirectory;


public class DaemonSend extends Thread {
	
	private String filePath = null;
	private MonitorDirectory md = null;
	private Client c = null;
	private int port;
	private ClientSession session = null;
	
	public DaemonSend (ClientSession session) {
		this.session = session;
		this.setDaemon(true);
//		this.port = Integer.parseInt(session.getSelf().getPort());
		this.filePath = session.getPathToSend();
	}
	
	public void run () {
		while(true) {
			this.md = new MonitorDirectory(session);
			Boolean changed = this.md.checkChanges();
			System.out.println("Checking changes : "+changed);
			
			if(changed)
			{
				List<File> fileToSend = md.getMyFiles(session.getPathToRecv(), session.getSelf().getPort());
				
				List<String> fileSent = new ArrayList<String>();
				
				HashCode hc = new HashCode();
				
				for (int i = 0; i<fileToSend.size(); i++)
			    {	
					if(!fileSent.contains(fileToSend.get(i).getName()))
					{
						List <File> listToSend = md.getRelatedFiles(fileToSend.get(i));
						
						for(int k=0; k<3; k++)
						{
							int idClient = 1;
							
							for(File f:listToSend)
							{
								if(fileIsMine(f))
								{
									int hash = hc.hashRandom(hc.hash(idClient)+k)%session.getUserList().size();
									this.port = Integer.parseInt(session.getUserList().get(hash).getPort());
									
									try {
										this.c = new Client(this.port);
									} catch (SocketException e) {
										e.printStackTrace();
									} catch (UnknownHostException e) {
										e.printStackTrace();
									}
									
							    	c.createConnection(f.getAbsolutePath(), session.getSelf().getPort(), false);
							    	
							    	c.close();
							    	
							    	if(!fileSent.contains(f.getName()))
							    		fileSent.add(f.getName());
								}
							}
						}
					}
			    }
			}
			
			try {
				this.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Boolean fileIsMine (File f) 
	{
		if (f.isFile() && !f.getName().contains("~")) {
        	FileInputStream fs;
			try {
				fs = new FileInputStream(f.getAbsolutePath());
				BufferedReader br = new BufferedReader(new InputStreamReader(fs));
	        		try {
						String line = br.readLine();
						if(line.contains("createdby")){
							String[] truncate = line.split(":");
							if (truncate[truncate.length-1].compareTo(session.getSelf().getPort()) == 0)
								return true;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return false;
    }
}
