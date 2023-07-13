import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
class ColorTitleBar extends JFrame{

    ColorTitleBar(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
        setLocationRelativeTo(null);
        setUndecorated(true);// vanishing the default title bar

        //Setting the RootPane as the main window frame
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setTitle("HELLO");
        JPanel panel = new JPanel();
        panel.setBackground(java.awt.Color.white);
        setContentPane(panel);
        panel.setLayout(null);
        panel.setBounds(100,100,400,400);
        //Hello World JLabel
        JLabel xyz=new JLabel("Hello World");
        xyz.setBounds(310,40,100,200);
        panel.add(xyz);
        MetalLookAndFeel.setCurrentTheme(new changeTheme());

        //UIManager code snippet to set the look and feel them for the window
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //A simple minded look and feel change that is to initialize its UI property with the current look and feel.
        SwingUtilities.updateComponentTreeUI(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ColorTitleBar();//calling ColorTitleBar
    }
}

//Theme Class to Change the default color to green color
class changeTheme extends DefaultMetalTheme {
    public ColorUIResource getWindowTitleInactiveBackground() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getWindowTitleBackground() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getPrimaryControlHighlight() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getPrimaryControlDarkShadow() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getPrimaryControl() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getControlHighlight() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getControlDarkShadow() {
        return new ColorUIResource(java.awt.Color.green);
    }

    public ColorUIResource getControl() {
        return new ColorUIResource(java.awt.Color.green);
    }
}