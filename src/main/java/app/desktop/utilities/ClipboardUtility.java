package app.desktop.utilities;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;


public class ClipboardUtility {

    private final Toolkit toolkit = Toolkit.getDefaultToolkit();
    private final Clipboard clipboard = toolkit.getSystemClipboard();
    private final Logger logger = Logger.getLogger(getClass().getName());


    public BufferedImage readImageFromClipboard() throws UnsupportedFlavorException, IOException {
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

    public void writeToClipboard(String text) {
        Transferable transferable = new StringSelection(text.trim());
        clipboard.setContents(transferable, null);
        logger.info("Text added to clipboard");
    }
    
}
