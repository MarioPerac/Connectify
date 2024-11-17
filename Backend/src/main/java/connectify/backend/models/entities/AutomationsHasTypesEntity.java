package connectify.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@jakarta.persistence.Table(name = "automations_has_types", schema = "connectify", catalog = "")
@jakarta.persistence.IdClass(connectify.backend.models.entities.AutomationsHasTypesEntityPK.class)
public class AutomationsHasTypesEntity {
    @Id
    @jakarta.persistence.Column(name = "automations_id")
    private Integer automationsId;


    @Id
    @jakarta.persistence.Column(name = "types_name")
    private String typesName;

}
