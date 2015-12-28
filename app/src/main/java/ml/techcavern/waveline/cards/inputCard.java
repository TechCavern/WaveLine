package ml.techcavern.waveline.cards;

import android.content.Context;
import android.text.InputType;
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
import ml.techcavern.waveline.utils.Registry;

/**
 * Created by jzhou on 12/22/2015.
 */


public class inputCard extends Card {
    protected EditText weatherInput;
    protected int action_find;

    public inputCard(Context context) {
        super(context, R.layout.oneline_input_card);
        init();
    }

    public void init() {
        CardHeader header = new CardHeader(getContext());
        header.setTitle("inputCard ");
        this.addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(final ViewGroup parent, View view) {
        weatherInput = (EditText) parent.findViewById(R.id.onelineInput);
        weatherInput.setHint("Zip Code");
        weatherInput.setInputType(InputType.TYPE_CLASS_TEXT);
        action_find = parent.getResources().getInteger(R.integer.action_find);
        weatherInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == action_find || actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        Registry.cardList.add(new outputCard(parent.getContext(), "Weather", InfoUtils.getWeather(weatherInput.getText().toString())));
                        parent.setVisibility(View.GONE);
                        parent.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
        /**
         weatherText = (TextView) parent.findViewById(R.id.onelineText);

         **/
    }
}