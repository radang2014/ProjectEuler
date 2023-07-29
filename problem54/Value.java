public enum Value {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE,
    INVALID;

    public static Value getValue(char c) {
        switch (c) {
            case '2':
                return Value.TWO;
            case '3':
                return Value.THREE;
            case '4':
                return Value.FOUR;
            case '5':
                return Value.FIVE;
            case '6':
                return Value.SIX;
            case '7':
                return Value.SEVEN;
            case '8':
                return Value.EIGHT;
            case '9':
                return Value.NINE;
            case 'T':
                return Value.TEN;
            case 'J':
                return Value.JACK;
            case 'Q':
                return Value.QUEEN;
            case 'K':
                return Value.KING;
            case 'A':
                return Value.ACE;
            default:
                return Value.INVALID;
        }
    }

    public char toChar() {
        switch (this) {
            case TWO:
                return '2';
            case THREE:
                return '3';
            case FOUR:
                return '4';
            case FIVE:
                return '5';
            case SIX:
                return '6';
            case SEVEN:
                return '7';
            case EIGHT:
                return '8';
            case NINE:
                return '9';
            case TEN:
                return 'T';
            case JACK:
                return 'J';
            case QUEEN:
                return 'Q';
            case KING:
                return 'K';
            case ACE:
                return 'A';
            default:
                throw new RuntimeException("Invalid card value.");
        }
    }
}