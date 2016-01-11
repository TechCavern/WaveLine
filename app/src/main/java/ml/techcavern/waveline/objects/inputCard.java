package ml.techcavern.waveline.objects;

import android.content.Context;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import ml.techcavern.waveline.R;
import ml.techcavern.waveline.utils.GeneralUtils;
import ml.techcavern.waveline.utils.Registry;

/**
 * Created by jzhou on 12/22/2015.
 */


public class InputCard extends Card {
    protected EditText inputText;
    protected int action_find;

    public InputCard(Context context) {
        super(context, R.layout.oneline_input_card);
        init();
    }

    public void init() {
        CardHeader header = new CardHeader(getContext());
        header.setTitle("Insert Command ");
        this.addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(final ViewGroup parent, View view) {
        inputText = (EditText) parent.findViewById(R.id.onelineInput);
        inputText.setHint("Command");
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        action_find = parent.getResources().getInteger(R.integer.action_find);
        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == action_find || actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        String[] message = StringUtils.split(inputText.getText().toString());
                        Command cmd = Registry.commands.get(message[0]);
                        try {
                            cmd.onCommand(view.getContext(), message);

                        } catch (Exception e) {
                            e.printStackTrace();
                            GeneralUtils.addCard(new OutputCard(view.getContext(), "An Error Occurred", "Please ensure you are following the syntax: " + cmd.getSyntax()));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GeneralUtils.addCard(new OutputCard(view.getContext(), "Invalid Command", "Invalid Command"));

                        e.printStackTrace();
                    }
                    inputText.setText("");
                    Registry.listView.setVisibility(View.GONE);
                    Registry.listView.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });
    }
}