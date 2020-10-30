package utilesPackage;

import java.util.Date;
import java.util.ArrayList;

public class Block {

	private byte[] hashPrevBlock;
	private byte[] hashMerkleRoot;
	private long timestamp;
	private int Nonce;
	private ArrayList<Transaction> transactions;
	private int level;
	private byte[] currentHash;

	public byte[] gettHash() {
		return currentHash;
	}
	public void setHash(byte[] h) {
		currentHash = h;
	}
	public byte[] getHashPrevBlock() {
		return hashPrevBlock;
	}
	public void setHashPrevBlock(byte[] h) {
		hashPrevBlock = h;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int l) {
		level = l;
	}
	public byte[] getHashMerkleRoot() {
		return hashMerkleRoot;
	}
	public void setHashMerkleRoot(byte[] h) {
		hashMerkleRoot = h;
	}
	public int getNonce() {
		return Nonce;
	}
	public void setNonce(int nonce) {
		Nonce = nonce;
	}
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> list) {
		transactions = list;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp() {
		timestamp = new Date().getTime();
	}
	public void setTimestamp(long t) {/////
		timestamp = new Date().getTime();
	}

}
