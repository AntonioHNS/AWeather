package pronatec.appweather;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import pronatec.appweather.Service.WeatherServiceCallback;
import pronatec.appweather.Service.YahooWeatherService;
import pronatec.appweather.data.Channel;
import pronatec.appweather.data.Condition;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {
    private boolean weatherServicesHasFailed = false;
    YahooWeatherService service;
    TextView temperatura, clima, location;
    Button previsao;
    ImageView icon;
    Condition[] previsoes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = new YahooWeatherService(this);
        tela();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1FAB89")));
        getSupportActionBar().setTitle("AWeather");
        previsao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(previsoes!= null){
                    previsao();
                }else{
                    Toast.makeText(getApplicationContext(),"Primeiro pesquise um local", Toast.LENGTH_LONG);
                }
            }
        });

    }


    @Override
    public void serviceSuccess(Channel chanel) {
        int temp  = chanel.getItem().getCondition().getTemperature();
        String condition = chanel.getItem().getCondition().getDescription();
        int code = chanel.getItem().getCondition().getCode();
        int icone = getResources().getIdentifier("icon_" + Integer.toString(code), "drawable", getPackageName());
        previsoes = chanel.getItem().getForecast();
        icon.setImageResource(icone);
        temperatura.setText(Integer.toString(temp)+"°");
        clima.setText(traduz(condition));
        location.setText(chanel.getLocation());

    }

    @Override
    public void onClick(View view) {
    }

    public void tela(){
        temperatura = findViewById(R.id.temperatura);
        clima = findViewById(R.id.clima);
        previsao = findViewById(R.id.previsao);
        icon = findViewById(R.id.icon);
        location = findViewById(R.id.location);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        mSearchView.setQueryHint("Insira a cidade");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                service.refreshWeather(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;

    }

    public void previsao(){
        Date date = new Date();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = getLayoutInflater();
        View view = li.inflate(R.layout.info, null);
        builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        int i = 0;
        for (Condition c:previsoes){
            if (i < 3) {
                date.setDate(date.getDate()+(i+1));
                ImageView icone = view.findViewById(getResources().getIdentifier("icon" + i, "id", getPackageName()));
                TextView condition = view.findViewById(getResources().getIdentifier("condition" + i, "id", getPackageName()));
                icone.setImageResource(getResources().getIdentifier("icon_" + c.getCode(), "drawable", getPackageName()));
                condition.setText(traduz(c.getDescription()));
                i++;
            }
        }
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    @Override
    public void serviceFailure(Exception exception) {
        if (weatherServicesHasFailed) {
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            weatherServicesHasFailed = true;
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String traduz(String cond){
        String condition = cond.toLowerCase();
        if (condition.equals("tornado")){
            return "Tornado";
        } else if (condition.equals("tropical storm")){
            return "Tempestade tropical";
        }else if (condition.equals("hurricane")){
            return "Furacão";
        }else if (condition.equals("severe thunderstorms")){
            return "Tempestade";
        }else if (condition.equals("thunderstorms")){
            return "Tempestade";
        }else if (condition.equals("mixed rain and snow")){
            return "Chuva e neve";
        }else if (condition.equals("mixed rain and sleet")){
            return "Chuva e granizo";
        }else if (condition.equals("mixed snow and sleet")){
            return "Neve e granizo";
        }else if (condition.equals("freezing drizzle")){
            return "Chuva fraca";
        }else if (condition.equals("drizzle")){
            return "Chuva fraca";
        }else if (condition.equals("freezing rain")||condition.equals("rain")){
            return "Chuva forte";
        }else if (condition.equals("showers")){
            return "Chuva";
        }else if (condition.equals("snow flurries")){
            return "Flocos de neve";
        }else if (condition.equals("light snow showers")){
            return "Nevasca leve";
        }else if (condition.equals("blowing snow")){
            return "Neve fraca";
        }else if (condition.equals("snow")){
            return "Neve";
        }else if (condition.equals("hail")){
            return "Granizo";
        }else if (condition.equals("sleet")){
            return "Granizo";
        }else if (condition.equals("dusty")){
            return "Seco";
        }else if (condition.equals("foggy")){
            return "Neblina";
        }else if (condition.equals("haze")){
            return "Névoa";
        }else if (condition.equals("smoky")){
            return "Seco";
        }else if (condition.equals("blustery")){
            return "Vento forte";
        }else if (condition.equals("windy")){
            return "Vento forte";
        }else if (condition.equals("cold") || condition.equals("breezy")){
            return "Frio";
        }else if (condition.equals("cloudy")){
            return "Nublado";
        }else if (condition.equals("mostly cloudy")){
            return "Nublado";
        }else if (condition.equals("mostly cloudy")){
            return "Nublado";
        }else if (condition.equals("partly cloudy")){
            return "Parcialmente nublado";
        }else if (condition.equals("partly cloudy")){
            return "Parcialmente nublado";
        }else if (condition.equals("clear")){
            return "Aberto";
        }else if (condition.equals("sunny")||condition.equals("Mostly Sunny")){
            return "Ensolarado";
        }else if (condition.equals("mixed rain and hail")){
            return "Chuva e granizo";
        }else if (condition.equals("hot")){
            return "Quente";
        }else if (condition.equals("isolated thunderstorms")){
            return "Tempestades isoladas";
        }else if (condition.equals("scattered thunderstorms")){
            return "Tempestades dispersas";
        }else if (condition.equals("scattered showers")){
            return "Pancadas de chuva";
        }else if (condition.equals("heavy snow")){
            return "Neve pesada";
        }else if (condition.equals("scattered snow showers")){
            return "Neve dispersas";
        }else if (condition.equals("partly cloudy")){
            return "Parcialmente nublado";
        }else if (condition.equals("thundershowers")){
            return "Trovoadas";
        }else if (condition.equals("snow showers")){
            return "Chuvas de neve";
        }else if (condition.equals("isolated thundershowers")){
            return "Trovoadas isoladas";
        }else if (condition.equals("fair (night)")||condition.equals("mostly clear")||condition.equals("fair (day)")){
            return "Claro (Poucas nuvens)";
        }
        return cond;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

}
