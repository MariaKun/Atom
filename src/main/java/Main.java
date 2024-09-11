import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static AtomicInteger count3 = new AtomicInteger(0);
    private static AtomicInteger count4 = new AtomicInteger(0);
    private static AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threads = new ArrayList<>();

        Thread thread1 = new Thread(() -> {
            for (String str : texts) {
                if (isAlphabetically(str)) {
                    inc(str);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String str : texts) {
                if (isPalindrome(str)) {
                    inc(str);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String str : texts) {
                if (isSameLetter(str)) {
                    inc(str);
                }
            }
        });

        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);
        thread1.start();
        thread2.start();
        thread3.start();

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Красивых слов с длиной 3: " + count3);
        System.out.println("Красивых слов с длиной 4: " + count4);
        System.out.println("Красивых слов с длиной 5: " + count5);
    }

    public static void inc(String str) {
        if (str.length() == 3)
            count3.getAndIncrement();
        if (str.length() == 4)
            count4.getAndIncrement();
        if (str.length() == 5)
            count5.getAndIncrement();
    }

    public static boolean isSameLetter(String text) {
        return text.chars().allMatch(x -> x == text.charAt(0));
    }

    public static boolean isAlphabetically(String text) {
        char[] tempArray = text.toCharArray();
        Arrays.sort(tempArray);
        return text.equals(Arrays.toString(tempArray));
    }

    public static boolean isPalindrome(String text) {
        StringBuilder reverse = new StringBuilder(text).reverse();
        return (reverse.toString()).equals(text);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}