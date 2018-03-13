package pt.uc.dei.as.loggerbeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Utils {

    private static HashMap<Integer, Logger> logger = new HashMap<>();

    public static Logger getLogger(int key) {
        if (!logger.containsKey(key))
            logger.put(key, LoggerFactory.getLogger("EPE_Logger_" + key));

        return logger.get(key);
    }
}
