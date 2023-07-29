import java.util.*;

public class Hand {
    public Card [] cards;

    public Hand(Card c1, Card c2, Card c3, Card c4, Card c5) {
        cards = new Card[5];
        cards[0] = c1;
        cards[1] = c2;
        cards[2] = c3;
        cards[3] = c4;
        cards[4] = c5;
    }

    public Hand(String s) {
        cards = new Card[5];

        String [] card_strs = s.split(" ");
        if (card_strs.length != 5) {
            throw new RuntimeException("String input to Hand constructor must have five tokens");
        }
        for (int i = 0; i < 5; i++) {
            cards[i] = new Card(card_strs[i]);
        }
    }

    public String toString() {
        String [] card_strs = new String[5];
        for (int i = 0; i < 5; i++) {
            card_strs[i] = cards[i].toString();
        }
        return String.join(" ", card_strs);
    }

    public void sort() {
        Arrays.sort(cards, new Comparator<Card>() {
            public int compare(Card c1, Card c2) {
                int valCompareTo = c1.value.compareTo(c2.value);
                if (valCompareTo != 0) {
                    return valCompareTo;
                }
                return c1.suit.compareTo(c2.suit);
            }
        });
    }
}