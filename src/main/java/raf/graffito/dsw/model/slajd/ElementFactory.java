package raf.graffito.dsw.model.slajd;

import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.view.prozori.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ElementFactory {
    public SlajdElement dajElement(String element) {
        if (element.toLowerCase().equals("pravougaonik")) return new Pravougaonik(20, 20, 50, 50, Color.BLACK);
        if (element.toLowerCase().equals("krug")) return new Krug(20, 20, 50, 50, Color.BLACK);
        if (element.toLowerCase().equals("logo")) return new Logo(20, 20, 50, 50, Color.BLACK);
        //if (element.toLowerCase().split(" ")[0].equals("text")) return new Text(20, 20, element.replace("text", ""));
        if (element.toLowerCase().equals("image")) {
            JFileChooser chooser = new JFileChooser();

            FileNameExtensionFilter imageFilter =
                    new FileNameExtensionFilter(
                            "Image files",
                            "jpg", "jpeg", "png", "gif", "bmp",
                            "tif", "tiff", "webp",
                            "heic", "heif",
                            "ico", "svg", "psd", "raw",
                            "cr2", "nef", "arw", "dng", "raf", "sr2"
                    );

            chooser.setFileFilter(imageFilter);
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileSystemView(FileSystemView.getFileSystemView());
            var option = chooser.showOpenDialog(MainFrame.getInstance());
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    ApplicationFramework.getInstance().loadNewImage(chooser.getSelectedFile().getAbsolutePath());
                    return new Slika(20, 20, chooser.getSelectedFile().getAbsolutePath());
                } catch (Exception e) {
                    ApplicationFramework.getInstance().getMessageGenerator().notifySubscriers(ApplicationFramework.getInstance().getMessageGenerator().generateMessage("NEW_IMAGE_ERROR_WITH_IMAGE", "error"));
                }
            }
        }


        return null;
    }
}
