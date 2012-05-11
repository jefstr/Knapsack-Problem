//////////////////////////////////////////////////////////////////////////////////////////
/// Knapsack Problem Implementation
/// Jeff Parvin
/// 24 Jan 2012
//////////////////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

public class Thief {

	//instance variables
	private int work;
	private Knapsack knapsack;
	private ArrayList<Item> itemList;

	// constructor
	public Thief(){
		knapsack = new Knapsack();
		itemList = new ArrayList<Item>();
	}

	// setters
	public void resetWork(){
		work = 0;
	}

	// getters
	public int getWork(){
		return work;
	}

	// add items method
	public void add(String name, int size, int value){
		Item newItem = new Item(name, size, value);
		itemList.add(newItem);
		//System.out.println("Added: "+newItem.getName()+", "+newItem.getValue()+", "+newItem.getSize());
	}

	// packing methods

	// recursive packing
	public Knapsack packRecursive(int capacityIn){
		int maxValue = optimize(capacityIn);
		knapsack = new Knapsack(capacityIn);
		knapsack.setValue(maxValue);
		return knapsack;
	}
	
	private int optimize(int capacityIn){
		int[] values = new int[itemList.size()];
		int maxValue;
		work++;

		if(capacityIn==0){
			return 0;
		}

		else{
			// find value if each item is last item put in
			for(int i=0; i<itemList.size(); i++){
				Item testItem = itemList.get(i);
				if(testItem.getSize()<=capacityIn){
					values[i] = optimize(capacityIn-testItem.getSize())+testItem.getValue();
				}
				else{
					values[i]=0;
				}
			}

			// find max value among all last item choices
			maxValue = values[0];
			for(int i=1; i<values.length; i++){
				if(values[i]>maxValue){
					maxValue = values[i];
				}
			}
		}

		return maxValue;
	}
	
	// memoized packing
	public Knapsack packMemoized(int capacityIn){
		
		int maxValue = packMemoized(capacityIn, new int[capacityIn+1]);
		Knapsack knapsack = new Knapsack(capacityIn);
		knapsack.setValue(maxValue);
		return knapsack;
	}

	public int packMemoized(int capacityIn, int[] known){

		int[] values = new int[itemList.size()];
		int maxValue;
		work++;

		if(known[capacityIn]!=0){
			return known[capacityIn];
		}
		
		if(capacityIn==0){
			return 0;
		}

		else{
			// find value if each item is last item put in
			for(int i=0; i<itemList.size(); i++){
				Item testItem = itemList.get(i);
				if(testItem.getSize()<=capacityIn){
					values[i] = packMemoized(capacityIn-testItem.getSize(), known)+testItem.getValue();
				}
				else{
					values[i]=0;
				}
			}

			// find max value among all last item choices
			maxValue = values[0];
			for(int i=1; i<values.length; i++){
				if(values[i]>maxValue){
					maxValue = values[i];
				}
			}
		}
		
		known[capacityIn] = maxValue;
		return maxValue;
	}

	// DP packing
	public Knapsack packDP(int capacityIn){				
		int targetCapacity = capacityIn;
		knapsack = new Knapsack(targetCapacity);
		int[] values = new int[targetCapacity+1];
		Item[] lastItem = new Item[capacityIn+1];

		values[0] = 0;

		for(int capacity=1; capacity<=targetCapacity; capacity++){		// loop through all capacities up to the target capacity
			values[capacity] = values[capacity-1];						// set value of current capacity = value of previous capacity
			lastItem[capacity] = lastItem[capacity-1];

			for(int i=0; i<itemList.size(); i++){						// loop through all available items
				Item testItem = itemList.get(i);						// pull item out of item list
				work++;		//FIGURE THIS OUT!

				// if current item is smaller than capacity 
				// && value with this item is greater than previous max value
				if (testItem.getSize() <= capacity && testItem.getValue()+values[capacity-testItem.getSize()] > values[capacity]){
					values[capacity] = testItem.getValue()+values[capacity-testItem.getSize()];
					lastItem[capacity] = testItem;
				}
			}
		}

		knapsack.setValue(values[targetCapacity]);


		int i = targetCapacity;
		while (i>0 && values[i]>0){
			knapsack.placeItem(lastItem[i]);
			i=i-lastItem[i].getSize();
		}

		return knapsack;
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	/// SubClasses
	//////////////////////////////////////////////////////////////////////////////////////////

	// Item Subclass:

	public class Item{		// do I need setters for this?

		// instance variables
		private String name;
		private int size;
		private int value;

		// default constructor
		public Item(){		
			name = "defaultItem";
			size = 1;
			value = 0;
		}

		// constructor
		public Item(String nameIn, int sizeIn, int valueIn){
			name = nameIn;
			size = sizeIn;
			value = valueIn;
		}

		// getters
		public String getName(){
			return name;
		}

		public int getSize(){
			return size;
		}

		public int getValue(){
			return value;
		}

	}

	// Knapsack Subclass:

	// does knapsack have an array that holds its current items?  
	// Maybe its just a string??
	// is the println(knapsack) output just from a print method within knapsack?

	public class Knapsack{		

		// instance variables
		private int capacity;
		private int value;
		private ArrayList<Item> packList = new ArrayList<Item>();

		// default constructor
		public Knapsack(){
			capacity=1;
			value=0;
		}

		// constructor
		public Knapsack(int capacityIn){
			capacity = capacityIn;
			value=0;
		}

		// getters
		public int getCapacity(){
			return capacity;
		}

		public int getValue(){
			return value;
		}

		public void setValue(int valueIn){
			value = valueIn;
		}

		// place item in sack
		public void placeItem(Item item){
			packList.add(item);
		}

		//print method
		public void print(){
			System.out.print("  Knapsack: ");

			for(int i=packList.size()-1; i>=0; i--){
				System.out.print(packList.get(i).getName()+" ");
			}

			System.out.println("("+value+")");
		}
		
		public String toString(){
			String temp = "";
			
			for(int i=packList.size()-1; i>=0; i--){
				temp+=packList.get(i).getName()+" ";
			}
			
			temp+="("+value+")";
			
			return temp;
		}

	}

}
