package namegen;

import java.util.*;

public class Model {

    private final Integer order;
    private final Float prior;
    private final List<String> alphabet;
    private final Map<String, List<String>> observations = new HashMap<>();
    private final Map<String, List<Float>> chains = new HashMap<>();

    public Model(Integer order, Float prior, List<String> alphabet, LinkedList<String> data) {
        this.order = order;
        this.prior = prior;
        this.alphabet = alphabet;

        train(data);
        buildChain();
    }

    public String generate(String context) {
        var chain = chains.get(context);
        if (chain == null) {
            return null;
        } else {
            return alphabet.get(selectIndex(chain));
        }
    }

    public void retrain(LinkedList<String> data) {
        train(data);
        buildChain();
    }

    private void train(LinkedList<String> data) {
        while (!data.isEmpty()) {
            var d = data.pop();
            d = ("#".repeat(order)) + d + "#";
            for (int i = 0; i < d.length() - order; i++ ){
                var key = d.substring(i, i + order);
                var value = observations.computeIfAbsent(key, k -> new ArrayList<>());
                value.add(String.valueOf(d.charAt(i + order)));
            }
        }
    }

    private void buildChain() {
        for (var context: observations.keySet()) {
            for (var prediction: alphabet) {
                var value = chains.computeIfAbsent(context, k -> new ArrayList<>());
                value.add(prior + countMatches(observations.get(context), prediction));
            }
        }
    }

    private int countMatches(List<String> arr, String v) {
        if (arr == null) {
            return 0;
        }

        int i = 0;
        for (var s: arr) {
            if (Objects.equals(s, v)) {
                i++;
            }
        }
        return i;
    }

    private int selectIndex(List<Float> chain) {
        var totals = new ArrayList<Float>();
        Float accumulator = 0.0f;

        for (var weight: chain) {
            accumulator += weight;
            totals.add(accumulator);
        }

        var rand = Math.random() * accumulator;
        for (int i = 0; i < totals.size(); i++) {
            if (rand < totals.get(i)) {
                return i;
            }
        }

        return 0;
    }
}
