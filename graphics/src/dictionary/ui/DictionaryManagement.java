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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.sound.sampled.*;

public class DictionaryManagement extends Dictionary {
    private int wordNum = 0;
    
    private String url = "jdbc:mysql://localhost:3306/mydictionary";
    private String username = "root";
    private String password = "";
    private String sqlSelect = "SELECT * FROM av";
    private String sqlSelect2 = "SELECT * FROM av WHERE word = ?";
    private String sqlInsert = "INSERT INTO av (word,description) VALUES (?, ?)";
    private String sqlUpdate = "UPDATE av SET word = ?, description = ? WHERE id = ?";
    private String sqlDelete = "DELETE FROM av WHERE word = ?";
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
        wordNum =0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlSelect);
                while (resultSet.next()) {
                    dict.add(new Word(resultSet.getString(2), resultSet.getString(4)));
                    wordNum++;
                }
                resultSet.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
    public void remove(String removeWord) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);
            PreparedStatement pre_statement = connection.prepareStatement(sqlDelete);
            pre_statement.setString(1, removeWord);
            pre_statement.execute();
            pre_statement.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void add(Word addWord) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false);
            PreparedStatement pre_statement = connection.prepareStatement(sqlInsert);
            ResultSet resultSet = pre_statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(addWord.getWord_target())) {
                    return;
                }
            }
            pre_statement.setString(1, addWord.getWord_target());
            pre_statement.setString(2, addWord.getWord_explain());
            pre_statement.execute();
            pre_statement.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void edit(Word preWord, Word editWord) {
       try (Connection connection = DriverManager.getConnection(url,username,password)) {
            connection.setAutoCommit(false);
            PreparedStatement pre_statement1 = connection.prepareStatement(sqlSelect2);
            pre_statement1.setString(1,preWord.getWord_target());
            ResultSet resultSet = pre_statement1.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            PreparedStatement pre_statement2 = connection.prepareStatement(sqlUpdate);
            pre_statement2.setString(1,editWord.getWord_target());
            pre_statement2.setString(2,editWord.getWord_explain());
            pre_statement2.setInt(3,id);
            pre_statement2.executeUpdate();
            pre_statement1.close();
            pre_statement2.close();
            resultSet.close();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void sound(String word) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("sound\\"+word+".wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
    }
    public String lookup(String searchWord) {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sqlSelect2);
            statement.setString(1, searchWord);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(4);
        } catch (SQLException throwables) {
            return "Không tìm thấy từ";
        }
        
    }
    public void dictionaryExportToFile() throws Exception {
       
    }
    
}
