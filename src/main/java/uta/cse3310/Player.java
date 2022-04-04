package uta.cse3310;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Player extends Card {
    int Id;
    String Name;
    boolean win = false;
    boolean lose = false;
    Card Cards[];
    int[] CardId = new int[5];
    String LastMessageToPlayer;

    public static HashMap<Integer, Integer> pile = new HashMap<>();

    public Player(int id)  {
        Id = id;
        Name = "not set";

        Cards = new Card[5];
        int set = 0;
        // dealing implementation that makes sure that specific card has not been dealt already
        for (int i = 0; i < 5; i++) {
            set = 0;
            while (set == 0) {
                Cards[i] = new Card();
                Cards[i].suite = Card.Suite.randomSuite();
                Cards[i].value = Card.Value.randomValue();
                if (!pile.containsKey(Cards[i].suite.val+Cards[i].value.val)) {
                    pile.put(Cards[i].suite.val+Cards[i].value.val, 1);
                    CardId[i] = Cards[i].suite.val+Cards[i].value.val;
                    set++;
                } else {
                    continue;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println("Player " + Id + " has " + Cards[i].value + " of " + Cards[i].suite);
        }
        // This dealing implementation is ok for now because 2 people only cover 10 cards in one suite so it wouldn't overflow
        // But this needs to be optimized if more players are going to join

    }

    // given their hand and the index of cards they want to discard
    // draw new cards and discard the index cards for the new ones.
    public Card[] draw(Card[] cards, int[] discard) {
        if (discard.length == 0) {
            return cards;
        }
        System.out.println("length = " +discard.length);
        int set = 0;  
        for (int i = 0; i < discard.length; i++) {
            set = 0;
            while (set == 0) {
                cards[discard[i]-1].suite = Card.Suite.randomSuite();
                cards[discard[i]-1].value = Card.Value.randomValue();
                if (!pile.containsKey(cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val)) {
                    pile.put(cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val, 1);
                    CardId[discard[i]-1] = cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val;
                    set++;
                } else {
                    continue;
                }
            }
        }
        return cards;
    }
    
    public thegameend(){
	System.out.println("The Game is Ended");
	return null;
    }
    
    public void win() {
        this.win = true;
        thegameend();
    }

    public void lose () {
        this.lose = true;
        thegameend();
    }

    public void SetName(String N) {
        Name = N;
        LastMessageToPlayer="Welcome " + N + " to the game.";
    }

    public String asJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
