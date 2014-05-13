package Fiches;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitorDirectory {
	
	public List<File> listOfPatientFiles;
	public List<File> listToSend;
	
	public MonitorDirectory(){
		listOfPatientFiles = new ArrayList<File>();
		listToSend = new ArrayList<File>();
		
		this.getFiles("/home/gui/ProjetOpti/OptiStochastique/patients");
		this.getToSend();
	}
 
	public void getFiles(String path){		 
		  String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
			   if (listOfFiles[i].isFile()) 
			   {
				   files = listOfFiles[i].getName();
				   System.out.println("PATIENTS : " + files);
				   listOfPatientFiles.add(listOfFiles[i]);
			   }
			   else if(listOfFiles[i].isDirectory()){
				   System.out.println("DIR");
			   }
		  }
	}
	
	public void getToSend(){
		String path = "/home/gui/ProjetOpti/OptiStochastique/toSend";
			String files;
		  File folder = new File(path);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
			   if (listOfFiles[i].isFile()) 
			   {
				   files = listOfFiles[i].getName();
				   System.out.println("TOSEND : " + files);
				   listToSend.add(listOfFiles[i]);
			   }
			   else if(listOfFiles[i].isDirectory()){
				   System.out.println("DIR");
			   }
		  }
	}
	
	public void checkChanges(){
		for(File file : listOfPatientFiles){
			boolean isInToSend = false;
			
			for(int j = 0; j < listToSend.size(); j++){
				
				File toSend = listToSend.get(j);
				//FILE EXISTS
				if(file.getName().compareTo(toSend.getName())==0){
					isInToSend = true;
					
					//NEW VERSION
					if(file.lastModified()>toSend.lastModified()){
						System.out.println("NEW VERSION OF " + file.getName() + " AVAILABLE");
						listToSend.set(j, file);
					}
					//OLD VERSION : DO NOTHING
				}
			}
			
			if(!isInToSend) {
				listToSend.add(file);
				System.out.println("ADD " + file.getName());
			}
			
			createFilesToSend();
		}
	}
	
	public void createFilesToSend(){
		for(File toSend : listToSend){
			File file = new File("/home/gui/ProjetOpti/OptiStochastique/toSend/"+toSend.getName());
			try {
				copyFileUsingFileChannels(toSend, file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static void copyFileUsingFileChannels(File source, File dest)
			throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
}