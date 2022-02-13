package app.desktop.utilities;

import java.awt.*;
import java.util.logging.Logger;

import app.desktop.config.ConfigProvider;
import app.desktop.listener.ClipboardEventListener;
import app.desktop.listener.FileEventListener;

public class TrayUtility {
    
    private final ConfigProvider config = ConfigProvider.getInstance();
    private final ClipboardEventListener clipboardListener = ClipboardEventListener.getInstance();
    private final FileEventListener fileEventListener = FileEventListener.getInstance();
    private final Logger logger = Logger.getAnonymousLogger();

    private SystemTray tray = null;

    private final PopupMenu menu = new PopupMenu();
    // private final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, config.getFontSize());

    public void initTray() {
        if(SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            logger.info("Initializing System tray icon");
        } else {
            logger.severe("System tray is not supported. Cannot start the application");
            System.exit(0);
        }
    }

    public void createMenu() {
        // menu.setFont(font);
        MenuItem item = new MenuItem("Clipboard");
        item.addActionListener(clipboardListener);
        menu.add(item);
        item = new MenuItem("Choose File");
        item.addActionListener(fileEventListener);
        menu.add(item);
        item = new MenuItem("Exit");
        item.addActionListener(e-> System.exit(0));
        menu.add(item);
    }


    public void setupSystemTray() throws AWTException {
        Image image = Toolkit.getDefaultToolkit().getImage(config.getTrayIcon());
        TrayIcon icon = new TrayIcon(image, "TextShot", menu);
        tray.add(icon);
        logger.info("Application started");
    }
}
