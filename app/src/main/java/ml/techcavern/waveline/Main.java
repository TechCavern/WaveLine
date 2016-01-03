package ml.techcavern.waveline;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import ml.techcavern.waveline.objects.InputCard;
import ml.techcavern.waveline.utils.LoadUtils;
import ml.techcavern.waveline.utils.Registry;


public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home);
        Registry.cardList.add(new InputCard(this));
        CardArrayAdapter cardAdapter = new CardArrayAdapter(this, Registry.cardList);
        Registry.listView = (CardListView) this.findViewById(R.id.cards);
        //      Registry.listView.setHasFixedSize(false);
        //    Registry.listView.setLayoutManager(new LinearLayoutManager(this));
        if (Registry.listView != null) {
            Registry.listView.setAdapter(cardAdapter);
        }
        LoadUtils.registerCommands();
    }

}
