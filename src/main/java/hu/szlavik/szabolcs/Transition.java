package hu.szlavik.szabolcs;

/**
 * Represents a single Turing machine transition.
 * <p>
 * A transition defines what happens when the machine is in a given state and
 * reads a symbol: it specifies the next state to enter, the symbol to write,
 * and the direction to move the tape head.
 * </p>
 */
public class Transition {

    /**
     * The state to switch to after this transition is applied.
     */
    public final String nextState;

    /**
     * The symbol to write to the current tape cell.
     */
    public final char write;

    /**
     * The direction to move the tape head:
     * <ul>
     *   <li>{@code 'L'} for left</li>
     *   <li>{@code 'R'} for right</li>
     * </ul>
     */
    public final char move;

    /**
     * Creates a new transition.
     *
     * @param nextState the next state to enter
     * @param write     the symbol to write on the tape
     * @param move      the move direction, typically {@code 'L'} or {@code 'R'}
     */
    public Transition(String nextState, char write, char move) {
        this.nextState = nextState;
        this.write = write;
        this.move = move;
    }
}
