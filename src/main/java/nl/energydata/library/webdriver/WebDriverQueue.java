package nl.energydata.library.webdriver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.WebDriver;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebDriverQueue {
    private BlockingQueue<WebDriver> webDrivers;
    private AtomicInteger tasksPending = new AtomicInteger(0);
}
