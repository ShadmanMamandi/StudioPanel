package ir.valinejad.entity;

import javax.persistence.*;

@Table(name = "arContent")
@Entity
public class ArContent {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "arcontent_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "wedding_id",nullable = false)
    private Wedding wedding;

    @Column(name = "photo_url")
    private String photo_url;

    @Column (name = "video_url")
    private String videoUrl;

    @Column(name = "width")
    private String width;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Column(name = "height")
    private String height;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Wedding getWedding() {
        return wedding;
    }

    public void setWedding(Wedding wedding) {
        this.wedding = wedding;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
