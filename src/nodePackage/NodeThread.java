package nodePackage;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import simple_blockchan.BuildingBlock;
import simple_blockchan.SHA256;
import utilesPackage.*;


public class NodeThread extends Thread{

	private BufferedReader bufferedReader;
	public NodeThread(Socket socket) throws IOException {
		bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public void run() {
		System.out.println("node thread running");
		boolean flag=true;
		while(flag) {
			try {
				Utiles u=new Utiles();
				String message=bufferedReader.readLine();
				Block b=u.StringToBlock(message);
				System.out.println("node received block "+Peer.peername);
				//TODO stops thread of building block
				
			} catch (Exception e) {
				// TODO: handle exception
				flag=false;
				interrupt();
			}
		}
	}
	public boolean verifyBlock(Block b){
		boolean broken = false;
		String blockHash = SHA256.hexToBin(SHA256.toHexString(b.gettHash()));
		for(int i = 0; i < BuildingBlock.diff; i++){
			if(blockHash.charAt(i) != '0') {
				broken = true;
				break;
			}
		}
		if(broken){
			return false;
		}
		String prevHash = SHA256.toHexString(b.getHashPrevBlock());
		String merkleRoot = SHA256.toHexString(b.getHashMerkleRoot());
		String time = Long.toString(b.getTimestamp());
		String nonce = Long.toString(b.getNonce());
		String value = prevHash + merkleRoot + time+ nonce;
		String calculatedHash =SHA256.toHexString(SHA256.getSHA(value));

		if(calculatedHash.equals(SHA256.toHexString(b.gettHash()))){
			ArrayList<Transaction> blockTransactions = b.getTransactions();
			for(int i =0;i<blockTransactions.size();i++){
				if(!BuildingBlock.validTransactions.contains(blockTransactions.get(i).getIndex())){
					if(!validateTransaction(blockTransactions.get(i))){
						return false;
					}
				}
			}
			return true;
		}

		return false;
	}

	public boolean validateTransaction(Transaction t){
		boolean validSig = t.verifyTransaction();
		if (validSig) {
			boolean doubleSpend = checkDoubleSpend(t);
			if (!doubleSpend) {
				return true;
			}
		}
		return false;
	}

	public boolean checkDoubleSpend(Transaction t){
		int transactionKey = t.getPrevioustx();
		ArrayList<Output> o = ClientThread.pool.getList(transactionKey);
		if(o == null || o.size() == 0) {
			return true;
		}
		for(Output out : o){
			if (out.getIndex()+1 == t.getOutputindex()) {
				ClientThread.pool.removeOutput(transactionKey, out);
				return false;
			}
		}
		return true;
	}
}
