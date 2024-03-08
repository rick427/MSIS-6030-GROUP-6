package TableStructure;

public class SongTable {
    private final String TABLE_NAME = "SONGS";
    private final String SONG_ID = "ID";
    private final String SONG_NAME = "NAME";
    private final String SONG_ARTIST = "ARTIST";
    private final String SONG_RATING = "RATING";
    private final String SONG_PLAY_COUNT = "PLAY_COUNT";

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public String getSONG_ID() {
        return SONG_ID;
    }

    public String getSONG_NAME() {
        return SONG_NAME;
    }

    public String getSONG_ARTIST() {
        return SONG_ARTIST;
    }

    public String getSONG_RATING() {
        return SONG_RATING;
    }

    public String getSONG_PLAY_COUNT() {
        return SONG_PLAY_COUNT;
    }
}
