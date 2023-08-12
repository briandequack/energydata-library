package nl.energydata.library.webdriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.energydata.library.log.ILogService;
import nl.energydata.library.log.LogService;

@Component
public class WebDriverQueueService {
	
	@Autowired
	private ILogService logService;

    @Autowired
    private WebDriverService webDriverService;
     
	public WebDriverQueue create(int n) {
		return logService.executeSafeWithLog("Create new WebDriverQueue", () -> {
	    List<WebDriver> resources = new ArrayList<>();
	    for (int i = 0; i < n; i++) {
	        resources.add(webDriverService.create());
	    } 
		BlockingQueue<WebDriver> blockingQueue = new LinkedBlockingQueue<>(resources);
		WebDriverQueue queueManager = new WebDriverQueue();
		queueManager.setWebDrivers(blockingQueue);
		return queueManager;
		});
	}
 
	public WebDriver pollFrom(WebDriverQueue queueManager) {	
		return logService.executeSafeWithLog("Poll WebDriver from WebDriverQueue", () -> {
		    BlockingQueue<WebDriver> queue = queueManager.getWebDrivers();
		    try {
		        return queue.poll(5, TimeUnit.SECONDS);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		        return null;
		    }
        });
	}
	
	public WebDriver takeFrom(WebDriverQueue queueManager) {
		return logService.executeSafeWithLog("Take WebDriver from WebDriverQueue", () -> {
			BlockingQueue<WebDriver> queue = queueManager.getWebDrivers();
		    try {
		        WebDriver WebDriver = queue.take();  
		        return WebDriver;
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		    }
			 return null;
		});
	}
	
	public void decrementTasksPending(WebDriverQueue queueManager) {
		logService.executeSafeWithLog("Decrement TasksPending in WebDriverQueue", () -> {
			return queueManager.getTasksPending().getAndDecrement();
		});
	}

	public void returnObject(WebDriverQueue queueManager, WebDriver WebDriver) { 
	    BlockingQueue<WebDriver> queue = queueManager.getWebDrivers();
	    try {      
	        if(queueManager.getTasksPending().get() > 0) {
	            queue.put(WebDriver);
	        } else {
	            WebDriver webDriver = WebDriver;
	            webDriver.quit();
	        }        
	    } catch (InterruptedException e) {
	    	System.out.println("Error." + e.getMessage());
	        Thread.currentThread().interrupt(); 
	    }
	}
}
