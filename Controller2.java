package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.round;


public class Controller2 {
    @FXML
    private Button AddButton,loadSongButton;
    @FXML
    private ComboBox playlistCombo,songsBox;
    @FXML
    private TextArea nameArea;
    @FXML
    private Tooltip addToolTip;
    @FXML
    private Label feedbackLabel;

    public String newFile;
    public File newfile;
    public int newfileruntimemins;
    public int newfileruntimesecondsleft;

    public ArrayList<File> songsarraylist=new ArrayList<File>();
    public ArrayList<File> oldsongarraylist=new ArrayList<>();
    private int index;

    private LinkedList trackslist_,newtrackslist=new LinkedList();

    public void fillPlaylist(String[] tracks, ArrayList<File> songslist,LinkedList trackslist) {
        index=0;
        trackslist_=trackslist;

        trackslist_.setCurrent(trackslist_.getFirst());

        for(int i=0;i<tracks.length;i++) {
            //add track names
            songsBox.getItems().add(trackslist_.getCurrent().getItem().getArtist()+" - "+trackslist_.getCurrent().getItem().getTrackName());
            trackslist_.setCurrent(trackslist_.getCurrent().getNext());
            //add songfiles to new list


        }
        oldsongarraylist=songslist;
        trackslist_.setCurrent(trackslist_.getFirst());

        if(songsBox.getValue()==null){
            AddButton.setDisable(true);
        }
    }
    public void addSong() {
        //BOOLEAN TO CHECK IF THE SONG IS FOUND
        boolean flag=false;


        String song= (String) songsBox.getValue();

        if(!song.equals("")){
            playlistCombo.getItems().add(song);

            if(trackslist_.getCurrent()!=null) {
                String fullname=trackslist_.getCurrent().getItem().getArtist()+" - "+trackslist_.getCurrent().getItem().getTrackName();
                for(int i=0;i<trackslist_.size();i++) {
                    if(!flag){
                        if(song.equals(fullname)){
                            songsarraylist.add(oldsongarraylist.get(i));
                            newtrackslist.insertLast(trackslist_.getCurrent().getItem());
                            //Pointers
                            flag=true;
                            trackslist_.setCurrent(trackslist_.getFirst());
                        }else{
                            trackslist_.setCurrent(trackslist_.getCurrent().getNext());
                            if((trackslist_.getCurrent()!=null)){
                                fullname=trackslist_.getCurrent().getItem().getArtist()+" - "+trackslist_.getCurrent().getItem().getTrackName();
                            }
                        }
                    }
                }
                if(newfile!=null){

                    if(song.equals(newfile.getName())){
                        songsarraylist.add(newfile);

                        Track newtrack=new Track();
                        newtrack.setTrackName(newfile.getName());
                        newtrack.setRuntime(newfileruntimemins+":"+newfileruntimesecondsleft);
                        newtrackslist.insertLast(newtrack);
                    }
                }
            }
            updateFeedbackLabel();
            trackslist_.setCurrent(trackslist_.getFirst());
        }else{
            throw new RuntimeException();

        }

    }
    public void saveAndReturn(ActionEvent event) throws IOException {
        //NAME OF PLAYLIST
        String name= nameArea.getText();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root=loader.load();

        Controller controller=loader.getController();
        controller.makeNewPlaylist(name,newtrackslist,songsarraylist);


        //root= FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    private Parent root;
    private Stage stage;
    private Scene scene;
    public void returntoscene(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT ALERT");
        alert.setContentText("Are you sure you want to exit without saving a playlist?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isEmpty()){
            System.out.println("Alert closed");
        }else if(result.get() == ButtonType.OK){
            root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    public void loadSong() throws UnsupportedAudioFileException, IOException {
        AddButton.setDisable(false);

        FileChooser fc=new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sound Files", "*.wav"));
        newfile=fc.showOpenDialog(stage);

        if(newfile!=null){
            newFile=newfile.getAbsolutePath();
            songsBox.getItems().add(""+newfile.getName());
        }

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(newfile);
        AudioFormat format = audioInputStream.getFormat();
        long frames = audioInputStream.getFrameLength();
        double durationInSeconds = (frames+0.0) / format.getFrameRate();
        newfileruntimemins=(int) (durationInSeconds/60);
        System.out.println(newfileruntimemins);
        newfileruntimesecondsleft=(int) (durationInSeconds%60);
        System.out.println(newfileruntimesecondsleft);

    }

    public void ComboStateChanged(){
        if(songsBox.getValue()!=null){
            AddButton.setDisable(false);
        }
    }


    public void updateFeedbackLabel(){
        feedbackLabel.setText("Song added!");
    }
    public void emptyLabel(){
        feedbackLabel.setText("");
    }
}
