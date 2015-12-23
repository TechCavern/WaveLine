package ml.techcavern.waveline;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import ml.techcavern.waveline.cards.Dummy;
import ml.techcavern.waveline.cards.Weather;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(Weather.createCard(this));
        cards.add(Dummy.createCard(this));
        CardArrayAdapter cardAdapter = new CardArrayAdapter(this, cards);
        CardListView listView = (CardListView) this.findViewById(R.id.cards);
        if (listView != null) {
            listView.setAdapter(cardAdapter);
        }

    }

}
