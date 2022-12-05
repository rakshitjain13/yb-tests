import java.sql.*;

public class driverDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            String classpath = System.getProperty("java.class.path");
            System.out.println(classpath);
            System.out.println("Could not load driver");
        }
        String jdbcConnectionUrl = "jdbc:postgresql://sgupta-pg-i-westc-validate.cbtcvpszcgdq.us-west-2.rds.amazonaws.com:5432/postgres?sslmode=require";
        String user = "postgres";
        String password = "Password321";
        Connection conn = DriverManager.getConnection(jdbcConnectionUrl, user, password);
        try {
            System.out.println("Connected to the PostgreSQL server successfully.");


            int count = 100;
            PreparedStatement pstmt = conn.prepareStatement("select * from pkey_hash_point_lookup_tbl_128_par_1 where col_varchar_id_1='1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa';");
            org.postgresql.PGStatement ps = (org.postgresql.PGStatement)pstmt;
            ps.setPrepareThreshold(0);
            for (int i = 0; i < count; i++) {
                ResultSet rs = pstmt.executeQuery();
                boolean usingServerPrepare = ps.isUseServerPrepare();
                double start = System.nanoTime();
                while (rs.next()) {
                    System.out.println(usingServerPrepare);
                }
                double end = System.nanoTime();
                System.out.println(end-start);
                rs.close();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
