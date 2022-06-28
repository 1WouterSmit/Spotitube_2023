package Domain;

public class Track {
    private int duration;
    private Long playcount, id;
    private String title, performer, album, publicationDate, description;
    private boolean available_offline;

    public Track() {}
    public Track(long id, String title, String performer, int duration, String album, Long playcount,
                 String publicationDate, String description, boolean available_offline) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this.playcount = playcount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.available_offline = available_offline;
    }

    public boolean isAvailableOffline() { return available_offline;}
    public void setAvailableOffline(boolean AO) { available_offline = AO;}
    public Long getPlaycount() {
        return playcount;
    }
    public void setPlaycount(Long playcount) {
        this.playcount = playcount;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public String getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPerformer() {
        return performer;
    }
    public void setPerformer(String performer) {
        this.performer = performer;
    }
}
