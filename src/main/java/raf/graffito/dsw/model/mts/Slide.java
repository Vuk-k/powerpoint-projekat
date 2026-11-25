package raf.graffito.dsw.model.mts;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.IPublisher;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.slajd.ISlajdElement;
import raf.graffito.dsw.model.slajd.SlajdElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class Slide extends GraffLeaf {
    private List<SlajdElement> elementiSlajda = null;
    @JsonCreator
    protected Slide() {}
    public Slide(GraffNode parent, String naziv) {
        super(parent, naziv);
        elementiSlajda = new ArrayList<>();
    }

    public void sortElements() {
        elementiSlajda.sort(new Comparator<SlajdElement>() {
            @Override
            public int compare(SlajdElement o1, SlajdElement o2) {
                return o2.getLayer() - o1.getLayer();
            }
        });
    }

}