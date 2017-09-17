package xyz.tanks.display;

import xyz.tanks.io.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {
    private static boolean created = false;

    private static float delta = 0;

    private static JFrame window;
    private static Canvas content;
    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;

    public static void create(int width, int height, String title, int color, int numBuffers) {
        if (created) {
            return;
        }

        // Init canvas
        Dimension size = new Dimension(width, height);
        content = new Canvas();



        content.setPreferredSize(size);
        content.setBackground(Color.BLACK);

        // Init window
        window = new JFrame(title);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt)buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        clearColor = color;

        getGraphics().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();


        created = true;
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }

    public static void swapBuffers() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics() {
        return (Graphics2D)bufferGraphics;
    }

    public static void destroy() {
        if (!created) {
            return;
        }

        window.dispose();
    }

    public static void setTitle(String title) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                window.setTitle(title);
            }
        });
    }

    public static void addInputListener(Input listener) {
        window.add(listener);
    }
}
