package connectify.backend.models.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class AutomationsHasTypesEntityPK implements Serializable {
    private Integer automationsId;
    private String typesName;
}
