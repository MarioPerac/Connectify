package connectify.backend.repositories;

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
    LEFT JOIN AutomationsEntity a ON t.name = a.typesName AND a.username = :username
    WHERE a.typesName IS NULL
""")
    List<String> findUnusedTypesByUserId(@Param("username") String username);
}
