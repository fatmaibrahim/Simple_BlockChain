package nodePackage;

import simple_blockchan.BuildingBlock;
import utilesPackage.TransactionPool;

public class BuildBlockThread extends Thread {
    TransactionPool pool;
    BuildingBlock build;
//    public BuildBlockThread(TransactionPool pool){
//        this.pool = pool;
//        build = new BuildingBlock(200,3,0, this.pool);
//    }

//    public void setPool(TransactionPool pool) {
//        this.pool = pool;
//    }

    public void run() {
        System.out.println("da5lt el thread");
        build = new BuildingBlock(200,3,0);
        while(true) {
            pool = ClientThread.pool;
            System.out.println("thread" + pool.getTransactionList().size());
            if (pool.getTransactionList().size() > 200) {
                System.out.println("la2et transaction");
                build.build();
            }
        }
    }
}
