import java.util.*;
import java.io.*; 
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Random;
import java.lang.*;
public class Passenger implements Comparable<Passenger>{
  int intended_floor;
  int tick;
  private static final Random r = new Random();
  Passenger( int starting_floor, int max, int tick){
    this.tick = tick;
	do{
		this.intended_floor = r.nextInt(max + 1);
	} while (starting_floor == this.intended_floor);
  }
  public int compareTo(Passenger a){
    return (Integer.compare(this.intended_floor, a.intended_floor));
  }
}