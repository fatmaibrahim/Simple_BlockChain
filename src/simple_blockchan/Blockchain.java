package simple_blockchan;

import utilesPackage.Block;

import java.util.ArrayList;

public class Blockchain {

    private ArrayList<Block> blockchain;
    public Blockchain(){
        blockchain = new ArrayList<>();
    }


    public void addBlock(Block b){
        blockchain.add(b);
    }

    public int getLength(){
        return blockchain.size();
    }

    public Block getLastBlock(){
        return blockchain.get(blockchain.size()-1);
    }

    public ArrayList<Block> getBlockchain(){
        return blockchain;
    }

    public void setBlockchain(ArrayList<Block> b){
        blockchain = b;
    }
}
