/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dictionary.ui;

/**
 *
 * @author ADMIN
 */
public class Word {
    private String word_target;
    private String word_explain;

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public Word (){
        word_target = new String();
        word_explain = new String();
    }

    public Word (String wt,String we){
        word_explain = we;
        word_target = wt;
    }
}
