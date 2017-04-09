package sorts;

public class ShakerSort {
	
	// Question 4
		public static void shakerSort(int list[]) {

			boolean sorted = false;
			for (int top = list.length - 1; top > 0 && sorted == false; top--) {

				sorted = true;
				System.out.println();
				for (int i = 0; i < top; i++) {

					
					if (list[i] > list[i + 1]) {
						sorted = false; // a swap was required
						int temp = list[i];
						list[i] = list[i + 1];
						list[i + 1] = temp;

					}
				
				
				}
				
				
	
				for (int t = top - 1; t > 0; t--) {
					if (list[t] < list[t - 1]) {
						sorted = false; // a swap was required
						int temp = list[t];
						list[t] = list[t - 1];
						list[t - 1] = temp;
						
					}
				
				
				}
		
				
				
				for(int z = 0; z < 5; z++) {
					System.out.print(list[z] + ", ");
				}
			}
		}

}
