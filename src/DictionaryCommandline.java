import java.util.Scanner;

public class DictionaryCommandline extends DictionaryManagement {
    public void showAllWords(){
        System.out.println("No      | English              | Vietnamese");
        for(int i=0; i<dict.size(); i++){
            System.out.printf("%-8d",i+1);
            System.out.printf("%-23s", "| " + dict.get(i).getWord_target());
            System.out.println("| " + dict.get(i).getWord_explain());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() throws Exception {
        insertFromFile();
        showAllWords();
        dictionaryLookup();
        dictionarySearcher();
        dictionaryExportToFile();
    }

    public void dictionarySearcher() {
        System.out.println("Tu can tim: ");
        Scanner scanner = new Scanner(System.in);
        String search = scanner.nextLine();
        for (int i=0; i<getWordNum(); i++) {
            if (search.length() < dict.get(i).getWord_target().length()){
                String sub = dict.get(i).getWord_target().substring(0,search.length());
                if (search.equals(sub)) {
                    System.out.print(dict.get(i).getWord_target() + ", ");
                }
            }
        }
        System.out.println("...");
    }
}
