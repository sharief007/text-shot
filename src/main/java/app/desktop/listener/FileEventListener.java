package app.desktop.listener;

import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.desktop.config.ConfigProvider;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

public class FileEventListener  implements ActionListener, Runnable{

    private static FileEventListener listener = null;

    private final ConfigProvider config = ConfigProvider.getInstance();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final JFileChooser chooser = new JFileChooser();
    private final ITesseract tesseract = new Tesseract();
    private final Logger logger = Logger.getAnonymousLogger();
    private final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private final FileNameExtensionFilter extFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "svg");

    private FileEventListener() {
        tesseract.setDatapath(config.getDataPath());
        chooser.setDialogTitle("Choose a file to read from");
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(extFilter);
    }

    public static FileEventListener getInstance() {
        if(Objects.isNull(listener)) {
            listener = new FileEventListener();
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
                String str = tesseract.doOCR(file);
                if(Objects.nonNull(str) && str.length() >0 && !str.isBlank()) {
                    Transferable transferable = new StringSelection(str.trim());
                    clipboard.setContents(transferable, null);
                    logger.info("Text added to clipboard");
                } else {
                    logger.info("Text content not found");
                }
            } 
        } catch (Exception e) {
            logger.severe("Error occured while reading and performing OCR on Selected File");
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        executor.submit(this);
    }
}
