public class Card {
    public Suit suit;
    public Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Card(String s) {
        if (s.length() != 2) {
            throw new RuntimeException("Card must be created from a 2-character string");
        }
        value = Value.getValue(s.charAt(0));
        suit = Suit.getSuit(s.charAt(1));
    }

    public String toString() {
        return Character.toString(value.toChar()) + Character.toString(suit.toChar());
    }
}