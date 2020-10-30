package simple_blockchan;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class BFTNode {
    int id = 0;
    boolean[] proposer = {true, false, false};
    int mode = 1;
    int blockSize = 0;
    int diff = 0;

    public BFTNode(int blockSize, int diff){
        this.blockSize = blockSize;
        this.diff = diff;
    }

    public void BFT() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException, IOException {
//        if(proposer[id]){
//            BuildingBlock build = new BuildingBlock(blockSize, diff, mode);
//            build.build();
//        } else {
//
//        }
    }
}
