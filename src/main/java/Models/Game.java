package Models;

import java.util.ArrayList;

/**
 * Created by Natha_000 on 3/5/2016.
 */
public class Game {
    private Player player1 = new Player();
    private Deck myDeck = new Deck();
    public ArrayList<ArrayList<Card>> hands = new ArrayList<>();
    private int dealertotal;
    public boolean canNew;
    public boolean canHit;
    public boolean canSplit;
    public boolean canHitSplit;
    public boolean canDoubleDown;
    public boolean isCanDoubleDownSplit;
    public boolean playing;
    public boolean playingSplit;


    public Game(){
        hands.add(new ArrayList<Card>());   //Dealer
        hands.add(new ArrayList<Card>());   //Player
        hands.add(new ArrayList<Card>());   //Player Split
        canNew = true;
        canHit = false;
        canSplit = false;
        canDoubleDown = false;
        playing = false;
        playingSplit = false;
        dealertotal = 0;
    }

    public void updateDealerTotal(Card dealerCard){
        if(dealerCard.getValue() == 1){
            dealertotal += 11;
        }
        else if(dealerCard.getValue() < 10){
            dealertotal += dealerCard.getValue();
        }
        else{
            dealertotal += 10;
        }
    }

    public void startGame(){
        Card currentCard = myDeck.Deal();
        player1.makeInitialBet();
        hands.get(0).add(currentCard);
        updateDealerTotal(currentCard);
        currentCard = myDeck.Deal();
        hands.get(1).add(currentCard);
        player1.hit(currentCard);
        currentCard = myDeck.Deal();
        hands.get(1).add(currentCard);
        player1.hit(currentCard);
    }
}
