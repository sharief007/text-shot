package app.desktop.listener;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.desktop.utilities.ClipboardUtility;
import app.desktop.utilities.TesseractUtility;

public class FileEventListener  implements  Runnable{

    private static FileEventListener listener = null;
    private final JFileChooser chooser = new JFileChooser();
    private final TesseractUtility tesseract = TesseractUtility.getInstance();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private final ClipboardUtility clipboardUtility = new ClipboardUtility();
    private final FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "svg");

    private final TrayIcon trayIcon;

    private FileEventListener(TrayIcon icon) {
        this.trayIcon = icon;
        chooser.setDialogTitle("Choose a file to read from");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(extFilter);
    }

    public static FileEventListener getInstance(TrayIcon icon) {
        if(Objects.isNull(listener)) {
            listener = new FileEventListener(icon);
        }
        return listener;
    }

    @Override
    public void run() {
        try {
            int i = chooser.showOpenDialog(null);
            if(i == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                logger.info("Trying to read file : "+ file.getAbsolutePath());
                String str = tesseract.runOCR(file);
                if(str.isEmpty() || str.isBlank()) {
                    logger.info("Text content not found");
                } else {
                    clipboardUtility.writeToClipboard(str);
                    trayIcon.displayMessage("Text copied to Clipboard", new String(), MessageType.INFO);
                }
            } 
        } catch (Exception e) {
            logger.severe("Error occured while reading and performing OCR on Selected File");
            logger.severe(e.getMessage());
        }
    }
}
