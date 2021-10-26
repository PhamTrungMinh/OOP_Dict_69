import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class DictionaryManagement extends Dictionary {
    private int wordNum = 0;
    private Scanner scanner = new Scanner(System.in);

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

    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
        this.setWordNum(scanner.nextInt());
        scanner.nextLine();
        for (int i = 0; i < wordNum; i++) {
            dict.add(new Word(scanner.nextLine(), scanner.nextLine()));
        }
    }

    public void insertFromFile() {
        try {
            FileReader file = null;
            file = new FileReader("dictionary.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String eng;
            while ((eng = bufferedReader.readLine()) != null) {
                String vie = bufferedReader.readLine();
                dict.add(new Word(eng, vie));
                wordNum++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(String w, String engMod, String vieMod) {
        int index = -1;
        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).getWord_target().equals(w)) {
                index = i;
                break;
            }
        }
        if (index < 0) return;
        if (!engMod.equals("")) {
            dict.get(index).setWord_target(engMod);
        }
        if (!vieMod.equals("")) {
            dict.get(index).setWord_explain(vieMod);
        }
    }

    public void dictionaryLookup() {
        System.out.println("Tu can tra: ");
        String lookup = scanner.nextLine();
        boolean flag = false;
        for (int i = 0; i < wordNum; i++) {
            if (lookup.equals(dict.get(i).getWord_target())) {
                System.out.println(dict.get(i).getWord_target() + " = " + dict.get(i).getWord_explain());
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Không có trong từ điển!");
        }
    }

    public void dictionaryExportToFile() throws Exception {
        FileWriter writer = new FileWriter("dictionaryExport.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        for (int i = 0; i < getWordNum(); i++) {
            bufferedWriter.write(dict.get(i).getWord_target() + "\t"
                    + dict.get(i).getWord_explain() + "\n");

        }
        bufferedWriter.close();
    }

    public void showAllDatabase() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                System.out.printf("%-8d", resultSet.getInt(1));
                System.out.printf("%-23s", "| " + resultSet.getString(2));
                System.out.println("| " + resultSet.getString(4));
            }
            resultSet.close();
            statement.close();
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

    public void edit(Word preWord, Word editWord) {
        try (Connection connection = DriverManager.getConnection(url,username,password)) {
            connection.setAutoCommit(false);
            PreparedStatement pre_statement1 = connection.prepareStatement(sqlSelect2);
            pre_statement1.setString(1,preWord.getWord_target());
            ResultSet resultSet = pre_statement1.executeQuery();
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
}
