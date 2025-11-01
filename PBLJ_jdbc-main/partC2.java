package controller;

import model.Student;
import java.sql.*;
import java.util.*;

public class StudentDAO {
    private Connection con;

    public StudentDAO() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schooldb", "root", "yourpassword");
    }

    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO Student VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, s.getStudentID());
            pst.setString(2, s.getName());
            pst.setString(3, s.getDepartment());
            pst.setDouble(4, s.getMarks());
            pst.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
        }
        return list;
    }

    public void updateMarks(int id, double marks) throws SQLException {
        String sql = "UPDATE Student SET Marks=? WHERE StudentID=?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setDouble(1, marks);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }
}
