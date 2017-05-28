package ir.valinejad.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "WEDDING")
@Entity
public class Wedding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wedding_id")
    private Long id;

    @Column (name = "title")
    private String title;

    @Column(name = "app_version", nullable = false)
    private Long appVersion;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "loginCount")
    private Long loginCount;

    @Column(name = "asset_version")
    private Long assetVersion;

    @Column(name = "damad_name")
    private String damadName;

    @Column(name = "aroos_name")
    private String aroos_name;

    @Column(name = "date")
    private String date;

    @ManyToOne()
    @JoinColumn(name = "studio_id", nullable = false)
    private Studio studio;

    @OneToMany(mappedBy = "wedding")
    private Set<Photo> photos;

    @OneToMany(mappedBy = "wedding")
    private Set<ArContent> arContents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Long appVersion) {
        this.appVersion = appVersion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public Long getAssetVersion() {
        return assetVersion;
    }

    public void setAssetVersion(Long assetVersion) {
        this.assetVersion = assetVersion;
    }

    public String getDamadName() {
        return damadName;
    }

    public void setDamadName(String damadName) {
        this.damadName = damadName;
    }

    public String getAroos_name() {
        return aroos_name;
    }

    public void setAroos_name(String aroos_name) {
        this.aroos_name = aroos_name;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<ArContent> getArContents() {
        return arContents;
    }

    public void setArContents(Set<ArContent> arContents) {
        this.arContents = arContents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
