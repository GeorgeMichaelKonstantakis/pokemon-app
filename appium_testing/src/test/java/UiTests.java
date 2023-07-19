import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;

public class UiTests {

    public UiTests() {
    }

    private final static String APP_PACKAGE_NAME = "com.gkonstantakis.pokemon";
    private final static String APP_ACTIVITY_NAME = "com.gkonstantakis.pokemon.ui.activities.MainActivity";
    private final String emulatorId = "emulator-5554";
    private final String pokemonAppPath = "/Users/george/Desktop/pokemon_v1.0.apk";

    private AndroidDriver<MobileElement> driver;
    private final CsvWriter csvWriter = new CsvWriter();
    private final AppActions appActions = new AppActions();

    private Boolean appNotInstalled = true;

    @BeforeTest
    public void setup() throws IOException {

        DesiredCapabilities dc = new DesiredCapabilities();
        if (appNotInstalled) {
            dc.setCapability(MobileCapabilityType.APP, "/Users/george/Desktop/pokemon_v1.0.apk");
        }
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, emulatorId);
        dc.setCapability("platformName", Platform.ANDROID);
        dc.setCapability(MobileCapabilityType.NO_RESET, true);
        dc.setCapability(MobileCapabilityType.UDID, emulatorId);
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE_NAME);
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY_NAME);
        dc.setCapability("autoGrantPermissions", true);
        dc.setCapability("autoDismissAlerts", true);
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
    }

    @Test
    public void appInstallation() {
        ResultsCsv resultsCsv = new ResultsCsv();
        resultsCsv.setTestName("installation");
        resultsCsv.setTimestamp(driver.getDeviceTime());
        try {
            Thread.sleep(2000);
            resultsCsv.setTestPassed(true);
            csvWriter.toCsv(resultsCsv);
        } catch (Exception e) {
            e.printStackTrace();
            resultsCsv.setTestPassed(false);
            resultsCsv.setTestResultMessage(e.toString());
            csvWriter.toCsv(resultsCsv);
        }
    }

    @Test
    public void clickPokemonItemAndOpenPokemonInfo() {
        ResultsCsv resultsCsv = new ResultsCsv();
        String testName = "clickPokemonItemAndOpenPokemonInfo";
        resultsCsv.setTestName(testName);
        resultsCsv.setTimestamp(driver.getDeviceTime());
        try {
            Thread.sleep(1500);
            appActions.scrollToElementByText(driver, ViewElementsUtils.pokemonName);
            Thread.sleep(1500);
            MobileElement adapterItemImage = driver.findElement(By.xpath(ViewElementsUtils.xPathBulbausaurAdapterItemImage));
            adapterItemImage.click();
            MobileElement pokemonCardWeightTextView = driver.findElement(By.xpath(ViewElementsUtils.xPathWeightTextView));
            Thread.sleep(100);
            MobileElement pokemonCardNameTextView = driver.findElement(By.xpath(ViewElementsUtils.xPathNameTextView));
            Thread.sleep(100);
            MobileElement pokemonCardHeightTextView = driver.findElement(By.xpath(ViewElementsUtils.xPathHeightTextView));
            Thread.sleep(100);
            MobileElement pokemonCardBaseExperienceTextView = driver.findElement(By.xpath(ViewElementsUtils.xPathBaseExperienceTextView));
            Thread.sleep(100);
            MobileElement pokemonCardAbilitiesTextView = driver.findElement(By.xpath(ViewElementsUtils.xPathAbilitiesTextView));
            Thread.sleep(100);
            MobileElement dismissPokemonInfoButton = driver.findElement(By.xpath(ViewElementsUtils.xPathDismissButton));
            Thread.sleep(1000);
            if(pokemonCardWeightTextView.isDisplayed() && pokemonCardNameTextView.isDisplayed() && pokemonCardHeightTextView.isDisplayed()
                    && pokemonCardBaseExperienceTextView.isDisplayed() && pokemonCardAbilitiesTextView.isDisplayed() && dismissPokemonInfoButton.isDisplayed()) {
                dismissPokemonInfoButton.click();
                resultsCsv.setTestPassed(true);
            } else {
                resultsCsv.setTestPassed(false);
            }
            Thread.sleep(300);
            csvWriter.toCsv(resultsCsv);
        } catch (Exception e) {
            e.printStackTrace();
            resultsCsv.setTestPassed(false);
            resultsCsv.setTestResultMessage(e.toString());
            csvWriter.toCsv(resultsCsv);
        }
    }

}
