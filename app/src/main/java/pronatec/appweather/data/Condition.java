package pronatec.appweather.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 26/08/2017.
 */

public class Condition implements JSONPopulator {
    int code;
    int temperature;
    String description;

    public int getCode(){ return code; }
    public String getDescription(){
        return description;
    }
    public int getTemperature(){
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("code", code);
            data.put("temp", temperature);
            data.put("text", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
