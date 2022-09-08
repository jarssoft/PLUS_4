import java.util.*;

public class Tunnusluvut {
   /****************
   * @param coll an ArrayList of Comparable objects
   * @return the median of coll
   *****************/
   
   public static <T extends Number> double median(ArrayList<T> coll, Comparator<T> comp) {
      double result;
      int n = coll.size()/2;
      
      if (coll.size() % 2 == 0)  // even number of items; find the middle two and average them
         result = (nth(coll, n-1, comp).doubleValue() + nth(coll, n, comp).doubleValue()) / 2.0;
      else                      // odd number of items; return the one in the middle
         result = nth(coll, n, comp).doubleValue();
         
      return result;
   } // median(coll)
   
   

   /*****************
   * @param coll a collection of Comparable objects
   * @param n  the position of the desired object, using the ordering defined on the list elements
   * @return the nth smallest object
   *******************/
   
   public static <T> T nth(ArrayList<T> coll, int n, Comparator<T> comp) {
      T result, pivot;
      ArrayList<T> underPivot = new ArrayList<>(), overPivot = new ArrayList<>(), equalPivot = new ArrayList<>();
      
      // choosing a pivot is a whole topic in itself.
      // this implementation uses the simple strategy of grabbing something from the middle of the ArrayList.
      
      pivot = coll.get(n/2);
      
      // split coll into 3 lists based on comparison with the pivot
      
      for (T obj : coll) {
         int order = comp.compare(obj, pivot);
         
         if (order < 0)        // obj < pivot
            underPivot.add(obj);
         else if (order > 0)   // obj > pivot
            overPivot.add(obj);
         else                  // obj = pivot
            equalPivot.add(obj);
      } // for each obj in coll
      
      // recurse on the appropriate list
      
      if (n < underPivot.size())
         result = nth(underPivot, n, comp);
      else if (n < underPivot.size() + equalPivot.size()) // equal to pivot; just return it
         result = pivot;
      else  // everything in underPivot and equalPivot is too small.  Adjust n accordingly in the recursion.
         result = nth(overPivot, n - underPivot.size() - equalPivot.size(), comp);
         
      return result;
   } // nth(coll, n)
   
   public static double keskiarvo(ArrayList<Integer> al) {
       double sum = 0.0;
       double length = al.size();

       for(double num : al) {
           sum += num;
       }

       return sum/length;
   }
   
   public static double keskihajonta(ArrayList<Integer> al) {
       double standardDeviation = 0.0;
       double length = al.size();
       double mean = keskiarvo(al);

       for(double num: al) {
           standardDeviation += Math.pow(num - mean, 2);
       }
       
       return Math.sqrt(standardDeviation/length);
   }
   
   public static void main (String[] args) {
      Comparator<Integer> comp = Comparator.naturalOrder();
      Random rnd = new Random();
      
      for (int size = 1; size <= 10; size++) {
         ArrayList<Integer> coll = new ArrayList<>(size);
         for (int i = 0; i < size; i++)
            coll.add(rnd.nextInt(100));
      
         System.out.println("Median of " + coll.toString() + " is " + median(coll, comp));
      } // for a range of possible input sizes
   } // main(args)
} // Utility
