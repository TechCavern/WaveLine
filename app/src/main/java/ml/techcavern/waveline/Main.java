package ml.techcavern.waveline;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import ml.techcavern.waveline.cards.Dummy;
import ml.techcavern.waveline.cards.Weather;
import ml.techcavern.waveline.utils.InfoUtils;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(Weather.createCard(this));
        cards.add(Dummy.createCard(this));
        CardArrayAdapter cardAdapter = new CardArrayAdapter(this, cards);
        cardAdapter.setInnerViewTypeCount(2);
        CardListView listView = (CardListView) this.findViewById(R.id.cards);
        if (listView != null) {
            listView.setAdapter(cardAdapter);
        }
        Weather.initializeCard(this);
    }
}
