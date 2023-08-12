package nl.energydata.library.webdriver;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.energydata.library.log.ILogService;
import nl.energydata.library.log.LogService;

@Service
public class WebDriverService {
	
	@Autowired
	ILogService logService;
	
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
	private ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
	
		
    public WebDriverService() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
    }
    
    public WebDriver create() {
    	return logService.executeSafeWithLog("Create new ChromeDriver", () -> {
    		ChromeOptions options = new ChromeOptions();
    		
    		List<String> userAgents = new ArrayList<>();
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.120 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
    		userAgents.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

   
    		options.addArguments("--user-agent=" + getRandomUserAgent(userAgents));	
    		
    		
    		
    		options.addArguments("--start-maximized");
    		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    		options.addArguments("--disable-blink-features=AutomationControlled");
    		ChromeDriver driver = new ChromeDriver(options);
    		return driver;
    		
    	
    	});
    }
    
    
    
    private  String getRandomUserAgent(List<String> userAgents) {
        int randomIndex = new Random().nextInt(userAgents.size());
        return userAgents.get(randomIndex);
    }
    
    
    public WebDriver setLocalWebDriver(WebDriver webDriver) {
    	return logService.executeSafeWithLog("Set WebDriver and Wait ThreadLocal for WebDriverService", () -> {
	    	this.webDriver.set(webDriver);
	    	this.wait.set(new WebDriverWait(webDriver,  Duration.ofSeconds(30)));
	    	return this.webDriver.get();
    	});
    }
    
    public WebDriver getLocalWebDriver() {
    	return logService.executeSafeWithLog("Get WebDriver ThreadLocal from WebDriverService", () -> {
    		return this.webDriver.get();
    	});
    }
    

    public void clearLocal() {
    	logService.executeSafeWithLog("Clear ThreadLocal for WebDriverService", () -> {
	    	this.webDriver.remove();
	    	this.wait.remove();
	    	return null;
    	});
    }
    
    public BigDecimal getBigDecimalAtIndex(WebElement webElement, int index) {	
    	return logService.executeSafeWithLog(String.format("Get number at index '%s'", index), () -> {
        try {
        	String regex = "\\b\\d+[,.]\\d+\\b";
        	Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(getWordAtIndex(webElement, index));
            String numberString = null;
            while (matcher.find()) {
            	numberString = matcher.group();
                System.out.println(matcher.group());
            }
            
            BigDecimal number = new BigDecimal(numberString.replace(",", "."));
            return number;
        } catch (NumberFormatException e) {
            return null;
        }
    	});
    }
          
    public String getWordAtIndex(WebElement webElement, int index) {
    	return logService.executeSafeWithLog(String.format("Get word at index '%s'", index), () -> {
    		return toWordsArray(webElement)[index];
    	});
    }
      
    public void printWordsArray(WebElement webElement) {
    	logService.executeSafeWithLog(String.format("Print words array"), () -> {
	    	String[] wordsArray = toWordsArray(webElement);
	   	 	for (int i = 0; i < wordsArray.length; i++) {
	            String word = wordsArray[i];
	            System.out.println("Index " + i + ": " + word);
	        }
    	});
    }
    
    public void matchStringAtIndex(WebElement webElement, int index, String string) {
    	logService.executeSafeWithLog(String.format("Match string '%s' at index '%s'", string, index), () -> {
    		String wordToMatch = getWordAtIndex(webElement, index);
	    	if (!wordToMatch.equals(string)) {
	           throw new IllegalArgumentException();
	        }
    	});
    }
    
    public String[] toWordsArray(WebElement webElement) {
    	return logService.executeSafeWithLog(String.format("WebElement to words array"), () -> {
	    	String[] wordsArray = webElement.getText().split("\\s+");  
	    	return wordsArray;
    	});
    }
    
    public WebElement scrollIntoViewAndGetUpdatedElement(WebElement webElement, String description) {
    	return logService.executeSafeWithLog(String.format("scrollIntoViewAndGetUpdatedElement, description '%s'",description), () -> {
	    	JavascriptExecutor je = (JavascriptExecutor) webDriver.get();
	    	je.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);
	    	WebElement updatedElement = getUpdatedElement(webElement, description);	    
	    	return updatedElement;
    	});
    }
    
    public void scrollElementIntoView(WebElement webElement, String description) {  	
    	logService.executeSafeWithLog(String.format("Click Element, description '%s'",description), () -> {
	    	JavascriptExecutor je = (JavascriptExecutor) webDriver.get();
	    	je.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);
    	});
    }
        

    public void clickElement(WebElement webElement, String description) {  	
    	logService.executeSafeWithLog(String.format("Click Element, description '%s'",description), () -> {
	    	JavascriptExecutor je = (JavascriptExecutor) webDriver.get();
	    	je.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);   
	    	 try {
	    		 webElement.click();
	         } catch (Exception e) {
	             je = (JavascriptExecutor) webDriver.get();
	             try {
	                 je.executeScript("arguments[0].click();", webElement);
	             } catch (Exception jsException) {
	                 throw new RuntimeException("Failed to click element: " + description, jsException);
	             }
	         }
    	});
    }
         
    public void fillElement(WebElement webElement, String input, String description) {  	
    	logService.executeSafeWithLog(String.format("Fill Element with '%s', description '%s'", input, description), () -> {
	    	JavascriptExecutor je = (JavascriptExecutor) webDriver.get();
	    	
	    	je.executeScript("arguments[0].scrollIntoView({block: 'center'});", webElement);
	    	WebElement updatedElement = getUpdatedElement(webElement, description);	    	
	    	updatedElement.click();
	    	randomSleep(1,2);
	    	updatedElement = getUpdatedElement(webElement, description);	 
	    	if (!updatedElement.getAttribute("value").isEmpty()) {
	    		updatedElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
	    		updatedElement = getUpdatedElement(webElement, description);
		    	if (!updatedElement.getAttribute("value").isEmpty()) {
		    		je.executeScript("arguments[0].value = '';", updatedElement);
				} 
			} 

	        webElement.sendKeys(input);  	
    	});
    }
    
    public WebElement getUpdatedElement(WebElement webElement, String description) {
    	String webElementString = webElement.toString();
		int startIndex = webElementString.lastIndexOf(":") + 1;
		String finalString = webElementString.substring(startIndex).trim();
		finalString = finalString.substring(0, finalString.length() - 1);
		System.out.println("final string: " + finalString);
		WebElement updatedWebElement = null; 
		if (webElementString.contains("xpath")) {
			 return updatedWebElement = getElementByxpath(finalString, description);
	    } else if (webElementString.contains("css")) {
	    	return updatedWebElement = getElementBycssSelector(finalString, description);
	    }
		return updatedWebElement;
    }
    

    // Get element(s) from webDriver.
	public WebElement getElementByxpath(String xpath, String description) {
	    return logService.executeSafeWithLog(String.format("Find element '%s' by xpath '%s'", description, xpath), () -> {   
	    	WebElement element = getElementsByxpath(xpath, description).get(0);
	    	return element;
	    });
	}
	 
	public WebElement getElementByxpath(String xpath, Integer index, String description) {
	    return logService.executeSafeWithLog(String.format("Find element '%s' by xpath '%s' at index %s", description, xpath, index), () -> {   
	    	WebElement element = getElementsByxpath(xpath, description).get(index);
	    	System.out.println(description + "-> index" + index);
	    	return element;
	    });
	}
	
	public List<WebElement> getElementsByxpath(String xpath, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Elements by css Selector '%s', description '%s'", xpath, description), () -> {    	
    		List<WebElement> elements = wait.get().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath)));
    		return elements;
    	});
    }

     
    public WebElement getElementBycssSelector(String cssSelector, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Element by css Selector '%s', description '%s'", cssSelector, description), () -> {
	    	WebElement element = getElementsBycssSelector(cssSelector, description).get(0);
	    	return element;
    	});
    }
        
    public WebElement getElementBycssSelector(String cssSelector, Integer integer, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Element by css Selector '%s', description '%s'", cssSelector, description), () -> {
	    	WebElement element = getElementsBycssSelector(cssSelector, description).get(integer);
	    	return element;
    	});
    }
      
    public List<WebElement> getElementsBycssSelector(String cssSelector, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Elements by css Selector '%s', description '%s'", cssSelector, description), () -> {
    		List<WebElement> elements = wait.get().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(cssSelector)));
    		return elements;	
    	});
    }
    
    
    // Get element(s) from webElement.
    public WebElement getElementBycssSelector(WebElement webElement, String cssSelector, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Element by css Selector '%s', description '%s'", cssSelector, description), () -> {
	    	WebElement element = getElementsBycssSelector(cssSelector, description).get(0);
	    	return element;
    	});
    }
    
    public WebElement getElementBycssSelector(WebElement webElement, String cssSelector, Integer integer, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Element by css Selector '%s', description '%s'", cssSelector, description), () -> {
	    	WebElement element = getElementsBycssSelector(cssSelector, description).get(integer);
	    	return element;
    	});
    }
    
    public List<WebElement> getElementsBycssSelector(WebElement webElement, String cssSelector, String description) { 
    	return logService.executeSafeWithLog(String.format("Get Elements by css Selector '%s', description '%s'", cssSelector, description), () -> {
    		return webElement.findElements(By.cssSelector(cssSelector));
    	});
    }
        
   
    
    // Get shadowRoot(s) from webDriver.
    public List<SearchContext> getShadowRootsBycssSelector(String cssSelector, String description) {
    	return logService.executeSafeWithLog(String.format("Search ShadowRoots for '%s', description '%s'", cssSelector, description), () -> {
  
    		WebElement rootElement = webDriver.get().findElement(By.cssSelector(cssSelector));
 
    		String script = "var shadowRoots = [];" +
                    "function findShadowRootsAndChildren(currentElement) {" +
                    "   if (currentElement.shadowRoot) {" +
                    "       shadowRoots.push(currentElement.shadowRoot);" +
                    "       var shadowChildren = currentElement.shadowRoot.children;" +
                    "       for (var i = 0; i < shadowChildren.length; i++) {" +
                    "           findShadowRootsAndChildren(shadowChildren[i]);" +
                    "       }" +
                    "   }" +
                    "   var children = currentElement.children;" +
                    "   for (var i = 0; i < children.length; i++) {" +
                    "       findShadowRootsAndChildren(children[i]);" +
                    "   }" +
                    "}" +
                    "findShadowRootsAndChildren(arguments[0]);" +
                    "return shadowRoots;";
            
            @SuppressWarnings("unchecked")
			List<SearchContext> shadowRoots = (List<SearchContext>) ((JavascriptExecutor) webDriver.get()).executeScript(script, rootElement);
            return shadowRoots;
    	});
    }
    



    public static void randomSleep(int min, int max) {
        Random rand = new Random();
        try {
        	int maxMil = max*1000;
        	int minMil = min*1000;
        	
            int sleepTime = rand.nextInt((maxMil - minMil) + 1) + minMil; 
    
            
            System.out.println("Sleeping for " + sleepTime + " seconds...");
            Thread.sleep(sleepTime);
            System.out.println("Awake after sleeping for " + sleepTime + " seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
	/*
	if (!elements.isEmpty()) {
		return elements;
	} else {
		List<SearchContext>shadowRoots = getShadowRootsBycssSelector("html", description);
		for (SearchContext shadowRoot : shadowRoots) {
           try {
           List<WebElement> shadowElements = shadowRoot.findElements(By.xpath(xpath));
           elements.addAll(shadowElements);		
           } catch(NoSuchElementException  e) {
           }
       } if (!elements.isEmpty()) {
    	   return elements;
       }  			
	}
	return null;
	*/

}
