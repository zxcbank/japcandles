package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends JPanel {
    static candle[] market = new candle[180];
    static int po = new Random().nextInt(20);
    Main(){
        JFrame fr = new JFrame("ЗАЛУПА ТРЕЙД");
        fr.add(this);
        fr.setVisible(true);
        fr.setSize(19200,10800);
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setFocusable(true);
    }

    public static void main(String[] args) {
        market[0] = new candle(200, 450, 600, 605);
        marketmaker(market);
        new Main();
    }

    public static candle candlemaker(int close){
        po = new Random().nextInt(100);
        if (close > 600) // если внизу
            po -= 1;
        if (close < 300)
            po += 1;
        if (new Random().nextInt(100) < 51)
            return new candle(close - po - new Random().nextInt(30),close + po + new Random().nextInt(50), close, close + po);
        else
            return new candle(close - po - new Random().nextInt(30),close + po + new Random().nextInt(50), close, close - po); //вверх
    }

    public static void marketmaker (candle[] array){
        for(int i = 1; i < array.length; i++)
            array[i] = candlemaker(array[i - 1].close);
    }

    public int growth(int max, int min){

        if (max - min < 0)
            return 1;
        return 0;
    }

    public void paintComponent( Graphics g ){
        super.paintComponent(g);
        g.drawLine(0,60,2000, 60);
        g.drawLine(0,300,2000, 300);
        for (int i = 0; i < market.length; i++) {
            g.setColor(Color.black);
            g.drawLine(i * 10 + 5, market[i].max, i * 10 + 5, market[i].min);
            if (growth(market[i].close, market[i].open) == 1) {
                g.drawRect(10 * i - 1, market[i].open - Math.abs(market[i].close - market[i].open) - 1, 11, Math.abs(market[i].close - market[i].open) + 1);
                g.setColor(Color.GREEN);
                g.fillRect(10 * i, market[i].open - Math.abs(market[i].close - market[i].open), 10, Math.abs(market[i].close - market[i].open));
            } else {
                g.drawRect(10 * i - 1, market[i].open, 11, Math.abs(market[i].close - market[i].open));
                g.setColor(Color.RED);
                g.fillRect(10 * i, market[i].open, 10, Math.abs(market[i].close - market[i].open));
            }

        }
    }
}
