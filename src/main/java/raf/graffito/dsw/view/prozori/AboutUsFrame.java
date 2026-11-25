package raf.graffito.dsw.view.prozori;

import raf.graffito.dsw.controller.AboutUsAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AboutUsFrame extends JFrame {
    public AboutUsFrame() {
        initialize();
    }

    private void initialize() {
        setTitle("About us");
        setLayout(new BorderLayout(10, 5));
        setSize(300, 400);

        JPanel panMain = new JPanel(new GridLayout(0, 2, 10, 5));

        URL urlAdrijan = getClass().getResource("/images/Adrijan_Cera_1.jpg");
        URL urlVuk = getClass().getResource("/images/Nije_Adrijan_Cera_1.png");
        Image imgAdrijan = null;
        Image imgVuk = null;

        if (urlAdrijan != null) {
            imgAdrijan = new ImageIcon(urlAdrijan).getImage();
            imgAdrijan = imgAdrijan.getScaledInstance(100, 200, Image.SCALE_DEFAULT);
        } else System.out.println("Problem sa Adirjanovom slikom");


        if (urlVuk != null) {
            imgVuk = new ImageIcon(urlVuk).getImage();
            imgVuk = imgVuk.getScaledInstance(100, 200, Image.SCALE_DEFAULT);
        } else System.out.println("Problem sa Vukovom slikom");

        JLabel lAdrijan = new JLabel("Adrijan Cera 11/24SI");
        JLabel lVuk = new JLabel("Vuk Krcadinac 34/24SI");

        if (imgAdrijan != null) panMain.add(new JLabel(new ImageIcon(imgAdrijan)));
        if (imgVuk != null) panMain.add(new JLabel(new ImageIcon(imgVuk)));

        panMain.add(lAdrijan);
        panMain.add(lVuk);

        add(panMain);

        setVisible(true);
    }
}
