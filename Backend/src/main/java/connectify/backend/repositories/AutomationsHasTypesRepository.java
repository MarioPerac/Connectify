package connectify.backend.repositories;

import connectify.backend.models.entities.AutomationsHasTypesEntity;
import connectify.backend.models.entities.AutomationsHasTypesEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationsHasTypesRepository extends JpaRepository<AutomationsHasTypesEntity, AutomationsHasTypesEntityPK> {
}
