package ml.techcavern.waveline.utils;


import org.reflections.Reflections;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;

public class Registry {
    public static final String VERSION = "WaveLine 0.1-dev";
    public static final Reflections wavelineReflection = new Reflections("ml.techcavern.waveline");
    public static final ArrayList<Card> cardList = new ArrayList<Card>();
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36";
}