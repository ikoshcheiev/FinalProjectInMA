package org.example.webDriverManager;

import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

import static io.github.bonigarcia.wdm.WebDriverManager.*;

public class Driver  {
    private static final ThreadLocal<WebDriver> driverTL = new ThreadLocal<>();

    private Driver() {
    }

    public static WebDriver getDriver() {
        if (driverTL.get() == null) {
            init();
        }
        return driverTL.get();
    }

    public static void killDriver() {
        if (driverTL.get() != null) {
            driverTL.get().quit();
            driverTL.remove();
        }
    }

    private static void init() {
        String browser = System.getProperty("browser", "chrome");
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driverTL.set(new ChromeDriver());
                driverTL.get().manage().window().maximize();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driverTL.set(new FirefoxDriver());
                driverTL.get().manage().window().maximize();
                break;
            case "edge":
                edgedriver().setup();
                driverTL.set(new EdgeDriver());
                break;
            case "IE":
                iedriver().setup();
                driverTL.set(new InternetExplorerDriver());
                break;
            case "opera":
                operadriver().setup();
                driverTL.set(new OperaDriver());
                break;
            default:
                throw new IllegalArgumentException(String.format("Browser %s not found", browser));

        }
        driverTL.get().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS); //было 10 сек, поменял на 5
        driverTL.get().manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);
        driverTL.get().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}