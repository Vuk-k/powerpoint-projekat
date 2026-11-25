package raf.graffito.dsw.view.komponente;

import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.mts.Project;

import javax.swing.*;

public class ProjectView extends JPanel {
    @Getter
    @Setter
    private JLabel nazivProjekta,kreatorProjekta,brojPrez,brojSlide;
    private String naziv,kreator;
    private int brojPrezentacija,brojSlideova;
    public ProjectView(Project p) {
        naziv=p.getNaziv();
        kreator=p.getAuthor();
        brojPrezentacija=p.getNumberOfPresentations();
        brojSlideova=p.getNumberOfSlides();
        brojPrez = new JLabel("Broj prezentacija:" + brojPrezentacija);
        brojSlide = new JLabel("Broj slideova: " + brojSlideova);
        nazivProjekta=new JLabel("Naziv projekta:" + naziv);
        kreatorProjekta = new JLabel("Autor Projekta:" + kreator);

        initialize();

    }

    private void initialize(){
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(nazivProjekta);
        add(kreatorProjekta);
        add(brojPrez);
        add(brojSlide);




    }



}
