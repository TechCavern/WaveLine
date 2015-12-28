package ml.techcavern.waveline.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import ml.techcavern.waveline.R;

/**
 * Created by jzhou on 12/22/2015.
 */


public class outputCard extends Card {
    protected TextView geoText;
    protected String outputText;
    protected String title;

    public outputCard(Context context, String Title, String OutputText) {
        super(context, R.layout.oneline_output_card);
        this.outputText = OutputText;
        this.title = Title;
        init();
    }

    public void init() {
        CardHeader header = new CardHeader(getContext());
        header.setTitle(this.title);
        this.addCardHeader(header);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        geoText = (TextView) parent.findViewById(R.id.onelineText);
        geoText.setText(this.outputText);

        /**
        geoInput = (EditText) parent.findViewById(R.id.onelineInput);
        geoInput.setHint("IP/Domain");
        geoInput.setInputType(InputType.TYPE_CLASS_TEXT);
        action_find = parent.getResources().getInteger(R.integer.action_find);
        geoInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == action_find || actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        String IP = GeneralUtils.getIP(geoInput.getText().toString());
                        if (IP == null) {
                        } else {
                            JsonObject objectJson = GeneralUtils.getJsonObject("http://ip-api.com/json/" + IP);
                            List<String> results = new ArrayList<>();
                            if (objectJson.get("status").getAsString().equalsIgnoreCase("success")) {
                                results.add(objectJson.get("city").getAsString());
                                results.add(objectJson.get("regionName").getAsString());
                                results.add(objectJson.get("country").getAsString());
                                results.add(objectJson.get("zip").getAsString());
                                results.add(objectJson.get("isp").getAsString());
                                results.add(objectJson.get("timezone").getAsString());
                                String message = "";
                                for (String res : results) {
                                    if (!res.isEmpty()) {
                                        if (message.isEmpty()) {
                                            message = res;
                                        } else {
                                            message += ", " + res;
                                        }
                                    }
                                }
                                if (!message.isEmpty()) {
                                    geoText.setText("[" + IP + "] " + message);
                                } else {
                                    geoText.setText("Unable to determine location (or you entered an invalid ip)");
                                }
                            } else {
                                geoText.setText("Unable to determine location (or you entered an invalid ip)");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        geoText.setText("Unable to determine location (or you entered an invalid ip)");
                    }
                    return true;
                }
                return false;
            }
        });
         **/
    }
}