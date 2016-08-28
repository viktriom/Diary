package com.sonu.diary.domain.enums;

/**
 * Created by sonu on 28/08/16.
 */
public enum Ratings {

    LOW(1),
    MEDIUM(3),
    HIGH(5);

    private int rating;

    Ratings(int rating){
        this.rating = rating;
    }

    public int getRating(){return rating;}

}
