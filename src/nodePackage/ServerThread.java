package nodePackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class ServerThread extends Thread{

	private ServerSocket serverSocket;
	private Set<ServerThreadThread> serverThreadThreads=new HashSet<ServerThreadThread>();
	public ServerThread(String portNum) throws NumberFormatException, IOException{
		serverSocket=new ServerSocket(Integer.valueOf(portNum));
	}
	
	public void run() {
		try {
			while(true) {
				ServerThreadThread serverThreadThread=new ServerThreadThread(serverSocket.accept(),this);
				serverThreadThreads.add(serverThreadThread);
				serverThreadThread.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	void sendMessage(String message) {
		try {
			if(message != null) {
				serverThreadThreads.forEach(t -> t.getPrintWriter().println(message));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public Set<ServerThreadThread> getServerThreadThreads(){
		return serverThreadThreads;
	}
}
