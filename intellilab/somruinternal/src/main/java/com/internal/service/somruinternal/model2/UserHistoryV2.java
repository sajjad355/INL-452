package com.internal.service.somruinternal.model2;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;



@Entity
@Table(name = "il_user_history")
public class UserHistoryV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userHistoryId;

    @Column(nullable = false)
    @CreationTimestamp
    private Date activityTime;

    @Column(nullable = false,length=50)
    private String username;

    @Column(nullable = false,length=100)
    private String component;

    @Column(nullable = false,length=100)
    private String activity;

    @Lob
    private String description;


    public UserHistoryV2() {
        super();
    }

  public UserHistoryV2(long userHistoryId, Date activityTime, String username, String component, String activity, String description) {
    super();
    this.userHistoryId = userHistoryId;
    this.activityTime = activityTime;
    this.username = username;
    this.component = component;
    this.activity = activity;
    this.description = description;
  }

    /**
     * @return the userHistoryId
     */
    public long getUserHistoryId() {
        return userHistoryId;
    }

    /**
     * @param userHistoryId the userHistoryId to set
     */
    public void setUserHistoryId(long userHistoryId) {
        this.userHistoryId = userHistoryId;
    }

    /**
     * @return the activityTime
     */
    public Date getActivityTime() {
        return activityTime;
    }

    /**
     * @param activityTime the activityTime to set
     */
    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the activity
     */
    public String getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append( "userHistoryId=");
        sb.append( userHistoryId );
        sb.append( ",activityTime=");
        sb.append( activityTime );
        sb.append( ",username=");
        sb.append( username );
        sb.append( ",component=");
        sb.append( component );
        sb.append( ",activity=");
        sb.append( activity );
        sb.append( ",description=");
        sb.append( description );
        sb.append( "\n");

        return sb.toString();
    }
}
