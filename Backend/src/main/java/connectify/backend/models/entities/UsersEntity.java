package connectify.backend.models.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "connectify", catalog = "")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "usersByUsername")
    private Collection<AutomationsEntity> automationsByUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public Collection<AutomationsEntity> getAutomationsByUsername() {
        return automationsByUsername;
    }

    public void setAutomationsByUsername(Collection<AutomationsEntity> automationsByUsername) {
        this.automationsByUsername = automationsByUsername;
    }
}
