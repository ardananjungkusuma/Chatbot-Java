/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author SARK-29
 */
public class Answer {

    private int id;
    private String answer, category;

    public Answer(String answer, String category) {
        this.answer = answer;
        this.category = category;
    }

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Answer getById(int id) {
        Answer ans = new Answer();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM answer WHERE id= '" + id + "'");

        try {
            while (rs.next()) {
                ans = new Answer();
                ans.setId(rs.getInt("id"));
                ans.setAnswer(rs.getString("answer"));
                ans.setCategory(rs.getString("category"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    public ArrayList<Answer> getAll() {
        ArrayList<Answer> listAnswer = new ArrayList();

        ResultSet rs = DBHelper.selectQuery("SELECT * FROM answer");
        try {
            while (rs.next()) {
                Answer ans = new Answer();
                ans.setId(rs.getInt("id"));
                ans.setAnswer(rs.getString("answer"));
                ans.setCategory(rs.getString("category"));

                listAnswer.add(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAnswer;
    }

    public String cariLur(String keyword) {
        String[] kolom = {"ID", "Nama", "Keterangan"};
        Answer a = new Answer();
        ArrayList<Answer> list = new Answer().search(keyword);
        String answer;
        Answer rand = Answer.getRandomAnswer(list);
        answer = rand.getAnswer();

        return answer;
    }

    public ArrayList<Answer> filterAsk(String keyword) {
        ArrayList<Answer> hasilCategory = new ArrayList();
        String sqlSearch = "SELECT * FROM ask WHERE ask = '" + keyword.toString() + "'";
        ResultSet rs = DBHelper.selectQuery(sqlSearch);
        Answer ans = new Answer();
        try {
            while (rs.next()) {
                ans.setCategory(rs.getString("category"));
                hasilCategory.add(ans);
            }

        } catch (Exception e) {
            ans.setCategory("undefined");
            hasilCategory.add(ans);
        }
        return hasilCategory;
    }

    public ArrayList<Answer> search(String keyword) {
        ArrayList<Answer> listAnswer = new ArrayList();
        String sqlSearch = "SELECT * FROM answer WHERE category LIKE '%" + keyword + "%'";
        ResultSet rs = DBHelper.selectQuery(sqlSearch);
        try {
            while (rs.next()) {
                Answer ans = new Answer();
                ans.setId(rs.getInt("id"));
                ans.setAnswer(rs.getString("answer"));
                ans.setCategory(rs.getString("category"));

                listAnswer.add(ans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAnswer;
    }

    public static Answer getRandomAnswer(List<Answer> answer) {
        return answer.get(new Random().nextInt(answer.size()));
    }

    public void save() {
        if (getById(id).getId() == 0) {
            String sqlInsert = "INSERT INTO answer (answer,category) VALUES('" + this.answer + "','" + this.category + "')";
            this.id = DBHelper.insertQueryGetId(sqlInsert);
        } else {
            String sqlUpdate = "UPDATE answer SET answer ='" + this.answer + "', category = '" + this.category + "' WHERE id= '" + this.id + "'";
            DBHelper.executeQuery(sqlUpdate);
        }
    }

    public void delete() {
        String sqlDelete = "DELETE FROM answer WHERE id='" + this.id + "'";
        DBHelper.executeQuery(sqlDelete);
    }
}
