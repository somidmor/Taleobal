//Source of this file is from UBC CPSC210 jsonExample repository
//link of the repository: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
