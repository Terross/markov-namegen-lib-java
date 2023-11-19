package namegen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NameGenerator {

    private final Generator generator;

    public NameGenerator(LinkedList<String> data, Integer order, Float prior, Boolean backoff) {
        this.generator = new Generator(data, order, prior, backoff);
    }

    public String generateName(Integer minLength, Integer maxLength, String startsWith, String endsWith,
                               String includes, String excludes) {
        var name = "";

        name = generator.generate();
        name = name.replace("#", "");
        if (name.length() >= minLength && name.length() >= maxLength) {
            return name;
        }

        return null;
    }

    public List<String> generateNames(int n, int minLength, int maxLength, String startsWith, String endsWith,
                                      String includes, String excludes) {
        List<String> names = new ArrayList<>();

        while (names.size() < n) {
            var name = generateName(minLength, maxLength, startsWith, endsWith, includes, excludes);
            if (name !=null) {
                names.add(name);
            }
        }
        return names;
    }
}
