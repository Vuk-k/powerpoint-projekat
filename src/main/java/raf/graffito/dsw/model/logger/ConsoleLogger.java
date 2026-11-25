package raf.graffito.dsw.model.logger;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.InfoMessage;
import raf.graffito.dsw.model.messages.Message;
import raf.graffito.dsw.model.messages.WarnningMessage;

import java.time.format.DateTimeFormatter;

public class ConsoleLogger extends Logger {
    @Override
    public void log(Message m) {
        if (m instanceof InfoMessage) {
            System.out.println("[INFO][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
        } else if (m instanceof WarnningMessage) {
            System.out.println("[WARNNING][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
        } else if (m instanceof ErrorMessage) {
            System.out.println("[ERROR][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
        } else {
            log(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("CONSOLE_LOGGER_CETVRTI_TIP_PORUKE_?!", "error"));
        }
    }
}