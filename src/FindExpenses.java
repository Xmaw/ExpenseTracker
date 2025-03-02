import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FindExpenses {
    public static void main(String[] args) throws FileNotFoundException {
        String baseFilePath = "/Users/elias/IdeaProjects/banking";
        List<File> files = new ArrayList<>();
        listf(baseFilePath, files);
        Category target = new Category();
        String targetExpense = "foodora";
        for (File file : files) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] transaction = line.split(";");
                String info = transaction[5].toLowerCase();
                String rawAmount = transaction[1];
                if (info.contains(targetExpense)) {
                    System.out.println(line);
                    String cleanedUpAmount = cleanupTransactionInformation(rawAmount);
                    double amount = Double.parseDouble(cleanedUpAmount);
                    TransactionData transactionData = new TransactionData(info, amount);
                    target.addTransaction(transactionData);
                }
            }
        }
        System.out.println(target.getTotal());

    }

    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile() && file.getName().contains("csv")) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }

    public static String cleanupTransactionInformation(String transaction) {
        return transaction.replace(",", ".");
    }
}
