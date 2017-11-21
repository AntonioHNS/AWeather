package pronatec.appweather.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 26/08/2017.
 */

public class Units implements JSONPopulator {
    String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
        temperature  = data.optString("temperature");
    }

    @Override
    public JSONObject toJSON() {
        JSONObject data = new JSONObject();

        try {
            data.put("temperature", temperature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }


}
