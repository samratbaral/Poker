package uta.cse3310;

public class Card implements Comparable<Card> {
    public enum Suite {
        HEARTS, CLUBS, DIAMONDS, SPADES
    }

    public enum Value {
        ACE(1),TWO(2),THREE(3),FOUR(4),FIVE(5),SIX(6),SEVEN(7),EIGHT(8),NINE(9),
       TEN(10),JACK(11),QUEEN(12),KING(13); 

       public final int val;
       private Value (int val)
       {
         this.val = val;
       }
    }

    public Suite suite;
    public Value value;

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
