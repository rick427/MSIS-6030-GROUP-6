import DBModels.UserData;
import frames.Home;
import frames.Library;
import models.JukeBox;
import models.Song;
import org.junit.jupiter.api.Test;
import services.DbService;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class Unit_Tests {
    DbService dbService = new DbService();
    File defaultPlaylist = new File("src/playlist.txt");
    Home home = new Home();
    JukeBox jukeBox = new JukeBox(home);
    Library library= new Library();

    @Test
    public void userLogin(){
        //This test should return false since these are incorrect user credentials
        boolean result = dbService.login("ray@gmail.com","12345");
        assertTrue(result);
    }

    @Test
    public void signUp(){
        //This test should return fasle since the user email is already being used by another user in the system
        UserData userData = new UserData();
        userData.setUsername("Arnold Aijuka");
        userData.setPassword("12345");
        userData.setPhone_number("123");
        userData.setEmail("ray@gmail.com");

        boolean result = dbService.signup(userData);
        assertTrue(result);
    }

    @Test
    public void loadSongTest(){
        //This test should try and play a song
        Song song = new Song("src/assets/music/Apex Legends Tales From the Outlands ft. Loba - Stephen Barton.mp3");
        jukeBox.loadSong(song);
    }

    @Test
    public void loadPlaylistTest(){
        //This test should try and load a playlist
        jukeBox.loadPlaylist(defaultPlaylist);
    }
    @Test
    public void getAllSongs(){
        //This test should try and get all the songs in the playlist
        assertTrue(library.getSongs());
    }
}
