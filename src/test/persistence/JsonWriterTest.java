package persistence;

import model.JournalCollection;
import model.TravelJournal;
import model.exceptions.EmptyInputException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Citation: this class is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Unit tests for JsonWriter class
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile(){
        try {
            JournalCollection jc = new JournalCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournalCollection(){
        try {
            JournalCollection jc = new JournalCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournalCollection.json");
            writer.open();
            writer.write(jc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournalCollection.json");
            jc = reader.read();
            assertTrue(jc.isEmpty());
            assertEquals(0, jc.numOfJournals());
        } catch (IOException | EmptyInputException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            JournalCollection jc = new JournalCollection();

            // Set up journalA
            TravelJournal journalA = new TravelJournal();
            journalA.setTitle("Canada's Wonderland Tour");
            journalA.setDate("2021-7-28");
            journalA.setLocation("Vaughan");
            journalA.setText("My favorite ride is Leviathan!\nHope to go again...\n");

            jc.addJournal(journalA);

            // Set up journalB
            TravelJournal journalB = new TravelJournal();
            journalB.setTitle("Universal Studios Japan Tour");
            journalB.setDate("2022-6-1");
            journalB.setLocation("Osaka");
            journalB.setText("The butter beer is so tasty...\n");

            jc.addJournal(journalB);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournalCollection.json");
            writer.open();
            writer.write(jc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournalCollection.json");
            jc = reader.read();
            assertEquals(2, jc.numOfJournals());

            ArrayList<TravelJournal> listOfJournals = jc.getJournalCollection();
            checkJournal("Canada's Wonderland Tour", "2021-7-28", "Vaughan",
                    "My favorite ride is Leviathan!\nHope to go again...\n", listOfJournals.get(0));
            checkJournal("Universal Studios Japan Tour", "2022-6-1", "Osaka",
                    "The butter beer is so tasty...\n", listOfJournals.get(1));


        } catch (IOException | EmptyInputException e) {
            fail("Exception should not have been thrown");
        }
    }

}
