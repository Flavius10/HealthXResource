package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import entities.enums.HealthMetricType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "health_metrics")
public class HealthMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 45)
    private HealthMetricType type;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonBackReference
    private HealthProfile profile;

    public HealthMetric() {}

    public int getId(){
        return this.id;
    }

    public BigDecimal getValue(){
        return this.value;
    }

    public HealthMetricType getType(){
        return this.type;
    }

    public HealthProfile getProfile(){
        return this.profile;
    }

    public void setValue(BigDecimal value){
        this.value = value;
    }

    public void setType(HealthMetricType type){
        this.type = type;
    }

    public void setProfile(HealthProfile profile){
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "HealthMetric [id=" + id + ", value=" + value + ", type=" + type + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        HealthMetric other = (HealthMetric) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
