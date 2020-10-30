package nodePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;

import utilesPackage.TransactionPool;
import utilesPackage.Utiles;

public class Peer {

	private BufferedReader bufferReader;
	private String username;
	private ServerThread serverThread;
	public static  String peername;
	public void startPeer() throws IOException {
		
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
	    Utiles u=new Utiles();
		System.out.println("enter name of peer");
	    ArrayList<String> lines= u.readFromFile("configuration.txt");
	    peername=bufferedReader.readLine();
	    for (int i = 0; i < lines.size(); i++) {
			if(lines.get(i).contains(peername)) {
				 String[] setupvalues=lines.get(i).split(" ");
				 ServerThread serverThread=new ServerThread(setupvalues[1]);
				   serverThread.start();
				   this.bufferReader=bufferedReader;
				   this.username=setupvalues[0];
				   this.serverThread=serverThread;
				   new Peer().updateListenToPeers(bufferedReader, setupvalues[0], serverThread,lines.get(i+1));
				   break;
			}
		}
	  
	  
	   
	}
	public BufferedReader getBufferReader() {
		return bufferReader;
	}
	public String getUsername() {
		return username;
	}
	public ServerThread getServerThread() {
		return serverThread;
	}
	public void updateListenToPeers(BufferedReader bufferReader,String username, ServerThread serverThread,String s) throws IOException {
		System.out.println("Enter any key if other nodes existing");
		String input=bufferReader.readLine();
		String[] inputvalues=s.split(" ");
		connectClientSocket(inputvalues[0]);
		for (int i = 1; i < inputvalues.length; i++) {
			connectNodeSocket(inputvalues[i]);
		}
	}
	
	private void connectClientSocket(String s) throws IOException {
		String[] address=s.split(":");
		Socket socket=null;
		try {
			socket=new Socket(address[0],Integer.valueOf(address[1]));
			new ClientThread(socket).start();
		}catch(Exception e) {
			if(socket != null) socket.close();
			else System.out.println("invalid input . skipping to next step.");
		}
	}
    private void connectNodeSocket(String s) throws IOException {
    	String[] address=s.split(":");
		Socket socket=null;
		try {
			socket=new Socket(address[0],Integer.valueOf(address[1]));
			new NodeThread(socket).start();
		}catch(Exception e) {
			if(socket != null) socket.close();
			else System.out.println("invalid input . skipping to next step.");
		}
	}
	
	public void sendBloadToAllPeers(String username, ServerThread serverThread,String Block){
			StringWriter stringWriter=new StringWriter();
					stringWriter.append(username);
					stringWriter.append(">>");
					stringWriter.append(Block);
					serverThread.sendMessage(stringWriter.toString());
	}
}
