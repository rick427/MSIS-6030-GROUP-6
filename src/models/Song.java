package models;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import java.io.File;
import java.io.IOException;

public class Song {
    private final String songTitle;
    private final String songArtist;
    private final String songLength;
    private final String filePath;
    private final Mp3File mp3File;
    private final double frameRatePerMs;

    public Song(String filePath){
        this.filePath = filePath;
        try{
            mp3File = new Mp3File(filePath);
            frameRatePerMs = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
            songLength = convertToSongLengthFormat();

            //@: Import the jaudiotagger library to create an audio file and read the mp3 file information
            AudioFile audioFile = AudioFileIO.read(new File(filePath));

            //@: Read the audio file's meta data
            Tag tag = audioFile.getTag();
            if(tag != null){
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            }
            //@: mp3 file meta-data could not be read
            else {
                songTitle = "N/A";
                songArtist = "N/A";
            }
        } catch (CannotReadException | TagException | InvalidAudioFrameException | InvalidDataException |
                 ReadOnlyFileException | UnsupportedTagException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertToSongLengthFormat(){
        long minutes = mp3File.getLengthInSeconds() / 60;
        long seconds = mp3File.getLengthInSeconds() % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getFilePath() {
        return filePath;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }

    public double getFrameRatePerMs() {
        return frameRatePerMs;
    }
}
