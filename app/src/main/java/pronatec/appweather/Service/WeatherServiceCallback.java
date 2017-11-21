package pronatec.appweather.Service;


import android.view.View;

import pronatec.appweather.data.Channel;

/**
 * Created by user on 26/08/2017.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void onClick(View view);

    void serviceFailure(Exception exception);
}

