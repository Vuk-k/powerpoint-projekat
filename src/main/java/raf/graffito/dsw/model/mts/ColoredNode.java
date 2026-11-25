package raf.graffito.dsw.model.mts;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
@Getter
@Setter
public class ColoredNode extends DecoratedNode {
    Color boja = Color.WHITE;
    public ColoredNode(GraffNode node) {
        super(node);
    }

    public void promeniBoju(Color promena)
    {
        boja=promena;
    }


}
