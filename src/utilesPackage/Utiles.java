package utilesPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

public class Utiles {
	KeyPairGenertor gn;
	public ArrayList<String> readFromFile(String fileName) {
		ArrayList<String> records = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null) {
				records.add(line);
			}
			reader.close();
			return records;
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.",
					fileName);
			e.printStackTrace();
			return null;
		}
	}
	public void clearFile(String fileName){
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(fileName));
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writetoFile(String fileName,String line) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true));
	    writer.write(line);
	    writer.newLine();
	    writer.close();
	}
	/** get arraylist of transaction from file*/
	public ArrayList<Transaction> parseTransaction() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {
		gn=new KeyPairGenertor();
		gn.generatekyes(50);
		ArrayList<Transaction> transactions=new ArrayList<Transaction>();
		ArrayList<String> lines=readFromFile("transactionFile.txt");
		for (int i = 0; i < 500; i++) {
			String[] line=lines.get(i).split("\t");
			ArrayList<String> newLines=new ArrayList<String>();
			for (int j = 0; j < line.length; j++) {
				String[] tempLine=line[j].split(" ");
				for (int k = 0; k < tempLine.length; k++) {
					newLines.add(tempLine[k]);
				}
			}
		if(newLines.size()>3){
		Transaction t=readTRansaction(newLines,lines.get(i));
		transactions.add(t);
			}
			
		}
		
		return transactions;
	}
	
	/** convert string to  transaction */
	
		public Transaction stringToTransaction(String s) throws NoSuchAlgorithmException, InvalidKeySpecException {
			Transaction t=new Transaction();
			String[] splitString=s.split("	");
			if(splitString.length>0) {
			int indexSplit=splitString[0].indexOf(':')+1;
			t.setIndex(Integer.parseInt(splitString[0].substring(indexSplit)));
			indexSplit=splitString[1].indexOf(':')+1;
			PublicKey p=getPublicKeyFromBytes(getByteFromString(splitString[1].substring(indexSplit)));
			t.setPublicKeyPayer(p);
			indexSplit=splitString[2].indexOf(':')+1;
			t.setPrevioustx(Integer.parseInt(splitString[2].substring(indexSplit)));
			indexSplit=splitString[3].indexOf(':')+1;
			t.setOutputindex(Integer.parseInt(splitString[3].substring(indexSplit)));
			indexSplit=splitString[4].indexOf(':')+1;
			t.setHash(getByteFromString(splitString[4].substring(indexSplit)));
			indexSplit=splitString[5].indexOf(':')+1;
			t.setSignature(getByteFromString(splitString[5].substring(indexSplit)));
			indexSplit=splitString[6].indexOf(':')+1;
			t.setOutputs(getOutputsFromString(splitString[6].substring(indexSplit)));
			}
			return t;
			
		}
	
	
	
	private ArrayList<Output> getOutputsFromString(String substring) throws NoSuchAlgorithmException, InvalidKeySpecException {
			// TODO Auto-generated method stub
		ArrayList<Output> outputs=new ArrayList<Output>();
		String[] splitString=substring.split("&");
		for (int i = 0; i < splitString.length; i++) {
			String[] split=splitString[i].split(" ");
			Double value=Double.parseDouble(split[0]);
			int index=Integer.parseInt(split[1]);
			PublicKey p= getPublicKeyFromBytes(getByteFromString(split[2]));
			Output out=new Output(value,index,p);
			outputs.add(out);
		}
		
			return outputs;
		}
	private PublicKey getPublicKeyFromBytes(byte[] byteFromString) throws NoSuchAlgorithmException, InvalidKeySpecException {
			// TODO Auto-generated method stub
		X509EncodedKeySpec ks = new X509EncodedKeySpec(byteFromString);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey pub = kf.generatePublic(ks);
			return pub;
		}
	
	
	/******** convert transaction to string ********/
	public String transactionToString(Transaction t) {
		StringBuilder str=new StringBuilder();
		str.append("Index:");
		str.append(String.valueOf(t.getIndex()));
		str.append("	");
		str.append("PublicKeyPayer:");
		str.append(getStringFromBytes(t.getPublicKeyPayer().getEncoded()));
		str.append("	");
		str.append("Previoustx:");
		str.append(String.valueOf(t.getPrevioustx()));
		str.append("	");
		str.append("OutputIndex:");
		str.append(String.valueOf(t.getOutputindex()));
		str.append("	");
		str.append("Hash:");
		str.append(getStringFromBytes(t.getHash()));
		str.append("	");
		str.append("Signature:");
		str.append(getStringFromBytes(t.getSignature()));
		str.append("	");
		str.append("Outputs:");
		str.append(getStringFromOutputs(t.getOutputs()));
		String sb=str.toString();
		
		return sb;
	}
	
	
private String getStringFromOutputs(ArrayList<Output> outputs) {
	StringBuilder str=new StringBuilder();
	String result="";
	for (int i = 0; i < outputs.size(); i++) {
		str.append(String.valueOf(outputs.get(i).getValue()));
		str.append(" ");
		str.append(String.valueOf(outputs.get(i).getIndex()));
		str.append(" ");
		str.append(getStringFromBytes(outputs.get(i).getPublicKeyPayee().getEncoded()));
	
	if(i!=(outputs.size()-1)) {
		str.append("&");
	}
	}
	result=str.toString();
	return result;
}
	private String getStringFromBytes(byte[] hash) {
		// TODO Auto-generated method stub
		String result="";
		for(int i=0;i<hash.length;i++) {
			int x=hash[i];
			result+=Integer.toString(x);
			if(i!=(hash.length-1)) {
				result+=",";
			}
		}
		
		return result;
	}
  private byte[] getByteFromString(String s) {
		
		String bytes[]=s.split(",");
		byte[] result=new byte[bytes.length];
		for(int i=0;i<bytes.length;i++) {
			result[i]=(byte)Integer.parseInt(bytes[i]);
		}
		
		return result;
	}
	private Transaction readTRansaction(ArrayList<String> newLines,String line) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {
		// TODO Auto-generated method stub
		SignatureClass keys= new SignatureClass();
		Transaction t=new Transaction();
		t.setIndex(Integer.parseInt(newLines.get(0)));
		int indexsplit=newLines.get(1).indexOf(':');
		int clientNum=Integer.parseInt(newLines.get(1).substring(indexsplit+1));
		t.setPublicKeyPayer(gn.getPublicKey(clientNum));
		int i = 4;
		if(newLines.size()>4) {
			indexsplit=newLines.get(2).indexOf(':');
		t.setPrevioustx(Integer.parseInt(newLines.get(2).substring(indexsplit+1)));
		indexsplit=newLines.get(3).indexOf(':');
		t.setOutputindex(Integer.parseInt(newLines.get(3).substring(indexsplit+1)));
		}else {
		 i = 2;
		 t.setPrevioustx(0);
		 t.setOutputindex(0);
		}
		ArrayList<Output> outputs=new ArrayList<Output>();
		int index=0;
		for ( ; i < newLines.size(); i+=2) {
			indexsplit=newLines.get(i).indexOf(':');
			Double value=Double.parseDouble(newLines.get(i).substring(indexsplit+1));
			indexsplit=newLines.get(i+1).indexOf(':');
			PublicKey p=gn.getPublicKey(Integer.parseInt(newLines.get(i+1).substring(indexsplit+1)));
			Output out=new Output(value,index,p);
			outputs.add(out);
			index++;

		}
		t.setOutputs(outputs);
		byte[] hash=keys.hashTransaction(line);
		t.setHash(hash);
		t.setSignature(keys.getSignature(gn.getPrivateKey(clientNum),hash));
		return t;
	}
	public String BlockToString(Block b) {
		// TODO Auto-generated method stub
		StringBuilder str=new StringBuilder();
		str.append("hashPrevBlock:");
		str.append(getStringFromBytes(b.getHashPrevBlock()));
		str.append("%");
		str.append("hashMerkleRoot:");
		str.append(getStringFromBytes(b.getHashMerkleRoot()));
		str.append("%");
		str.append("timestamp:");
		str.append(String.valueOf(b.getTimestamp()));
		str.append("%");
		str.append("Nonce:");
		str.append(String.valueOf(b.getNonce()));
		str.append("%");
		str.append("Level:");
		str.append(String.valueOf(b.getLevel()));
		str.append("%");
		str.append("currentHash:");
		str.append(getStringFromBytes((b.gettHash())));
		str.append("%");
		str.append("Transactions:");
		for (int i = 0; i < b.getTransactions().size(); i++) {
			str.append(transactionToString(b.getTransactions().get(i)));
			if(i!=(b.getTransactions().size()-1)) {
				str.append("##");
			}
		}
		return str.toString();
	}
	public Block StringToBlock(String message) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// TODO Auto-generated method stub
		System.out.println("Receiving "+message);
		Block b=new Block();
		String[] splitString=message.split("%");
		int indexsplit=splitString[0].indexOf(':')+1;
		b.setHashPrevBlock(getByteFromString(splitString[0].substring(indexsplit)));
		indexsplit=splitString[1].indexOf(':')+1;
		b.setHashMerkleRoot(getByteFromString(splitString[1].substring(indexsplit)));
		indexsplit=splitString[2].indexOf(':')+1;
		b.setTimestamp(Long.parseLong(splitString[2].substring(indexsplit)));
		indexsplit=splitString[3].indexOf(':')+1;
		b.setNonce(Integer.parseInt(splitString[3].substring(indexsplit)));
		indexsplit=splitString[4].indexOf(':')+1;
		b.setLevel(Integer.parseInt(splitString[4].substring(indexsplit)));
		indexsplit=splitString[5].indexOf(':')+1;
		b.setHash(getByteFromString(splitString[5].substring(indexsplit)));
		indexsplit=splitString[6].indexOf(':')+1;

		System.out.println("block hash "+b.gettHash());
		System.out.println("block prevHash "+b.getHashPrevBlock());
		System.out.println("block merkle tree "+b.getHashMerkleRoot());
		System.out.println("block nonce "+b.getNonce());
		System.out.println("block level "+b.getLevel());

		String transactionString=splitString[6].substring(indexsplit);
		ArrayList<Transaction> transactions=new ArrayList<Transaction>();
		String [] splitTransaction=transactionString.split("##");
		System.out.println("split trans "+splitTransaction.length);
		for (int i = 0; i < splitTransaction.length; i++){
			System.out.println("trans "+stringToTransaction(splitTransaction[i]));
			System.out.println(i);
			transactions.add(stringToTransaction(splitTransaction[i]));
		}
		System.out.println("transactions "+transactions.size());

		b.setTransactions(transactions);

		System.out.println("block transactions "+b.getTransactions().size());
		return b;
	}
}