import java.util.*;
import java.io.*; 
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.Random;
import java.lang.*;
public class Elevator{
  public final PriorityQueue<Passenger> min = new PriorityQueue<>();
  public final PriorityQueue<Passenger> max = new PriorityQueue<>(Collections.reverseOrder());
  public List<Integer> times;
  int capacity;
  int cur_floor;
  boolean isUp;

  Elevator(int capacity, boolean isLinked){
    this.capacity = capacity;
	cur_floor = 0;
	isUp = true;
	if(isLinked)
		times = new LinkedList<Integer>();
	else
		times = new ArrayList<Integer>();
  }
  
  public void load(Deque<Passenger> passengers) {
	if (isUp)
		while (min.size() < capacity && !passengers.isEmpty())
			this.min.add(passengers.poll());
	else
		while (max.size() < capacity && !passengers.isEmpty())
			this.max.add(passengers.poll());
  }
  public void unload(int floor, int cur_tick) {
		PriorityQueue<Passenger> passengers;

		if (isUp)
			passengers = min;
		else
			passengers = max;

		if (passengers.peek() == null)
			return;
			
		while (passengers.peek() != null && passengers.peek().intended_floor == floor) {
			Passenger p = passengers.poll();
			times.add(cur_tick - p.tick);
		}
	}
}