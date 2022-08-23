package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;

import static java.lang.System.exit;

public class State implements Comparable<State>{
    public Integer [][] grid ;
    public int [] receivedArray , deliveredArray ;
    public int [][] receiveXY , deliverXY;
    public int sizeR,sizeC,packageNum,cost,estimate,buildingNum, startX,startY, currX, currY;
    public State father;

    Scanner scanner = new Scanner(System.in);

    public void initial(){
        int row,col;
        System.out.print("Enter the grid size ( Row then Col ): ");
        this.sizeR = scanner.nextByte();
        this.sizeC = scanner.nextByte();
        this.grid = new Integer[sizeR][sizeC];
        System.out.print("Enter the number of packages : ");
        this.packageNum = scanner.nextByte();
        this.receivedArray = new int[packageNum];
        this.deliveredArray = new int[packageNum];
        this.receiveXY = new int[packageNum][2];
        this.deliverXY = new int[packageNum][2];
        System.out.print("Enter the number of buildings : ");
        this.buildingNum = scanner.nextByte();
        System.out.println("enter the position of each building in grid :");
        for(int i=0;i<buildingNum;i++) {
            System.out.println("building[ " + (i + 1) + " ]:");
            while (true) {
                System.out.print("  building row: ");
                row = scanner.nextByte();
                System.out.print("  building col: ");
                col = scanner.nextByte();
                if ( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null ) {
                    System.out.println("    you can not fill this index !");
                    print();
                } else {
                    this.grid[row][col] = 0;
                    break;
                }
            }
        }
        System.out.println("enter the position of each package in grid :");
        for(int i=0;i<packageNum;i++){
            System.out.println("package[ " + (i + 1) + " ]:");
            while (true) {
                System.out.print("  receive row: ");
                row = scanner.nextByte();
                System.out.print("  receive col: ");
                col = scanner.nextByte();
                if ( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null ) {
                    System.out.println("    you can not fill this index !");
                }
                else {
                    this.receiveXY[i][0] = row;
                    this.receiveXY[i][1] = col;
                    this.grid[row][col] = i+1 ;
                    break;
                }
            }
            while (true) {
                System.out.print("  deliver row: ");
                row = scanner.nextByte();
                System.out.print("  receive col: ");
                col = scanner.nextByte();
                if( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null) {
                    System.out.println(row+"   yy you can not fill this index !"+col);
                }
                else {
                    this.deliverXY[i][0] = row;
                    this.receiveXY[i][1] = col;
                    this.grid[row][col] = -1*(i+1) ;
                    break;
                }
            }
        }
        System.out.println("enter starting point :");
        while (true) {
            System.out.print("  starting row: ");
            this.startX = scanner.nextByte();
            System.out.print("  starting col: ");
            this.startY = scanner.nextByte();
            if ( startX >= sizeR || startX < 0 || startY >= sizeC || startY < 0 ||
                    ( this.grid[startX][startY] != null && this.grid[startX][startY] == 0) ) {
                System.out.println("    you can not start from this index !");
            } else {
                break;
            }
        }
    }

    public void read(){

        ArrayList<Integer> nums = new ArrayList<>();
        File file = new File("input.txt");
        Scanner f = null;
        try {
            f = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            assert f != null;
            if (!f.hasNextLine()) break;
            nums.add(Integer.valueOf(f.nextLine()));
        }
        System.out.println(nums);
        this.sizeR = nums.get(0);
//        System.out.println(sizeR);
        this.sizeC = nums.get(1);
//        System.out.println(sizeC);
        this.grid = new Integer[sizeR][sizeC];
        this.packageNum = nums.get(2);
//        System.out.println(packageNum);
        this.buildingNum = nums.get(3);
//        System.out.println(buildingNum);
        this.receivedArray = new int[packageNum];
        this.deliveredArray = new int[packageNum];
        this.receiveXY = new int[packageNum][2];
        this.deliverXY = new int[packageNum][2];
        int row,col;
        for(int i=4;i<(buildingNum*2)+4;i+=2) {
            row = nums.get(i);
//            System.out.println(row);
            col = nums.get(i+1);
//            System.out.println(col);
            if ( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null ) {
                System.out.println("wrong input !");
                exit(0);
            } else {
                this.grid[row][col] = 0;
            }
        }
        int cout=0;
        for(int i=4+(buildingNum*2);i<4+(buildingNum*2)+(packageNum*4);i+=4){
            cout++;
            row = nums.get(i);
//            System.out.println(row);
            col = nums.get(i+1);
//            System.out.println(col);
            if ( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null ) {
                System.out.println("wrong input !");
                exit(0);
            }
            else {
                this.receiveXY[cout-1][0] = row;
                this.receiveXY[cout-1][1] = col;
                this.grid[row][col] = cout ;
            }
            row = nums.get(i+2);
//            System.out.println(row);
            col = nums.get(i+3);
//            System.out.println(col);
            if( row >= sizeR || row < 0 || col >= sizeC || col < 0 || this.grid[row][col] != null) {
                System.out.println("wrong input !");
                exit(0);
            }
            else {
                this.deliverXY[cout-1][0] = row;
                this.deliverXY[cout-1][1] = col;
                this.grid[row][col] = -1*(cout) ;
            }
        }
        this.startX = nums.get(nums.size()-2);
//        System.out.println(startX);
        this.startY = nums.get(nums.size()-1);
//        System.out.println(startY);
        if ( startX >= sizeR || startX < 0 || startY >= sizeC || startY < 0 ||
                ( this.grid[startX][startY] != null && this.grid[startX][startY] == 0) ) {
            System.out.println("wrong input !");
            exit(0);
        }
    }

    public State(){
//        initial();
        read();
//        print();
        this.currX = startX;
        this.currY = startY;
        this.cost = 0;
        this.father = null ;
    }

    public State(State state) {
        this.sizeR = state.sizeR;
        this.sizeC = state.sizeC;
        this.buildingNum = state.buildingNum;
        this.packageNum = state.packageNum;
        this.grid = new Integer[state.sizeR][state.sizeC];
        for(int i = 0; i<state.sizeR; i++) {
            if (state.sizeC >= 0) System.arraycopy(state.grid[i], 0, this.grid[i], 0, state.sizeC);
        }
        this.startX = state.startX;
        this.startY = state.startY;
        this.receivedArray = new int [state.packageNum];
        this.deliveredArray = new int [state.packageNum];
        for(int i=0;i<state.packageNum;i++) {
            this.receivedArray[i] = state.receivedArray[i];
            this.deliveredArray[i] = state.deliveredArray[i];
        }
        this.receiveXY = new int[state.packageNum][2];
        this.deliverXY = new int[state.packageNum][2];
        for (int i=0;i<this.packageNum;i++){
            for(int j=0;j<2;j++){
                this.receiveXY[i][j] = state.receiveXY[i][j];
                this.deliverXY[i][j] = state.deliverXY[i][j];
            }
        }
        this.currX = state.currX;
        this.currY = state.currY;
        this.cost = state.cost;
        this.estimate = state.estimate;
        this.father = state ;
    }

    public void getCost(){
        int rec = 0,del = 0;
        for(int i=0;i<this.packageNum;i++){
            if(this.receivedArray[i] != 0) rec++;
            if(this.deliveredArray[i] != 0) del++;
        }
        this.cost = this.cost + 1 + (rec-del) ;
        this.estimate = this.estimate + 1 + (rec-del) ;
    }

    public void moveUp(){
        this.currX = this.currX - 1;
    }

    public void moveDown(){
        this.currX = this.currX + 1;
    }

    public void moveRight(){
        this.currY = this.currY + 1;
    }

    public void moveLeft(){
        this.currY = this.currY - 1;
    }

    public void receive(){
        this.receivedArray[this.grid[currX][currY]-1] = this.cost;
    }

    public void deliver(){
        this.deliveredArray[(this.grid[currX][currY]*-1)-1] = this.cost;
    }

    public ArrayList<State> getAllNextStates(){
        ArrayList<State> allNextStates = new ArrayList<>();
        // up
        if( (this.currX -1 >= 0 ) && (this.grid[this.currX-1][this.currY] == null || this.grid[this.currX-1][this.currY] != 0) ){
            State up = new State(this);
            up.moveUp();
            up.getCost();
            allNextStates.add(up);
        }
        // down
        if( (this.currX +1 < this.sizeR) && (this.grid[this.currX +1][this.currY] == null || this.grid[this.currX +1][this.currY] != 0)){
            State down = new State(this);
            down.moveDown();
            down.getCost();
            allNextStates.add(down);
        }
        // right
        if(this.currY + 1 < this.sizeC && (this.grid[this.currX][this.currY +1] == null ||this.grid[this.currX][this.currY +1] != 0) ){
            State right = new State(this);
            right.moveRight();
            right.getCost();
            allNextStates.add(right);
        }
        // left
        if( (this.currY-1 >= 0 ) && (this.grid[this.currX][this.currY-1] == null || this.grid[this.currX][this.currY-1] != 0) ){
            State left = new State(this);
            left.moveLeft(); ;
            left.getCost();
            allNextStates.add(left);
        }
        // receive
        if(this.grid[currX][currY] != null && this.grid[currX][currY] > 0
                && this.receivedArray[this.grid[currX][currY]-1] == 0){
            State receive = new State(this);
            receive.receive();
            allNextStates.add(receive);
        }
        // deliver
        if(this.grid[currX][currY] != null && this.grid[currX][currY] < 0
                && this.receivedArray[(this.grid[currX][currY]*-1)-1]  > 0
                && this.deliveredArray[(this.grid[currX][currY]*-1)-1]  == 0){
            State deliver = new State(this);
            deliver.deliver();
            allNextStates.add(deliver);
        }
        return allNextStates;
    }

    public boolean isFinished(){
        return this.currY == this.startY &&
                this.currX == this.startX &&
                IntStream.of(this.receivedArray).noneMatch(x -> x == 0) &&
                IntStream.of(this.deliveredArray).noneMatch(x -> x == 0);
    }

    public void print(){
        System.out.print("\033[0;34m");
        System.out.println("-----------------------------");
        for(int i = 0; i< sizeR; i++){
            for(int j = 0; j< sizeC; j++){
                if(grid[i][j] == null ){
                    if(i == startX && j == startY) {
                        System.out.print(" T");
                        continue;
                    }
                    System.out.print(" .");
                }
                else System.out.print(" "+grid[i][j]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
        System.out.print("\033[0m");
    }

    public int heuristic(int x){
        if(x == 0){
            return heuristic1();
        }
        else if (x == 1){
            return heuristic2(1);
        }
        else return heuristic3();
    }

    public int heuristic1 (){
        int d = 0;
        if(currX != startX && currY != startY)
            d = (int) ( Math.sqrt( Math.pow(currX-startX,2) + Math.pow(currY-startY,2)));
        return d;
    }

    public int heuristic2 (int x){
        int d=0,n=x;
        if( this.receivedArray[n-1] == 0 ) {
            d = (int) (Math.sqrt(Math.pow(currX - this.receiveXY[n - 1][0], 2) + Math.pow(currY - this.receiveXY[n - 1][1], 2)));
            d = d + 2 * (int) (Math.sqrt(Math.pow(this.receiveXY[n - 1][0] - this.deliverXY[n - 1][0], 2) + Math.pow(this.receiveXY[n - 1][1] - this.deliverXY[n - 1][1], 2)));
        }
        if(this.receivedArray[n - 1] != 0 && this.deliveredArray[n - 1] == 0){
            d = 2 * (int) (Math.sqrt(Math.pow(currX - this.deliverXY[n - 1][0], 2) + Math.pow(currY - this.deliverXY[n - 1][1], 2)));
        }
        return d;
    }

    public int heuristic3 (){

        int d;
        ArrayList<Integer> costs = new ArrayList<>();
        for(int i=1;i<=packageNum;i++){
            d = heuristic2(i);
            costs.add(d);
        }
        return Collections.max(costs);
    }

    @Override
    public int compareTo(State state) {
        if (this.estimate == state.estimate) return 0;
        return this.estimate > state.estimate ? 1 : -1 ;
    }

}
