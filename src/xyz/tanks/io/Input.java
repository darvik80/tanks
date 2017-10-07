package xyz.tanks.io;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Input extends JComponent {
    private boolean[] map;

    public Input() {
        map = new boolean[256];
        for (int idx = 0; idx < map.length; idx++) {
            final int KEY_CODE = idx;
            // Push button
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(idx, 0, false),
                idx * 2
            );

            getActionMap().put(idx * 2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;
                }
            });

            // Release button
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(idx, 0, true),
                    idx * 2 + 1
            );
            getActionMap().put(idx * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = false;
                }
            });
        }
    }

    public boolean[] getMap() {
        return Arrays.copyOf(map, map.length);
    }

    public boolean getKey(int keyCode) {
        return map[keyCode];
    }
}
