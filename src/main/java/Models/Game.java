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
    public boolean hasSplit;
    public boolean canNew;  //If the player game start a new hand
    public boolean canHit;  //If the player can hit
    public boolean canSplit;    //If the player can split his cards
    public boolean canHitSplit; //If the player has split, and their split pile can hit
    public boolean canDoubleDown;   //If the player can double down
    public boolean canStay;
    public boolean canStaySplit;
    //public boolean canDoubleDownSplit;    //If the player has split, and their split pile can double down
    public boolean playing; //If the player is still currently playing
    public boolean playingSplit;    //If the players split pile is still currently playing


    public Game(){
        hands.add(new ArrayList<Card>());   //Dealer
        hands.add(new ArrayList<Card>());   //Player
        hands.add(new ArrayList<Card>());   //Player Split
        canNew = true;
        hasSplit = false;
        canStay = false;
        canStaySplit = false;
        canHit = false;
        canHitSplit = false;
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

    public void startnewRound(){
        hands.get(0).clear();
        hands.get(1).clear();
        hands.get(2).clear();
        myDeck.resetDeck();
        player1.reset();
        canNew =false;
        canHit = true;
        canStay = true;
        hasSplit = false;
        canSplit = false;
        canDoubleDown = true;
        playing = true;
        playingSplit = false;
        dealertotal = 0;
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
        if(hands.get(1).get(0).getValue() == hands.get(1).get(1).getValue()){
            canSplit = true;
        }
    }
    public void rigSplit(){         //Strictly for testing
        hands.get(1).clear();
        player1.reset();
        hands.get(1).add(new Card(5, "Spades"));
        player1.hit(new Card(5, "Spades"));
        hands.get(1).add(new Card(5, "Hearts"));
        player1.hit(new Card(5, "Hearts"));
        if(hands.get(1).get(0).getValue() == hands.get(1).get(1).getValue()){
            canSplit = true;
        }
    }

    public void roundCheck(){           //Checks and updates the current state of the round
        if(player1.getRoundValue() == 21){
            playing = false;
            player1.won();
        }
        else if(player1.getRoundValue() < 21){
            canHit = true;
            canStay = true;
        }
        else{
            playing = false;
            player1.lost();
        }
        if(hasSplit) {
            if (player1.getRoundValueSplit() == 21) {
                playingSplit = false;
                player1.wonSplit();
            } else if (player1.getRoundValueSplit() < 21) {
                canHitSplit = true;
                canStaySplit = true;
            } else {
                playingSplit = false;
                player1.lostSplit();
            }
        }
        if(!playing && !playingSplit){  //Prompts the user only the ability to start a new round
            canNew = true;
            canStay = false;
            canStaySplit = false;
            canHit = false;
            canHitSplit = false;
            canSplit = false;
            canDoubleDown = false;
            dealertotal = 0;
        }
    }

    public void hit(){
        canSplit = false;
        canDoubleDown = false;
        Card currentCard = myDeck.Deal();
        hands.get(1).add(currentCard);
        player1.hit(currentCard);
        roundCheck();
    }
    public void hitSplit(){
        Card currentCard = myDeck.Deal();
        hands.get(2).add(currentCard);
        player1.hitSplit(currentCard);
        roundCheck();
    }

    public void doubleDown(){
        player1.doubleDown();
        Card currentCard = myDeck.Deal();
        hands.get(1).add(currentCard);
        player1.hit(currentCard);
        roundCheck();
    }
    public void stay(){
            while (dealertotal < 17 && (dealertotal < player1.getRoundValue())) {
                updateDealerTotal(myDeck.Deal());
            }
            if (dealertotal > 21) {
                player1.won();

            } else if (dealertotal == 21 || dealertotal > player1.getRoundValue()) {
                player1.lost();
            }
            else{
                player1.won();
            }
            playing = false;
        if(!playing && !playingSplit){  //Prompts the user only the ability to start a new round
            canNew = true;
            canStay = false;
            canStaySplit = false;
            canHit = false;
            canHitSplit = false;
            canSplit = false;
            canDoubleDown = false;
            dealertotal = 0;
        }
    }
    public void staySplit(){
            while (dealertotal < 17 && (dealertotal < player1.getRoundValueSplit())) {
                updateDealerTotal(myDeck.Deal());
            }
            if (dealertotal > 21) {
                player1.wonSplit();
            } else if (dealertotal == 21 || dealertotal > player1.getRoundValueSplit()) {
                player1.lostSplit();
            }
            else{
                player1.wonSplit();
            }
            playingSplit = false;
        if(!playing && !playingSplit){  //Prompts the user only the ability to start a new round
            canNew = true;
            canStay = false;
            canStaySplit = false;
            canHit = false;
            canHitSplit = false;
            canSplit = false;
            canDoubleDown = false;
            dealertotal = 0;
        }
    }
    public void split(){
        hands.get(2).add(hands.get(1).get(1));
        hands.get(1).remove(1);
        player1.split(hands.get(2).get(0));
        hasSplit = true;
        canSplit = false;
        playingSplit = true;
        canHitSplit = true;
        canStaySplit = true;
    }
}
