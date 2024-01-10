package net.splodgebox.discordpaypalbot.config;

import lombok.Getter;

import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Getter
public class TransactionsConfig {

    private final static String FILE_NAME = "transactions.json";

    private final List<String> transactions;

    public TransactionsConfig() {
        transactions = new ArrayList<>();
    }

    public boolean transactionExists(String transactionId) {
        return transactions.contains(transactionId);
    }

    public void addTransaction(String transactionId) {
        transactions.add(transactionId);
        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile() throws IOException {
        JsonWriter writer = Json.createWriter(new FileWriter(FILE_NAME));
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        transactions.forEach(jsonArrayBuilder::add);

        JsonArray jsonArray = jsonArrayBuilder.build();
        writer.write(jsonArray);
    }

    public void loadFromFile() throws FileNotFoundException {
        JsonReader jsonReader = Json.createReader(new FileReader(FILE_NAME));
        JsonArray jsonArray = jsonReader.readArray();

        transactions.clear();
        IntStream.range(0, jsonArray.size()).mapToObj(jsonArray::getString).forEach(transactions::add);
    }

}
