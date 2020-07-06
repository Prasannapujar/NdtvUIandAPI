package ndtv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class Page {
    private static Logger log= LogManager.getLogger(Page.class);

    WebDriver driver;

    public Page(WebDriver driver) {
      this.driver=driver;
    }



}
