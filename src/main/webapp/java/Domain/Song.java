package Domain;

public class Song extends Track{
    private String album;

    public Song(int id, String title, String performer, int duration, String album, boolean offlineAvailable) {
        super();
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this. offlineAvailable = offlineAvailable;
    }

    public Song(String title) {
        setTitle(title);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album =album;
    }
}
