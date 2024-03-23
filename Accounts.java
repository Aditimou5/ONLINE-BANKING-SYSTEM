package Bank;
/*import java.sql.*;
import java.util.Scanner;
public class Accounts 
{
	private Connection con;
	private Scanner sc;
	public Accounts(Connection con,Scanner sc)
	{
		this.con=con;
		this.sc=sc;
	}
	public int open_account(String email)
	{	if(!account_exist(email))
		{
		sc.nextLine();
        System.out.print("enter name: ");
        String nm = sc.nextLine();
        System.out.print("enter balance: ");
        int bal = sc.nextInt();
		try
		{
			String q="insert into BankAccount(ACCNO,NAME,EMAIL,BALANCE) values (?,?,?,?)";
			int acc=generateAccountNo();
			PreparedStatement st=con.prepareStatement(q);
			st.setInt(1, acc);
			st.setString(2, nm);
			st.setString(3, email);
			st.setInt(4, bal);
			int a=st.executeUpdate();
			if(a>0)
			{
				System.out.println("Account creation successfull");
				return acc;
			}
			else
			{
				throw new RuntimeException("Account Creation failed");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
		throw new RuntimeException("Account already exist"); 
	}
	  public int open_account(String email){
	        if(!account_exist(email)) {
	            String open_account_query = "INSERT INTO BankAccount(ACCNO, NAME, EMAIL, BALANCE) VALUES(?, ?, ?, ?)";
	            sc.nextLine();
	            System.out.print("Enter Full Name: ");
	            String full_name = sc.nextLine();
	            System.out.print("Enter Initial Amount: ");
	            int balance = sc.nextInt();
	            sc.nextLine();
	            try {
	                int account_number = generateAccountNo();
	                PreparedStatement preparedStatement = con.prepareStatement(open_account_query);
	                preparedStatement.setInt(1, account_number);
	                preparedStatement.setString(2, full_name);
	                preparedStatement.setString(3, email);
	                preparedStatement.setDouble(4, balance);
	                //preparedStatement.setString(5, security_pin);
	                int rowsAffected = preparedStatement.executeUpdate();
	                if (rowsAffected > 0) {
	                    return account_number;
	                } else {
	                    throw new RuntimeException("Account Creation failed!!");
	                }
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        throw new RuntimeException("Account Already Exist");

	    }
	  private int generateAccountNo() {
	        try {
	            String q = "SELECT ACCNO FROM BankAccount ORDER BY ACCNO DESC FETCH FIRST 1 ROW ONLY";
	            PreparedStatement st = con.prepareStatement(q);
	            ResultSet resultSet = st.executeQuery(); // Execute the prepared statement
	            if (resultSet.next()) {
	                int last_account_number = resultSet.getInt("ACCNO");
	                return last_account_number + 1;
	            } else {
	                return 10000100;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return 10000100;
	    }
public int getAccountNumber(String email)
	 {
		 try
		 {
		 String q="select ACCNO from BankAccount where email=?";
		 PreparedStatement st=con.prepareStatement(q);
		 st.setString(1, email);
		 ResultSet rs=st.executeQuery();
		 if(rs.next())
		 {
			 return rs.getInt("ACCNO");
		 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 throw new RuntimeException("Account number doesn't exist"); 
	 }
	 
	 
	 public boolean account_exist(String email)
	 {
		String q="select accNo from BankAccount where email=?";
		try{
            PreparedStatement preparedStatement = con.prepareStatement(q);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
	 }
}*/
import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;
    public Accounts(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;

    }




    public long open_account(String email){
        if(!account_exist(email)) {
            String open_account_query = "INSERT INTO BankAccount(ACCNO, NAME, EMAIL, BALANCE) VALUES( ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.print("Enter Full Name: ");
            String full_name = scanner.nextLine();
            System.out.print("Enter Initial Amount: ");
            double balance = scanner.nextDouble();
            try {
                long account_number = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(open_account_query);
                preparedStatement.setLong(1, account_number);
                preparedStatement.setString(2, full_name);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account Creation failed!!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Account Already Exist");

    }

    public long getAccountNumber(String email) {
        String query = "SELECT ACCNO from BankAccount WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("ACCNO");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }



    private long generateAccountNumber() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(" select ACCNO from BankAccount where rownum<=1 order by ACCNO desc");
            if (resultSet.next()) {
                long last_account_number = resultSet.getLong("ACCNO");
                return last_account_number+1;
            } else {
                return 10000100;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 10000100;
    }

    public boolean account_exist(String email){
        String query = "SELECT ACCNO from BankAccount WHERE email = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }
}