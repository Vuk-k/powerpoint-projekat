package raf.graffito.dsw.model.logger;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.Message;

public abstract class Logger implements ISubscriber {
    public abstract void log(Message m);
    @Override
    public void notify(Object notf) {
        if (notf instanceof Message) log((Message) notf);
        else log(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("LOGGER_PORUKA_NIJE_PORUKA_?!", "error"));
    }
}
