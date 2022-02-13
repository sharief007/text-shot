package app.desktop;

import app.desktop.utilities.DataProvider;
import app.desktop.utilities.TrayUtility;

public class App 
{
    private final static DataProvider dataProvider = new DataProvider();

    static {
        dataProvider.checkAndDownloadTrainedData();
    }

    public static void main(String[] args )
    {
        try {
            TrayUtility utility = new TrayUtility();
            utility.initTray();
            utility.createMenu();
            utility.setupSystemTray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
