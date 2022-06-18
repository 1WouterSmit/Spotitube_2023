package Domain;

import java.util.ArrayList;

public class Playlist implements DomainObject{
    private Long id, owner;
    private String name;
    private ArrayList<Track> tracks;

    public Playlist(Long id, String name, Long owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        tracks = new ArrayList<>();
    }

    public Playlist(Long id, String name, Long owner, ArrayList<Track> tracks) {
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

    public Long getID() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getOwner() {
        return owner;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Long owner) {
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
