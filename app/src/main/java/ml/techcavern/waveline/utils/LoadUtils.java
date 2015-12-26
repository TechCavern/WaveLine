package ml.techcavern.waveline.utils;

import android.content.Context;

import java.util.Set;

import it.gmariotti.cardslib.library.internal.Card;
import ml.techcavern.waveline.annots.LCard;

/**
 * Created by jzhou on 12/26/2015.
 */
public class LoadUtils {
    public static void registerCards(Context context) {
        Set<Class<?>> classes = Registry.wavelineReflection.getTypesAnnotatedWith(LCard.class);
        for (Class<?> clss : classes) {
            try {
                Card lCard = (Card) clss.newInstance();
                lCard.setContext(context);
                Registry.cardList.add(lCard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
