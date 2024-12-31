package com.example.demo;

import java.awt.*;
import java.io.File;
import javafx.scene.image.Image;

public class Track {
    private String trackName;
    private String recordName;
    private String artist;
    private String type;
    private int songNumber;
    private String runtime;
    private File trackFile;
    private String filePath;
    private int releaseDate;
    private Image songcover;

    public Track(){}

    public Track(String trackName_,String artist_, String recordName_, String type_, int songNumber_, String runtime_,File trackFile_,String filePath_,int releaseDate_,Image songcover_) {
        trackName = trackName_;
        artist=artist_;
        recordName = recordName_;
        type = type_;
        songNumber = songNumber_;
        runtime = runtime_;
        trackFile=trackFile_;
        filePath = filePath;
        releaseDate = releaseDate_;
        songcover = songcover_;
    }
    public void setTrackName(String trackName_) {
        trackName = trackName_;
    }
    public void setRuntime(String runtime_) {
        runtime = runtime_;
    }
    public String getTrackName() {
        return trackName;
    }
    public String getArtist() {
        return artist;
    }
    public String getRecordName(){
        return recordName;
    }
    public String getType(){
        return type;
    }
    public int songNumber(){
        return songNumber;
    }
    public String runtime(){
        return runtime;
    }
    public File getTrackFile(){
        return trackFile;
    }
    public String getFilePath(){
        return filePath;
    }
    public int getReleaseDate(){
        return releaseDate;
    }
    public Image getSongcover(){
        return songcover;
    }
    public String getRuntime(){
        return runtime;
    }
    public String toString(){
        return("The track: "+trackName+" is the song number: "+songNumber+" in the record: "+recordName+" and has a runtime of: "+runtime+" minutes and it was released in "+releaseDate);
    }
}
