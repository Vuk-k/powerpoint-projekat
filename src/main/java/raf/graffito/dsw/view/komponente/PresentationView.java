package raf.graffito.dsw.view.komponente;

import raf.graffito.dsw.model.mts.Presentation;

import javax.swing.*;

public class PresentationView extends JPanel {
    private JLabel naziv,brojSlideova;
    public PresentationView(Presentation p) {
        naziv=new JLabel("Naziv prezentacije:" + p.getNaziv());
        brojSlideova = new JLabel("Broj slideova u prezentaciji:" + p.getNumborOfSlides());

        init();
    }

    private void init()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        add(naziv);
        add(brojSlideova);


    }
}
