public class ArrayEvenOrOdd {
    public static void main(String[] args) {

        int[] arey = {1,2,3,4,5,6,7,8,9,10,12,11,13};
        boolean results = false;
        int even_count = 0;
        int odd_count = 0;

        for (int i = 0; i < arey.length; i++) {
            if (arey[i] % 2 == 0) {
                even_count++;
                odd_count = 0;
            } else {
                odd_count++;
                even_count = 0;
            }

            if (even_count >= 3 || odd_count >= 3) {
                results = true;
                break; // stop early once found
            }
        }

        System.out.println(results);
    }
}
