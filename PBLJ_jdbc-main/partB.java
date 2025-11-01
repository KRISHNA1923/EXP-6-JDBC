import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/companydb";
    static final String USER = "root";
    static final String PASS = "yourpassword";

    public static void main(String[] args) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\n------ Product Menu ------");
                System.out.println("1. Insert Product");
                System.out.println("2. Display All Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1 -> insertProduct(con, sc);
                    case 2 -> displayProducts(con);
                    case 3 -> updateProduct(con, sc);
                    case 4 -> deleteProduct(con, sc);
                    case 5 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void insertProduct(Connection con, Scanner sc) throws SQLException {
        String sql = "INSERT INTO Product VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            System.out.print("Enter ProductID: ");
            pst.setInt(1, sc.nextInt());
            System.out.print("Enter ProductName: ");
            pst.setString(2, sc.next());
            System.out.print("Enter Price: ");
            pst.setDouble(3, sc.nextDouble());
            System.out.print("Enter Quantity: ");
            pst.setInt(4, sc.nextInt());
            pst.executeUpdate();
            System.out.println("✅ Product inserted successfully!");
        }
    }

    static void displayProducts(Connection con) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("ProductID\tName\tPrice\tQuantity");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("ProductID") + "\t\t" +
                                   rs.getString("ProductName") + "\t" +
                                   rs.getDouble("Price") + "\t" +
                                   rs.getInt("Quantity"));
            }
        }
    }

    static void updateProduct(Connection con, Scanner sc) throws SQLException {
        con.setAutoCommit(false);
        try {
            String sql = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                System.out.print("Enter ProductID: ");
                int id = sc.nextInt();
                System.out.print("Enter new Price: ");
                pst.setDouble(1, sc.nextDouble());
                System.out.print("Enter new Quantity: ");
                pst.setInt(2, sc.nextInt());
                pst.setInt(3, id);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    con.commit();
                    System.out.println("✅ Product updated successfully!");
                } else {
                    con.rollback();
                    System.out.println("❌ Product not found!");
                }
            }
        } finally {
            con.setAutoCommit(true);
        }
    }

    static void deleteProduct(Connection con, Scanner sc) throws SQLException {
        con.setAutoCommit(false);
        try {
            String sql = "DELETE FROM Product WHERE ProductID=?";
            try (PreparedStatement pst = con.prepareStatement(sql)) {
                System.out.print("Enter ProductID: ");
                pst.setInt(1, sc.nextInt());
                int rows = pst.executeUpdate();
                if (rows > 0) {
                    con.commit();
                    System.out.println("✅ Product deleted successfully!");
                } else {
                    con.rollback();
                    System.out.println("❌ Product not found!");
                }
            }
        } finally {
            con.setAutoCommit(true);
        }
    }
}
