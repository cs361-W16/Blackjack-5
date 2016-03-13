package Models;

/**
 * Created by Natha_000 on 3/5/2016.
 */
public class Card {
    private int value;
    private String suit;

    public Card(int val, String su){
        this.value = val;
        this.suit = su;
    }

    public String getSuit(){
        return suit;
    }
    public int getValue(){
        return value;
    }
}
