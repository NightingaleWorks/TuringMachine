package hu.szlavik.szabolcs;

import java.io.*;
import java.util.*;

/**
 * Stores and manages transitions for a Turing machine.
 * <p>
 * The table is loaded from a text file where each non-empty, non-comment line
 * describes a transition in a whitespace-separated format.
 * </p>
 * <p>
 * Expected line format:
 * {@code currentState readSymbol -> writeSymbol moveDirection nextState}
 * </p>
 * Example:
 * {@code q0 1 -> 0 R q1}
 */
public class TransitionTable {
    private final Map<String, Transition> table = new HashMap<>();

    /**
     * Creates a transition table by loading all transitions from the specified file.
     *
     * @param filename the path to the file containing transition definitions
     * @throws IOException if an I/O error occurs while reading the file
     */
    public TransitionTable(String filename) throws IOException {
        load(filename);
    }

    /**
     * Loads transition definitions from the given file and stores them in memory.
     * <p>
     * Lines that are empty or start with {@code #} are ignored.
     * Each remaining line is expected to contain a transition definition
     * separated by whitespace.
     * </p>
     *
     * @param filename the path to the file containing transition definitions
     * @throws IOException if an I/O error occurs while reading the file
     */
    private void load(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Skip empty lines or comments
                if (line.isEmpty() || line.startsWith("#")) continue;

                // Format: q0 1 -> 0 R q1
                String[] parts = line.split("\\s+");

                String currentState = parts[0];
                char readSymbol = parts[1].charAt(0);
                char writeSymbol = parts[3].charAt(0);
                char move = parts[4].charAt(0);
                String nextState = parts[5];

                String key = currentState + ":" + readSymbol;
                table.put(key, new Transition(nextState, writeSymbol, move));
            }
        }
    }

    /**
     * Returns the transition associated with the given state and read symbol.
     *
     * @param state the current state
     * @param read  the symbol read from the tape
     * @return the matching transition, or {@code null} if no transition exists
     */
    public Transition get(String state, char read) {
        return table.get(state + ":" + read);
    }
}
