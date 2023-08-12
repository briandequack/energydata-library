package nl.energydata.library.dataprovider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.datacontainer.DataContainer;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "data_provider")
@Entity
public abstract class DataProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
 
    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    
}
