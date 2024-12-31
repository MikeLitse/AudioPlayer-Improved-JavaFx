package com.example.demo;

import java.awt.event.MouseAdapter;
import java.io.*;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label songlabel;
    @FXML
    private Label volumeValue;
    @FXML
    private Button nextButton,previousButton,MakePlaylistButton,AboutButton;
    @FXML
    private Slider playbackSlider,volumeSlider;
    @FXML
    private ImageView songcover;
    @FXML
    private ImageView pausePic;
    @FXML
    private ToggleButton pauseButton;
    @FXML
    private ComboBox queueBox;
    @FXML
    private Label songNameLabel;
    @FXML
    public Label playlistLabel,runtimeLabel,songtimeLabel,songruntimeminLabel;


    Image pause=new Image(getClass().getResourceAsStream("/assets/pause.png"));
    Image play=new Image(getClass().getResourceAsStream("/assets/play.png"));



    //Flag resuming/stopping mediaplayer
    private boolean running=false;
    //File variables
    private ArrayList<File> songs;

    private int songNumber=0;
    private Track currentTrack;

    private Media media;
    private MediaPlayer mediaPlayer;
    //Images
    Image HellHoleImage=new Image(getClass().getResourceAsStream("/assets/Six_Desperate_Ballads.png"));

    //Timer
    private Timer timer;
    private TimerTask timerTask;



    //File variables
    File HellHoleFile = new File("src/main/resources/assets/music/The_Garden-Hell_Hole.wav");

    Track HellHoleTrack=new Track("Hell Hole","The Garden","Six Desperate Ballads","EP",5,"2:03",HellHoleFile, "src/main/resources/assets/music/The_Garden-Hell_Hole.wav",2024,HellHoleImage);

    LinkedList trackslist =new LinkedList(null,null);




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ArrayList of Files
        songs =new ArrayList<File>();
        songs.add(HellHoleFile);
        //Add tracks to linkedlist
        trackslist.insertLast(HellHoleTrack);
        runtimeLabel.setText(trackslist.getFirst().getItem().getRuntime());
        //Media Player initialization
        media =new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songNameLabel.setText(trackslist.getFirst().getItem().getArtist()+" - "+trackslist.getFirst().getItem().getTrackName());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
                volumeValue.setText((int) volumeSlider.getValue()+"%");
            }
        });

    }
    public void playMedia(){
        if(pauseButton.isSelected()){
            running=true;
            playSong();
        }else{
            running=false;
            playSong();
        }
    }
    public void nextMedia(){
        songNumber++;
        mediaPlayer.stop();
        if(songNumber==songs.size()){
            songNumber=0;
        }
        //List changes
        if(trackslist.getCurrent().getNext()!=null){
            trackslist.setCurrent(trackslist.getCurrent().getNext());
        }else{
            trackslist.setCurrent(trackslist.getFirst());
        }

        if(trackslist.getCurrent().getItem().getSongcover()!=null){
            songcover.setImage(trackslist.getCurrent().getItem().getSongcover());
            songNameLabel.setText(trackslist.getCurrent().getItem().getArtist()+" - "+trackslist.getCurrent().getItem().getTrackName());
            runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
            runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
        }else{
            songcover.setImage(null);
            songNameLabel.setText(songs.get(songNumber).getName());
            runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
        }


        //Media changes
        media =new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        running=true;
        playSong();
        
    }
    public void previousMedia(){
        //File changes
        songNumber=songNumber-1;
        mediaPlayer.stop();

        if(songNumber==-1){
            songNumber=songs.size()-1;
        }
        //List changes
        if(trackslist.getCurrent().getPrevious()!=null){
            trackslist.setCurrent(trackslist.getCurrent().getPrevious());
        }else{
            trackslist.setCurrent(trackslist.getLast());
        }

        if(trackslist.getCurrent().getItem().getSongcover()!=null){
            songcover.setImage(trackslist.getCurrent().getItem().getSongcover());
            songNameLabel.setText(trackslist.getCurrent().getItem().getArtist()+" - "+trackslist.getCurrent().getItem().getTrackName());
            runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
        }else{
            songcover.setImage(null);
            songNameLabel.setText(songs.get(songNumber).getName());
            runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
        }
        //Media changes
        media =new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        running=true;
        playSong();
    }
    private void playSong(){
        if(running){
            beginTimer();
            mediaPlayer.play();
            pausePic.setImage(pause);
        }else{
            cancelTimer();
            mediaPlayer.pause();
            pausePic.setImage(play);
        }
    }
    public void beginTimer(){
        timer =new Timer();
        timerTask = new TimerTask() {

            @Override
            public void run() {

                double current=mediaPlayer.getCurrentTime().toSeconds();
                double end=media.getDuration().toSeconds();
                playbackSlider.setValue( (current/end)*100 );

                if(current/end==1){
                    cancelTimer();
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }
    public void cancelTimer(){
        timer.cancel();
    }
    public void updateSlider(){
        mediaPlayer.seek(Duration.millis(media.getDuration().toSeconds()));
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToScene2(ActionEvent event) throws IOException {
        String tracknames[]= new String[trackslist.size()];

        mediaPlayer.stop();
        pausePic.setImage(play);


        trackslist.setCurrent(trackslist.getFirst());

        for(int i=0;i<trackslist.size();i++){
            tracknames[i]=trackslist.getCurrent().getItem().getArtist()+" - "+trackslist.getCurrent().getItem().getTrackName();
            trackslist.setCurrent(trackslist.getCurrent().getNext());
        }
        trackslist.setCurrent(trackslist.getFirst());

        FXMLLoader loader=new FXMLLoader(getClass().getResource("scene2.fxml"));
        root=loader.load();

        Controller2 controller2=loader.getController();
        controller2.fillPlaylist(tracknames,songs,trackslist);


        //root= FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void makeNewPlaylist(String name,LinkedList trackslist_,ArrayList<File> newsongs){
        changeGui(name,newsongs);
        trackslist.printList();
        //Empties old list
        trackslist.emptyList();
        //Inserts new list into old list
        for(int i=0; i<trackslist_.size(); i++){
            trackslist.insertLast(trackslist_.getCurrent().getItem());
            trackslist_.setCurrent(trackslist_.getCurrent().getNext());

        }
        trackslist.printList();
        //gui changes

        songcover.setImage(trackslist.getCurrent().getItem().getSongcover());

        if(trackslist.getCurrent().getItem().getArtist()==null){
            songNameLabel.setText(trackslist.getCurrent().getItem().getTrackName());
        }else{
            songNameLabel.setText(trackslist.getCurrent().getItem().getArtist()+" - "+trackslist.getCurrent().getItem().getTrackName());
        }

        trackslist.setCurrent(trackslist.getFirst());

        for(int i=0;i<trackslist.size();i++){
            if(trackslist.getCurrent().getItem().getArtist()!=null){
                queueBox.getItems().add(trackslist.getCurrent().getItem().getArtist()+" - "+trackslist.getCurrent().getItem().getTrackName());
                trackslist.setCurrent(trackslist.getCurrent().getNext());
            }else{
                queueBox.getItems().add(trackslist.getCurrent().getItem().getTrackName());
            }

            if(trackslist.getCurrent().getNext()!=null){
                trackslist.setCurrent(trackslist.getCurrent().getNext());
            }
        }
        trackslist.setCurrent(trackslist.getFirst());
        runtimeLabel.setText(trackslist.getCurrent().getItem().getRuntime());
    }
    public void changeGui(String name,ArrayList<File> newsongs){
        System.out.println("called");
        //Empty queuecombobox
        queueBox.getItems().clear();
        songNumber=0;
        //Empty filelist
        songs.clear();
        //Insert new file list into old file list
        songs=newsongs;

        //QUEUE UPDATE

        //GUI CHANGES
        playlistLabel.setText(name);

        media =new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        if(trackslist.getCurrent().getItem().getSongcover()==null){
            songcover.setImage(null);
        }
    }

    public void switchToScene3(ActionEvent event) throws IOException {
        mediaPlayer.stop();
        root= FXMLLoader.load(getClass().getResource("scene3.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void mousePressed(){
        mediaPlayer.pause();
    }
    public void mouseReleased(){
        double time=playbackSlider.getValue();
        mediaPlayer.seek(Duration.millis(1300*time));
        mediaPlayer.play();

    }
}
