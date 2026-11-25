package raf.graffito.dsw.model.mts;

public class GraffObjectFactory {
    public GraffNode getComponent(String compName, Object params[]) {
        if (compName.equals("Workspace")) {
            return new Workspace((String) params[0]);
        } else if (compName.equals("Project")) {
            return new Project((String) params[0], (String) params[1], (GraffNode) params[2]);
        } else if (compName.equals("Presentation")) {
            return new Presentation((GraffNode) params[0], (String) params[1], (String) params[2]);
        } else if (compName.equals("Slide")) {
            return new Slide((GraffNode) params[0], (String) params[1]);
        } else if (compName.equals("ColoredProject")){
            return new ColoredProject(new Project((String) params[0], (String) params[1], (GraffNode) params[2]));
        } else {
            return null;
        }
    }
}
