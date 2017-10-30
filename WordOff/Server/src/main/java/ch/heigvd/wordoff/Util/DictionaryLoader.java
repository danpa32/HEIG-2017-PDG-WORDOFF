package ch.heigvd.wordoff.Util;

import ch.heigvd.wordoff.common.Dictionary;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

@Component
public class DictionaryLoader {
    private Map<String, Dictionary> loadedDico = new TreeMap<>();

    public void loadDictionary(String lang) {
        if (!loadedDico.containsKey(lang)) {
            URL path = getClass().getResource(lang + ".txt");
            // Check if exist
            loadedDico.put(lang, new Dictionary(path.toString()));
        }
    }

    public Dictionary getDico(String lang) {
        loadDictionary(lang);
        return loadedDico.get(lang);
    }
}