import namegen.NameGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {


    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<String> data = new LinkedList<>();
        File file = new File("/home/dmitry/Documents/MarkovNicknameGenerator/src/main/resources/musical_styles.txt");
        Scanner scanner = new Scanner(file);
        for (int i = 0; i < 450; i ++) {
            data.add(scanner.next());
        }
        System.err.println(123);
        NameGenerator nameGenerator = new NameGenerator(data, 2, 0.0f, false);
        var result = new HashSet<>(nameGenerator.generateNames(100000, 5, 15, "", "", "", ""));
        System.out.println(result);

    }
}
