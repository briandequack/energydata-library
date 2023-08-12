package nl.energydata.library.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.energydata.library.objectholder.ObjectHolder;

@Service
public class LogService implements ILogService{
	  
	@Autowired
    LogRepository logRepository;
		
	private ThreadLocal<ObjectHolder> objectHolder = new ThreadLocal<>();
        
    public Log create(String message, LogStatus status) {
        Log log = new Log();
        log.setMessage(message);
        log.setStatus(status);
        return log;
    }
    
    public <T> T executeSafeWithLog(String message, Supplier<T> dbOperation) {
        try {
            T result = dbOperation.get();
            if(objectHolder.get() != null) {
            	addToLocalObjectHolder(create(message, LogStatus.INFO));
            }
            return result;
        } catch (Exception e) {
          	StringWriter sw = new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	String exceptionAsString = sw.toString();	
            if(objectHolder.get() != null) {
            	Log log = create(message, LogStatus.ERROR);
            	log.setError(exceptionAsString); 
            	addToLocalObjectHolder(log);        	
            }           
            return null;
        }
    }
    
    public void executeSafeWithLog(String message, Runnable dbOperation) {
        try {
            dbOperation.run();
            if(objectHolder.get() != null) {
            	addToLocalObjectHolder(create(message, LogStatus.INFO));
            }
        } catch (Exception e) {
           	StringWriter sw = new StringWriter();
        	e.printStackTrace(new PrintWriter(sw));
        	String exceptionAsString = sw.toString();
            if(objectHolder.get() != null) {
            	Log log = create(message, LogStatus.ERROR);
            	log.setError(exceptionAsString);
            	addToLocalObjectHolder(log);
            }        
        }
    }
     
    public void addToLocalObjectHolder(Log log) {
    	objectHolder.get().addLog(log);
    }
    
    public ObjectHolder setLocalObjectHolder(ObjectHolder objectHolder) {
    	return executeSafeWithLog("Set ObjectHolder ThreadLocal for LogService", () -> {
	    	this.objectHolder.set(objectHolder);
	    	return objectHolder;
    	});
    }
    
    public void clearLocal() {
    	executeSafeWithLog("Clear ThreadLocal for LogService", () -> {
    		this.objectHolder.remove();
    		return null;
    	});
    }
    
}
