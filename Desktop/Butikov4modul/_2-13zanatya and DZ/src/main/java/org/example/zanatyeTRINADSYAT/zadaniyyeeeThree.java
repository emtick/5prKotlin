package org.example.zanatyeTRINADSYAT;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class zadaniyyeeeThree {
    private DatabaseConnector dbConnector;

    public void FinanceService() {
        this.dbConnector = new DatabaseConnector();
    }

    public boolean addRecordToUser(long chatId, String date, String sum, String type, String category) {
        JsonObject jsonRecord = new JsonObject();
        jsonRecord.addProperty("chatId", String.valueOf(chatId));
        jsonRecord.addProperty("sum", sum);
        jsonRecord.addProperty("type", type);
        jsonRecord.addProperty("category", category);
        jsonRecord.addProperty("date", date + " 0:00:00");
        if (dbConnector.addRecord(jsonRecord)) {
            System.out.println("Запись добавлена");
            return true;
        }
        return false;
    }

    public List<zadanyeTWOOoo.FinanceRecord> getFinanceRecordsByDate(String strDate, long chatId) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(strDate, formatter);

        List<zadanyeTWOOoo.FinanceRecord> financeRecordsByDate = new ArrayList<>();
        List<FinanceRecord> allFinanceRecords = getAllFinanceRecordsForChatId(chatId);

        for (FinanceRecord r:allFinanceRecords) {
            if (r.getDateOfOperation().equals(date)) {
            }
        }
        return financeRecordsByDate;
    }


    public List<FinanceRecord> getAllFinanceRecordsForChatId (long chatId) {
        JsonArray jsonArray = dbConnector.selectRecordsById(chatId);
        List<FinanceRecord> allFinanceRecords = new ArrayList<>();

        if(jsonArray != null) {
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm:ss");
                LocalDate date = LocalDate.parse(obj.get("date").getAsString(), formatter);
                String type = obj.get("type").getAsString();
                String category = obj.get("category").getAsString();
                double sum = obj.get("sum").getAsDouble();
                FinanceRecord record = new FinanceRecord();
                allFinanceRecords.add(record);
            }
        }
        return allFinanceRecords;
    }
}
