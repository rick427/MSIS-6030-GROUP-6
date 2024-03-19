package Models;

import java.util.Arrays;

public class PlaylistData {
    private int Playlist_ID;
    private String[] songs;
    private String user_ID;

    public PlaylistData(int playlist_ID, String[] songs, String user_ID) {
        Playlist_ID = playlist_ID;
        this.songs = songs;
        this.user_ID = user_ID;
    }

    @Override
    public String toString() {
        return "PlaylistData{" +
                "Playlist_ID=" + Playlist_ID +
                ", songs=" + Arrays.toString(songs) +
                ", user_ID='" + user_ID + '\'' +
                '}';
    }

    public int getPlaylist_ID() {
        return Playlist_ID;
    }

    public void setPlaylist_ID(int playlist_ID) {
        Playlist_ID = playlist_ID;
    }

    public String[] getSongs() {
        return songs;
    }

    public void setSongs(String[] songs) {
        this.songs = songs;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }
}