public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int len = word.length();
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < len; i++)
            deque.addLast(word.charAt(i));
        return deque;
    }

    public boolean isPalindrome(String word) {
        int len = word.length(), hlen = len / 2;
        for (int i = 0; i < hlen; i++)
            if (word.charAt(i) != word.charAt(len - i - 1))
                return false;
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length(), hlen = len / 2;
        for (int i = 0; i < hlen; i++)
            if (cc.equalChars(word.charAt(i), word.charAt(len - i - 1)) == false)
                return false;
        return true;
    }
}
