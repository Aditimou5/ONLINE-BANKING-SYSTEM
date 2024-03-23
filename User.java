package Bank;
import java.sql.*;
import java.util.*;
public class User 
{
	private Connection connection;
	private Scanner sc;
	public User(Connection connection, Scanner sc)
	{
		this.connection=connection;
		this.sc=sc;
	}
	public void register()
	{
		sc.nextLine();
		System.out.println("enter full name");
		String name=sc.nextLine();
		System.out.println("enter email id");
		String email=sc.nextLine();
		System.out.println("enter password");
		String pass=sc.nextLine();
		if(existingUser(email))
		{
			System.out.println("user already exist .go to login");
			return;
		}
		else
		{
			try
			{
				String q="Insert into BankUserTable(name,email,pass) values(?,?,?)";
				PreparedStatement st=connection.prepareStatement(q);
				st.setString(1, name);
				st.setString(2, email);
				st.setString(3, pass);
				int a=st.executeUpdate();
				if(a>0)
				{
					System.out.println("Registration Succesfull");
				}
				else
				{
					System.out.println("Registration failed");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public String login()
	{
		sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
		try
		{
			String q="select email,pass from BankUserTable where email=? and pass=?";
			PreparedStatement st=connection.prepareStatement(q);
			st.setString(1,email);
			st.setString(2,pass);
			ResultSet rs=st.executeQuery();
			if(rs.next())
			{
				return email;
			}
			else
			{
				return null;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public boolean existingUser(String email)
	{
		String q="select email from BankUserTable where email=?";
		try 
		{
			PreparedStatement st=connection.prepareStatement(q);
			st.setString(1,email);
			ResultSet rs=st.executeQuery();
			if(rs.next())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		return false;	
	}
	
	
}
