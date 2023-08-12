package nl.energydata.library.datacontainer;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.dataprovider.DataProvider;
import nl.energydata.library.session.Session;
import nl.energydata.library.session.SessionGroup;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "data_container")
@Entity
public class DataContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    //@BatchSize(size = 9)
    @JoinColumn(nullable = false)
    private DataProvider dataProvider;
    
    @ManyToOne
    private SessionGroup sessionGroup;
    
    @ManyToOne
    private Session session;
    
    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    
    @UpdateTimestamp
    @Column(name = "update_time_stamp")
    private LocalDateTime updateTimeStamp;
    
}


