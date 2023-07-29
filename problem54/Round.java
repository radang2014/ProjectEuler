import java.util.*;

public class Round {
    public Hand p1_hand;
    public Hand p2_hand;

    public Round(Hand p1_hand, Hand p2_hand) {
        this.p1_hand = p1_hand;
        this.p2_hand = p2_hand;
    }

    public Round(String s) {
        String [] card_strs = s.split(" ");
        if (card_strs.length != 10) {
            throw new RuntimeException("String input to Round constructor must have 10 tokens.");
        }
        String [] p1_card_strs = Arrays.copyOfRange(card_strs, 0, 5);
        String [] p2_card_strs = Arrays.copyOfRange(card_strs, 5, 10);

        p1_hand = new Hand(String.join(" ", p1_card_strs));
        p2_hand = new Hand(String.join(" ", p2_card_strs));
    }

    public String toString() {
        return p1_hand.toString() + " " + p2_hand.toString();
    }

    public Player getWinner() {
        p1_hand.sort();
        p2_hand.sort();

        HandRank p1_rank = HandRank.getHandRank(p1_hand);
        HandRank p2_rank = HandRank.getHandRank(p2_hand);

        int rank_cmp = p1_rank.compareTo(p2_rank);
        if (rank_cmp > 0) {
            return Player.ONE;
        } else if (rank_cmp < 0) {
            return Player.TWO;
        }

        return p1_rank.breakTie(p1_hand, p2_hand);
    }
}