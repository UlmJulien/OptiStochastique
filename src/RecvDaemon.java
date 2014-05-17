
public class RecvDaemon extends Thread{
	
	public Receiver rcv;

	public RecvDaemon()
	{
		this.rcv = new Receiver();
	}
	
	public void run () 
	{
		this.rcv.setup();
		this.rcv.process();		
		this.rcv.terminate();
	}
}
