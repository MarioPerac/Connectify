package connectify.backend.models.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Automation {
    private Integer id;
    private String jiraProject;
    private String typesName;
    private Timestamp createdAt;
}
