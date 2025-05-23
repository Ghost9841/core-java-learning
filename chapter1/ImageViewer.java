package chapter1;
// Listing 1.2 Image Viewer 

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class ImageViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ImageViewerFrame();
            frame.setTitle("Image Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
class ImageViewerFrame extends JFrame{
        private static final int DEFAULT_WIDTH = 300;
        private static final int DEFAULT_HEIGHT = 400;

        public ImageViewerFrame(){
            setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
            
            // Use a label to display the images
            var label = new JLabel();
            add(label);

            // Set upJ  JKL;  the file chooser
            var chooser = new JFileChooser();   
            chooser.setCurrentDirectory(new File("."));

            //set up the menu bar
            var menuBar = new JMenuBar();
            setJMenuBar(menuBar);

            var menu = new JMenu("File");
            menuBar.add(menu);

            var openItem = new JMenuItem("Open");
            menu.add(openItem);

            openItem.addActionListener(event -> {
                // show file dialog
                int result = chooser.showOpenDialog(null);

                // if file selected, set it as icon of the label
                if (result == JFileChooser.APPROVE_OPTION) {
                    String name = chooser.getSelectedFile().getPath();
                    label.setIcon(new ImageIcon(name));
                }
            });
        var exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));
        }
}

// Now Go to Chapter 2, FirstSample.java