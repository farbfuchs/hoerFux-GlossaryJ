package com.github.farbfuchs.hoerfuchs;

import java.io.PrintStream;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class Glossary {

    private final Random random = new Random();
    private Map<String, GlossaryEntry> map = null;

    public Glossary(Stream<GlossaryEntry> entryStream) {
        map = entryStream.collect(toMap(GlossaryEntry::getNormalizedKey, Function.identity()));
    }

    public GlossaryEntry getRandomEntry() {
        final int randomIndex = random.nextInt(map.size());
        return map.values().stream().skip(randomIndex).findFirst().get();
    }

    /**
     * Find an entry in the glossary. Whitespace and special characters are removed to find the key in the glossary.
     *
     * @param searchKey The key to serach for.
     */
    public GlossaryEntry findEntry(String searchKey) {
        return map.get(GlossaryEntry.normalizeKey(searchKey));
    }

    /**
     * Print all keys as provided in the glossary. Keys are sorted alphabetically by normalized name.
     *
     * @param out The stream to print to.
     */
    public void printAllKeys(PrintStream out) {
        map.keySet().stream().sorted().forEach(key -> out.println(map.get(key).getKey()));
    }
}
