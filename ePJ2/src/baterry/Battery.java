package baterry;

public class Battery {
	
	private static final String message1 = "Battery capaciti: " + "\n" + 
											"100 for Scooters" + "\n" +
											"200 for Bikes" + "\n" +
											"300 for Cars" + "\n";
	
	private int capacity;		//	maximum capacity, for now idead is 100/200/300 scooter/bike/car
	private double status=100;	//	battery percentage , lets say ..it's fully charged
	
	public Battery(int capacity)throws Exception{
		if(capacity != 100 && capacity != 200 && capacity != 300) {
			throw new BatteryException(message1);
		}
		this.capacity=capacity;
	}
	
	
	
}
/*
 * Praznjenje tokom kretanja vozila,
 * punjenje,
 * svakakvih izmjena i dopuna ce ovdje biti..
 */