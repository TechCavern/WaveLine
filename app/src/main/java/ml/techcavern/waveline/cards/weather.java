package ml.techcavern.waveline.cards;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import ml.techcavern.waveline.R;
import ml.techcavern.waveline.utils.InfoUtils;

/**
 * Created by jzhou on 12/22/2015.
 */
public class Weather {
    public static Card createCard(Activity activity){
        Card card = new Card(activity);
        CardHeader header = new CardHeader(activity);
        header.setTitle("Weather ");
        card.addCardHeader(header);
        card.setInnerLayout(R.layout.weather);
        card.setType(1);
        return card;
    }
    public static void initializeCard(Activity activity){
        final TextView weatherText = (TextView) activity.findViewById(R.id.weatherText);

        final EditText weatherInput = (EditText) activity.findViewById(R.id.weatherInput);
        final int action_find = activity.getResources().getInteger(R.integer.action_find);
        weatherInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if(actionId == action_find || actionId == EditorInfo.IME_ACTION_DONE){
                    try {
                        weatherText.setText(InfoUtils.getWeather(weatherInput.getText().toString()));
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
