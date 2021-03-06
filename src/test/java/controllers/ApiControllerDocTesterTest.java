/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;


import Models.Card;
import Models.Deck;
import Models.Game;
import Models.Player;
import org.junit.Test;

import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ApiControllerDocTesterTest extends NinjaDocTester {
    
    String URL_INDEX = "/";
    String URL_BLACKJACK = "/Blackjack";
    
    @Test
    public void testGetIndex() {
    
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)));

        assertThat(response.payload, containsString("Hello World!"));
        assertThat(response.payload, containsString("BAM!"));


    }
    
    @Test
    public void testBlackjack() {
    
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_BLACKJACK)));

        assertThat(response.payload, containsString("Blackjack"));

    
    }

    @Test
    public void TestCard(){
        Card mycard = new Card(5,"Hearts");
        assertEquals(5, mycard.getValue());
    }
    @Test
    public void TestDeck(){
        Card mycard = new Card(1, "Hearts");
        Deck mydeck = new Deck();
        assertEquals(mycard.getValue(), mydeck.getCard(0).getValue());
        assertEquals(mycard.getSuit(), mydeck.getCard(0).getSuit());

        Card one = mydeck.Deal();
        Card test;
        for(int i = 0; i < 51; i++){
            test = mydeck.Deal();
            if(one.getValue() == test.getValue()) {
                assertNotEquals(one.getSuit(), test.getSuit());
            }
        }
        assertEquals(mydeck.getSize(), 0);
        mydeck.resetDeck();
        assertEquals(mydeck.getSize(), 52);
        assertEquals(mycard.getValue(), mydeck.getCard(0).getValue());
        assertEquals(mycard.getSuit(), mydeck.getCard(0).getSuit());
    }
    @Test
    public void TestPlayer(){
        Player myPlayer = new Player();
        assertEquals(myPlayer.getMoney(), 100);
        assertEquals(myPlayer.getRoundValue(), 0);
        assertEquals(myPlayer.getRoundMoney(), 0);
        assertEquals(myPlayer.getRoundMoneySplit(), 0);
        assertEquals(myPlayer.getRoundValueSplit(), 0);
        myPlayer.makeInitialBet();
        assertEquals(myPlayer.getMoney(), 98);
        assertEquals(myPlayer.getRoundMoney(), 2);
        myPlayer.hit(new Card(6, "Diamonds"));
        myPlayer.hit(new Card(6, "Spades"));
        assertEquals(myPlayer.getRoundValue(), 12);

        myPlayer.split(new Card(6, "Spades"));
        assertEquals(myPlayer.getRoundMoney(), 2);
        assertEquals(myPlayer.getRoundMoneySplit(), 2);
        assertEquals(myPlayer.getRoundValue(), 6);
        assertEquals(myPlayer.getRoundValueSplit(), 6);
        myPlayer.hit(new Card(10, "Hearts"));
        myPlayer.hitSplit(new Card(2, "Hearts"));
        assertEquals(myPlayer.getRoundValue(), 16);
        assertEquals(myPlayer.getRoundValueSplit(), 8);
        myPlayer.hit(new Card(1, "Hearts"));
        myPlayer.hitSplit(new Card(1, "Diamonds"));
        assertEquals(myPlayer.getRoundValue(), 17);
        assertEquals(myPlayer.getRoundValueSplit(), 19);
        assertEquals(myPlayer.getRoundMoney(), 2);
        assertEquals(myPlayer.getRoundMoneySplit(), 2);
        myPlayer.wonSplit();
        myPlayer.reset();
        assertEquals(myPlayer.getMoney(), 100); //Player did not win their other hand (gained 2, and lost 2)
        myPlayer.reset();
        myPlayer.makeInitialBet();
        myPlayer.hit(new Card(2, "Hearts"));
        myPlayer.hit(new Card(4, "Spades"));
        myPlayer.doubleDown();
        assertEquals(myPlayer.getMoney(), 96);
        myPlayer.won();
        assertEquals(myPlayer.getMoney(), 104);


    }
    @Test
    public void TestGame(){
        Game g = new Game();
        assertEquals(g.canNew, true);
        assertEquals(g.canHit, false);
        g.startnewRound();
        assertEquals(g.canNew, false);
        assertEquals(g.canHit,true);
        g.rigSplit();
        assertEquals(g.canSplit, true);
        g.split();
        assertEquals(g.canSplit, false);
        assertEquals(g.hasSplit, true);
        g.stay();
        assertEquals(g.playing, false);
        assertEquals(g.playingSplit, true);
        g.staySplit();
        assertEquals(g.playingSplit, false);
        g.hit();
        g.hitSplit();
        assertEquals(g.hands.get(0).size(), 1);
        assertEquals(g.hands.get(1).size(), 2);
        assertEquals(g.hands.get(2).size(), 2);
        g.startnewRound();
        g.doubleDown();
        assertEquals(g.hands.get(1).size(), 3);
        g.startnewRound();
        g.rigWinBoth();
        assertEquals(g.player1.getRoundValue(), 21);
        assertEquals(g.player1.getRoundValueSplit(), 21);
        g.roundCheck();
        assertEquals(g.playing, false);
        assertEquals(g.playingSplit, false);
        assertEquals(g.canHitSplit, false);
        assertEquals(g.canStaySplit, false);

    }

}
