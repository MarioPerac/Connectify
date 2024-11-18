package connectify.backend.models.dto;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Automation {
    private Integer id;
    private String jiraProject;
    private List<String> types;
    private Timestamp createdAt;
    private Boolean status;
    public Automation(Integer id, String jiraProject, List<String> types, Timestamp createdAt, Boolean status) {
        this.id = id;
        this.jiraProject = jiraProject;
        this.types = types;
        this.createdAt = createdAt;
        this.status = status;
    }
}
