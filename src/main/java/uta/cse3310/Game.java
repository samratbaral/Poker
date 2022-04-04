package uta.cse3310;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uta.cse3310.UserEvent.UserEventType;
import uta.cse3310.*;

// mvn exec:java -Dexec.mainClass=uta.cse3310.WebPoker
public Timeforplayer extends TimerTask{
    int i = 0;
    public void runtime()
    {
        System.out.println("Time Count: (" + i + ")");
		i++;
		if(i=10)
			{
				synchronized(Game.eachplayertime)
				{
					Game.eachplayertime.notify();
				}
			}
		System.out.println("10 Sec Countdown Over");
    }
}
public class Game {

    ArrayList<Player> players = new ArrayList<>();
    int turn = 0; // player ID that has the current turn
    int round_num = 1;
    int winner_id = -1;
     public static Game eachplayertime;
        eachplayertime = new Game();
        Timer time = new Timer();
        TimerTask task = new Time Timerforplayer;
    
        timer.schedule(task,10000); // 10 sec
	
     @Override
     public void runtime(){
        System.out.println("You Have Only 10 Sec Countdown");
     }
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
        System.out.println(playerid +" has been removed");
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).Id!=playerid) {
                players.get(i).win = true;
                winner_id = i;
                System.out.println("winner id set to " + winner_id);
            } else {
                players.get(i).lose = true;
            }
        }
        players.remove(playerid);
        
    }

    public void processMessage(String msg) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // take the string we just received, and turn it into a user event
        UserEvent event = gson.fromJson(msg, UserEvent.class);
        //int count_losses = 0;
        int loop = 0;
        while(loop != 1 )
        {
        synchronized(eachplayertime)
        {
         eachplayertime.wait();
        }
        if (event.event == UserEventType.NAME) {
            players.get(event.playerID).SetName(event.name);
            timer.cancel(); 
        }
        else if (event.event == UserEventType.FOLD) {
            players.get(event.playerID).lose();
            for (int i = 0; i < players.size(); i++) {
                if (i!=event.playerID) {
                    players.get(i).win=true;
                    winner_id=i;
                    timer.cancel(); 
                    return;
                }
            }
            // if the player folds, the other player wins
        } 
        else if (event.event == UserEventType.DRAW) {
            /*
            System.out.println("Before = ");
            for (int k = 0; k < 5; k++) {
                System.out.println(players.get(event.playerID).CardId[k]);
            }
            */
            players.get(event.playerID).Cards = players.get(event.playerID).draw(players.get(event.playerID).Cards, event.discard);
            timer.cancel(); 
            /*
            System.out.println("After = ");
            for (int k = 0; k < 5; k++) {
                System.out.println(players.get(event.playerID).CardId[k]);
            }
            */
            // if the player draws, they get a choice which cards to discard and draw new ones for
            // the message should have sent the indexes of cards to be discarded and the player that sent the message
        }
        else if (event.event == UserEventType.BET) {
            // not implemented for iteration 1 so there is only folding and standing for now
            // this does not count as a turn
             timer.cancel(); 
        }
       else if (event.event == UserEventType.COIN) {
		    timer.cancel(); 
        }
        else if (event.event == UserEventType.PASSWORD) {
            timer.cancel(); 
        }
        else if (event.event == UserEventType.EXIT) {
            players.get(event.playerID).lose();
                for (int i = 0; i < players.size(); i++) {
                    if (i!=event.playerID) {
                        players.get(i).win=true;
                        winner_id=i; // the game ends here return 0
                        timer.cancel(); 
                        return;
                    }
                } 	
        }
         System.out.println("Player The Timer is Ended" + task.cancel);
        timer.cancel(); 
        loop = 1;    
            
        turn++;
        if (turn > players.size() - 1) {
            turn = 0;
            round_num += 1;
        }
        if (round_num==2) {
            //It is draw round and we need to discard and draw new cards for each user if they choose to

        }
        if (round_num==4) {
            Hand[] hands = new Hand[5];
            for (int j = 0; j < players.size(); j++) {
                players.get(j).Cards = Hand.sortHand(players.get(j).Cards);
                hands[j] = new Hand();
                hands[j].cards = players.get(j).Cards;
            }
            if (hands[0].is_better_than(hands[1])) {
                players.get(1).lose();
                winner_id = 0;
            } 
            else if (hands[1].is_better_than(hands[0])) {
                players.get(0).lose();
                winner_id = 1;
            } else {
                winner_id = 500; // A winner id of 500 means that there has been an error
            }
            //It is the showdown round and put the hands of both players through the is_better_than() in Hand.java
        }
        /*
        for(i=0;i<players.size();i++) // Count number of players who've lost
        {
            if(players.get(i).lose)
            {
                count_losses++;
            }
        }
        if(count_losses == players.size() - 1) // If every player but one lost, set winner_id
        {
            for(i=0;i<players.size();i++)
            {
                if(!players.get(i).lose)
                {
                    players.get(i).win = true;
                    winner_id = i;
                }
            }
        }
        */
    
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
    TODO Implement that after the 4th round, it goes to a showdown
    TODO Integrate the Hand class's is_better_than to determine winner at showdown

*/
