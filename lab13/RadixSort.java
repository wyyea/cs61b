/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        
        int max_len = 0;
        for (String str: asciis)
            max_len = max_len > str.length() ? max_len : str.length();
        
        String[] sorted = asciis.clone();
        for (int i = max_len - 1; i >= 0; i--){
            sortHelperLSD(sorted, i);
        }
        
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        
        int[] counts = new int[260];
        int[] values = new int[asciis.length];
        for (int i = 0; i < asciis.length; i++){
            String str = asciis[i];
            values[i] = index < str.length() ? str.charAt(index) + 1 : 0;            
            counts[values[i]]++;
        }

        int pos = 0;
        int[] starts = new int[260];
        for (int i = 0; i < 260; i++){
            starts[i] = pos; 
            pos += counts[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++){
            pos = starts[values[i]];
            sorted[pos] = asciis[i]; // copy the string or not ?
            starts[values[i]]++;
        }

        for (int i = 0; i < asciis.length; i++){
            asciis[i] = sorted[i];
        }

        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
    
    public static void main(String[] args) {
        String[] arr = new String[]{"qlhtglq", "ahglah", "ahthewn", "ohwenkfn", "jclhlsfj"};
        for (String str: arr)
            System.out.print(str + ", ");
        System.out.println("");
        arr = sort(arr);
        for (String str: arr)
            System.out.print(str + ", ");
        System.out.println("");
    }
}
