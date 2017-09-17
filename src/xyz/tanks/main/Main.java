package xyz.tanks.main;

import xyz.tanks.display.Display;
import xyz.tanks.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
