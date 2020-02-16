package twentypractise;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainClass {

    private static final String filePath = "/home/dunu008/Hash-code/Folder2020/Dataset";
    private static final String[] readFileName = {"//a_example.in", "//b_small.in", "//c_medium.in",
            "//d_quite_big.in", "//e_also_big.in"};

    public static void main(String[] args) {
        for (String fileName : readFileName) {
            doCalculate(fileName);
        }
    }

    static void doCalculate(String fileName) {
        List<Integer> answerList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath + fileName))) {
            List<String> arrayList = stream.collect(Collectors.toCollection(ArrayList::new));

            int breakIndex = 0;
            int[] firstLineData = Arrays.stream(arrayList.get(0).split(" ")).mapToInt(Integer::parseInt)
                    .toArray();
            int[] secondLineData = Arrays.stream(arrayList.get(1).split(" ")).mapToInt(Integer::parseInt)
                    .toArray();

            int firstLineFirstData = firstLineData[0];

            //keep adding to answerLIst until a maximum number and store the break point.
            for (int i = 0; i <= secondLineData.length; i++) {
                if (firstLineFirstData - secondLineData[i] > 0) {
                    answerList.add(secondLineData[i]);
                    firstLineFirstData = firstLineFirstData - secondLineData[i];
                } else {
                    breakIndex = i;
                    break;
                }
            }

            int copyBreakPoint = breakIndex;

            //Remove each element in answerList and check whether we can add any element from break point.
            for (int j = 0; j < answerList.size(); j++) {
                if (breakIndex > secondLineData.length - 1) {
                    break;
                }
                if (firstLineFirstData + answerList.get(j) - secondLineData[breakIndex] >= 0) {
                    firstLineFirstData = firstLineFirstData + answerList.get(j) - secondLineData[breakIndex];
                    answerList.remove(j);
                    answerList.add(secondLineData[breakIndex]);
                    breakIndex++;
                }
            }

            System.out.println(answerList.stream().mapToInt(Integer::intValue).sum());

            PrintWriter writer = new PrintWriter(filePath + "//answers" + fileName, "UTF-8");
            writer.println(answerList.size());
            writer.println(String.join(" ", answerList.stream().map(String::valueOf)
                    .collect(Collectors.joining(" "))));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
