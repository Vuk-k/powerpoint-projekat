package raf.graffito.dsw.model.mts;

public class DecoratedProject extends Project{

    public DecoratedProject(String title, String author, GraffNode parent) {
        super(title, author, parent);
    }

    public DecoratedProject(String title, String author, int number, GraffNode parent) {
        super(title, author, number, parent);
    }

    public DecoratedProject() {
    }
}
