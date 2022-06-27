package JSONDTO;

public class TrackDTO {
    // id title preformer duration album playcount publicationdate description offlineavailable
    private Long id;
    private String title;
    private String performer;
    private int duration;
    private String album;
    private Long playcount;
    private String publicationDate;
    private String description;
    private boolean offlineAvailable;

    public TrackDTO() {}

    public TrackDTO(Long id, String title, String performer, int duration, String album, Long playcount,
                    String publicationDate, String description, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album =album;
        this.playcount = playcount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
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

    public Long getPlaycount() {
        return playcount;
    }

    public String getTitle() {
        return title;
    }

    public String getPerformer() {
        return performer;
    }

    public String getAlbum() {
        return album;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOfflineAvailable() {
        return offlineAvailable;
    }
}
