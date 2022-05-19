import IO.OutputLogger;

import java.util.ArrayList;

public class MoneypoolIterator {

    ArrayList<Moneypool> moneypools;
    private int idx = 0;
    private int pagesize;

    MoneypoolIterator(ArrayList<Moneypool> moneypools, int pagesize){
        this.moneypools = moneypools;
        this.pagesize = pagesize;
    }

    void print(){
        int i = 0;
        while(hasNext() && i<pagesize){
            Moneypool moneypool = next();
            OutputLogger.log(moneypool.getId()+": "+moneypool.getBalance());
            i++;
        }
    }

    boolean hasNext() {
        return moneypools.get(idx) != null;
    }

    private Moneypool next(){
        return moneypools.get(idx++);
    }

    private Moneypool prev(){
        return moneypools.get(idx--);
    }
}
