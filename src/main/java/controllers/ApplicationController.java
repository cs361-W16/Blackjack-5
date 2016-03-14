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

import Models.Game;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;


@Singleton
public class ApplicationController {

    Game g;

    public Result index() {

        return Results.html();

    }

    public Result blackJack(){

        return Results.html().template("views/Blackjack/Blackjack.flt.html");
    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }


    public Result gameGet(){
        g = new Game();

        return Results.json().render(g);
    }
    public Result gameNew(Context context, Game g){
        if (context.getRequestPath().contains("newRound"))
            g.startnewRound();

        return Results.json().render(g);
    }

    public Result gameHit(Context context, Game g){
        if (context.getRequestPath().contains("hit"))
            g.hit();

        return Results.json().render(g);
    }

    public Result gameHitSplit(Context context, Game g){
        if (context.getRequestPath().contains("hitSplit"))
            g.hitSplit();

        return Results.json().render(g);
    }

    public Result gameDoubleDown(Context context, Game g){
        if (context.getRequestPath().contains("doubleDown"))
            g.doubleDown();

        return Results.json().render(g);
    }

    public Result gameStay(Context context, Game g){
        if (context.getRequestPath().contains("stay"))
            g.stay();

        return Results.json().render(g);
    }

    public Result gameStaySplit(Context context, Game g){
        if (context.getRequestPath().contains("staySplit"))
            g.staySplit();

        return Results.json().render(g);
    }

    public Result gameSplit(Context context, Game g){
        if (context.getRequestPath().contains("split"))
            g.split();

        return Results.json().render(g);
    }

    public static class SimplePojo {

        public String content;
        
    }
}
