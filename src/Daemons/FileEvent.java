package Daemons;

import java.io.Serializable;
 
public class FileEvent implements Serializable {
 
    public FileEvent() {
    }
 
    private static final long serialVersionUID = 1L;
    
    private Boolean isBroadcast = false;
    private int portToReply;
	private String destinationDirectory;
    private String sourceDirectory;
    private String filename;
    private long fileSize;
    private byte[] fileData;
    private String status;
 
    public int getPortToReply() {
		return portToReply;
	}

	public void setPortToReply(int portToReply) {
		this.portToReply = portToReply;
	}
    
    public Boolean getIsBroadcast() {
		return isBroadcast;
	}

	public void setIsBroadcast(Boolean isBroadcast) {
		this.isBroadcast = isBroadcast;
	}
    
    public String getDestinationDirectory() {
        return destinationDirectory;
    }
 
    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }
 
    public String getSourceDirectory() {
        return sourceDirectory;
    }
 
    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }
 
    public String getFilename() {
        return filename;
    }
 
    public void setFilename(String filename) {
        this.filename = filename;
    }
 
    public long getFileSize() {
        return fileSize;
    }
 
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public byte[] getFileData() {
        return fileData;
    }
 
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}