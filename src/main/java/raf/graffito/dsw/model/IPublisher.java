package raf.graffito.dsw.model;

import lombok.Getter;

import java.util.List;

public interface IPublisher {
    public boolean addSuscriber(ISubscriber s);
    public boolean removeSubscriber(ISubscriber s);
    public void notifySubscriers(Object notf);
}
