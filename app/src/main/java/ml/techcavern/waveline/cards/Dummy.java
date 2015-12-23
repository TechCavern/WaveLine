package ml.techcavern.waveline.cards;

import android.app.Activity;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Created by jzhou on 12/22/2015.
 */
public class Dummy {
    public static Card createCard(Activity activity){
        Card card = new Card(activity);
        CardHeader header2 = new CardHeader(activity);
        header2.setTitle("Angry bird 2: ");
        card.setTitle("sample title 2");
        card.addCardHeader(header2);
        return card;
    }
}