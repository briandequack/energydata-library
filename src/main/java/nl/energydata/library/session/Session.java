package nl.energydata.library.session;

import jakarta.persistence.*;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.dataprovider.DataProvider;
import nl.energydata.library.log.LogStatus;

@Getter
@Setter
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date startedAt;
    
    @ManyToOne
    private DataProvider dataProvider;
    
    @ManyToOne
    private SessionGroup sessionGroup;
    
    @Enumerated(EnumType.STRING)
    private LogStatus status;

    public Session() {
        this.startedAt = new Date();
    }

}
