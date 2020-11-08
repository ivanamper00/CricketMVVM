package com.billy.cricketmvvm.repositories;

public class Presets {
    public static String seriesId = "2693";
    public static String teamId = "0";
    public static String matchId = "49296";

    public static String nullable(String string){
        return string == null ?  "N/A" : string.equals("") ? "N/A" : string;
    }
}
