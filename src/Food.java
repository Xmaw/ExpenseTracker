import java.util.ArrayList;
import java.util.List;

public class Food {
    private double totalAmount;
    private List<TransactionData> transactionDataList;
    public Food(){
        totalAmount = 0;
        transactionDataList = new ArrayList<>();
    }

    public void addTransaction(TransactionData transactionData){
        this.totalAmount += transactionData.getAmount();
        transactionDataList.add(transactionData);
    }
    public List<TransactionData> getTransactionDataList(){
        return this.transactionDataList;
    }
    public void removeTransaction(TransactionData transactionData){
        transactionDataList.remove(transactionData);
    }
    public double getTotal(){
        return this.totalAmount;
    }
}
