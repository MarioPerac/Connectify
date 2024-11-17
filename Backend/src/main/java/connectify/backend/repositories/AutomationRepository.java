package connectify.backend.repositories;

import connectify.backend.models.entities.AutomationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationRepository extends JpaRepository<AutomationsEntity, Integer> {

    @Query("SELECT a.slackWebhookUrl FROM AutomationsEntity a WHERE a.jiraWebhookId = :webhookId AND a.jiraAccountId = :accountId")
    String getSlackWebhookUrl(@Param("webhookId") Integer webhookId, @Param("accountId") String accountId);

    List<AutomationsEntity> getAllByUsername(String username);
}
