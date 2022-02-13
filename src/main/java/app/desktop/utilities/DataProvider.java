package app.desktop.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import app.desktop.config.ConfigProvider;

public class DataProvider {

    private ConfigProvider config = ConfigProvider.getInstance();
    private final Logger logger = Logger.getAnonymousLogger();
    
    public void checkAndDownloadTrainedData() {
        File dir = new File(config.getDataPath());
        File trainedData = new File(dir, "eng.traineddata");
        if(!trainedData.exists()) {
            try {
                dir.mkdirs();
                FileOutputStream oStream = new FileOutputStream(trainedData);
                URL url = new URL(config.getTrainedDataURL());
                InputStream iStream = url.openStream();
                iStream.transferTo(oStream);
                iStream.close();
                oStream.close();
            } catch (IOException e) {
                logger.severe("Cannot download trained data from "+ config.getTrainedDataURL());
                logger.severe(e.getMessage());
                System.exit(0);
            } finally {
                logger.info("Downloaded trained data");
            }
        }
    }

}
