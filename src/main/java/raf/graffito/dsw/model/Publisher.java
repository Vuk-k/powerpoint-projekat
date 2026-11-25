package raf.graffito.dsw.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public abstract class Publisher implements IPublisher {
    @Getter
    private List<ISubscriber> subs;

    public Publisher() {
        subs = new ArrayList<>();
    }
}
