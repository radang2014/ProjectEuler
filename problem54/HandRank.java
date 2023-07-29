import java.util.*;

public enum HandRank {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIRS,
    THREE_OF_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH,
    INVALID;

    public static HandRank getHandRank(Hand h) {
        if (isRoyalFlush(h)) {
            return ROYAL_FLUSH;
        } else if (isStraightFlush(h)) {
            return STRAIGHT_FLUSH;
        } else if (isFourOfKind(h)) {
            return FOUR_OF_KIND;
        } else if (isFullHouse(h)) {
            return FULL_HOUSE;
        } else if (isFlush(h)) {
            return FLUSH;
        } else if (isStraight(h)) {
            return STRAIGHT;
        } else if (isThreeOfKind(h)) {
            return THREE_OF_KIND;
        } else if (isTwoPairs(h)) {
            return TWO_PAIRS;
        } else if (isOnePair(h)) {
            return ONE_PAIR;
        } else {
            return HIGH_CARD;
        }
    }

    private static boolean isRoyalFlush(Hand h) {
        return allSameSuit(h) && h.cards[0].value == Value.TEN && consecutiveValues(h);
    }

    private static boolean isStraightFlush(Hand h) {
        return allSameSuit(h) && consecutiveValues(h);
    }

    private static boolean isFourOfKind(Hand h) {
        return isNumOfKind(h, 4, 1);
    }

    private static boolean isFullHouse(Hand h) {
        return isNumOfKind(h, 3, 1) && isNumOfKind(h, 2, 1);
    }

    private static boolean isFlush(Hand h) {
        return allSameSuit(h);
    }

    private static boolean isStraight(Hand h) {
        return consecutiveValues(h);
    }

    private static boolean isThreeOfKind(Hand h) {
        return isNumOfKind(h, 3, 1);
    }

    private static boolean isTwoPairs(Hand h) {
        return isNumOfKind(h, 2, 2);
    }

    private static boolean isOnePair(Hand h) {
        return isNumOfKind(h, 2, 1);
    }

    private static boolean allSameSuit(Hand h) {
        Suit suit = h.cards[0].suit;
        for (int i = 0; i < 5; i++) {
            if (h.cards[i].suit != suit) {
                return false;
            }
        }
        return true;
    }

    private static boolean consecutiveValues(Hand h) {
        for (int i = 0; i < 4; i++) {
            if (h.cards[i].value.compareTo(h.cards[i+1].value) != -1) {
                return false;
            }
        }
        return true;
    }

    // num: The number we are testing, i.e. if `h` contains `num` of a kind.
    // numNums: The number of `num`s of a kind needed
    private static boolean isNumOfKind(Hand h, int num, int numNums) {
        return numOfKindGetValue(h, num, numNums) != null;
    }

    public Player breakTie(Hand h1, Hand h2) {
        Value value1;
        Value value2;
        Value [] values1;
        Value [] values2;
        Value [] remainingCards1;
        Value [] remainingCards2;
        switch (this) {
            case ROYAL_FLUSH:
                return Player.TWO;
            case STRAIGHT_FLUSH:
            case STRAIGHT:
                if (h1.cards[0].value.compareTo(h2.cards[0].value) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case FOUR_OF_KIND:
                // Compare values making up four of kind
                value1 = numOfKindGetValue(h1, 4, 1);
                value2 = numOfKindGetValue(h2, 4, 1);
                if (value1.compareTo(value2) > 0) {
                    return Player.ONE;
                } else if (value1.compareTo(value2) < 0) {
                    return Player.TWO;
                }

                // Compare value of remaining card 
                remainingCards1 = getValuesExcluding(h1, new Value[] {value1});
                remainingCards2 = getValuesExcluding(h2, new Value[] {value2});

                if (remainingCards1.length != 1 || remainingCards2.length != 1) {
                    throw new RuntimeException("There should only be one remaining card other than the four of a kind.");
                }

                if (remainingCards1[0].compareTo(remainingCards2[0]) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case FULL_HOUSE:
                // Compare values making up three of kind 
                value1 = numOfKindGetValue(h1, 3, 1);
                value2 = numOfKindGetValue(h2, 3, 1);
                if (value1.compareTo(value2) > 0) {
                    return Player.ONE;
                } else if (value1.compareTo(value2) < 0) {
                    return Player.TWO;
                }

                // Compare values making up two of kind 
                value1 = numOfKindGetValue(h1, 2, 1);
                value2 = numOfKindGetValue(h2, 2, 1);
                if (value1.compareTo(value2) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case FLUSH:
            case HIGH_CARD:
                values1 = getValuesFromHand(h1);
                values2 = getValuesFromHand(h2);
                if (compareValuesDescending(values1, values2) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case THREE_OF_KIND:
                // Compare values making up three of kind
                value1 = numOfKindGetValue(h1, 3, 1);
                value2 = numOfKindGetValue(h2, 3, 1);
                if (value1.compareTo(value2) > 0) {
                    return Player.ONE;
                } else if (value1.compareTo(value2) < 0) {
                    return Player.TWO;
                }

                // Compare value of remaining cards 
                remainingCards1 = getValuesExcluding(h1, new Value[] {value1});
                remainingCards2 = getValuesExcluding(h2, new Value[] {value2});

                if (remainingCards1.length != 2 || remainingCards2.length != 2) {
                    throw new RuntimeException("There should be two remaining cards other than the three of a kind.");
                }

                if (compareValuesDescending(remainingCards1, remainingCards2) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case TWO_PAIRS:
                // Compare values making up first pair (higher values)
                Value value1pair1 = numOfKindGetValue(h1, 2, 2);
                Value value2pair1 = numOfKindGetValue(h2, 2, 2);
                if (value1pair1.compareTo(value2pair1) > 0) {
                    return Player.ONE;
                } else if (value1pair1.compareTo(value2pair1) < 0) {
                    return Player.TWO;
                }

                // Compare values making up second pair (lower values)
                Value value1pair2 = numOfKindGetValue(h1, 2, 1);
                Value value2pair2 = numOfKindGetValue(h2, 2, 1);
                if (value1pair2.compareTo(value2pair2) > 0) {
                    return Player.ONE;
                } else if (value1pair2.compareTo(value2pair2) < 0) {
                    return Player.TWO;
                }

                // Compare value of remaining card 
                remainingCards1 = getValuesExcluding(h1, new Value[] {value1pair1, value1pair2});
                remainingCards2 = getValuesExcluding(h2, new Value[] {value2pair1, value2pair2});

                if (remainingCards1.length != 1 || remainingCards2.length != 1) {
                    throw new RuntimeException("There should only be one remaining card other than the four of a kind.");
                }

                if (remainingCards1[0].compareTo(remainingCards2[0]) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
            case ONE_PAIR:
                // Compare values making up pair
                value1 = numOfKindGetValue(h1, 2, 1);
                value2 = numOfKindGetValue(h2, 2, 1);
                if (value1.compareTo(value2) > 0) {
                    return Player.ONE;
                } else if (value1.compareTo(value2) < 0) {
                    return Player.TWO;
                }

                // Compare value of remaining cards 
                remainingCards1 = getValuesExcluding(h1, new Value[] {value1});
                remainingCards2 = getValuesExcluding(h2, new Value[] {value2});

                if (remainingCards1.length != 3 || remainingCards2.length != 3) {
                    throw new RuntimeException("There should be no more than three remaining cards other than the pair.");
                }

                if (compareValuesDescending(remainingCards1, remainingCards2) > 0) {
                    return Player.ONE;
                }
                return Player.TWO;
        }
        return Player.TWO;
    }

    private static Value numOfKindGetValue(Hand h, int num, int numNums) {
        Value [] values = Value.values();
        int [] counts = new int[values.length];

        // initialize to 0
        for (int i = 0; i < counts.length; i++) {
            counts[i] = 0;
        }

        for (Card c : h.cards) {
            counts[c.value.ordinal()]++;
        }

        int numCount = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] == num) {
                numCount++;
                if (numCount == numNums) {
                    return values[i];
                }
            }
        }
        return null;
    }

    private static Value [] getValuesExcluding(Hand h, Value [] exclude) {
        List<Value> result = new ArrayList<>();
        for (Card c : h.cards) {
            // Check if c's value is in exclude 
            boolean exclude_curr = false;
            for (Value v : exclude) {
                if (c.value == v) {
                    exclude_curr = true;
                }
            }

            if (!exclude_curr) {
                result.add(c.value);
            }
        }
        return result.toArray(new Value[0]);
    }

    private static Value [] getValuesFromHand(Hand h) {
        Value [] result = new Value[5];
        for (int i = 0; i < 5; i++) {
            result[i] = h.cards[i].value;
        }
        return result;
    }

    private static int compareValuesDescending(Value [] values1, Value [] values2) {
        if (values1.length != values2.length) {
            throw new RuntimeException("Value arrays inputted to `compareValuesDescending` must be same length.");
        }
        for (int i = values1.length - 1; i >= 0; i--) {
            int valCompareTo = values1[i].compareTo(values2[i]);
            if (valCompareTo != 0) {
                return valCompareTo;
            }
        }
        return 0;
    }
}