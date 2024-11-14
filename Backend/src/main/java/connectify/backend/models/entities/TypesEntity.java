package connectify.backend.models.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "types", schema = "connectify", catalog = "")
public class TypesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "typesByTypesName")
    private Collection<AutomationsEntity> automationsByName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypesEntity that = (TypesEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Collection<AutomationsEntity> getAutomationsByName() {
        return automationsByName;
    }

    public void setAutomationsByName(Collection<AutomationsEntity> automationsByName) {
        this.automationsByName = automationsByName;
    }
}
