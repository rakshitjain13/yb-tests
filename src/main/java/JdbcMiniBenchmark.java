import java.sql.*;
import java.lang.*;

public class JdbcMiniBenchmark {
    public static void SelectSingleRow(int prepare_threshold, int number_to_loop, int number_to_fetch) {
        try {
            Connection c=DriverManager.getConnection("jdbc:postgresql://sgupta-pg-i-westc-validate.cbtcvpszcgdq.us-west-2.rds.amazonaws.com:5432/postgres?sslmode=require&preferQueryMode=simple","postgres","Password321");
            Statement s=c.createStatement();
            ResultSet rs;
            float results_time[] = new float[number_to_fetch];
            int results_number[] = new int[number_to_fetch];
            long beginTime;
            long endTime;
            for (int loop=0; loop < number_to_loop; loop++) {
                PreparedStatement p = c.prepareStatement("select i from t where i = ?");
                org.postgresql.PGStatement ps = (org.postgresql.PGStatement)p;
                ps.setPrepareThreshold(prepare_threshold);
                for (int i=0; i < number_to_fetch; i++) {
                    p.setInt(1,i);
                    beginTime = System.nanoTime();
                    rs=p.executeQuery();
                    endTime = System.nanoTime();
                    results_time[i]+=(endTime-beginTime);
                    results_number[i]+=1;
                }
            }
            System.out.print("prepareThreshold: "+prepare_threshold+", ");
            System.out.print("loop: "+number_to_loop+", ");
            System.out.print("fetch: ");
            for (int i=0; i < number_to_fetch; i++) {
                //System.out.printf("[%d];%8.0f",i,results_time[i]/results_number[i]);
                System.out.printf("%8.0f ",results_time[i]/results_number[i]);
            }
            System.out.print("\n");
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
    }
    public static void TruncateInsert(int number_of_rows) {
        try {
            Connection c=DriverManager.getConnection("jdbc:postgresql://sgupta-pg-i-westc-validate.cbtcvpszcgdq.us-west-2.rds.amazonaws.com:5432/postgres?sslmode=require&preferQueryMode=simple","postgres","Password321");
            Statement s=c.createStatement();
            String q="truncate t";
            s.executeUpdate(q);
            PreparedStatement p = c.prepareStatement("insert into t (i) values (?)");
            for (int i=0; i < 1000; i++) {
                p.setInt(1, i);
                p.addBatch();
            }
            p.executeBatch();
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName()+":"+e.getMessage());
            System.exit(0);
        }
    }
    public static void main(String args[]) {
        int number_of_rows=1000;
        TruncateInsert(number_of_rows);

        int number_to_loop=10000;
        int fetch_in_loop=10;
        // warmup
        SelectSingleRow(0, 100000, 1);

        for (int threshold=0; threshold < 6; threshold++) {
            for (int tries=0; tries < 7; tries++) {
                SelectSingleRow(threshold, number_to_loop, fetch_in_loop);
            }
        }
    }
}