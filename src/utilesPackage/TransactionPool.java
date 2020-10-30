package utilesPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionPool {
    private Map<Integer, ArrayList<Output>> pool = new HashMap<>();
    private ArrayList<Transaction> TransactionList = new ArrayList<Transaction>();

    public void addToPool(int transactionNum, ArrayList<Output> outputs){
        pool.put(transactionNum,outputs);
    }
    public ArrayList<Output> getList(int key){
        if(pool.containsKey(key))
            return pool.get(key);
        return null;
    }
    public void removeOutput(int transactionNum, Output out){
        pool.get(transactionNum).remove(out);
    }
    public void addTransaction(Transaction t){
        TransactionList.add(t);
    }
    public Transaction getTransaction(int index){
        return TransactionList.get(index);
    }
    public void removeTransaction(Transaction t){
        TransactionList.remove(t);
    }
    public void removeBlock(ArrayList<Transaction> list){
        for(int i = 0; i < list.size(); i++){
            removeTransaction(list.get(i));
        }
    }
    public ArrayList<Transaction> getTransactionList(){
        return TransactionList;
    }

}
