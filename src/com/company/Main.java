package com.company;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Play p = new Play();


//        p.ucs();
//        Play p1 = new Play();
//        p1.a_star(0);
//        Play p2 = new Play();
//        p2.a_star(1);
//        Play p3 = new Play();
//        p3.a_star(3);


        int choice=0;
        System.out.println("1- UCS");
        System.out.println("2- A*-heuristic1");
        System.out.println("3- A*-heuristic2");
        System.out.println("4- A*-heuristic3");
        System.out.println("0- Exit");
        choice = scanner.nextByte();
        switch (choice){
            case 1:
                p.ucs();
                break;
            case 2:
                p.a_star(0);
                break;
            case 3:
                p.a_star(1);
                break;
            case 4:
                p.a_star(2);
                break;
            case 0:
                return;
            default:
                System.out.print("\033[0;31m");
                System.out.println("Wrong input !");
                System.out.print("\033[0m");
        }
    }
}
