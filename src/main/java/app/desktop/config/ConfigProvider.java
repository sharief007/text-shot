package app.desktop.config;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class ConfigProvider {

    private ConfigProvider() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        trayIcon = classloader.getResource("icon.png");
    }

    private static ConfigProvider provider = null;

    private final String homePath = System.getProperty("user.home");
    private final String dataPath =  homePath + File.separator + "tessdata";
    private final String trainedDataURL = "https://github.com/tesseract-ocr/tessdata/blob/main/eng.traineddata?raw=true";
    private final int fontSize = 14;
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
