package app.desktop.config;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.desktop.listener.ClipboardEvent;
import app.desktop.listener.FileEventListener;

public class MenuProvider {

    private final PopupMenu menu = new PopupMenu();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final ConfigProvider config = ConfigProvider.getInstance();
    private final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, config.getFontSize());

    private final TrayIcon icon;

    public MenuProvider(TrayIcon icon) {
        this.icon = icon;
    }

    public PopupMenu createMenu() {
        menu.setFont(font);
        createClipboardMenuItem();
        createFileChooserMenuItem();
        createExitMenuItem();
        return menu;
    }

    private void createClipboardMenuItem() {
        MenuItem item = new MenuItem("Clipboard");
        item.addActionListener(event -> {
            ClipboardEvent clipboardEvent = new ClipboardEvent(icon);
            executor.submit(clipboardEvent);
        });
        menu.add(item);
    }

    private void createFileChooserMenuItem() {
        MenuItem item = new MenuItem("Choose File");
        item.addActionListener(event -> {
            FileEventListener filechooser = FileEventListener.getInstance(icon);
            executor.submit(filechooser);
        });
        menu.add(item);
    }

    private void createExitMenuItem() {
        MenuItem item = new MenuItem("Exit");
        item.addActionListener(e-> System.exit(0));
        menu.add(item);
    }

}
