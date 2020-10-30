package simple_blockchan;

import utilesPackage.Block;

public class Consensus {
    String prevHash = "";
    String merkleRoot = "";
    String time = "";
    String value = "";
    String hash = "";
    int nonce = -1;

    public Consensus(Block currentBlock){
        this.prevHash = SHA256.toHexString(currentBlock.getHashPrevBlock());
        this.merkleRoot = SHA256.toHexString(currentBlock.getHashMerkleRoot());
        this.time = Long.toString(currentBlock.getTimestamp());
        this.value = prevHash + merkleRoot + time;
    }

    public String POW(Block currentBlock, int diff){
        boolean found = false;
        while(!found){
            nonce++;
            hash = SHA256.hexToBin(SHA256.toHexString(SHA256.getSHA(value+nonce)));
            boolean broken = false;
            for(int i = 0; i < diff; i++){
                if(hash.charAt(i) != '0') {
                    broken = true;
                    break;
                }
            }
            if(!broken){
                found = true;
            }
        }
        currentBlock.setNonce(nonce);
        return hash;
    }

    public String BFT(Block currentBlock){
        hash = SHA256.hexToBin(SHA256.toHexString(SHA256.getSHA(value+nonce)));
        currentBlock.setNonce(nonce);
        return hash;
    }
}
