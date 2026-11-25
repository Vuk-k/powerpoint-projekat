package raf.graffito.dsw.model.logger;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.InfoMessage;
import raf.graffito.dsw.model.messages.Message;
import raf.graffito.dsw.model.messages.WarnningMessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class FileLogger extends Logger {
    @Override
    public void log(Message m) {
        try {
            if (!(new File("log.txt").exists())) {
                System.out.println("PRAVIM FILE");
                new File("log.txt").createNewFile();
            }
            FileWriter fw = new FileWriter(new File("log.txt"), true);
            //BufferedWriter bfw = new BufferedWriter(fw);

            if (m instanceof InfoMessage) {
                fw.append("[INFO][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
                fw.append("\n");
            } else if (m instanceof WarnningMessage) {
                fw.append("[WARNNING][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
                fw.append("\n");
            } else if (m instanceof ErrorMessage) {
                fw.append("[ERROR][" + DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm").format(m.getTimeStamp()) + "] " + m.getContent());
                fw.append("\n");
            } else {
                log(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("FILE_LOGGER_CETVRTI_TIP_PORUKE_?!", "error"));
            }
            //bfw.close();
            fw.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
