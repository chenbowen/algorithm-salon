class Integer2roman {
    // https://leetcode.com/problems/integer-to-roman/
    public String intToRoman(int num) {
        int[] flags = new int[] {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        //string[] romans = new string[] {'I', 'IV', 'V', 'IX', 'X', 'XL', 'L', 'XC', 'C', 'CD', 'D', 'CM', 'M'};
        String[] romans = new String[] {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int curr = num;
        String s = "";
        //System.out.println("curr=" + curr);

        for (int i = flags.length - 1; i >= 0; i--) {
            if (flags[i] <= curr) {
                curr = curr - flags[i];
                s = s + romans[i].toString();
                i++;
            }
        }
        return s;
    }
    public static void main(String[] args) {
        Integer2roman s = new Integer2roman();
        System.out.println("Should be I: " + s.intToRoman(1));
        System.out.println("Should be IV: " + s.intToRoman(4));
        System.out.println("Should be IX: " + s.intToRoman(9));
    }
}
