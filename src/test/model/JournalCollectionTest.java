package model;

import model.exceptions.EmptyInputException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for JournalCollection class
class JournalCollectionTest {

    private JournalCollection testJournalCollection;
    private TravelJournal journalA;
    private TravelJournal journalB;
    private TravelJournal journalC;
    private TravelJournal journalD;

    @BeforeEach
    public void SetUp(){
        testJournalCollection = new JournalCollection();

        try {
            // Set up journalA
            journalA = new TravelJournal();
            journalA.setTitle("Canada's Wonderland Tour");
            journalA.setText("An amazing amusement park...");
            journalA.setLocation("Vaughan");
            journalA.setDate("2021-7-28");

            // Set up journalB
            journalB = new TravelJournal();
            journalB.setTitle("Universal Studios Japan Tour");
            journalB.setText("The butter beer is so tasty...");
            journalB.setLocation("Osaka");
            journalB.setDate("2022-6-1");

            // Set up journalC
            journalC = new TravelJournal();
            journalC.setTitle("Canada's Wonderland Tour");
            journalC.setText("My favorite ride is Leviathan!");
            journalC.setLocation("Vaughan");
            journalC.setDate("2021-7-28");

            // Set up journalD
            journalD = new TravelJournal();
            journalD.setTitle("Niagara Falls Tour");
            journalD.setText("The scenery is spectacular!");
            journalD.setLocation("Niagara Falls");
            journalD.setDate("2021-7-28");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }

    }

    @Test
    public void testConstructor(){
        assertTrue(testJournalCollection.isEmpty());
        assertEquals(0, testJournalCollection.numOfJournals());
    }

    @Test
    public void testAddOneJournal(){
        assertTrue(testJournalCollection.addJournal(journalA));

        assertFalse(testJournalCollection.isEmpty());
        assertEquals(1, testJournalCollection.numOfJournals());
        assertEquals(journalA.toString() + "\n", testJournalCollection.viewAllJournals());

    }

    @Test
    public void testAddTwoJournals(){
        assertTrue(testJournalCollection.addJournal(journalA));
        assertTrue(testJournalCollection.addJournal(journalB));

        assertFalse(testJournalCollection.isEmpty());
        assertEquals(2, testJournalCollection.numOfJournals());
        assertEquals(journalA.toString() + "\n" + journalB.toString() + "\n",
                testJournalCollection.viewAllJournals());
    }

    @Test
    public void testAddTwoJournalsWithSameTitle(){
        assertTrue(testJournalCollection.addJournal(journalA));
        assertFalse(testJournalCollection.addJournal(journalC));

        assertFalse(testJournalCollection.isEmpty());
        assertEquals(1, testJournalCollection.numOfJournals());
        assertEquals(journalA.toString() + "\n", testJournalCollection.viewAllJournals());
    }

    @Test
    public void testDeleteExistingJournalByTitle(){
        testJournalCollection.addJournal(journalA);
        testJournalCollection.addJournal(journalB);

        assertTrue(testJournalCollection.deleteJournalByTitle("Canada's Wonderland Tour"));

        assertEquals(1, testJournalCollection.numOfJournals());
        assertEquals(journalB.toString() + "\n", testJournalCollection.viewAllJournals());


    }

    @Test
    public void testDeleteNonExistentJournalByTitle(){
        testJournalCollection.addJournal(journalA);

        assertFalse(testJournalCollection.deleteJournalByTitle("Universal Studios Japan Tour"));

        assertEquals(1, testJournalCollection.numOfJournals());
        assertEquals(journalA.toString() + "\n", testJournalCollection.viewAllJournals());

    }

    @Test
    public void testViewAllJournals(){
        testJournalCollection.addJournal(journalA);
        assertEquals(journalA.toString() + "\n", testJournalCollection.viewAllJournals());

        testJournalCollection.addJournal(journalB);
        assertEquals(journalA.toString() + "\n" + journalB.toString() + "\n",
                testJournalCollection.viewAllJournals());

    }

    @Test
    public void testFindExistingJournalByTitle(){
        addThreeJournals();
        assertEquals(journalA, testJournalCollection.findJournalByTitle("Canada's Wonderland Tour"));
    }

    @Test
    public void testFindNonExistentJournalByTitle(){
        addThreeJournals();
        assertNull(testJournalCollection.findJournalByTitle("Trip to Vancouver"));
    }

    @Test
    public void testFindExistingJournalsByDate(){
        addThreeJournals();

        ArrayList<TravelJournal> journalList = testJournalCollection.findJournalsByDate("2021-7-28");
        assertEquals(2, journalList.size());
        assertEquals(journalA, journalList.get(0));
        assertEquals(journalD, journalList.get(1));

    }

    @Test
    public void testFindNonExistentJournalsByDate(){
        addThreeJournals();
        ArrayList<TravelJournal> journalList = testJournalCollection.findJournalsByDate("2018-3-3");
        assertEquals(0, journalList.size());

    }

    @Test
    public void testFindExistingJournalsByLocation(){
        addThreeJournals();
        TravelJournal journalE = new TravelJournal();

        try {
            journalE.setTitle("Trip to Tsutenkaku");
            journalE.setText("The food sold around is amazing...");
            journalE.setLocation("Osaka");
            journalE.setDate("2019-7-20");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        testJournalCollection.addJournal(journalE);

        ArrayList<TravelJournal> journalList = testJournalCollection.findJournalsByLocation("Osaka");
        assertEquals(2, journalList.size());
        assertEquals(journalB, journalList.get(0));
        assertEquals(journalE, journalList.get(1));

    }

    @Test
    public void testFindNonExistentJournalsByLocation(){
        addThreeJournals();

        ArrayList<TravelJournal> journalList = testJournalCollection.findJournalsByLocation("Beijing");
        assertEquals(0, journalList.size());

    }

    @Test
    public void testIsEmpty(){
        assertTrue(testJournalCollection.isEmpty());

        testJournalCollection.addJournal(journalA);
        assertFalse(testJournalCollection.isEmpty());
    }

    @Test
    public void numOfJournals(){
        assertEquals(0, testJournalCollection.numOfJournals());

        addThreeJournals();
        assertEquals(3, testJournalCollection.numOfJournals());
    }

    @Test
    public void testGetJournalCollection(){
        addThreeJournals();

        ArrayList<TravelJournal> listOfJournals = testJournalCollection.getJournalCollection();
        assertEquals(3, listOfJournals.size());
        assertEquals(journalA, listOfJournals.get(0));
        assertEquals(journalB, listOfJournals.get(1));
        assertEquals(journalD, listOfJournals.get(2));
    }

    @Test
    public void testToJson() {
        addThreeJournals();

        JSONObject jsonObject = testJournalCollection.toJson();

        JSONArray jsonArray = jsonObject.getJSONArray("journalCollection");
        JSONObject jsonJournalA = jsonArray.getJSONObject(0);
        JSONObject jsonJournalB = jsonArray.getJSONObject(1);
        JSONObject jsonJournalD = jsonArray.getJSONObject(2);

        assertEquals(jsonJournalA.getString("title"), journalA.getTitle());
        assertEquals(jsonJournalA.getString("date"), journalA.getDate());
        assertEquals(jsonJournalA.getString("location"), journalA.getLocation());
        assertEquals(jsonJournalA.getString("text"), journalA.getText());

        assertEquals(jsonJournalB.getString("title"), journalB.getTitle());
        assertEquals(jsonJournalB.getString("date"), journalB.getDate());
        assertEquals(jsonJournalB.getString("location"), journalB.getLocation());
        assertEquals(jsonJournalB.getString("text"), journalB.getText());

        assertEquals(jsonJournalD.getString("title"), journalD.getTitle());
        assertEquals(jsonJournalD.getString("date"), journalD.getDate());
        assertEquals(jsonJournalD.getString("location"), journalD.getLocation());
        assertEquals(jsonJournalD.getString("text"), journalD.getText());
    }

    // MODIFIES: this
    // EFFECTS: helper method: adds three journals to the journal collection
    public void addThreeJournals(){
        testJournalCollection.addJournal(journalA);
        testJournalCollection.addJournal(journalB);
        testJournalCollection.addJournal(journalD);
    }


}