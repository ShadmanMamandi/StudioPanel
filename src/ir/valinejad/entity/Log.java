package ir.valinejad.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by arash on 8/20/16.
 * a.valinejad@gmail.com
 */
@Entity
@Table (name = "tbl_log")
public class Log {
    public static final String TYPE_ERROR = "Error";
    public static final String TYPE_ACTIVITY = "Activity";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "log_type", nullable = false)
    private String logType;

    @Column (name = "title", nullable = false)
    private String title;

    @Column (name = "description", columnDefinition = " TEXT ")
    private String description;

    @ManyToOne
    @JoinColumn (name = "fk_studio_id")
    private Studio studio;

    @Column (name = "log_timestamp")
    private Timestamp logTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio user) {
        this.studio = user;
    }

    public Timestamp getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Timestamp logTimestamp) {
        this.logTimestamp = logTimestamp;
    }
}
