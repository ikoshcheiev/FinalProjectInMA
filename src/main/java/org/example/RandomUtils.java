package org.example;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class RandomUtils {

    public static WebElement getRandomElement(List<WebElement> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
