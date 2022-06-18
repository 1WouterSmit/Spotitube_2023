package Domain;

public class Track implements DomainObject {
    private int duration;
    private Long playcount, id;
    private String title, performer, album, publicationDate, description;
    private boolean available_offline;

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
