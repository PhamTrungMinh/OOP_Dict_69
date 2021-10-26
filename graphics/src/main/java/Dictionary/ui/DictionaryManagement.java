/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dictionary.ui;

/**
 *
 * @author ADMIN
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;

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
            dict = new ArrayList<>();
            wordNum = 0;
            FileInputStream file = new FileInputStream("dictionary.txt");
            Reader reader = new java.io.InputStreamReader(file, "utf8");
            BufferedReader br = new BufferedReader(reader);
            String eng;
            while((eng = br.readLine()) != null){
                String vie = br.readLine();
                dict.add(new Word(eng,vie));
                wordNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String dictionaryLookup(String lookup) {
        insertFromFile();
        for (int i=0; i<wordNum; i++) {
            if (lookup.equals(dict.get(i).getWord_target())) {               
                return dict.get(i).getWord_explain().toString();
            }
        }
            return "";
    }
    public void delete(String word) {
        for (int i=0; i<wordNum; i++) {
            if (word.equals(dict.get(i).getWord_target())) {               
                 dict.remove(i);
                 wordNum--;
                 break;
            }
        }
        try {
            dictionaryExportToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void AddWord(String vie,String eng) {
        dict.add(new Word(vie,eng));
        wordNum++;
        try {
            dictionaryExportToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void EditWord(String word, String newExplain) {
        for (int i=0; i<wordNum; i++) {
            if (word.equals(dict.get(i).getWord_target())) {               
                 dict.get(i).setWord_explain(newExplain);
                 break;
            }
        }
        try {
            dictionaryExportToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sound(String word) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("sound\\"+word+".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
    }
    public void dictionaryExportToFile() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("dictionary.txt");
        Writer writer = new java.io.OutputStreamWriter(fileOutputStream, "utf8");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (int i=0; i<getWordNum(); i++) {
            bufferedWriter.write(dict.get(i).getWord_target() + "\n"
                                    + dict.get(i).getWord_explain() + "\n");

        }
        bufferedWriter.close();
    }
    
}
