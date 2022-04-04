package pl.szymonmilczarek.phorestapp.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Component
public class ResultGenerator {

    public final static Integer SLOTS = 4;

    private final static Map<Integer, String> resultMap = Map.of(
            1, "black",
            2, "white",
            3, "green",
            4, "yellow"
    );

    public ArrayList<String> getResult() {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < SLOTS; i++) {
            result.add(resultMap.get(getRandomNumberInRange(1, SLOTS)));
        }
        return result;
    }

    private static int getRandomNumberInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
