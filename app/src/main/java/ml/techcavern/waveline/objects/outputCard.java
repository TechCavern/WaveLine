package ml.techcavern.waveline.objects;

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


public class OutputCard extends Card {
    protected TextView geoText;
    protected String outputText;
    protected String title;

    public OutputCard(Context context, String Title, String OutputText) {
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
    }
}