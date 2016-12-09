import java.lang.*;
import java.util.*;

public class Snippet {
	public static void main(String[] args) {
		/* Calculates FIbonacci numbers */
		
		System.out.println("How many fibonacci numbers do you want?");
		
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		
		List<Long> fibonacciNumbers = getFibonacciNumbers(n);
		for(Long i : fibonacciNumbers) {
			System.out.println(i);	
		}
	}
	
	private static List<Long> getFibonacciNumbers(int n) {
		ArrayList<Long> numbers = new ArrayList<Long>();

		long a = 1;
		long b = 1;
		
		while(numbers.size() < n) {
			numbers.add(a);
				
			long tmp = a;
			a = b;
			b += tmp;
		}
		
		return numbers;
	}
}