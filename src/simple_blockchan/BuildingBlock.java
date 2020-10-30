package simple_blockchan;

import clientPackage.Client;
import nodePackage.ClientThread;
import nodePackage.Node;
import nodePackage.Peer;
import utilesPackage.Block;
import utilesPackage.Output;
import utilesPackage.Transaction;
import utilesPackage.TransactionPool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;

public class BuildingBlock extends Thread{

    TransactionPool poolObj;
    Blockchain blockchainObj = new Blockchain();
    int blockSize = 0;
    ArrayList<Transaction> list;
    String prevHash;
    Block currentBlock = new Block();
    Consensus c;
    public static int diff = 0;
    int mode = 0;
    public static ArrayList<Integer> validTransactions;
    public BuildingBlock(int blockSize, int diff, int mode){
        this.blockSize = blockSize;
        this.diff = diff;
        this.mode = mode;
        this.validTransactions = new ArrayList<>();
    }

    public void run() {
        int listSize = 0;
        while(true) {
            poolObj = ClientThread.pool;
            listSize = poolObj.getTransactionList().size();
            if (listSize > 0) {
                build();
            }
        }
    }

    public boolean validateTransaction(Transaction t){
        boolean validSig = t.verifyTransaction();
        if (validSig) {
            boolean doubleSpend = checkDoubleSpend(t);
            if (!doubleSpend) {
                return true;
            }
        }
        return false;
    }

    public void build(){
        System.out.println("Start Building");
        int i = 0;
        list = new ArrayList<>(blockSize);
        while (list.size() < blockSize) {
            poolObj = ClientThread.pool;
            if(poolObj.getTransactionList().size() != list.size()) {
                Transaction t = poolObj.getTransaction(i);
                if (t.getIndex() <= 49 || validateTransaction(t)) {
                    ClientThread.pool.addToPool(t.getIndex(), t.getOutputs());
                    list.add(t);
                    i++;
                } else {
                    ClientThread.pool.removeTransaction(t);
                }
            }
        }
        if (blockchainObj.getLength() == 0) {
            //first block
            prevHash = "0";
            currentBlock.setLevel(0);
        } else {
            prevHash = SHA256.toHexString(blockchainObj.getLastBlock().gettHash());
            currentBlock.setLevel(blockchainObj.getLength());
        }
        currentBlock.setHashPrevBlock(prevHash.getBytes());
        currentBlock.setTransactions(list);
        ArrayList<String> merkleHash = merkleTree();
        currentBlock.setHashMerkleRoot(merkleHash.get(merkleHash.size() - 1).getBytes());
        currentBlock.setTimestamp();
        if (mode == 0) {
            c = new Consensus(currentBlock);
            currentBlock.setHash(c.POW(currentBlock, diff).getBytes());
            // send block to all nodes
            blockchainObj.addBlock(currentBlock);
            if(Peer.peername.equals("node1")) {
                System.out.println(Peer.peername+" sending block");
                Node.sendBlockToAllNodes(currentBlock);
            }
            ClientThread.pool.removeBlock(list);
        }
            try {
                FileWriter myWriter = new FileWriter("blocks of "+Peer.peername+".txt", true);
                myWriter.write("Done Adding Block"+"\n");
                myWriter.write("Block Level  " + currentBlock.getLevel()+"\n");
                myWriter.write("Merkle Root  " + currentBlock.getHashMerkleRoot()+"\n");
                myWriter.write("Prev hash  " + currentBlock.getHashPrevBlock()+"\n");
                myWriter.write("Timestamp  " + currentBlock.getTimestamp()+"\n");
                myWriter.write("Block hash  " + currentBlock.gettHash()+"\n");
                myWriter.write("Nonce  " + currentBlock.getNonce()+"\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

    }

    public boolean checkDoubleSpend(Transaction t){
        int transactionKey = t.getPrevioustx();
        ArrayList<Output> o = poolObj.getList(transactionKey);
        if(o == null || o.size() == 0) {
                try {
                    FileWriter myWriter = new FileWriter("validations of "+Peer.peername+".txt", true);
                    if (o == null) myWriter.write("invalid because it references double spending transaction " + t.getIndex()+"\n");
                    else if (o.size() == 0) myWriter.write("double spending transaction " + t.getIndex()+"\n");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            return true;
        }
        for(Output out : o){
            if (out.getIndex()+1 == t.getOutputindex()) {
                ClientThread.pool.removeOutput(transactionKey, out);
                validTransactions.add(t.getIndex());
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> merkleTree() {
        ArrayList<String> tree = new ArrayList<>();
        for (Transaction t : list) {
            tree.add(SHA256.toHexString(t.getHash()));
        }
        int levelOffset = 0;
        for (int levelSize = list.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                String tleft = tree.get(levelOffset + left);
                String tright = tree.get(levelOffset + right);
                tree.add(SHA256.toHexString(SHA256.getSHA(tleft + tright)));
            }
            levelOffset += levelSize;
        }
        return tree;
    }
}
