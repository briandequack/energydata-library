package nl.energydata.library.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.energydata.library.log.ILogService;
import nl.energydata.library.log.LogService;
import nl.energydata.library.log.LogStatus;

@Service
public class SessionService {

	@Autowired
    SessionRepository sessionRepository;
	
	@Autowired
	ILogService logService;

    public Session create() { 
    	return logService.executeSafeWithLog("Create new Session", () -> {
		    Session session = new Session();
	        session.setStatus(LogStatus.IN_PROGRESS);        
	        return sessionRepository.save(session);  
    	});
    }
    
    public Session save(Session session) {
    	return logService.executeSafeWithLog("Save Session", () -> {
    		return sessionRepository.save(session);
    	});
    }

}