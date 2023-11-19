package namegen;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Generator {

    private final Integer order;
    private final Float prior;
    private final Boolean backoff;
    private final List<Model> models = new ArrayList<>();

    public Generator(LinkedList<String> data, Integer order, Float prior, Boolean backoff) {
        this.backoff = backoff;
        this.order = order;
        this.prior = prior;

        var letters = new HashSet<String>();
        for (var word: data) {
            for (int i = 0; i < word.length(); i++) {
                letters.add(String.valueOf(word.charAt(i)));
            }
        }
        ArrayList<String> sortedLetters = new ArrayList<>(letters.stream().sorted(String::compareTo).toList());
        sortedLetters.add(0, "#");

        if (backoff) {
            for (int i = 0; i < order; i++) {
                models.add(new Model(order - i, prior, sortedLetters, new LinkedList<>(data)));
            }
        } else {
            models.add(new Model(order, prior, sortedLetters, new LinkedList<>(data)));
        }
    }

    public String generate() {
        var word = "#".repeat(order);
        var letter = getLetter(word);
        while (!Objects.equals(letter, "#") && letter != null) {
            word += letter;
            letter = getLetter(word);
        }
        return word;
    }

    private String getLetter(String word) {
        String letter = null;
        String context = word.substring(word.length() - order);
        for (var model: models) {
            letter = model.generate(context);
            if (letter == null || letter.equals("#")) {
                context = context.substring(1);
            } else {
                break;
            }
        }
        return letter;
    }

}
