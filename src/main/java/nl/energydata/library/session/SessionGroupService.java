package nl.energydata.library.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.energydata.library.log.ILogService;
import nl.energydata.library.log.LogService;
import nl.energydata.library.log.LogStatus;

@Service
public class SessionGroupService{

	@Autowired
    SessionGroupRepository sessionGroupRepository;
	
	@Autowired
	ILogService logService;

    public SessionGroup create() {
    	return logService.executeSafeWithLog("Create new SessionGroup", () -> {
	        SessionGroup sessionGroup = new SessionGroup();
	        sessionGroup.setStatus(LogStatus.IN_PROGRESS);
	        return sessionGroup;
    	});
       
    }
    
    public SessionGroup save(SessionGroup sessionGroup) {
    	return logService.executeSafeWithLog("Save SessionGroup", () -> {
    		return sessionGroupRepository.save(sessionGroup);  
    	});
    }

}
