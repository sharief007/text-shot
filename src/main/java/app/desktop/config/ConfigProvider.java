package app.desktop.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;

public class ConfigProvider {

    private ConfigProvider() {
        try {
            trayIcon = new URL("https://github.com/sharief007/text-shot/blob/master/src/main/resources/icon.png?raw=true");
        } catch (MalformedURLException e) {
            logger.severe("Tray Icon not found");
            logger.severe(e.getMessage());
        }
    }

    private static ConfigProvider provider = null;

    private final String homePath = System.getProperty("user.home");
    private final String dataPath =  homePath + File.separator + "tessdata";
    private final String trainedDataURL = "https://github.com/tesseract-ocr/tessdata/blob/main/eng.traineddata?raw=true";
    private final int fontSize = 14;
    private final Logger logger = Logger.getAnonymousLogger();
    private URL trayIcon = null;

    public static ConfigProvider getInstance() {
        if (Objects.isNull(provider)) {
            provider = new ConfigProvider();
        }
        return provider;
    }

    public String getDataPath() {
        return dataPath;
    }

    public String getTrainedDataURL() {
        return trainedDataURL;
    }

    public int getFontSize() {
        return fontSize;
    }

    public URL getTrayIcon() {
        return trayIcon;
    }

    public String getHomePath() {
        return homePath;
    }

}
