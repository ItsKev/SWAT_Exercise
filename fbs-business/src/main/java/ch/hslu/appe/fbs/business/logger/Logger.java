package ch.hslu.appe.fbs.business.logger;


import org.apache.logging.log4j.LogManager;

public class Logger {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Logger.class.getName());

    public static void logInfo(String username, String message) {
        LOG.info(username + " - " + message);
    }

    public static void logWarning(String username, String message) {
        LOG.warn(username + " - " + message);
    }

    public static void logError(String username, String message) {
        LOG.error(username + " - " + message);
    }

}
