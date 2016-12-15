package kristoprifti.me.sampleappforprivalia.utils;

import java.io.IOException;

/**
 * Created by k.prifti on 15.12.2016 г..
 */

public class InternetConnectionCheck {

    public boolean hasActiveInternetConnection() throws InterruptedException, IOException
    {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec (command).waitFor() == 0);
    }
}
