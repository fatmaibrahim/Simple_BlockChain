package utilesPackage;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;


public class KeyPairGenertor {
	
	ArrayList<PublicKey> publicKeys=new ArrayList<PublicKey>();
    ArrayList<PrivateKey> privateKeys=new ArrayList<PrivateKey>();

	public void generatekyes(int NumUser) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		// TODO Auto-generated method stub
		 //Creating KeyPair generator object
	    
		for (int i = 0; i < NumUser; i++) {
			
		
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
	      
	      //Initializing the KeyPairGenerator
	      keyPairGen.initialize(512);
	      
	      //Generating the pair of keys
	      KeyPair pair = keyPairGen.generateKeyPair();
	      
	      //Getting the private key from the key pair
	      PrivateKey privKey = pair.getPrivate();   
	     // System.out.println(privKey);
	      //Getting the public key from the key pair
	      PublicKey publicKey = pair.getPublic(); 
	      //System.out.println(publicKey);
	     // System.out.println("Keys generated");
	      publicKeys.add(publicKey);
	      privateKeys.add(privKey);

	}
	}

	public PublicKey getPublicKey(int clientNum) {
	return 	publicKeys.get(clientNum);
	}
	public PrivateKey getPrivateKey(int clientNum) {
		return 	privateKeys.get(clientNum);

	}
	
	}
