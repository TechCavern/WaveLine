package ml.techcavern.waveline;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
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
        CardArrayRecyclerViewAdapter cardAdapter = new CardArrayRecyclerViewAdapter(this, Registry.cardList);
        CardRecyclerView listView = (CardRecyclerView) this.findViewById(R.id.cards);
        listView.setHasFixedSize(false);
        listView.setLayoutManager(new LinearLayoutManager(this));
        if (listView != null) {
            listView.setAdapter(cardAdapter);
        }
        LoadUtils.registerCommands();
    }

}
