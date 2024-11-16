package connectify.backend.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "users", schema = "connectify", catalog = "")
public class UsersEntity{
    @Id
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
}
