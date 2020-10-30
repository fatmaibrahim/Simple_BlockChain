package utilesPackage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureClass {
		
	public byte[] hashTransaction(String input) throws NoSuchAlgorithmException {
	    
		MessageDigest md = MessageDigest.getInstance("SHA-256");  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
		
	}
	
	public byte[] getSignature(PrivateKey pvt,byte[]hash) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(pvt);
		
		
		    byte[] buf = new byte[512];
		    int len=0;
		    
		    for (int i = 0; i < hash.length; i++) {
		    	if(len==512) {
		    		sign.update(buf, 0, len);
		    		buf = new byte[512];
					len=0;
				}
		    	buf[len]=hash[i];
		    	len++;
			}
		    if(len!=512) {
		    	sign.update(buf, 0, len);
		    }
		    
		    
		    byte[] signature = sign.sign();
		return signature;
	}
	public boolean verifySignature(PublicKey pub, byte[] signature,byte[]hash) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initVerify(pub);
		 
		
		byte[] buf = new byte[512];
	    int len=0;
	    
	    for (int i = 0; i < hash.length; i++) {
	    	if(len==512) {
	    		sign.update(buf, 0, len);
	    		buf = new byte[512];
				len=0;
			}
	    	buf[len]=hash[i];
	    	len++;
		}
	    if(len!=512) {
	    	sign.update(buf, 0, len);
	    }
		

	    return sign.verify(signature);
		 		
	}
	
}
