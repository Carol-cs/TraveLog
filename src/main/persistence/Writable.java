package persistence;

import org.json.JSONObject;

// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents the interface that allows classes to be converted to JSON objects.
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
