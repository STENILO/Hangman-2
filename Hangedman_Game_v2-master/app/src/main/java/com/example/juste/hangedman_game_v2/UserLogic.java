package com.example.juste.hangedman_game_v2;


public class UserLogic {
    private String name;
    private int score;


    public UserLogic() {}

    public String getName(){
        return name;
    }
    public int getScore(){return score;}
    public String toString(){
        return "name = "+ name + " score = " + score;
    }

}
