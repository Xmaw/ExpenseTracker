import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final List<String> foodStores = List.of("ica", "willys", "tempo");
    private static final Map<String, Integer> headersIndexMapping = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/elias/IdeaProjects/banking/2025/january.csv");
        Scanner scanner = new Scanner(file);
        ArrayList<String> transactionList = new ArrayList<>();
        while (scanner.hasNext()) {
            transactionList.add(scanner.nextLine());
        }
        String[] headers = transactionList.removeFirst().split(";");

        //Bokföringsdag, belopp, avsändare, mottagare, namn, rubrik, saldo, valuta
        int index = 0;
        for (String header : headers) {
            headersIndexMapping.put(header.toLowerCase(), index);
            index += 1;
        }

        Category food = getFoodTransactions(transactionList);
    }

    private static Category getFoodTransactions(ArrayList<String> transactionList) {
        Category food = new Category();
        for (String t : new ArrayList<>(transactionList)) {
            String[] transaction = t.split(";");
            String info = transaction[headersIndexMapping.get("rubrik")].toLowerCase();
            String rawAmount = transaction[headersIndexMapping.get("belopp")];
            String cleanedUpAmount = cleanupTransactionInformation(rawAmount);
            double amount = Double.parseDouble(cleanedUpAmount);
            TransactionData transactionData = new TransactionData(info, amount);
            for (String foodStore : foodStores)
                if (info.contains(foodStore)) {
                    food.addTransaction(transactionData);
                    transactionList.remove(t);
                }
        }
        return food;
    }

    public static String cleanupTransactionInformation(String transaction) {
        return transaction.replace(",", ".");
    }
}