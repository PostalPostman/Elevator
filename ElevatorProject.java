import java.util.*;
import java.io.*; 
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Random;
import java.lang.Integer;
import java.lang.Double;
 /**
  * This project made me want to KMS
  */
public class ElevatorProject{
	/**
	 * this is the tick it has elevator travel
	 *@param curTick short for current tick
	 *@param elevs the array of elevator
	 *@param floors the array of floors
	 *@param numOfFloors number of floors
	 *@param chance of generation
	 */
  public static void tick(int curTick, Elevator[] elevs, Floor[] floors, int numOfFloors, double chance){
	for (int i = 0; i < floors.length; i++) {
		floors[i].generate_passenger(curTick, chance, numOfFloors - 1);
	}
	for (int i = 0; i < elevs.length; i++) {
		if (elevs[i].isUp) {
			if (elevs[i].min.isEmpty()) { // travels if empty 
				if (elevs[i].cur_floor + 5 <= numOfFloors - 1)
					elevs[i].cur_floor = elevs[i].cur_floor + 5;
				else
					elevs[i].cur_floor = numOfFloors - 1;
			} else {// travels if not
				Passenger root = elevs[i].min.peek();
				if (root.intended_floor - elevs[i].cur_floor > 5){
					elevs[i].cur_floor = elevs[i].cur_floor + 5;
				}else
					elevs[i].cur_floor = root.intended_floor;
			}
		} else {
			if (elevs[i].max.isEmpty()) {// same here
				if (elevs[i].cur_floor - 5 >= 0){
						elevs[i].cur_floor = elevs[i].cur_floor - 5;
				}else
					elevs[i].cur_floor = 0;
				} else {
					Passenger root = elevs[i].max.peek();
					if (elevs[i].cur_floor - root.intended_floor > 5)
						elevs[i].cur_floor = elevs[i].cur_floor - 5;
					else
						elevs[i].cur_floor = root.intended_floor;
				}
			}
			elevs[i].unload(elevs[i].cur_floor, curTick); // unload mechanism
		if (elevs[i].isUp) //load mechanism
			elevs[i].load(floors[elevs[i].cur_floor].upDawg);
		else
			elevs[i].load(floors[elevs[i].cur_floor].downDawg);
		if (elevs[i].cur_floor == numOfFloors - 1) //switch direction
			elevs[i].isUp = false;
		else if (elevs[i].cur_floor == 0)
				elevs[i].isUp = true;
		}
	}
  public static void main(String[] args) throws Exception{
	  List<Integer> timeList = timeList = new LinkedList<Integer>(); //initialization of defaults
	  boolean isLinked = true;
	  int floors = 32;
	  double pasChance = 0.03;
	  int elevators = 1;
	  int capacity = 10;
	  int duration = 500;
	  if(args.length == 1){
		FileReader fread = new FileReader(args[0]); //prepare for file opening
		Properties prop = new Properties(); 
		prop.load(fread);
		if(prop.getProperty("structures").equals("array")){ // begin reading
			isLinked = false;
			timeList = new ArrayList<Integer>();
		}
		try{
			if(Integer.getInteger(prop.getProperty("floors")) >= 2)
				floors = Integer.getInteger(prop.getProperty("floors"));
		} catch (NumberFormatException e){}
		try{
			if(Double.valueOf(prop.getProperty("passengers")) > 0 && Double.valueOf(prop.getProperty("passengers")) < 1)
				pasChance = Double.valueOf(prop.getProperty("passengers"));
		} catch (NumberFormatException e){}
		try{
			if(Integer.getInteger(prop.getProperty("elevators")) >= 1)
				elevators= Integer.getInteger(prop.getProperty("elevators"));
		} catch (NumberFormatException e){}
		try{
			if(Integer.getInteger(prop.getProperty("elevatorCapacity")) >= 1)
				capacity = Integer.getInteger(prop.getProperty("elevatorCapacity"));
		} catch (NumberFormatException e){}
		try{
			if(Integer.getInteger(prop.getProperty("duration")) >= 1)
				duration = Integer.getInteger(prop.getProperty("duration"));
		} catch (NumberFormatException e){} //done reading
	  }
	  Elevator[] elevs = new Elevator[elevators]; // initialize elevators and floors
	  Floor[] floor_arr = new Floor[floors];
	  for(int i = 0; i < elevators; i++)
		  elevs[i] = new Elevator(capacity, isLinked);
	  for(int i = 0; i < floors; i++)
		  floor_arr[i] = new Floor(isLinked, i);
	  for(int i = 0; i < duration; i++){ //tick tock clock
		  tick(i, elevs, floor_arr, floors, pasChance);
	  }
	  for(int i = 0; i < elevators; i++){ // time for tabulation
		  for(int j = 0; j < elevs[i].times.size(); j++){
			  timeList.add(elevs[i].times.get(j));
		  }
	  }
	  Collections.sort(timeList);
	  System.out.println("shortest time is " + timeList.get(0));
	  Collections.sort(timeList, Collections.reverseOrder());
	  System.out.println("Longest time is " + timeList.get(0));
	  int avg = 0;
	  for(int i = 0; i < timeList.size(); i++){
		  avg += timeList.get(i);
	  }
	  System.out.println("avg time is " + (avg/timeList.size()));
  }
} 
