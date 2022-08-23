package com.company;

import java.util.*;

public class Play {

    public State state;
    public ArrayList<Integer> visited = new ArrayList<>();
    public int initialGridHash,step=1,stepNum=1;
    String[][] solution;

    public Play(){
        state = new State();
        initialGridHash = Objects.hash(state);
        solution = new String[state.sizeR][state.sizeC];
        for(int i = 0; i<state.sizeR; i++){
            for(int j = 0; j<state.sizeC; j++){
                if(state.grid[i][j] == null ) solution[i][j] = ".";
                else if(state.grid[i][j] == 0 ) solution[i][j] = "#";
                else if(state.grid[i][j] > 0 ) solution[i][j] = "P"+(state.grid[i][j]).toString();
                else if(state.grid[i][j] < 0 ) solution[i][j] = "D"+ state.grid[i][j] * -1;
            }
        }
        solution[state.startX][state.startY] = "S";
        print();
    }

    public void printPath(State state,int step){

        if(state != null ) {
            int hash = Objects.hash(state.currX, state.currY, Arrays.hashCode(state.receivedArray), Arrays.hashCode(state.deliveredArray));
            if (hash == initialGridHash) return;
            stepNum++;
            printPath(state.father, step+1);
            solution[state.currX][state.currY] = solution[state.currX][state.currY] + "," + (stepNum-step);
        }
    }

    void print(){
        int maxLength = 0;
        for(int i = 0; i<state.sizeR; i++){
            for(int j = 0; j<state.sizeC; j++){
                if(solution[i][j].length() > maxLength) maxLength = solution[i][j].length();
            }
        }

        System.out.print("\033[0;34m");
        System.out.println();
        for(int i = 0; i<state.sizeR; i++){
            for(int j = 0; j<state.sizeC; j++){
                if(solution[i][j].length() == maxLength) System.out.print("\t\t"+solution[i][j]);
                else {
                        System.out.print("\t\t"+solution[i][j]);
                        for(int l = 0; l<(maxLength-solution[i][j].length()); l++) {
                            System.out.print(" ");
                        }
                    }
                }
            System.out.println();
        }
        System.out.println("-----------------------------");
        System.out.print("\033[0m");
    }

    public void ucs(){

        int count=0,hash;
        ArrayList<State> allNextState ;
        PriorityQueue<State> pQueue = new PriorityQueue<>() ;
        pQueue.add(state);
        Date d1 = new Date();
        while (!pQueue.isEmpty()) {
            state = pQueue.remove();
            hash = Objects.hash(state.currX,state.currY,Arrays.hashCode(state.receivedArray),Arrays.hashCode(state.deliveredArray));
            if (!visited.contains(hash)){
                count++;
                visited.add(hash);
                if (state.isFinished()) {
                    Date d2 = new Date();
                    printPath(state,step);
                    print();
                    System.out.println("\u001B[33m");
                    System.out.println("Executing time is   " + (d2.getTime()-d1.getTime()) +" ms.");
                    System.out.println("Mission completed after processing = "+count+" node");
                    System.out.println("Solving Cost: "+(state.cost));
                    System.out.println("Solving steps: "+(stepNum-1));
                    for(int i=0;i<state.packageNum;i++){
                        System.out.print("the cost for delivering the package number "+(i+1)+" : ");
                        System.out.println(state.deliveredArray[i] - state.receivedArray[i]);
                    }
                    System.out.print("\033[0m");
                    return;
                }
                allNextState = state.getAllNextStates();
                if(allNextState.size() !=0){
                    for(State one:allNextState){
                        hash = Objects.hash(one.currX,one.currY,Arrays.hashCode(one.receivedArray),Arrays.hashCode(one.deliveredArray));
                        if(!visited.contains(hash)) {
                            pQueue.add(one);
                        }
                    }
                }
            }
        }
        System.out.print("\033[0;31m");
        System.out.println("Can not be solve !");
        System.out.print("\033[0m");

    }

    public void a_star(int h){
        int count=0,hash;
        ArrayList<State> allNextState ;
        PriorityQueue<State> pQueue = new PriorityQueue<>() ;
        pQueue.add(state);
        Date d1 = new Date();
        while (!pQueue.isEmpty()) {
            state = pQueue.remove();
            hash = Objects.hash(state.currX,state.currY,Arrays.hashCode(state.receivedArray),Arrays.hashCode(state.deliveredArray));
            if (!visited.contains(hash)){
                count++;
                visited.add(hash);
                if (state.isFinished()) {
                    Date d2 = new Date();
                    printPath(state,step);
                    print();
                    System.out.println("\u001B[33m");
                    System.out.println("Executing time is   " + (d2.getTime()-d1.getTime()) +" ms.");
                    System.out.println("Mission completed after processing = "+count+" node");
                    System.out.println("Solving Cost: "+(state.cost));
                    System.out.println("Solving steps: "+(stepNum-1));
                    for(int i=0;i<state.packageNum;i++){
                        System.out.print("the cost for delivering the package number "+(i+1)+" : ");
                        System.out.println(state.deliveredArray[i] - state.receivedArray[i]);
                    }
                    System.out.print("\033[0m");
                    return;
                }
                allNextState = state.getAllNextStates();
                if(allNextState.size() !=0){
                    for(State one:allNextState){
                        hash = Objects.hash(one.currX,one.currY,Arrays.hashCode(one.receivedArray),Arrays.hashCode(one.deliveredArray));
                        if(!visited.contains(hash)) {
                            one.estimate = one.estimate + one.heuristic(h);
                            pQueue.add(one);
                        }
                    }
                }
            }
        }
        System.out.print("\033[0;31m");
        System.out.println("Can not be solve !");
        System.out.print("\033[0m");

    }

}