package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the collection of all travel journals
public class JournalCollection implements Writable {

    private ArrayList<TravelJournal> journalCollection;

    // EFFECTS: create a new empty travel journal collection
    public JournalCollection() {
        journalCollection = new ArrayList<>();
    }


    // MODIFIES: this
    // EFFECTS: IF there are no journals in the journal collection with the same title as the one to be added, then
    //              adds the journal to the journal collection
    //              returns true
    //          OTHERWISE
    //              just returns false
    public Boolean addJournal(TravelJournal travelJournal) {
        for (TravelJournal tj: journalCollection) {
            if (tj.getTitle().equals(travelJournal.getTitle())) {
                return false;
            }
        }
        EventLog.getInstance().logEvent(new Event("Added a travel journal titled \""
                + travelJournal.getTitle() + "\""));
        journalCollection.add(travelJournal);
        return true;

    }

    // MODIFIES: this
    // EFFECTS: IF there are no journals with the given title in the journal collection
    //              just returns false
    //          OTHERWISE
    //              removes the journal with the given title from the journal collection,
    //              returns true
    public Boolean deleteJournalByTitle(String title) {
        for (TravelJournal tj: journalCollection) {
            if (tj.getTitle().equals(title)) {
                EventLog.getInstance().logEvent(new Event("Removed the travel journal titled \""
                        + tj.getTitle() + "\""));
                journalCollection.remove(tj);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns all the travel journals as a string
    public String viewAllJournals() {
        StringBuilder output = new StringBuilder();
        for (TravelJournal tj: journalCollection) {
            output.append(tj.toString()).append("\n");
        }
        return output.toString();
    }

    // EFFECTS: returns the journal with given title
    //          If there is no journal with given title, returns null
    public TravelJournal findJournalByTitle(String title) {
        for (TravelJournal tj: journalCollection) {
            if (tj.getTitle().equals(title)) {
                return tj;
            }
        }
        return null;
    }

    // EFFECTS: returns a list of travel journals for the given date
    //          If there is no journal for the given date, returns an empty list
    public ArrayList<TravelJournal> findJournalsByDate(String date) {
        ArrayList<TravelJournal> journalList = new ArrayList<>();

        for (TravelJournal tj: journalCollection) {
            if (tj.getDate().equals(date)) {
                journalList.add(tj);
            }
        }
        return journalList;
    }


    // EFFECTS: returns a list of travel journals for the given location
    //          if there is no journal for the given location, returns an empty list
    public ArrayList<TravelJournal> findJournalsByLocation(String location) {
        ArrayList<TravelJournal> journalList = new ArrayList<>();

        for (TravelJournal tj: journalCollection) {
            if (tj.getLocation().equals(location)) {
                journalList.add(tj);
            }
        }
        return journalList;
    }

    // EFFECTS: returns true if the journal collection is empty, otherwise, returns false.
    public boolean isEmpty() {
        return journalCollection.isEmpty();
    }

    // EFFECTS: returns the number of journals in the journal collection
    public int numOfJournals() {
        return journalCollection.size();
    }

    // EFFECTS: returns a list of travel journals in this journal collection
    public ArrayList<TravelJournal> getJournalCollection() {
        return journalCollection;
    }

    // Citation: this method is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: converts a journal collection to a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("journalCollection", journalCollectionToJson());
        return json;
    }

    // Citation: this method is modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns journals in this journal collection as a JSON array
    private JSONArray journalCollectionToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TravelJournal tj : journalCollection) {
            jsonArray.put(tj.toJson());
        }

        return jsonArray;
    }


}
