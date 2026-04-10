package hu.szlavik.szabolcs;

import java.util.LinkedList;

/**
 * Represents a simple Turing machine simulator.
 * <p>
 * The machine maintains a tape, a head position, a current state, and a
 * transition table used to determine how the machine evolves step by step.
 * </p>
 */
public class TuringMachine {
    LinkedList<Character> tape = new LinkedList<>();
    int head = 0;
    String state = "q0"; // start state
    final TransitionTable transitions;

    /**
     * Creates a Turing machine with the given input tape and transition table.
     *
     * @param input       the initial tape contents
     * @param transitions the transition table used to execute machine steps
     */
    public TuringMachine(String input, TransitionTable transitions) {
        for (char c : input.toCharArray()) tape.add(c);
        this.transitions = transitions;
    }

    /**
     * Executes one transition of the Turing machine.
     * <p>
     * The machine reads the current tape symbol, looks up the matching transition,
     * writes the new symbol, moves the head, and updates the current state.
     * If no valid transition exists or the machine is already halted, the method
     * prints a message and returns without changing the configuration.
     * </p>
     */
    public void step() {
        char read = tape.get(head);
        Transition t = transitions.get(state, read);

        if (t == null || state.equals("ACCEPT") || state.equals("REJECT") || state.equals("HALT")) {
            System.out.println("Machine halted.");
            //state = "HALT";
            return;
        }

        // Write
        tape.set(head, t.write);

        // Move
        if (t.move == 'L') moveLeft();
        else moveRight();

        // Next state
        state = t.nextState;
    }

    /**
     * Moves the head one position to the left.
     * <p>
     * If the head is already at the first cell, a blank symbol is added to the
     * beginning of the tape.
     * </p>
     */
    private void moveLeft() {
        if (head == 0) tape.addFirst('_');
        else head--;
    }

    /**
     * Moves the head one position to the right.
     * <p>
     * If the head moves past the current end of the tape, a blank symbol is
     * appended automatically.
     * </p>
     */
    private void moveRight() {
        head++;
        if (head == tape.size()) tape.addLast('_');
    }

    /**
     * Prints the current tape contents and highlights the cell under the head.
     * This method is useful for debugging and inspection in the console.
     */
    public void printTape() {
        for (int i = 0; i < tape.size(); i++) {
            if (i == head) System.out.print("[" + tape.get(i) + "]");
            else System.out.print(" " + tape.get(i) + " ");
        }
        System.out.println("   State: " + state);
    }

    /**
     * Returns the current head position.
     *
     * @return the zero-based index of the head on the tape
     */
    public int getHeadPosition() {
        return head;
    }

    /**
     * Returns the current number of tape cells.
     *
     * @return the tape size
     */
    public int getTapeSize() {
        return tape.size();
    }

    /**
     * Returns the symbol stored at the specified tape index.
     *
     * @param index the tape cell index
     * @return the symbol at the given index
     */
    public char getTapeSymbol(int index) {
        return tape.get(index);
    }

    /**
     * Checks whether the machine is in a halted state.
     *
     * @return {@code true} if the machine is in {@code ACCEPT}, {@code REJECT},
     * or {@code HALT}; otherwise {@code false}
     */
    public boolean isHalted() {
        return state.equals("ACCEPT") || state.equals("REJECT") || state.equals("HALT");
    }

    /**
     * Returns the current state of the machine.
     *
     * @return the current state name
     */
    public String getState() {
        return state;
    }

}
