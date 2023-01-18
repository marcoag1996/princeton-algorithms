import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        //read number of strings
        final int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String[] strings = StdIn.readAllStrings();
        for (String text : strings) {
            queue.enqueue(text);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
