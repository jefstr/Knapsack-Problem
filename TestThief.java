//////////////////////////////////////////////////////////////////////////////////////////
/// Knapsack Problem Test File
/// Jeff Parvin
/// 24 Jan 2012
//////////////////////////////////////////////////////////////////////////////////////////

public class TestThief {

	public static void main(String[] args) {

		Thief thief = new Thief();
		
		///
		/// Set up experiment:
		///
		int INITIAL_CAPACITY = 1 ;
		int INCREMENT = 1 ;
		int MAX_CAPACITY = 100 ;


		///
		/// Create objects with add(NAME,SIZE,VALUE):
		///
		thief.add("A",3,4) ;
		thief.add("B",4,5) ;
		thief.add("C",7,10) ;
		//thief.add("D",8,11) ;
		//thief.add("E",9,13) ;

		///
		/// Run experiment:
		///
		Thief.Knapsack knapsack ;
		for (int capacity=INITIAL_CAPACITY ; capacity<=MAX_CAPACITY ; capacity+=INCREMENT) {

			System.out.println("Capacity = " + capacity) ;

			thief.resetWork();
			knapsack = thief.packRecursive(capacity) ;
			System.out.println( "  Recursive VALUE/WORK: " + knapsack.getValue() + "/" + thief.getWork() ) ;

			thief.resetWork();
			knapsack = thief.packMemoized(capacity) ;
			System.out.println( "  Memoized VALUE/WORK: " + knapsack.getValue() + "/" + thief.getWork() ) ;

			thief.resetWork();
			knapsack = thief.packDP(capacity) ;
			System.out.println( "  DP VALUE/WORK: " + knapsack.getValue() + "/" + thief.getWork() ) ;

			System.out.println( "  Knapsack: " + knapsack ) ;
		}
	}

}

/// End-of-File

