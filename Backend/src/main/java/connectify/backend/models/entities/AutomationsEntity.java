package connectify.backend.models.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "automations", schema = "connectify", catalog = "")
public class AutomationsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "jira_cloud_id")
    private String jiraCloudId;
    @Basic
    @Column(name = "slack_webhook_url")
    private String slackWebhookUrl;
    @Basic
    @Column(name = "jira_project")
    private String jiraProject;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "types_name")
    private String typesName;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "jira_webhook_id")
    private Integer jiraWebhookId;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    private UsersEntity usersByUsername;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJiraCloudId() {
        return jiraCloudId;
    }

    public void setJiraCloudId(String jiraCloudId) {
        this.jiraCloudId = jiraCloudId;
    }

    public String getSlackWebhookUrl() {
        return slackWebhookUrl;
    }

    public void setSlackWebhookUrl(String slackWebhookUrl) {
        this.slackWebhookUrl = slackWebhookUrl;
    }

    public String getJiraProject() {
        return jiraProject;
    }

    public void setJiraProject(String jiraProject) {
        this.jiraProject = jiraProject;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getTypesName() {
        return typesName;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJiraWebhookId() {
        return jiraWebhookId;
    }

    public void setJiraWebhookId(Integer jiraWebhookId) {
        this.jiraWebhookId = jiraWebhookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomationsEntity that = (AutomationsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(jiraCloudId, that.jiraCloudId) && Objects.equals(slackWebhookUrl, that.slackWebhookUrl) && Objects.equals(jiraProject, that.jiraProject) && Objects.equals(createdAt, that.createdAt) && Objects.equals(typesName, that.typesName) && Objects.equals(username, that.username) && Objects.equals(jiraWebhookId, that.jiraWebhookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jiraCloudId, slackWebhookUrl, jiraProject, createdAt, typesName, username, jiraWebhookId);
    }
    public UsersEntity getUsersByUsername() {
        return usersByUsername;
    }

    public void setUsersByUsername(UsersEntity usersByUsername) {
        this.usersByUsername = usersByUsername;
    }
}
