package Fiches;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Security.ClientSession;

public class MonitorDirectory {
	
	public List<File> listOfPatientFiles;
	public List<File> listToSend;
	private String folderToCheck = null;
	private String folderToSend = null;
	private String projectAbsolutePath = new File("").getAbsolutePath();
	
	public MonitorDirectory(ClientSession session){
		listOfPatientFiles = new ArrayList<File>();
		listToSend = new ArrayList<File>();
		folderToCheck = session.getPathToRecv();
		folderToSend = session.getPathToSend();
		
		this.getFiles();
		this.getToSend();
	}
 
	public void getFiles(){		 
		  String files;
		  File folder = new File(folderToCheck);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
			   if (listOfFiles[i].isFile()) 
			   {
				   files = listOfFiles[i].getName();
//				   System.out.println("PATIENTS : " + files);
				   listOfPatientFiles.add(listOfFiles[i]);
			   }
			   else if(listOfFiles[i].isDirectory()){
				   System.out.println("DIR");
			   }
		  }
	}
	
	public void getToSend(){
		  String files;
		  File folder = new File(folderToSend);
		  File[] listOfFiles = folder.listFiles(); 
		 
		  for (int i = 0; i < listOfFiles.length; i++) 
		  {
			   if (listOfFiles[i].isFile()) 
			   {
				   files = listOfFiles[i].getName();
//				   System.out.println("TOSEND : " + files);
				   listToSend.add(listOfFiles[i]);
			   }
			   else if(listOfFiles[i].isDirectory()){
//				   System.out.println("DIR");
			   }
		  }
	}
	
	public Boolean checkChanges(){
		Boolean res = false;
		for(File file : listOfPatientFiles)
		{
			if(addInList(getRelatedFiles(file), listToSend))
				res = true;
		}
		createFilesToSend();
		return res;
	}
	
	public Boolean addInList (List<File> src, List<File> dest) 
	{
		Boolean res = false;
		for(File f:src)
		{
			int index = fileInList(f, dest);
			if(index != -1)
			{
				if(f.lastModified()>dest.get(index).lastModified())
				{
					dest.set(index, f);
					res = true;
				}
			}
			else
			{
				dest.add(f);
			}
		}
		return res;
	}
	
	public int fileInList (File file, List<File> lf)
	{
		for(int i=0; i<lf.size(); i++)
		{
			if(lf.get(i).getName().compareTo(file.getName())==0)
				return i;
		}
		return -1;
	}
	
	public List<File> getRelatedFiles(File f) 
	{
		List<File> result = new ArrayList<File>();
		String client = f.getName().split("_")[0];
		
		for(File file : listOfPatientFiles){
			if(file.getName().compareTo(client+"_fec.txt") == 0 
					|| file.getName().compareTo(client+"_fmt.txt") == 0
					|| file.getName().compareTo(client+"_fmn.txt") == 0
					|| file.getName().compareTo(client+"_fct.txt") == 0) 
			{
				result.add(file);
			}
		}
		
		return result;		
	}
	
	public void createFilesToSend(){
		for(File toSend : listToSend){
			File file = new File(folderToSend+toSend.getName());
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
	
	public List<File> getMyFiles(String dirPath, String port){
		
		List<File> myFileList = new ArrayList<File>();
		
		if(new File(dirPath).exists())
		{
			File folder = new File(dirPath);
			File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile() && !listOfFiles[i].getName().contains("~")) {
		        	FileInputStream fs;
					try {
						fs = new FileInputStream(dirPath + "/" + listOfFiles[i].getName());
						BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			        		try {
								String line = br.readLine();
								if(line.contains("createdby")){
									String[] truncate = line.split(":");
									if (truncate[truncate.length-1].compareTo(port) == 0)
										myFileList.add(listOfFiles[i]);
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
		    }
		}
		
		return myFileList;
	}
}