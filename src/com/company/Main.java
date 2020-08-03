package com.company;

public class Main {

    public static void main(String[] args) {
        //creating a gui object
        GUI gui = new GUI();
        //creating a thread of the gui
        Thread thread = new Thread(gui);
        //running the thread
        thread.start();
    }
}
