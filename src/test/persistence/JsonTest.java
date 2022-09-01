package persistence;

import model.TravelJournal;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Citation: this class is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Extended by JsonReaderTest and JsonWriterTest to compare the given journal(s) with the data saved in file
public class JsonTest {

    // EFFECTS: compares the given journal(s) with the data saved in file
    protected void checkJournal(String title, String date, String location, String text, TravelJournal tj) {
        assertEquals(title, tj.getTitle());
        assertEquals(date, tj.getDate());
        assertEquals(location, tj.getLocation());
        assertEquals(text, tj.getText());
    }
}
