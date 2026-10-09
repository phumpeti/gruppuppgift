package com.example.decathlon.selenium.webui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;



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

    @BeforeEach
    void resetApplication() throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/test/reset"))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(204, response.statusCode());
    }


    @Test
    void insertAndCheckName() {
        //Lägg till en deltagare (namn)
        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            String name = "Henric";
            driver.findElement(By.id("name")).sendKeys(name);
            driver.findElement(By.id("add")).click();

            String name2 = driver.findElement(By.cssSelector("#standings tr td:first-child")).getText();

            assertEquals(name, name2);

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

            int split = Integer.parseInt(result.split(" ")[2].replace("(", ""));

            int calcResult = (int) (25.4347 * Math.pow(18 - 12, 1.81));

            assertEquals(calcResult, split);

        } finally {
            driver.quit();
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

            Select event = new Select(driver.findElement(By.id("event")));
            event.selectByValue("400m");

            double firstResult = 60;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(firstResult));
            driver.findElement(By.id("save")).click();

            driver.findElement(By.id("raw")).clear();

            Select event2 = new Select(driver.findElement(By.id("event")));
            event2.selectByValue("longJump");

            double secondResult = 654;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(secondResult));
            driver.findElement(By.id("save")).click();

            driver.findElement(By.id("raw")).clear();

            String textResult = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[4]")
            ).getText();

            int result = Integer.parseInt(textResult);

            int trackResult = (int) (1.53775 * Math.pow( 82 - firstResult, 1.81));
            int fieldResult = (int) (0.14354 * Math.pow((secondResult - 220), 1.4));

            assertEquals(trackResult + fieldResult, result);

        } finally {
            driver.quit();
        }
    }
    @Test
    void addCompetitorAndCheckRanking() {
        //Lägg till ytterligare deltagare samt resultat - kontrollera att placering visas/uppdateras
        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            driver.findElement(By.id("name2")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            Select event = new Select(driver.findElement(By.id("event")));
            event.selectByValue("400m");

            double firstResult = 60;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(firstResult));
            driver.findElement(By.id("save")).click();

            String textResult = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()-1]")
            ).getText();

            String textRank1 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()]")
            ).getText();

            driver.findElement(By.id("raw")).clear();
            driver.findElement(By.id("name2")).clear();

            driver.findElement(By.id("name2")).sendKeys("Emma");
            driver.findElement(By.id("add")).click();

            Select event2 = new Select(driver.findElement(By.id("event")));
            event2.selectByValue("longJump");

            double secondResult = 654;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(secondResult));
            driver.findElement(By.id("save")).click();

            String textResult2 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Emma']/td[last()-1]")
            ).getText();

            String textRank2 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Emma']/td[last()]")
            ).getText();

            int result = Integer.parseInt(textResult);
            int result2 = Integer.parseInt(textResult2);

            System.out.println("Test 1");
            if (result > result2) {
                assertEquals("1", textRank1);
                System.out.print("Henric is in the lead");
            } else if (result < result2) {
                assertEquals("1", textRank2);
                System.out.println("Emma is in the lead");
            } else {
                System.out.println("Same points");
            }

            driver.findElement(By.id("name2")).clear();
            driver.findElement(By.id("raw")).clear();

            driver.findElement(By.id("name2")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            Select event3 = new Select(driver.findElement(By.id("event")));
            event3.selectByValue("100m");

            double thirdResult = 11;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(thirdResult));
            driver.findElement(By.id("save")).click();

            String textResult3 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()-1]")
            ).getText();

            String textRank3 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()]")
            ).getText();

            int result3 = Integer.parseInt(textResult3);

            System.out.println("Test 2");
            if (result3 > result2) {
                assertEquals("1", textRank1);
                System.out.print("Henric is in the lead");
            } else if (result3 < result2) {
                assertEquals("1", textRank2);
                System.out.print("Emma is in the lead");
            } else {
                System.out.println("Same points");
            }

        } finally {
            driver.quit();
        }
    }

    @Test
    void exportData() throws IOException {
        //Exportera data.

        //OBS nedanstående test är ej färdigt

        Path csvFile = Path.of("/home/henric/results.csv");
        Files.exists(csvFile);
        Files.deleteIfExists(csvFile);

        WebDriver driver = new FirefoxDriver();

        try {
            driver.get("http://localhost:8080/");

            driver.findElement(By.id("name2")).sendKeys("Henric");
            driver.findElement(By.id("add")).click();

            Select event = new Select(driver.findElement(By.id("event")));
            event.selectByValue("400m");

            double firstResult = 60;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(firstResult));
            driver.findElement(By.id("save")).click();

            String textResult = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()-1]")
            ).getText();

            String textRank1 = driver.findElement(
                    By.xpath("//tbody[@id='standings']//tr[td[1]='Henric']/td[last()]")
            ).getText();

            driver.findElement(By.id("raw")).clear();
            driver.findElement(By.id("name2")).clear();

            driver.findElement(By.id("name2")).sendKeys("Emma");
            driver.findElement(By.id("add")).click();

            Select event2 = new Select(driver.findElement(By.id("event")));
            event2.selectByValue("longJump");

            double secondResult = 654;
            driver.findElement(By.id("raw")).sendKeys(String.valueOf(secondResult));
            driver.findElement(By.id("save")).click();


            driver.findElement(By.id("export")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(d -> {
                try {
                    return Files.exists(csvFile) && Files.size(csvFile) > 0;
                } catch (IOException e) {
                    return false;
                }
            });


        } finally {
            driver.quit();
        }

    }




}