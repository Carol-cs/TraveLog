package model;

import model.exceptions.EmptyInputException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for TravelJournal class
public class TravelJournalTest {


    private TravelJournal journalA;

    @BeforeEach
    public void setUpNoException(){
        journalA = new TravelJournal();
        try {
            journalA.setTitle("Canada's Wonderland Tour");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        try {
            journalA.setText("An amazing amusement park...");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        try {
            journalA.setLocation("Vaughan");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        try {
            journalA.setDate("2021-7-28");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
    }


    @Test
    public void testSetAndGetTitleNoException(){
        try {
            journalA.setTitle("Wonderful trip to Canada's Wonderland");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        assertEquals("Wonderful trip to Canada's Wonderland", journalA.getTitle());
    }

    @Test
    public void testSetTitleExpectEmptyInputException(){
        try {
            journalA.setTitle("");
            fail("EmptyInputException was not thrown");
        } catch (EmptyInputException e) {
            // all good
        }
    }

    @Test
    public void testSetAndGetTextNoException(){
        try {
            journalA.setText("My favorite ride is Leviathan!");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        assertEquals("My favorite ride is Leviathan!", journalA.getText());
    }

    @Test
    public void testSetTextExpectEmptyInputException(){
        try {
            journalA.setText("");
            fail("EmptyInputException was not thrown");
        } catch (EmptyInputException e) {
            // all good
        }
    }

    @Test
    public void testSetAndGetDateNoException(){
        try {
            journalA.setDate("2021-3-20");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        assertEquals("2021-3-20", journalA.getDate());
    }

    @Test
    public void testSetDateExpectEmptyInputException(){
        try {
            journalA.setDate("");
            fail("EmptyInputException was not thrown");
        } catch (EmptyInputException e) {
            // all good
        }
    }

    @Test
    public void testSetAndGetLocationNoException(){
        try {
            journalA.setLocation("1 Canada's Wonderland Drive");
        } catch (EmptyInputException e) {
            fail("Unexpected EmptyInputException was thrown");
        }
        assertEquals("1 Canada's Wonderland Drive", journalA.getLocation());
    }

    @Test
    public void testSetLocationExpectEmptyInputException(){
        try {
            journalA.setLocation("");
            fail("EmptyInputException was not thrown");
        } catch (EmptyInputException e) {
            // all good
        }
    }

    @Test
    public void testToString(){
        assertEquals("<html>Canada's Wonderland Tour<br/>Date: 2021-7-28<br/>Location: Vaughan</html>",
                journalA.toString());
    }

    @Test
    public void testToJson() {

        JSONObject jsonJournalA = journalA.toJson();

        assertEquals(jsonJournalA.getString("title"), journalA.getTitle());
        assertEquals(jsonJournalA.getString("date"), journalA.getDate());
        assertEquals(jsonJournalA.getString("location"), journalA.getLocation());
        assertEquals(jsonJournalA.getString("text"), journalA.getText());

    }


}
