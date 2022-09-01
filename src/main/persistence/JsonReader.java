package persistence;

import model.JournalCollection;
import model.TravelJournal;
import model.exceptions.EmptyInputException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Citation: this class is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads journal collection from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public JournalCollection read() throws IOException, EmptyInputException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournalCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal collection from JSON object and returns it
    private JournalCollection parseJournalCollection(JSONObject jsonObject) throws EmptyInputException {
        JournalCollection jc = new JournalCollection();
        addJournals(jc, jsonObject);
        return jc;
    }

    // MODIFIES: jc
    // EFFECTS: parses journals from JSON object and adds them to journal collection
    private void addJournals(JournalCollection jc, JSONObject jsonObject) throws EmptyInputException {
        JSONArray jsonArray = jsonObject.getJSONArray("journalCollection");
        for (Object json : jsonArray) {
            JSONObject nextJournal = (JSONObject) json;
            addJournal(jc, nextJournal);
        }
    }

    // MODIFIES: jc
    // EFFECTS: parses journal from JSON object and adds it to journal collection
    private void addJournal(JournalCollection jc, JSONObject jsonObject) throws EmptyInputException {
        String title = jsonObject.getString("title");
        String date = jsonObject.getString("date");
        String location = jsonObject.getString("location");
        String text = jsonObject.getString("text");

        TravelJournal travelJournal = new TravelJournal();

        travelJournal.setTitle(title);
        travelJournal.setDate(date);
        travelJournal.setLocation(location);
        travelJournal.setText(text);

        jc.addJournal(travelJournal);
    }
}
