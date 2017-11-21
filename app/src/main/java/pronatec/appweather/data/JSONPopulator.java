package pronatec.appweather.data;

import org.json.JSONObject;

/**
 * Created by user on 26/08/2017.
 */

public interface JSONPopulator {
        void populate(JSONObject data);

        JSONObject toJSON();
}
