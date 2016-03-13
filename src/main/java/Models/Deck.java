package Models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Natha_000 on 3/5/2016.
 */
public class Deck {

    private ArrayList<Card> deck = new ArrayList<>();
    private int numCards;

    public Deck(){
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Hearts"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Diamonds"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Clubs"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Spades"));
        }
        numCards = 52;
    }

    public void resetDeck(){
        deck.clear();
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Hearts"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Diamonds"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Clubs"));
        }
        for(int i = 1; i < 14; i++){
            deck.add(new Card(i, "Spades"));
        }
        numCards = 52;
    }

    public Card Deal(){
        Random random = new Random();
        int index = random.nextInt(numCards);
        Card returnCard = getCard(index);
        deck.remove(index);
        numCards--;
        return returnCard;
    }

    public int getSize(){
        return numCards;
    }

    public Card getCard(int index){
        return deck.get(index);
    }

}
