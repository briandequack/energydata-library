package nl.energydata.library.objectholder;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import lombok.Getter;
import lombok.Setter;
import nl.energydata.library.datacontainer.DataContainer;
import nl.energydata.library.dataprovider.DataProvider;
import nl.energydata.library.log.Log;
import nl.energydata.library.log.LogStatus;
import nl.energydata.library.session.Session;
import nl.energydata.library.session.SessionGroup;
import nl.energydata.library.webdriver.WebDriverQueue;

@Getter
@Setter
public class ObjectHolder {
	
	WebDriver webDriver;
	
	WebDriverQueue webDriverQueue;

	SessionGroup sessionGroup;
	
	Session session;
	
	DataProvider dataProvider;
	
	List<DataContainer> dataContainers = new ArrayList<>();
		
	List<Log> logs = new ArrayList<>();
	
	List<Log> errorLogs = new ArrayList<>();
	
	public void addLog(Log log) {
		if(log.getStatus() == LogStatus.ERROR) {
			this.errorLogs.add(log);
		}
		this.logs.add(log);
	}

	public void addContainer(DataContainer dataContainer) {
		this.dataContainers.add(dataContainer);		
	}

}
