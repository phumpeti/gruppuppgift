package com.example.decathlon.selenium.webui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DecathlonWebTest {

        //Vilka funktioner vill vi testa i en regressionssvit:
        //Lägg till en deltagare (namn)
        //Välja gren
        //Ange resultat
        //Beräkna poäng per gren
        //Lägga till fler grenar och resultat för en deltagare och att poängen summeras
        //Lägg till ytterligare deltagare samt resultat - kontrollera att placering visas/uppdateras
        //Exportera data
        //Importera data
        //Korrekt poängberäkning (enhetstest i IntelliJ - gränsvärden samt 2001 IAAF scoring rules)

    @Test
    void insertAndCheckName() {
        //Lägg till en deltagare (namn)
        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            driver.findElement(By.id("name")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            String name = driver.findElement(By.cssSelector("#standings tr td:first-child")).getText();

            assertEquals("Henric", name);

        } finally {
            driver.quit();
        }
    }
    @Test
    void chooseParticipant() {
        //Välja gren
        WebDriver driver = new FirefoxDriver();


        try {
            driver.get("http://localhost:8080/");

            Select event = new Select(driver.findElement(By.id("event")));
            event.selectByValue("deca110mHurdles");

            String selectedEvent = event.getFirstSelectedOption().getText();

            assertEquals("Decathlon 110m Hurdles (s)", selectedEvent);

        } finally {
            driver.quit();
        }

    }
    @Test
    void chooseResult() {
        //Ange resultat
        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            driver.findElement(By.id("name2")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            driver.findElement(By.id("raw")).sendKeys("12");
            driver.findElement(By.id("save")).click();

            String result = driver.findElement(
                            By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[2]")
                    ).getText();

            String split = result.split(" ")[0];

            assertEquals("12", split);

        } finally {
            driver.quit();
        }

    }
    @Test
    void calculatePointsPerResult() {
        //Beräkna poäng per gren
        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            driver.findElement(By.id("name2")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            driver.findElement(By.id("raw")).sendKeys("12");
            driver.findElement(By.id("save")).click();

            String result = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[2]")
            ).getText();

            int split = Integer.parseInt(result.split(" ")[2].replace("(",""));

            int calResult = (int)(25.4347*Math.pow(18 - 12, 1.81));

            assertEquals(calResult, split);

        } finally {
              driver.quit();
        }

    }

}
@Test
void addMoreResults() {
    //Lägga till fler grenar och resultat för en deltagare och att poängen summeras
    WebDriver driver = new FirefoxDriver();

    try {
        driver.get("http://localhost:8080/");

        driver.findElement(By.id("name2")).sendKeys("Henric");
        driver.findElement(By.id("add")).click();

        driver.findElement(By.id("raw")).sendKeys("12");
        driver.findElement(By.id("save")).click();

        

        driver.findElement(By.id("raw")).sendKeys("28");
        driver.findElement(By.id("save")).click();

        String result = driver.findElement(
                By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[2]")
        ).getText();

        String split = result.split(" ")[0];

        assertEquals("12", split);

    } finally {
        // driver.quit();
    }

}