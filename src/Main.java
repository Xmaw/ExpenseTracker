import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final Map<String, Integer> headersIndexMapping = new HashMap<>();

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/elias/IdeaProjects/banking/2024/july.csv");
        Scanner scanner = new Scanner(file);
        ArrayList<String> transactionList = new ArrayList<>();
        while (scanner.hasNext()) {
            transactionList.add(scanner.nextLine());
        }
        List<String> foodStores = List.of("ica", "willys", "tempo");
        List<String> pleasureNames = List.of("gamer2gamer", "frisorateljen");
        List<String> recurringNames = List.of("vimarhem", "omsättning lån", "vimmerby ene", "telia", "alfa kassan", "hallon", "raidbots premium", "1password", "blizzard entertain", "youtube", "max", "hyresgästför", "spotify");
        List<String> restaurantNames = List.of("foodora", "sibylla", "big wong", "monte car", "burger king");
        List<String> ignoredNames = List.of("överföring", "autogiro", "klarna refun");
        List<String> incomeSourceNames = List.of("lön");
        List<String> clothesStoreNames = List.of("dressman", "intersport");
        List<String> alcoholAndBarNames = List.of("best western", "kharma", "pink bistro", "systembolaget");


        String[] headers = transactionList.removeFirst().split(";");
        createHeaderMap(headers);
        Category ignored = getCategorySpecificTransaction(transactionList, ignoredNames);
        Category food = getCategorySpecificTransaction(transactionList, foodStores);
        Category recurring = getCategorySpecificTransaction(transactionList, recurringNames);
        Category restaurants = getCategorySpecificTransaction(transactionList, restaurantNames);
        Category pleasure = getCategorySpecificTransaction(transactionList, pleasureNames);
        Category clothes = getCategorySpecificTransaction(transactionList, clothesStoreNames);
        Category alcoholAndBars = getCategorySpecificTransaction(transactionList, alcoholAndBarNames);
        Category income = getCategorySpecificTransaction(transactionList, incomeSourceNames);

        printRemainingTransactions(transactionList);
        System.out.println("----------------------------------------");
        System.out.println("Ignored: " + ignored);
        System.out.println("Groceries: " + food.getTotal());
        System.out.println("Recurring: " + recurring.getTotal());
        System.out.println("Restaurants:" + restaurants.getTotal());
        System.out.println("Pleasure: " + pleasure.getTotal());
        System.out.println("Clothes: " + clothes.getTotal());
        System.out.println("Bars: " + alcoholAndBars.getTotal());
        System.out.println("Income: " + income.getTotal());
    }

    private static void printRemainingTransactions(ArrayList<String> transactionList) {
        for (String t : new ArrayList<>(transactionList)) {
            String[] transaction = t.split(";");
            String info = transaction[headersIndexMapping.get("rubrik")].toLowerCase();
            String rawAmount = transaction[headersIndexMapping.get("belopp")];
            String cleanedUpAmount = cleanupTransactionInformation(rawAmount);
            double amount = Double.parseDouble(cleanedUpAmount);
            TransactionData transactionData = new TransactionData(info, amount);
            System.out.printf("Store: %s. Amount: %.2f\n", transactionData.getStore(), transactionData.getAmount());
        }
    }


    private static void createHeaderMap(String[] headers) {
        //Bokföringsdag, belopp, avsändare, mottagare, namn, rubrik, saldo, valuta
        int index = 0;
        for (String header : headers) {
            headersIndexMapping.put(header.toLowerCase(), index);
            index += 1;
        }
    }

    private static Category getCategorySpecificTransaction(ArrayList<String> transactionList, List<String> categoryList) {
        Category category = new Category();
        for (String t : new ArrayList<>(transactionList)) {
            String[] transaction = t.split(";");
            String info = transaction[headersIndexMapping.get("rubrik")].toLowerCase();
            String rawAmount = transaction[headersIndexMapping.get("belopp")];
            String cleanedUpAmount = cleanupTransactionInformation(rawAmount);
            double amount = Double.parseDouble(cleanedUpAmount);
            TransactionData transactionData = new TransactionData(info, amount);
            for (String element : categoryList)
                if (info.contains(element)) {
                    category.addTransaction(transactionData);
                    transactionList.remove(t);
                }
        }
        return category;
    }

    public static String cleanupTransactionInformation(String transaction) {
        return transaction.replace(",", ".");
    }
}