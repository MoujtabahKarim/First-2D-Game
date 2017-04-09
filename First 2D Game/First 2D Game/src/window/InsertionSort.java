package window ; 

public class InsertionSort {

	public static void sort(int[] array) {
		System.out.println();
		for(int top = 1; top < array.length; top++) {
			int item = array[top] ; 
			int i = top ; 
				
			while (i > 0 && item < array[i - 1]) {
				array[i] = array[i - 1];
				i--;
			}
			
			array[i] = item ; 

			for(int z = 0; z < 5; z++) {
				System.out.print(array[z] + ", ");
			}
			System.out.println();
		}
		
	}

}