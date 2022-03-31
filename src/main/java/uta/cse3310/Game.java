package uta.cse3310;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uta.cse3310.UserEvent.UserEventType;

// mvn exec:java -Dexec.mainClass=uta.cse3310.WebPoker

public class Game {

    ArrayList<Player> players = new ArrayList<>();
    int turn = 0; // player ID that has the current turn
    int round_num = 1;

    public String exportStateAsJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(int playerid) {
        // given it's player number, this method
        // deletes the player from the players array
        // and does whatever else is needed to remove
        // the player from the game.
        players.remove(playerid);
    }

    public void processMessage(String msg) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // take the string we just received, and turn it into a user event
        UserEvent event = gson.fromJson(msg, UserEvent.class);

        if (event.event == UserEventType.NAME) {
            players.get(event.playerID).SetName(event.name);
        }
        else if (event.event == UserEventType.FOLD) {
            // if the player folds, the other player wins
        } 
        else if (event.event == UserEventType.STAND) {
            // if the player stands, they get a choice which cards to discard and draw new ones for
            // the message should have sent the indexes of cards to be discarded and the player that sent the message
        }
        else if (event.event == UserEventType.BET) {
            // not implemented for iteration 1 so there is only folding and standing for now
            // this does not count as a turn
        }
        turn++;
        if (turn > players.size() - 1) {
            turn = 0;
            round_num += 1;
        }

    }

    

    public boolean update() {

        // this function is called on a periodic basis (once a second) by a timer
        // it is to allow time based situations to be handled in the game
        // if the game state is changed, it returns a true.
        return false;
        // expecting that returning a true will trigger a send of the game
        // state to everyone

    }

    public Game() {
        System.out.println("creating a Game Object");
    }
    

}
/*

    TODO Implement how a player chooses and discards the cards in the draw round
    TODO Implement how a player wins
    TODO Integrate the Hand class's is_better_than to determine winner at showdown

*/
