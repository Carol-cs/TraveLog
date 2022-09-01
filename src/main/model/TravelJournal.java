package model;

import model.exceptions.EmptyInputException;
import org.json.JSONObject;
import persistence.Writable;

// Represents a travel journal with title, text, location and date.
public class TravelJournal implements Writable {

    private String title;
    private String text;
    private String location;
    private String date;

    // setters
    // MODIFIES: this
    // EFFECTS: set the travel journal title to the given new title
    //          If the new title has a zero length, throws EmptyInputException
    public void setTitle(String newTitle) throws EmptyInputException {
        if (newTitle.isEmpty()) {
            throw new EmptyInputException();
        }
        this.title = newTitle;
    }

    // MODIFIES: this
    // EFFECTS: set the text of the travel journal to the given new text
    //          If the new text has a zero length, throws EmptyInputException
    public void setText(String newText) throws EmptyInputException {
        if (newText.isEmpty()) {
            throw new EmptyInputException();
        }
        this.text = newText;
    }

    // MODIFIES: this
    // EFFECTS: set the travel location to the given new location
    //          If the new location has a zero length, throws EmptyInputException
    public void setLocation(String newLocation) throws EmptyInputException {
        if (newLocation.isEmpty()) {
            throw new EmptyInputException();
        }
        this.location = newLocation;
    }

    // MODIFIES: this
    // EFFECTS: set the travel date to the given new date
    //          If the new date has a zero length, throws EmptyInputException
    public void setDate(String newDate) throws EmptyInputException {
        if (newDate.isEmpty()) {
            throw new EmptyInputException();
        }
        this.date = newDate;
    }


    // getters
    // EFFECTS: getter for travel journal title
    public String getTitle() {
        return this.title;
    }

    // EFFECTS: getter for travel journal text
    public String getText() {
        return this.text;
    }

    // EFFECTS: getter for travel location recorded in a journal
    public String getLocation() {
        return this.location;
    }

    // EFFECTS: getter for travel date of a journal
    public String getDate() {
        return this.date;
    }


    // EFFECTS: returns a string representation of the entire journal
    public String toString() {
        // return this.title + "\n" + this.date + "\n" + "Location: " + this.location + "\n" + this.text;
        return "<html>" + this.title +  "<br/>Date: " + this.date + "<br/>Location: " + this.location + "</html>";
    }

    // Citation: this method is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("date", date);
        json.put("location", location);
        json.put("text", text);
        return json;
    }

}
