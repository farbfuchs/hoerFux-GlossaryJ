package com.github.farbfuchs.hoerfuchs;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GlossaryTest {

    @org.junit.jupiter.api.Test
    void getRandomEntry() {
        // Given
        Glossary glossary = new Glossary(Stream.of(
                GlossaryEntry.create("a-key: This the value"))
        );
        // When
        GlossaryEntry entry = glossary.getRandomEntry();
        // Then
        assertEquals("a-key", entry.getKey(), "Only possible entry should be found");
    }

    @org.junit.jupiter.api.Test
    void findEntryWithSpecialCharacter() {
        Glossary glossary = new Glossary(Stream.of(
                GlossaryEntry.create("a-key: This the value"),
                GlossaryEntry.create("Foo Bar: Words which are used for testing"))
        );
        // When
        GlossaryEntry entry = glossary.findEntry("akey");
        // Then
        assertEquals("a-key", entry.getKey(), "Entry with special char - should be found");
    }

    @org.junit.jupiter.api.Test
    void findEntryIgnoreCase() {
        Glossary glossary = new Glossary(Stream.of(
                GlossaryEntry.create("a-key: This the value"),
                GlossaryEntry.create("FooBar: Words which are used for testing"))
        );
        // When
        GlossaryEntry entry = glossary.findEntry("Foobar");
        // Then
        assertEquals("FooBar", entry.getKey(), "Entry search should ignore uppercase and lowercase");
    }
}