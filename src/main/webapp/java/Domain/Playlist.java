package Domain;

import java.util.ArrayList;

public class Playlist {
    private int id;
    private String name, owner;
    private ArrayList<Track> tracks;

    public Playlist(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        tracks = new ArrayList<>();
    }

    public Playlist(int id, String name, String owner, ArrayList<Track> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public int getLength() {
        int length = 0;
        for(Track track : tracks) {
            length += track.getDuration();
        }
        return length;
    }

    public boolean containsTrack(Track track) {
        return tracks.contains(track);
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void removeTrack(Track track) {
        tracks.remove(track);
    }
}
