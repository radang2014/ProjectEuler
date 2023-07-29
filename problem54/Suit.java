public enum Suit {
    SPADES,
    HEARTS,
    DIAMONDS,
    CLUBS,
    INVALID;

    public static Suit getSuit(char c) {
        switch (c) {
            case 'S':
                return Suit.SPADES;
            case 'H':
                return Suit.HEARTS;
            case 'D':
                return Suit.DIAMONDS;
            case 'C':
                return Suit.CLUBS;
            default:
                return Suit.INVALID;
        }
    }

    public char toChar() {
        switch (this) {
            case SPADES:
                return 'S';
            case HEARTS:
                return 'H';
            case DIAMONDS:
                return 'D';
            case CLUBS:
                return 'C';
            default:
                throw new RuntimeException("Suit not valid.");
        }
    }
}