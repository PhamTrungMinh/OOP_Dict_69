import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        DictionaryCommandline dcl = new DictionaryCommandline();
//        dcl.dictionaryBasic();
//        dcl.dictionaryAdvanced();
//        dcl.edit(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
//        dcl.showAllWords();
//        dcl.showAllDatabase();
//        dcl.add(new Word(scanner.nextLine(), scanner.nextLine()));
        dcl.edit(new Word("year","năm"),new Word("year","123"));
    }
}
