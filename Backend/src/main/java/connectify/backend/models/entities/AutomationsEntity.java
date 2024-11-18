package connectify.backend.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.XSlf4j;

import java.awt.desktop.SystemEventListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
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
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "jira_webhook_id")
    private Integer jiraWebhookId;
    @Basic
    @Column(name = "jira_account_id")
    private String jiraAccountId;
    @Basic
    @Column(name = "status")
    private Boolean status = true;
    @Basic
    @Column(name= "access_token")
    private String accessToken;
    @Basic
    @Column(name ="refresh_token")
    private String refreshToken;
    @Basic
    @Column(name = "expires_in")
    private Timestamp expiresIn;
    @OneToMany(mappedBy = "automationsId", cascade = CascadeType.REMOVE)
    private List<AutomationsHasTypesEntity> types;

}
