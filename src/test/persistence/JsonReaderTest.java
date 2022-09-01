package persistence;

import model.JournalCollection;
import model.TravelJournal;
import model.exceptions.EmptyInputException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Citation: this class is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Unit tests for JsonReader class
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile(){
        JsonReader reader = new JsonReader("./data/noSuchFile.json");

        try {
            JournalCollection jc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
    }

    @Test
    void testReaderEmptyJournalCollection(){
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournalCollection.json");

        try {
            JournalCollection jc = reader.read();
            assertTrue(jc.isEmpty());
            assertEquals(0, jc.numOfJournals());
        } catch (IOException | EmptyInputException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournalCollection(){
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournalCollection.json");

        try {
            JournalCollection jc = reader.read();
            ArrayList<TravelJournal> listOfJournals = jc.getJournalCollection();
            assertEquals(2, listOfJournals.size());

            checkJournal("Canada's Wonderland Tour", "2021-7-28", "Vaughan",
                    "My favorite ride is Leviathan!\nHope to go again...\n", listOfJournals.get(0));
            checkJournal("Niagara Falls Tour", "2020-5-5", "Niagara Falls",
                    "The scenery is spectacular!\nEnjoyed the boat tour...\n", listOfJournals.get(1));
        } catch (IOException | EmptyInputException e) {
            fail("Couldn't read from file");
        }
    }
}
