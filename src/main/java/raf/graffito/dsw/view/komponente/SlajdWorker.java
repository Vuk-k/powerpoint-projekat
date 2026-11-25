package raf.graffito.dsw.view.komponente;

import raf.graffito.dsw.model.mts.Slide;
import raf.graffito.dsw.model.slajd.SlajdElement;

public class SlajdWorker {
    public SlajdWorker(Slide s) {
        if (s == null) setNoSlide();
        else {
            setSlide(s);
        }
    }

    public void setNoSlide() {

    }

    public void setSlide(Slide s) {

    }
}
