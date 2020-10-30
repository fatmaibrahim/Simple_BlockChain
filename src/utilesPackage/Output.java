package utilesPackage;

import java.security.PublicKey;

public class Output {

	private double value;
	private int index;
	private PublicKey publicKeyPayee;
	
	public Output(double value,int index,PublicKey publicKeyPayee) {
		this.index=index;
		this.publicKeyPayee=publicKeyPayee;
		this.value=value; 
	}
	
	public double getValue() {
		return value;
	}
	public int getIndex() {
		return index;
	}
	public PublicKey getPublicKeyPayee() {
		return publicKeyPayee;
	}
	
	
	
}
