package app.desktop.listener;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.logging.Logger;

import app.desktop.utilities.ClipboardUtility;
import app.desktop.utilities.TesseractUtility;

public class ClipboardEvent implements Runnable {

    private final TesseractUtility tessaractUtility = TesseractUtility.getInstance();
    private final ClipboardUtility clipboardUtility = new ClipboardUtility();
    private final Logger logger = Logger.getLogger(getClass().getName());

    private final TrayIcon trayIcon; 

    public ClipboardEvent(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    @Override
    public void run() {
        try {
            BufferedImage buff  = clipboardUtility.readImageFromClipboard();
            if(Objects.nonNull(buff)) {
                String str = tessaractUtility.runOCR(buff);
                if(str.isEmpty() || str.isBlank()) {
                    logger.warning("No text found");
                } else {
                    clipboardUtility.writeToClipboard(str);
                    trayIcon.displayMessage("Text copied to Clipboard", new String(), MessageType.INFO);
                }
            }
        } catch (Exception e) {
            logger.severe("Error occured while reading and performing OCR on clipboard data");
            logger.severe(e.getMessage());
        }
    }
}
