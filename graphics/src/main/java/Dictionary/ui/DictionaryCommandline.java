/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dictionary.ui;

/**
 *
 * @author ADMIN
 */
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
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
        showAllWords();
        dictionaryExportToFile();
    }

    /**
     *
     * @param search
     * @return
     */
   public List<Word> dictionarySearcher(String search) {
        insertFromFile();
        List<Word> ls = new ArrayList<>();
        for (int i=0; i<getWordNum(); i++) {
            if (search.length() <= dict.get(i).getWord_target().length()){
                String sub = dict.get(i).getWord_target().substring(0,search.length());
                if (search.equals(sub)) {
                   ls.add(dict.get(i));
                }
            }
        }
        return ls;
    }
}