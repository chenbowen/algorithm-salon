public class Roman2Integer {
    public int romanToInt(String s) {
        //https://leetcode.com/problems/roman-to-integer/
        int[] flags = new int[] {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
        String[] romans = new String[] {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

        int i = romans.length - 1;
        int ans = 0;
        int j = 0; // Points to the start of target string

        // Just replace whenever it sees a combination of romans elements
        while (j < s.length()) {
            if (romans[i].length() == 1 && s.substring(j, j+1).equals(romans[i])) {
                ans = ans + flags[i];
                j += 1;
                i += 1;
            } else if (j < s.length()-1 && romans[i].length() == 2 && s.substring(j, j+2).equals(romans[i])) {
                ans = ans + flags[i];
                j += 2;
                i += 1;
            }
            i--;
        }
        return ans;
    }

    public static void main(String[] args) {
        Roman2Integer r = new Roman2Integer();
        System.out.println("Should be 1: " + r.romanToInt("I"));
        System.out.println("Should be 9: " + r.romanToInt("IX"));
        System.out.println("Should be 41: " + r.romanToInt("XLI"));
        System.out.println("Should be 2943: " + r.romanToInt("MMCMXLIII"));

    }
}
