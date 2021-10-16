import java.io.*;
import java.util.Scanner;

public class DictionaryManagement extends Dictionary {
    private int wordNum = 0;

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public void insertFromCommandline(){
        Scanner scanner = new Scanner(System.in);
        this.setWordNum(scanner.nextInt());
        scanner.nextLine();
        for(int i=0; i<wordNum; i++){
            dict.add(new Word(scanner.nextLine(), scanner.nextLine()));
        }
    }

    public void insertFromFile(){
        try {
            FileReader file = null;
            file = new FileReader("dictionary.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String eng;
            while((eng = bufferedReader.readLine()) != null){
                String vie = bufferedReader.readLine();
                dict.add(new Word(eng,vie));
                wordNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dictionaryLookup() {
        System.out.println("Tu can tra: ");
        Scanner scanner = new Scanner(System.in);
        String lookup = scanner.nextLine();
        boolean flag = false;
        for (int i=0; i<wordNum; i++) {
            if (lookup.equals(dict.get(i).getWord_target())) {
                System.out.println(dict.get(i).getWord_target() + " = " + dict.get(i).getWord_explain());
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Khong co trong tu dien!");
        }
    }

    public void dictionaryExportToFile() throws Exception {
        FileWriter writer = new FileWriter("dictionaryExport.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (int i=0; i<getWordNum(); i++) {
            bufferedWriter.write(dict.get(i).getWord_target() + "\t"
                                    + dict.get(i).getWord_explain() + "\n");

        }
        bufferedWriter.close();
    }
}
