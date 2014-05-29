package Daemons;

import Security.ClientSession;

public class DaemonRcv extends Thread {

	private String destPath = null;
	private ClientSession session = null;
	
	public DaemonRcv (ClientSession session) {
		this.setDaemon(true);
		this.session = session;
	}
	
	public void run () {
        Server server = new Server(session);
        server.createAndListenSocket();
	}
}
