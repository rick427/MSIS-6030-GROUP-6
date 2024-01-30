package utils;

import java.util.*;

public class LibraryData{
    private static class Track {
        private final String name;
        private final String artist;
        private int rating;
        private int playCount;

        Track(String trackName, String artistName, int trackRating){
            this.name = trackName;
            this.artist = artistName;
            this.rating = trackRating;
        }
    }

    private static final Map<String, Track> library = new TreeMap<>();

    static {
        library.put("01", new Track("Komiyo", "Niniola", 3));
        library.put("02", new Track("Ohema (with Crayon & Bella Shmurda)", "Victony, Crayon", 4));
        library.put("03", new Track("Too Busy To Be Bae", "Kizz Daniel", 4));
        library.put("04", new Track("Ngozi", "Crayon, Arya Starr", 5));
        library.put("05", new Track("Obaa Sima", "Fireboy DML", 4));
        library.put("06", new Track("I Pray", "KCee, Oxlade", 4));
        library.put("07", new Track("Clear Road", "Azanti PsychoYP", 3));
        library.put("08", new Track("American Love", "Qing Madi", 5));
        library.put("09", new Track("PIANO", "Oxlade, P.Priime", 2));
        library.put("10", new Track("Cry (shayo) (feat.Lojay)", "Majeed, Lojay", 5));
        library.put("11", new Track("My Heart Goes (La Di Da)", "Betty Hill, Topic", 3));
        library.put("12", new Track("Not An Angel", "Tems", 5));
        library.put("13", new Track("Lose Control (feat. Blxckie)", "Mayorkun, Blxckie", 1));
        library.put("14", new Track("Lovin On Me", "Jack Harlow", 2));
        library.put("15", new Track("Ayo Girl (Fayahh Beat)[feat. Rema]", "Robinson, Jason Derulo, Rema", 3));
        library.put("16", new Track("Unbreakable (with Sam Gray)", "TELYKAST", 5));
        library.put("17", new Track("Happiness (feat. Asaka & Gunna)", "Sarz, Asake, Gunna", 2));
        library.put("18", new Track("Agora Hills", "Doja Cat", 2));
        library.put("19", new Track("Oh My", "Fireboy DML", 5));
        library.put("20", new Track("Arizona", "Lojay, Olamide", 1));
    }

    public static String listAll() {
        String output = "";
        for (String key: library.keySet()) {
            Track item = library.get(key);
            output += key + ": " + item.name + " - " + item.artist + "\n";
        }
        return output;
    }

    public static int getTotal(){
        return library.size();
    }

    public static String getName(String key) {
        Track item = library.get(key);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.name;
        }
    }

    public static String getArtist(String key) {
        Track item = library.get(key);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.artist;
        }
    }

    public static int getRating(String key) {
        Track item = library.get(key);
        if (item == null) {
            return -1; // negative quantity means no such item
        } else {
            return item.rating;
        }
    }

    public static void setRating(String key, int rating) {
        Track item = library.get(key);
        if (item != null) {
            item.rating = rating;
        }
    }

    public static int getPlayCount(String key) {
        Track item = library.get(key);
        if (item == null) {
            return -1; // negative quantity means no such item
        } else {
            return item.playCount;
        }
    }

    public static void incrementPlayCount(String key) {
        Track item = library.get(key);
        if (item != null) {
            item.playCount += 1;
        }
    }
}