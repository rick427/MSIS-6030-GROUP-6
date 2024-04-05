package models;

import frames.Home;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.*;
import java.util.ArrayList;

public class JukeBox extends PlaybackListener {
    //@: Used to update the isPaused flag synchronously
    private static final Object playSignal = new Object();

    private ArrayList<Song> playlist;

    //@: Track the index playing in the playlist
    private int currentPlaylistIndex;

    //@: We need a reference to the home GUI, so we can update from this class
    private final Home home;

    //@: We need a way to store our song's details
    private Song currentSong;

    //@: Import JLayer lib to create an AdvancedPlayer obj which will handle playing the song
    private AdvancedPlayer advancedPlayer;

    //@: Pause button flag for indicating whether the jukebox has been paused or not
    private boolean isPaused;

    //@: Flag to determine when a song has finished
    private boolean songFinished;

    private boolean pressedNext, pressedPrev;

    //@: Store the last frame when the playback is finished (i.e. for pausing and resuming)
    private int currentFrame;

    //@: Track how many milliseconds has passed since playing the song (i.e. used in updating the slider)
    private int currentTimeInMs;

    //@: Constructor
    public JukeBox(Home home){
        this.home = home;
    }


    public void loadSong(Song song){
        currentSong = song;
        playlist = null;

        //@: Stop the song
        if(!songFinished) stopSong();

        //@: Play the current song if not null
        if(currentSong != null){
            currentFrame = 0;
            currentTimeInMs = 0;
            home.setPlaybackSliderValue(0);

            playCurrentSong();
        }
    }

    public void loadPlaylist(File playlistFile){
        playlist = new ArrayList<>();

        //@: Store the paths from text file into the playlist array list
        try {
            FileReader fileReader = new FileReader(playlistFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //@: Read each line from the text file and store the text into the songPath variable
            String songPath;
            while((songPath = bufferedReader.readLine()) != null){
                //@: Create song object based on song path
                Song song = new Song(songPath);

                //@: Add to playlist array list
                playlist.add(song);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!playlist.isEmpty()){
            //@: Reset playback slider
            home.setPlaybackSliderValue(0);
            currentTimeInMs = 0;

            //@: Update the current song to the first song in the playlist
            currentSong = playlist.getFirst();

            //@: Start from the beginning of the frame
            currentFrame = 0;

            //@: Update the GUI
            home.togglePauseButton();
            home.updatePlaybackSlider(currentSong);
            home.setSelectedSong(currentSong.getFilePath());

            //@: Start song
            playCurrentSong();
        }
    }

    public void pauseSong(){
        if(advancedPlayer != null){
            //@: Update the paused flag
            isPaused = true;

            //@: Stop the player
            stopSong();
        }
    }

    public void stopSong(){
        if(advancedPlayer != null){
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void nextSong(){
        //@: No need to go the next song if there is no playlist
        if(playlist == null) return;

        //@: Check to see if we have reached the end of the playlist, if so ignore
        if(currentPlaylistIndex + 1 > playlist.size() - 1) return;

        pressedNext = true;

        //@: Stop the song
        if(!songFinished) stopSong();

        //@: Increase current playlist index
        currentPlaylistIndex++;

        //@: Update the current song;
        currentSong = playlist.get(currentPlaylistIndex);

        //@: Reset the frame
        currentFrame = 0;

        //@: Reset current time in ms
        currentTimeInMs = 0;

        //@: Update the GUI
        home.togglePauseButton();
        home.updatePlaybackSlider(currentSong);
        home.setSelectedSong(currentSong.getFilePath());
        //@: update song name and title here if needed

        //@: Play the song
        playCurrentSong();
    }

    public void previousSong(){
        //@: No need to go the next song if there is no playlist
        if(playlist == null) return;

        //@: Check to see if we can go back
        if(currentPlaylistIndex - 1 < 0) return;

        pressedPrev = true;

        //@: Stop the song
        if(!songFinished) stopSong();

        //@: Decrease current playlist index
        currentPlaylistIndex--;

        //@: Update the current song;
        currentSong = playlist.get(currentPlaylistIndex);

        //@: Reset the frame
        currentFrame = 0;

        //@: Reset current time in ms
        currentTimeInMs = 0;

        //@: Update the GUI
        home.togglePauseButton();
        home.updatePlaybackSlider(currentSong);
        home.setSelectedSong(currentSong.getFilePath());
        //@: update song name and title here if needed

        //@: Play the song
        playCurrentSong();
    }

    public void playCurrentSong(){
        if(currentSong == null) return;
        try{
            //@: Read the mp3 audio data
            FileInputStream fileInputStream = new FileInputStream(currentSong.getFilePath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            //@: Create a new advanced player
            advancedPlayer = new AdvancedPlayer(bufferedInputStream);
            advancedPlayer.setPlayBackListener(this);

            //@: Start music
            startMusicThread();

            //@: Start playback slider thread
            startPlaybackSliderThread();

        } catch (FileNotFoundException | JavaLayerException e) {
            throw new RuntimeException(e);
        }
    }

    //@: Create a thread that will handle playing the music
    private void startMusicThread(){
        new Thread(() -> {
            try{
                if(isPaused){
                    synchronized (playSignal){
                        //@: Update the flag
                        isPaused = false;

                        //@: Notify the other threads to continue
                        playSignal.notify();
                    }

                    //@: Resume the song from the last frame
                    advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                }
                else{
                    //@: Play the song
                    advancedPlayer.play();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    //@: Create a thread that will handle updating the slider
    private void startPlaybackSliderThread(){
        new Thread(() -> {
            if(isPaused){
                try{
                    //@: Make sure this get notified by the thread to continue
                    //@: Also make sure that the isPaused flag updates to false before continuing
                    synchronized (playSignal){
                        playSignal.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while(!isPaused && !songFinished && !pressedNext && !pressedPrev){
                try {
                    //@: Increment current time in ms
                    currentTimeInMs++;

                    //@: Calculate to frames
                    int calculatedFrame = (int) ((double) currentTimeInMs * 1.65 * currentSong.getFrameRatePerMs());

                    //@: Update the GUI (i.e. Home)
                    home.setPlaybackSliderValue(calculatedFrame);

                    //@: Mimic 1ms using thread.sleep
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void setCurrentFrame(int frame){
        currentFrame = frame;
    }

    public void setCurrentTimeInMs(int timeInMs){
        currentTimeInMs = timeInMs;
    }

    public Song getCurrentSong(){
        return currentSong;
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        //@: This triggers at the start of a song
        System.out.println("Playback started");
        songFinished = false;
        pressedNext = false;
        pressedPrev = false;
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        //@: This triggers when a song finishes or if the player is closed
        System.out.println("Playback finished");

        //@: This looks ugly, I know. But if it works, DON'T TOUCH IT! PSA It works!
        if(isPaused){
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMs());
        }
        else {
            //@: If the user presses the next or previous button, ignore the rest of the code
            if(pressedNext || pressedPrev) return;

            //@: When the song ends
            songFinished = true;

            if(playlist == null){
                //@: Update the GUI
                home.togglePlayButton();
            }
            else {
                if(currentPlaylistIndex == playlist.size() - 1){
                    home.togglePlayButton();
                }
                else{
                    nextSong();
                }
            }
        }
    }
}
