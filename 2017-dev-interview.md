# easy
1. ## https://leetcode.com/problems/subtree-of-another-tree/

    - ### Solution 1:Recursively check each node
        - ```java
           public class Solution {
              public boolean isSubtree(TreeNode s, TreeNode t) {
                  if (s == null) return false;
                  if (isSame(s, t)) return true;
                  return isSubtree(s.left, t) || isSubtree(s.right, t);
              }

              private boolean isSame(TreeNode s, TreeNode t) {
                  if (s == null && t == null) return true;
                  if (s == null || t == null) return false;

                  if (s.val != t.val) return false;

                  return isSame(s.left, t.left) && isSame(s.right, t.right);
              }
          }
          ````
      - ### Solution 2:Check if two preorder strings is the same
        - ```java
            public class Solution {
              HashSet < String > trees = new HashSet < > ();
              public boolean isSubtree(TreeNode s, TreeNode t) {
                  String tree1 = preorder(s, true);
                  String tree2 = preorder(t, true);
                  return tree1.indexOf(tree2) >= 0;
              }
              public String preorder(TreeNode t, boolean left) {
                  if (t == null) {
                      if (left)
                          return "lnull";
                      else
                          return "rnull";
                  }
                  return "#"+t.val + " " +preorder(t.left, true)+" " +preorder(t.right, false);
              }
          }
          ```

2. ## https://leetcode.com/problems/merge-two-binary-trees
    - ### Solution:
      - ```python
          # Definition for a binary tree node.
          # class TreeNode(object):
          #     def __init__(self, x):
          #         self.val = x
          #         self.left = None
          #         self.right = None

          class Solution:
              def mergeTrees(self, t1, t2):
                  """
                  :type t1: TreeNode
                  :type t2: TreeNode
                  :rtype: TreeNode
                  """
                  if not t1:
                      return t2
                  if not t2:
                      return t1
                  t1.val += t2.val
                  t1.left = self.mergeTrees(t1.left, t2.left)
                  t1.right = self.mergeTrees(t1.right, t2.right)
                  return t1
        ```
3. ## https://leetcode.com/problems/valid-anagram/
    - ### Solution 1: two strings should be the same after sorting.
      - ```java
         public boolean isAnagram(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }
            char[] str1 = s.toCharArray();
            char[] str2 = t.toCharArray();
            Arrays.sort(str1);
            Arrays.sort(str2);
            return Arrays.equals(str1, str2);
        }
        ```
    - ### Solution 2: check if two dictionary counters are the same
      - ```java
         public class Solution {
            public boolean isAnagram(String s, String t) {
                int[] alphabet = new int[26];
                for (int i = 0; i < s.length(); i++) 
                  alphabet[s.charAt(i) - 'a']++;  // ascii, save space
                for (int i = 0; i < t.length(); i++) 
                  alphabet[t.charAt(i) - 'a']--;
                for (int i : alphabet) if (i != 0) return false;
                return true;
            }
        }
        ```
4. ## https://leetcode.com/problems/longest-harmonious-subsequence
    - ### Solution1: using hashmap(easy)
        - ```java
            public class Solution {
                public int findLHS(int[] nums) {
                    HashMap < Integer, Integer > map = new HashMap < > ();
                    int res = 0;
                    for (int num: nums) {
                        map.put(num, map.getOrDefault(num, 0) + 1);
                    }
                    for (int key: map.keySet()) {
                        if (map.containsKey(key + 1))
                            res = Math.max(res, map.get(key) + map.get(key + 1));
                    }
                    return res;
                }
            }
          ```
# not easy
1. ## https://leetcode.com/problems/letter-combinations-of-a-phone-number
    - ### Solution:
        - ```java
            public class Solution {
              public List<String> letterCombinations(String digits) {
                  String[] a = new String[10];
                  a[0] = "0";
                  a[2] = "abc";
                  a[3] = "def";
                  a[4] = "ghi";
                  a[5] = "jkl";
                  a[6] = "mno";
                  a[7] = "pqrs";
                  a[8] = "tuv";
                  a[9] = "wxyz";
                  List<String> res = new ArrayList<String>();
                  if (digits.length() == 0) return res;
                  char[] cur = new char[digits.length()];
                  recursive(digits, 0, a, cur, res);
                  return res;

              }
              private void recursive(String digits, int n, String[] a, char[] cur, List<String> res) {
                  if (n == digits.length()) {
                      res.add(new String(cur));
                  } else {
                      String b = a[digits.charAt(n) - '0'];
                      for (int i = 0; i < b.length(); i++) {
                          cur[n] = b.charAt(i);
                          recursive(digits, n+1, a, cur, res);
                      }
                  }
              }
          }
          ```
2. ## https://leetcode.com/problems/rotate-image/description/
  - ## Solution:
    - ```java
        public class Solution {
          public void rotate(int[][] matrix) {
              int n = matrix.length;
              // col
              for (int i = 0; i < n/2; i++) {
                  int m = n-i*2-1;
                  for (int j = i; j < i+m; j++) {
                      int t = matrix[j][i];
                      matrix[j][i] = matrix[i+m][j];
                      matrix[i+m][j] = matrix[n-1-j][i+m];
                      matrix[n-1-j][i+m] = matrix[i][n-1-j];
                      matrix[i][n-1-j] = t;
                  }
              }
          }
      }
      ```
3. ## https://leetcode.com/problems/longest-palindromic-substring
  - ## Solution:
    - ```java
        public class Solution {
          private int lo, maxLen;

          public String longestPalindrome(String s) {
            int len = s.length();
            if (len < 2)
              return s;

              for (int i = 0; i < len-1; i++) {
                extendPalindrome(s, i, i);  //assume odd length, try to extend Palindrome as possible
                extendPalindrome(s, i, i+1); //assume even length.
              }
              return s.substring(lo, lo + maxLen);
          }

          private void extendPalindrome(String s, int j, int k) {
            while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
              j--;
              k++;
            }
            if (maxLen < k - j - 1) {
              lo = j + 1;
              maxLen = k - j - 1;
            }
          }
      }
      ```
