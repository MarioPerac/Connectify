package connectify.backend.repositories;

import connectify.backend.models.dto.Automation;
import connectify.backend.models.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, String> {
    @Query("""
    SELECT t.name
    FROM TypesEntity t
    WHERE t.name NOT IN (
        SELECT ht.typesName
        FROM AutomationsHasTypesEntity ht
        JOIN AutomationsEntity a ON ht.automationsId = a.id
        WHERE a.username = :username
    )
""")
    List<String> findTypesNotInAutomationForUser(@Param("username") String username);
}
