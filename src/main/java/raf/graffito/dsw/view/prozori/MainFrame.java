package raf.graffito.dsw.view.prozori;

import lombok.Getter;
import raf.graffito.dsw.controller.ActionManager;
import raf.graffito.dsw.model.ApplicationFramework;
import raf.graffito.dsw.model.ISubscriber;
import raf.graffito.dsw.model.WindowState;
import raf.graffito.dsw.model.messages.ErrorMessage;
import raf.graffito.dsw.model.messages.Message;
import raf.graffito.dsw.model.messages.WarnningMessage;
import raf.graffito.dsw.model.mts.GraffNode;
import raf.graffito.dsw.model.mts.Project;
import raf.graffito.dsw.view.komponente.MainFramePanel;
import raf.graffito.dsw.view.komponente.MyMenuBar;
import raf.graffito.dsw.view.komponente.MyToolBar;
import raf.graffito.dsw.view.komponente.SlajdMaker;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ISubscriber {
    @Getter
    private static MainFrame instance = null;
    @Getter
    private static ActionManager actionManager = null;

    static {
        actionManager = new ActionManager(); // Bitan je redosled jer MainFrame i ApplicationFramework pozivaju ActionManager
        instance = new MainFrame();
    }

    @Getter
    MainFramePanel contentPane = null;
    @Getter
    WindowState windowState;
    private MyToolBar toolBar = null;

    private MainFrame() {
        initialize();
    }

    private void initialize() {
        ApplicationFramework.getInstance().getMessageGenerator().addSuscriber(this);

        Toolkit kit = Toolkit.getDefaultToolkit(); // Toolkit omogućava interakciju sa platformom
        Dimension screenSize = kit.getScreenSize(); // Veličina ekrana
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        this.setMinimumSize(new Dimension(screenWidth/3,screenHeight/3));
        int prefw= (int) ((int)screenWidth/1.5),prefh= (int) ((int)screenHeight/1.5);
        this.setPreferredSize(new Dimension(prefw,prefh));
        setSize(prefw, prefh);
        this.setResizable(false);
        setLocationRelativeTo(null); // Centriranje prozora na ekranu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zatvaranje aplikacije pri zatvaranju prozora
        setTitle("Graffito"); // Naslov prozora
        setContentPane((contentPane = new MainFramePanel()));
        windowState = WindowState.NORMAL;

        MyMenuBar menu = new MyMenuBar(); // Kreiranje menija
        setJMenuBar(menu); // Postavljanje menija na prozor

        toolBar = new MyToolBar(); // Kreiranje toolbar-a
        add(toolBar, BorderLayout.NORTH); // Postavljanje toolbar-a na vrh prozora
    }

    @Override
    public void notify(Object notf) {
        if (notf instanceof Message) {
            Message m = (Message) notf;
            if (!(notf instanceof WarnningMessage)) {
                JOptionPane.showMessageDialog(this, m.getContent(), "nesto", notf instanceof ErrorMessage ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE );
            }
        } else System.out.println("nije tipa poruka");
    }
    public void subscribeDesniPanel(GraffNode n)
    {
        if(n instanceof Project) {
            n.addSuscriber(getInstance().getContentPane().getPain());
        }
        n.addSuscriber(MainFrame.getInstance().contentPane.getDesniPanel());
    }

    public void setWindowState(WindowState ws) {
        if (windowState == ws) return;

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        switch (ws) {
            case SMALL:
                if(gd.getFullScreenWindow() != null)
                {
                    gd.setFullScreenWindow(null);
                }
                MainFrame.getInstance().setSize(MainFrame.getInstance().getMinimumSize());
                break;
            case NORMAL:
                if(gd.getFullScreenWindow() != null)
                {
                    gd.setFullScreenWindow(null);
                }
                MainFrame.getInstance().setSize(new Dimension((int)MainFrame.getInstance().getMinimumSize().getWidth()*2,(int)MainFrame.getInstance().getMinimumSize().getHeight()*2));
                break;
            case LARGE:
                if(gd.isFullScreenSupported())
                {
                    gd.setFullScreenWindow(MainFrame.getInstance());
                }
                break;
        }

        toolBar.setWindowState(ws);
        windowState = ws;

        SlajdMaker sm = MainFrame.getInstance().getContentPane().getPain().getSlajdMaker();
        if (sm != null) {
            sm.updateScale();
        }
    }
}
