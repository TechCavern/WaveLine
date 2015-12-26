package ml.techcavern.waveline.cards;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import ml.techcavern.waveline.R;
import ml.techcavern.waveline.utils.InfoUtils;

/**
 * Created by jzhou on 12/22/2015.
 */


public class Weather extends Card {
    protected TextView weatherText;
    protected EditText weatherInput;
    protected int action_find;

    public Weather(Context context) {
        super(context, R.layout.weather_card);
        init();
    }

    public void init() {
        CardHeader header = new CardHeader(getContext());
        header.setTitle("Weather ");
        this.addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        weatherText = (TextView) parent.findViewById(R.id.weatherText);

        weatherInput = (EditText) parent.findViewById(R.id.weatherInput);
        action_find = parent.getResources().getInteger(R.integer.action_find);
        weatherInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == action_find || actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        weatherText.setText(InfoUtils.getWeather(weatherInput.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        weatherText.setText("Invalid Zip Code");
                    }
                    return true;
                }
                return false;
            }
        });
    }
}