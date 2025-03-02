public class TransactionData {
    private final String store;
    private final double amount;
    public TransactionData(String store, double amount){
        this.store = store;
        this.amount = amount;
    }
    public String getStore(){
        return this.store;
    }
    public double getAmount(){
        return this.amount;
    }
}
