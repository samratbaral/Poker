package uta.cse3310;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Player extends Card {
    int Id;
    String Name;
    Card Cards[];
    String LastMessageToPlayer;

    public static HashMap<Card.Suite, Integer> suitePile = new HashMap<>();
    public static HashMap<Card.Value, Integer> valuePile = new HashMap<>();

    public Player(int id)  {
        Id = id;
        Name = "not set";
        // there is a lot smarter ways to do this,
        // but at least this is obvious
        Cards = new Card[5];
        int set = 0;
        // dealing implementation that makes sure that specific card has not been dealt already
        for (int i = 0; i < 5; i++) {
            set = 0;
            while (set == 0) {
                Cards[i] = new Card();
                Cards[i].suite = Card.Suite.randomSuite();
                Cards[i].value = Card.Value.randomValue();
                set++;
            }
            
            
        }
        // This dealing implementation is ok for now because 2 people only cover 10 cards in one suite so it wouldn't overflow
        // But this needs to be optimized if more players are going to join

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
