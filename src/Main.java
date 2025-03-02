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
        System.out.println(scanner.hasNext());
        ArrayList<String> transactionList = new ArrayList<>();
        while (scanner.hasNext()) {
            transactionList.add(scanner.nextLine());
        }
        System.out.println(transactionList.size());
        String[] headers = transactionList.removeFirst().split(";");

        //Bokföringsdag, belopp, avsändare, mottagare, namn, rubrik, saldo, valuta
        int index = 0;
        for (String header : headers) {
            System.out.println(header);
            headersIndexMapping.put(header.toLowerCase(), index);
            index += 1;
        }

        Category food = new Category();
        for (String t : transactionList) {
            String[] transaction = t.split(";");
            String info = transaction[headersIndexMapping.get("rubrik")].toLowerCase();
            String rawAmount = transaction[headersIndexMapping.get("belopp")];
            String cleanedUpAmount = cleanupTransactionInformation(rawAmount);
            double amount = Double.parseDouble(cleanedUpAmount);
            TransactionData transactionData = new TransactionData(info, amount);
            for (String foodStore : foodStores)
                if (info.contains(foodStore)) {
                    food.addTransaction(transactionData);
                }
        }
        for (TransactionData transactionData : food.getTransactionDataList()) {
            System.out.printf("Store: %s. Amount: %s\n", transactionData.getStore(), transactionData.getAmount());
        }
        System.out.println(food.getTotal());
    }

    public static String cleanupTransactionInformation(String transaction) {
        return transaction.replace(",", ".");
    }
}