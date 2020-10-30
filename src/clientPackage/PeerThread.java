package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class PeerThread extends Thread{

	private BufferedReader bufferedReader;
	public PeerThread(Socket socket) throws IOException {
		bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void run() {
		boolean flag=true;
		while(flag) {
			try {
				
				//String message=bufferedReader.readLine();
			    //System.out.println("message"+message);
			
			} catch (Exception e) {
				// TODO: handle exception
				flag=false;
				interrupt();
			}
		}
	}
}
