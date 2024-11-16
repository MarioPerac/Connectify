package connectify.backend.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.desktop.SystemEventListener;
import java.sql.Timestamp;
import java.util.Objects;

@Data
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
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    @Basic
    @Column(name = "types_name")
    private String typesName;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "jira_webhook_id")
    private Integer jiraWebhookId;
    @Basic
    @Column(name = "jira_account_id")
    private String jiraAccountId;

}
