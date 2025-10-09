package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class HealthProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @OneToMany(mappedBy = "profile",cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<HealthMetric> metrics;

    public HealthProfile() {}

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public List<HealthMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<HealthMetric> metrics) {
        this.metrics = metrics;
    }

    @Override
    public String toString() {
        return "HealthProfile [id=" + id + ", username=" + username + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        HealthProfile other = (HealthProfile) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
