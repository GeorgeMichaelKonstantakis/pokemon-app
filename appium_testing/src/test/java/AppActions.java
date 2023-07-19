import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.io.IOException;

public class AppActions {

    private String applicationId = "com.gkonstantakis.pokemon";

    public AppActions() {
    }

    public int pressHomeButton() throws IOException, InterruptedException {
        String cmd = "adb shell input keyevent 3";
        Process exec = Runtime.getRuntime().exec(cmd); //press home button key
        exec.waitFor();
        return exec.exitValue();
    }

    public int pressBackButton() throws IOException, InterruptedException {
        String cmd = "adb shell input keyevent 4";
        Process exec = Runtime.getRuntime().exec(cmd); //press back button key
        exec.waitFor();
        return exec.exitValue();
    }

    public void scrollToElementByText(AndroidDriver<MobileElement> driver, String text) {
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + text + "\").instance(0))");
    }

}
