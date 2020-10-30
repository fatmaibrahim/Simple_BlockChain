package nodePackage;

import java.io.IOException;

import utilesPackage.Block;
import utilesPackage.Utiles;

public class Node {
	private static Peer peer;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
    peer=new Peer();
    peer.startPeer();
	}
	


	public static void sendBlockToAllNodes(Block b) {
		Utiles u=new Utiles();
		String blockString=u.BlockToString(b);
		peer.sendBloadToAllPeers(peer.getUsername(), peer.getServerThread(),blockString );
	}
	
	
}
