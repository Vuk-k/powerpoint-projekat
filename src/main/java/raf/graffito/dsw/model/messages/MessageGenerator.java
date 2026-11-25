package raf.graffito.dsw.model.messages;

import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.Publisher;

public class MessageGenerator extends Publisher {
    public Message generateMessage(String content, String type) {
        type = type.toLowerCase();
        if (type.equals("error")) return new ErrorMessage(content);

        if (type.equals("warnning")) return new WarnningMessage(content);

        if (type.equals("info")) return new InfoMessage(content);

        return null;
    }
    @Override
    public boolean addSuscriber(ISubscriber s) {
        getSubs().add(s);
        return true;
    }

    @Override
    public boolean removeSubscriber(ISubscriber s) {
        getSubs().remove(s);
        return true;
    }

    @Override
    public void notifySubscriers(Object notf) {
        getSubs().forEach(s -> {
            s.notify(notf);
        });
    }
}
