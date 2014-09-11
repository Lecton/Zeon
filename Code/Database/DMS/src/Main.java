import connection.DBConnect;

public class Main {
    public static void main(String[] args) {
        DBConnect con = new DBConnect("127.0.0.1", 5433, "stream2me", "postgres", "root");
        
        if(con.connect()){
            System.out.println("YEAH!!!!");
        }else{
            System.out.println("NOOOOO!!!!!!!");
        }
    }
}
