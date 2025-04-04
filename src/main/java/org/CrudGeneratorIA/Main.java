package org.CrudGeneratorIA;

import javax.swing.SwingUtilities;
import org.CrudGeneratorIA.ui.CrudGeneratorFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CrudGeneratorFrame().setVisible(true);
        });
    }
}
