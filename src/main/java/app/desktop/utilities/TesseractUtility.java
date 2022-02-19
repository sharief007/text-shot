package app.desktop.utilities;

import java.util.Objects;
import java.util.logging.Logger;
import java.awt.image.*;
import java.io.File;

import app.desktop.config.ConfigProvider;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TesseractUtility {

    private final ITesseract tesseract = new Tesseract();
    private final ConfigProvider config = ConfigProvider.getInstance();
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static TesseractUtility tessaractUtility = null;

    private TesseractUtility() {
        tesseract.setDatapath(config.getDataPath());
        logger.info("Tessaract Initialized");
    }
    
    public static TesseractUtility getInstance() {
        if(Objects.isNull(tessaractUtility)) {
            tessaractUtility = new TesseractUtility();
        }
        return tessaractUtility;
    }

    public String runOCR(BufferedImage image) {
        String textContent = new String();
        try {
            logger.info("Applying Tessaract OCR");
            textContent =  this.tesseract.doOCR(image);
            logger.info("Conversion successful");
        } catch (TesseractException e) {
            logger.warning("Error extracting text content from image");
        }
        return textContent;
    }

    public String runOCR(File image) {
        String textContent = new String();
        try {
            logger.info("Applying Tessaract OCR");
            textContent =  this.tesseract.doOCR(image);
            logger.info("Conversion successful");
        } catch (TesseractException e) {
            logger.warning("Error extracting text content from image");
        }
        return textContent;
    }

}
