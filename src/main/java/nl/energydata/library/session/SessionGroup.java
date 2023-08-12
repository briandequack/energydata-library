package nl.energydata.library.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.log.LogStatus;

@Setter
@Getter
@Entity
public class SessionGroup {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private Date startedAt;
    
    private int failedSessions;
    
    private int successfullSessions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogStatus status;
    
    public SessionGroup() {
        this.startedAt = new Date();
    }

   
}