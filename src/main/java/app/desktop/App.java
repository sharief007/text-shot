package app.desktop;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.desktop.config.ConfigProvider;
import app.desktop.config.MenuProvider;
import app.desktop.utilities.DataProvider;


public class App 
{
    private final static DataProvider dataProvider = new DataProvider();
    private final static ConfigProvider config = ConfigProvider.getInstance();
    private final static Logger logger = Logger.getGlobal();

    static {
        dataProvider.checkAndDownloadTrainedData();
    }

    public static void main(String[] args )
    {
        if(SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage(config.getTrayIcon());
                TrayIcon  trayIcon= new TrayIcon(image, "TextShot", null);
                MenuProvider menuProvider = new MenuProvider(trayIcon);
                trayIcon.setPopupMenu(menuProvider.createMenu());
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);
                logger.info("Application started");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Failed to access system tray", e);
            }
        } else {
            logger.severe("System tray is not supported. Cannot start the application");
            System.exit(0);
        }
    }
}
