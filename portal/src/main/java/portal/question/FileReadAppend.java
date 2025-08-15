package portal.question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * 读取一个txt文件的内容，输出文件的内容至控制台，并在该文件的末尾写入一个字符串“Hello World”
 */
public class FileReadAppend {
    public static void main(String[] args) {
        // 指定文件路径（请替换为实际文件路径）
        String filePath = "example.txt";
        Path path = Paths.get(filePath);

        try {
            // 1. 读取文件内容并输出到控制台
            System.out.println("文件内容：");
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                System.out.println(line);
            }

            // 2. 在文件末尾追加字符串
            String appendText = "Hello World";
            Files.write(path,
                    System.lineSeparator().getBytes(),  // 添加换行符
                    StandardOpenOption.APPEND);
            Files.write(path,
                    appendText.getBytes(),
                    StandardOpenOption.APPEND);

            System.out.println("\n成功在文件末尾添加: \"" + appendText + "\"");

        } catch (IOException e) {
            System.err.println("操作失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void solutionByScanner() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C://t.txt");
        Scanner scanner = new Scanner(fileInputStream);
        while (scanner.hasNext()){
            System.out.println(scanner.nextLine());
        }
    }

    private void solutionByFiles() throws IOException {
        Path path = Paths.get("C://t.txt");
        //小文件处理可以直接get
        List<String> strings = Files.readAllLines(path);

        //大文件转bufferreader
        BufferedReader bufferedReader = Files.newBufferedReader(path);
        Stream<String> lines = bufferedReader.lines();
    }


}
