package ml.techcavern.waveline;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ml.techcavern.waveline.utils.InfoUtils;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home);
        TextView weatherTitle = (TextView) findViewById(R.id.weatherTitle);
        final CardView cardView = (CardView) findViewById(R.id.weatherCard);
        weatherTitle.setText("Weather");
        final TextView weatherText = (TextView) findViewById(R.id.weatherText);

        final EditText weatherInput = (EditText) findViewById(R.id.weatherInput);
        weatherInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        weatherText.setText(InfoUtils.getWeather(weatherInput.getText().toString()));
                        int visiblity = weatherInput.getVisibility();
                        weatherInput.setVisibility(View.GONE);
                        weatherInput.setVisibility(visiblity);

                    }catch (Exception e){
                        weatherText.setText("Invalid Zip Code");
                    }
                    return true;
                }
                return false;
            }
        });

    }
}
