import java.util.*;
import java.io.*; 
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Random;
import java.lang.*;
/**
 *Contains a floor
 */
public class Floor{
  int floor_val;
  public Deque<Passenger> upDawg;
  public Deque<Passenger> downDawg;
  private Random r = new Random();
  /**
   * floor constructor
   *@param isLinked checks if linked or not purposed for linky list or array list
   *@param floor floor number i don't know why i used 2 different ones im just tired
   */
  Floor(boolean isLinked, int floor){
    floor_val = floor;
    if(isLinked){
       upDawg = new LinkedBlockingDeque<Passenger>();
       downDawg = new LinkedBlockingDeque<Passenger>();
    }
    else{
		upDawg = new ArrayDeque<Passenger>();
		downDawg = new ArrayDeque<Passenger>();
    }
  }
  /**
   *generation for passenger
   *@param tick for tabulation purposes
   *@param chance for chance of generations
   *@param max number of floors
   */
  public void generate_passenger(int tick, double chance, int max){
	if(Math.random() < chance){
		Passenger p = new Passenger(this.floor_val, max, tick);
		if(floor_val < p.intended_floor)
			upDawg.add(p);
		else
			downDawg.add(p);
	}
  }

}