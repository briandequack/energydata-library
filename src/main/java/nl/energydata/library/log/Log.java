package nl.energydata.library.log;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.session.Session;

@Getter
@Setter
@Entity
public class Log implements ILog{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 240)
    private String message;
    
    @Column(length = 3000)
    private String stackTrace;
    
    @ManyToOne
    private Session session;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LogStatus status;
    
    @CreationTimestamp
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    
    public Log() {}
    
    public void setMessage(String message) {
        int maxLen = 240;
        if (message != null && message.length() > maxLen) {
            message = message.substring(0, maxLen);
        }
        this.message = message;
    }
    
    public void setError(String stackTrace) {
        int maxLen = 3000;
        if (stackTrace != null && stackTrace.length() > maxLen) {
        	stackTrace = stackTrace.substring(stackTrace.length() - maxLen);
        }
        this.stackTrace = stackTrace;
    }

}
