package nodePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import simple_blockchan.BuildingBlock;
import utilesPackage.*;

public class ClientThread extends Thread{

	private BufferedReader bufferedReader;
	public static TransactionPool pool = new TransactionPool();
	public ClientThread(Socket socket) throws IOException {
		bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void run() {
		boolean flag=true;
		BuildingBlock bbt = new BuildingBlock(200,3,0);
		Thread t = new Thread(bbt);
		t.start();
		while(flag) {
			try {
				Utiles u=new Utiles();
				String message=bufferedReader.readLine();			
			    Transaction trans=u.stringToTransaction(message.substring(7));
                pool.addTransaction(trans);
			} catch (Exception e) {
				// TODO: handle exception
				flag=false;
				interrupt();
			}
		}
	}
}
