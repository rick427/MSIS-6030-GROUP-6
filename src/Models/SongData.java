package Models;

public class SongData {
    private int songID;
    private String songName;
    private String songArtist;
    private String songRating;
    private String songPlayCount;

    public SongData(int songID, String songName, String songArtist, String songRating, String songPlayCount) {
        this.songID = songID;
        this.songName = songName;
        this.songArtist = songArtist;
        this.songRating = songRating;
        this.songPlayCount = songPlayCount;
    }

    @Override
    public String toString() {
        return "SongData{" +
                "songID=" + songID +
                ", songName='" + songName + '\'' +
                ", songArtist='" + songArtist + '\'' +
                ", songRating='" + songRating + '\'' +
                ", songPlayCount='" + songPlayCount + '\'' +
                '}';
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongRating() {
        return songRating;
    }

    public void setSongRating(String songRating) {
        this.songRating = songRating;
    }

    public String getSongPlayCount() {
        return songPlayCount;
    }

    public void setSongPlayCount(String songPlayCount) {
        this.songPlayCount = songPlayCount;
    }
}
