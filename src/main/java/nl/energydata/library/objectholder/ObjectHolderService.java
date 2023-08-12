package nl.energydata.library.objectholder;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nl.energydata.library.datacontainer.DataContainer;
import nl.energydata.library.datacontainer.DataContainerRepository;
import nl.energydata.library.datacontainer.DataContainerService;
import nl.energydata.library.log.Log;
import nl.energydata.library.log.LogRepository;
import nl.energydata.library.log.LogService;
import nl.energydata.library.log.LogStatus;
import nl.energydata.library.session.Session;
import nl.energydata.library.session.SessionGroup;
import nl.energydata.library.session.SessionRepository;
import nl.energydata.library.webdriver.WebDriverQueue;

@Service
public class ObjectHolderService {
	
	@Autowired
	LogService logService;
	
	@Autowired 
	LogRepository logRepository;
	
	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	DataContainerRepository dataContainerRepository;
	
	@Autowired
	DataContainerService dataContainerService;
	
	public ObjectHolder create() {
		return logService.executeSafeWithLog("Create new ObjectHolder", () -> {
			ObjectHolder objectHolder = new ObjectHolder();
			return objectHolder;	
		});
	}
	
	public SessionGroup setSessionGroup(ObjectHolder objectHolder, SessionGroup sessionGroup){
		return logService.executeSafeWithLog("Set SessionGroup for ObjectHolder", () -> {
			objectHolder.setSessionGroup(sessionGroup);
			return sessionGroup;
		});	
	}
	
	public Session setSession(ObjectHolder objectHolder, Session session){
		return logService.executeSafeWithLog("Set Session for ObjectHolder", () -> {
			objectHolder.setSession(session);
			return session;
		});	
	}
	
	public WebDriverQueue setWebDriverQueue(ObjectHolder objectHolder, WebDriverQueue webDriverQueue){
		return logService.executeSafeWithLog("Set WebDriverQueue for ObjectHolder", () -> {
			objectHolder.setWebDriverQueue(webDriverQueue);
			return webDriverQueue;
		});	
	}
	
	public WebDriver setWebDriver(ObjectHolder objectHolder, WebDriver webDriver){
		return logService.executeSafeWithLog("Set WebDriver for ObjectHolder", () -> {
			objectHolder.setWebDriver(webDriver);
			return webDriver;
		});	
	}
	
	@Transactional
	public LogStatus process(ObjectHolder objectHolder) {		
		
		Session session = objectHolder.getSession();
		session.setSessionGroup(objectHolder.getSessionGroup());
		session.setDataProvider(objectHolder.getDataProvider());
					
		
		List<Log> logs = objectHolder.getLogs();
		for(Log log: logs) {
			if(log.getStatus() == LogStatus.FAILURE) {
				log.setSession(session);
				logRepository.save(log);
			}
		}
		
		if(objectHolder.getErrorLogs().isEmpty()) {
			session.setStatus(LogStatus.SUCCESS);	
			
			List<DataContainer> dataContainers = objectHolder.getDataContainers();
			for(DataContainer dataContainer: dataContainers) {
				dataContainer.setSessionGroup(objectHolder.getSessionGroup());
				dataContainer.setSession(objectHolder.getSession());
				dataContainer.setDataProvider(objectHolder.getDataProvider());
				dataContainerService.saveOrUpdate(dataContainer);
			}
		} else {
			session.setStatus(LogStatus.FAILURE);	
		}
				
		sessionRepository.save(session);
		return session.getStatus();
	}
}
