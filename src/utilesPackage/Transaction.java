package utilesPackage;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.util.ArrayList;

public class Transaction {
    private int index;
    private PublicKey publicKeyPayer;
    private int previoustx;
    private int outputindex;
    private byte[]hash;
    private byte[] signature;
	private ArrayList<Output> outputs;

	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}


	public boolean verifyTransaction() {
		SignatureClass ld;
		boolean valid = false;
		try {
			ld=new SignatureClass();
			valid = ld.verifySignature(publicKeyPayer,signature,hash);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return valid;
	}
	public void setOutputs(ArrayList<Output> outputs) {
		this.outputs = outputs;
	}
	
	
	public ArrayList<Output> getOutputs() {
		return outputs;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}



	public PublicKey getPublicKeyPayer() {
		return publicKeyPayer;
	}



	public void setPublicKeyPayer(PublicKey publicKeyPayer) {
		this.publicKeyPayer = publicKeyPayer;
	}



	public int getPrevioustx() {
		return previoustx;
	}



	public void setPrevioustx(int previoustx) {
		this.previoustx = previoustx;
	}



	public int getOutputindex() {
		return outputindex;
	}



	public void setOutputindex(int outputindex) {
		this.outputindex = outputindex;
	}
	
	
	
	
}
