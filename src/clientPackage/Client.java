
package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;

import utilesPackage.*;

public class Client {

	public static void main(String[] args) throws IOException {
		
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Clint is starting");
		Utiles u=new Utiles();
	    ArrayList<String> lines= u.readFromFile("configuration.txt");
	   String[] setupvalues=lines.get(0).split(" ");
	   ServerThread serverThread=new ServerThread(setupvalues[1]);
	   serverThread.start();
	   new Client().updateListenToPeers(bufferedReader, setupvalues[0], serverThread,lines.get(1));
	}
	public void updateListenToPeers(BufferedReader bufferReader,String username, ServerThread serverThread,String s) throws IOException {
		System.out.println("Enter any key if other nodes existing");
		String input=bufferReader.readLine();
		String[] inputvalues=s.split(" ");
		if(!input.equals("s")) {
			for (int i = 0; i < inputvalues.length; i++) {
				String[] address=inputvalues[i].split(":");
				Socket socket=null;
				try {
					socket=new Socket(address[0],Integer.valueOf(address[1]));
					new PeerThread(socket).start();
				}catch(Exception e) {
					if(socket != null) socket.close();
					else System.out.println("invalid input . skipping to next step.");
				}
			}
		}
		communicate( bufferReader,username,serverThread);
	}
	public void communicate(BufferedReader bufferReader,String username, ServerThread serverThread){
		try {
		    System.out.println("Loading Transaction From database...");
			Utiles u=new Utiles();
			 ArrayList<Transaction> transactions=u.parseTransaction();
    		System.out.println("Transaction is sending now ");
			for(int i=0;i<transactions.size();i++) {
				String message="Client:"+u.transactionToString(transactions.get(i));
					StringWriter stringWriter=new StringWriter();
					stringWriter.append(message);
					serverThread.sendMessage(stringWriter.toString());
					Thread.sleep((long) 0.2);
				}		
			System.exit(0);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
}
