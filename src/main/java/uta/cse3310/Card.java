package uta.cse3310;

import java.util.*;

public class Card implements Comparable<Card> {
    public enum Suite {
        HEARTS(10), CLUBS(50), DIAMONDS(100), SPADES(150);

        private static final List<Suite> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Suite randomSuite()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }

        public final int val;
        private Suite (int val) {
            this.val = val;
        }
    }

    public enum Value {
        ACE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),
        TEN(10),JACK(11),QUEEN(12),KING(13); 

        private static final List<Value> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Value randomValue()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
            }

        public final int val;
        private Value (int val)
        {
            this.val = val;
        }
    }

    public Suite suite;
    public Value value;

    // make a hashmap that gives a unique card each time card is instantiated

    public Card() {
    }

    @Override
    public int compareTo(Card o) {
      if (this.value.val < o.value.val)
      {
         return -1;
      }
      else if (this.value.val > o.value.val)
      {
         return 1;
      }
      return 0;
   }

}
