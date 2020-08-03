package com.company;

import javax.swing.*;
import java.awt.*;


public class GUI implements Runnable{
    //creating threads
    Thread t, th, th1, th2, carThread2, carThread3, carThread4,carThread1, threadTime;
    //making a boolean variable
    boolean isStop = true;

    //identifying the table's data
    Integer[][] carData = {{1, 1200, 0, 0}, {2, 1500, 0, 0}, {3, 2200, 0, 0}, {4, 3500, 0, 0}};
    String[] carColumns = {"Car", "X", "Y", "Speed"};
    Object[][] intersectionData = {{1, 1000, 0, ""}, {2, 2000, 0, ""}, {3, 2500, 0, ""}, {4, 3000, 0, ""}};
    String[] interSectionColumns = {"Intersection", "X", "Y", "Status"};


    //overriding the run method for the thread
    @Override
    public void run() {
        //creating the gui
        JFrame frame = new JFrame("Traffic Simulation");
        frame.setSize(1000, 400);
        Container container = frame.getContentPane();
        container.setLayout(null);
        JLabel currentTime = new JLabel("Current Time:");
        currentTime.setBounds(10, 10, 100, 30);
        JTextArea time = new JTextArea();
        time.setBounds(110, 10, 100, 30);
        time.setEditable(false);
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(230, 10, 50, 30);
        JTextArea status = new JTextArea("Not started");
        status.setBounds(280, 10, 100, 30);
        status.setEditable(false);



        final JTable[] carTable = {new JTable(carData, carColumns)};
        JScrollPane carPane = new JScrollPane(carTable[0]);
        carPane.setBounds(40, 80, 400, 230);
        final JTable[] intersectionTable = {new JTable(intersectionData, interSectionColumns)};
        JScrollPane intersectionPane = new JScrollPane(intersectionTable[0]);
        intersectionPane.setBounds(500, 80, 400, 230);


        JButton start = new JButton("Start");
        start.setBounds(330, 330, 80, 30);
        JButton pause = new JButton("Pause");
        pause.setBounds(420, 330, 80, 30);
        JButton continueButton = new JButton("Continue");
        continueButton.setBounds(510, 330, 90, 30);
        JButton stop = new JButton("Stop");
        stop.setBounds(610, 330, 80, 30);



        //adding components to the frame
        frame.add(currentTime);
        frame.add(time);
        frame.add(statusLabel);
        frame.add(status);
        frame.add(carPane);
        frame.add(intersectionPane);
        frame.add(start);
        frame.add(pause);
        frame.add(continueButton);
        frame.add(stop);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);


        //creating objects from the TrafficLightSimulator for the intersection
        TrafficLightSimulator t1 =
                new TrafficLightSimulator(TrafficLightColor.YELLOW);
        TrafficLightSimulator t2 =
                new TrafficLightSimulator(TrafficLightColor.RED);
        TrafficLightSimulator t3 =
                new TrafficLightSimulator(TrafficLightColor.GREEN);
        TrafficLightSimulator t4 =
                new TrafficLightSimulator(TrafficLightColor.RED);

        //creating a time object
        Time time1 = new Time();

        //creating cars objects and setting speed and distance
        Car car1 = new Car();
        car1.setDistance(1200);
        car1.setSpeed(20);
        carTable[0].setValueAt(car1.getSpeed(), 0, 3);
        Car car2 = new Car();
        car2.setDistance(1500);
        car2.setSpeed(20);
        carTable[0].setValueAt(car2.getSpeed(), 1, 3);
        Car car3 = new Car();
        car3.setDistance(2200);
        car3.setSpeed(20);
        carTable[0].setValueAt(car3.getSpeed(), 2, 3);
        Car car4 = new Car();
        car4.setDistance(3500);
        car4.setSpeed(20);
        carTable[0].setValueAt(car4.getSpeed(), 3, 3);

        //adding action listener for the start button
        start.addActionListener(e -> {

            status.setText("Started");

            //running the threads
            Thread thread1 = new Thread(t1);
            thread1.start();
            Thread thread2 = new Thread(t2);
            thread2.start();
            Thread thread3 = new Thread(t3);
            thread3.start();
            Thread thread4 = new Thread(t4);
            thread4.start();

                t = new Thread(() -> {
                    for (int i = 0; i < 25; i++) {
                        intersectionTable[0].setValueAt(t1.getColor().toString(), 0, 3);
                        t1.waitForChange();
                    }
                    while (isStop){
                        Thread.currentThread().interrupt();
                    }
                });
                t.start();

                th = new Thread(() -> {
                    for (int i = 0; i < 25; i++) {
                        intersectionTable[0].setValueAt(t2.getColor().toString(), 1, 3);
                        t2.waitForChange();
                    }
                });
                th.start();
                th1 = new Thread(() -> {
                    for (int i = 0; i < 25; i++) {
                        intersectionTable[0].setValueAt(t3.getColor().toString(), 2, 3);
                        t3.waitForChange();
                    }

                });
                th1.start();
                th2 = new Thread(() -> {
                    for (int i = 0; i < 25; i++) {
                        intersectionTable[0].setValueAt(t4.getColor().toString(), 3, 3);
                        t4.waitForChange();
                    }

                });
                th2.start();


                //creating and starting the time thread
                threadTime = new Thread(() -> {
                        for (int i = 0; i < 240; i++) {
                            time.setText(time1.displayTime());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                System.out.println(e1);
                            }

                        }
                });
                threadTime.start();

                //creating and starting the car threads
                carThread1 = new Thread() {
                    @Override
                    public void run() {
                            while (car1.getDistance() > 0) {

                                carTable[0].setValueAt(car1.changeDistance(), 0, 1);
                                if (car1.getDistance() == 1000 && t1.getColor() == TrafficLightColor.RED) {
                                    car1.setSpeed(0);
                                } else if (car1.getDistance() == 0) {
                                    car1.setSpeed(0);
                                } else {
                                    car1.setSpeed(20);
                                }
                                carTable[0].setValueAt(car1.getSpeed(), 0, 3);
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                while (!isStop) {
                                    interrupt();
                                }
                            }

                    }
                };
                carThread1.start();
                carThread2 = new Thread() {
                    @Override
                    public void run() {
                        while (car2.getDistance() > 0) {
                            carTable[0].setValueAt(car2.changeDistance(), 1, 1);
                            if (car2.getDistance() == 1000 && t1.getColor() == TrafficLightColor.RED) {
                                car2.setSpeed(0);
                            } else if (car2.getDistance() == 0) {
                                car2.setSpeed(0);
                            } else {
                                car2.setSpeed(20);
                            }
                            carTable[0].setValueAt(car2.getSpeed(), 1, 3);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                            }

                            while (!isStop) {
                                interrupt();
                            }
                            }

                    }
                };
                carThread2.start();
                carThread3 = new Thread() {
                    @Override
                    public void run() {

                        while (car3.getDistance() > 0) {

                            carTable[0].setValueAt(car3.changeDistance(), 2, 1);
                            if ((car3.getDistance() == 1000 && t1.getColor() == TrafficLightColor.RED) || (car3.getDistance() == 2000 && t2.getColor() == TrafficLightColor.RED)) {
                                car3.setSpeed(0);
                            } else if (car3.getDistance() == 0) {
                                car3.setSpeed(0);
                            } else {
                                car3.setSpeed(20);
                            }
                            carTable[0].setValueAt(car3.getSpeed(), 2, 3);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            while (!isStop){
                                while (!isStop) {
                                    interrupt();
                                }
                            }

                        }
                    }
                };
                carThread3.start();
                carThread4 = new Thread() {
                    @Override
                    public void run() {
                        while (car4.getDistance() > 0) {
                            carTable[0].setValueAt(car4.changeDistance(), 3, 1);
                            if ((car4.getDistance() == 1000 && t1.getColor() == TrafficLightColor.RED) || (car4.getDistance() == 2000 && t2.getColor() == TrafficLightColor.RED) || (car4.getDistance() == 2500 && t3.getColor() == TrafficLightColor.RED) || (car4.getDistance() == 3000 && t4.getColor() == TrafficLightColor.RED)) {
                                car4.setSpeed(0);
                            } else if (car4.getDistance() == 0) {
                                car4.setSpeed(0);
                            } else {
                                car4.setSpeed(20);
                            }
                            carTable[0].setValueAt(car4.getSpeed(), 3, 3);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            while (!isStop) {
                                    interrupt();
                            }
                        }
                    }
                };
                carThread4.start();

        });


        //adding action listener for the pause button
        pause.addActionListener(e -> {
            status.setText("Paused");
            isStop = false;
            try {
                Thread.currentThread().wait();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        //adding action listener for the continue button
        continueButton.addActionListener(e -> {
            status.setText("Continued");
            isStop = true;
        });
        //adding action listener for the stop button
        stop.addActionListener(e -> {
            status.setText("Stopped");
            isStop = false;
            t1.cancel();
            t2.cancel();
            t3.cancel();
            t4.cancel();
        });
    }

}
