package com.github.farbfuchs.hoerfuchs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.lang.System.out;

public class Main {

    public static final String COMMAND_PREFIX = "/";
    private static final Map<String, Consumer<Glossary>> commands = new HashMap<>();

    static {
        commands.put("/rand", glossary ->
                out.println(glossary.getRandomEntry()));
        commands.put("/keys", glossary ->
                glossary.printAllKeys(out);
        );
        commands.put("/help", glossary ->
                out.println("All commands: " + commands.keySet()));
        commands.put("/quit", glossary -> {
            out.println("Bye!");
            System.exit(0);
        });
    }

    public static void main(String[] args) throws IOException {
        out.println("Input arguments:" + Arrays.stream(args).toList());
        if (args.length != 1) {
            out.println("Start with filename as parameter");
            System.exit(1);
        }
        // Read file
        final Path path = Paths.get(args[0]);
        final List<String> lines = Files.readAllLines(path);

        // Create Glossary
        final Stream<GlossaryEntry> entryStream = lines.stream().map(line -> GlossaryEntry.create(line));
        final Glossary glossary = new Glossary(entryStream);

        // Write a random entry
        out.println("Random Entry: ");
        commands.get("/rand").accept(glossary);

        // Read input
        Scanner scanner = new Scanner(System.in);
        while (true) {
            out.println("Enter a word or a command, e.g. /help >");
            String input = scanner.nextLine();
            if (input.startsWith(COMMAND_PREFIX)) {
                runCommand(input, glossary);
            } else {
                findEntry(input, glossary);
            }
        }
    }

    private static void runCommand(String commandInput, Glossary glossary) {
        final Consumer<Glossary> command = commands.get(commandInput);
        if (command == null) {
            out.println("Command not found: " + commandInput + "\nUse one of: " + commands.keySet());
            return;
        }
        command.accept(glossary);
    }

    private static void findEntry(String input, Glossary glossary) {
        final GlossaryEntry entry = glossary.findEntry(input);
        if (entry == null) {
            out.println("Entry not found: " + input);
            return;
        }
        out.println(entry);
    }
}