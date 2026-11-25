package raf.graffito.dsw.model.logger;

public class LoggerFactory {
    public static Logger dajLogger(String tip) {
        if (tip.toLowerCase().equals("console")) return new ConsoleLogger();
        if (tip.toLowerCase().equals("file")) return new FileLogger();

        return null;
    }
}
