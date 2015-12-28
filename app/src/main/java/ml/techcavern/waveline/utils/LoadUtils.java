package ml.techcavern.waveline.utils;

import android.content.Context;

import ml.techcavern.waveline.cards.inputCard;

/**
 * Created by jzhou on 12/26/2015.
 */
public class LoadUtils {
    public static void registerCards(Context context) {
        Registry.cardList.add(new inputCard(context));
    }
}
