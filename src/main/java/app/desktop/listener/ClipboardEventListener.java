package app.desktop.listener;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import app.desktop.config.ConfigProvider;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class ClipboardEventListener implements ActionListener, Runnable {

    private static ClipboardEventListener eventListener = null;
    private final ConfigProvider config = ConfigProvider.getInstance();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ITesseract tesseract = new Tesseract();
    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final Clipboard clipboard = toolkit.getSystemClipboard();
    private final Logger logger = Logger.getAnonymousLogger();

    private ClipboardEventListener(){
        tesseract.setDatapath(config.getHomePath());
    }

    public static ClipboardEventListener getInstance() {
        if(Objects.isNull(eventListener)) {
            eventListener = new ClipboardEventListener();
        }
        return eventListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        executor.submit(this);
    }

    @Override
    public void run() {
        try {
            BufferedImage buff  = readImageFromClipboard();
            if(Objects.nonNull(buff)) {
                logger.info("Applying Tessaract OCR");
                String str = tesseract.doOCR(buff);
                logger.info("Conversion successful");
                if(Objects.nonNull(str) && str.length()>0 && !str.isBlank()) {
                    Transferable transferable = new StringSelection(str.trim());
                    clipboard.setContents(transferable, null);
                    logger.info("Text added to clipboard");
                } else {
                    logger.warning("No text found");
                }
            }
        } catch (Exception e) {
            logger.severe("Error occured while reading and performing OCR on clipboard data");
            logger.severe(e.getMessage());
        }
    }

    private BufferedImage readImageFromClipboard() throws UnsupportedFlavorException, IOException {
        logger.info("Trying to acces clipboard");
        Transferable trans = clipboard.getContents(null);
        if(Objects.nonNull(trans) && trans.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            logger.info("Reading Image data");
            Image img = (Image) trans.getTransferData(DataFlavor.imageFlavor);
            BufferedImage buff = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = buff.createGraphics();
            graphics.drawImage(img, 0, 0, null);
            graphics.dispose();
            logger.info("Successfully read image data");
            return buff;
        }
        logger.warning("Image data not found");
        return null;
    }
}
