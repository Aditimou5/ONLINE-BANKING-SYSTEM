package Bank;
import java.sql.*;
import java.util.*;
public class ManagingAccount 
{
	private Connection con;
	private Scanner sc;
	ManagingAccount(Connection con,Scanner sc)
	{
		this.con=con;
		this.sc=sc;
	}
	
	public void debit_money(long acc)
	{
		sc.nextLine();
		System.out.println("Enter Amout :");
		Double amnt=sc.nextDouble();
		try
		{
			String q="Update BankAccount SET BALANCE=BALANCE-? where ACCNO=?";
			PreparedStatement st= con.prepareStatement(q);
			st.setDouble(1,amnt );
			st.setLong(2, acc);
			int rs=st.executeUpdate();
			if(rs>0)
			{
				System.out.println("Rs."+ amnt+"credited succesfully");
				q="select BALANCE from BankAccount where ACCNO=?";
				st=con.prepareStatement(q);
				st.setDouble(1, acc);
				ResultSet rs1=st.executeQuery();
				if(rs1.next())
				{
				System.out.println("Current Balance is "+ rs1.getDouble("BALANCE"));
				}
				con.commit();
			}
			else
			{
				System.out.println("Transaction Failed");
				con.rollback();
			}
			con.setAutoCommit(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void credit_money(long acc)
	{

		sc.nextLine();
		System.out.println("Enter Amout :");
		Double amnt=sc.nextDouble();
		try
		{
			String q="Update BankAccount SET BALANCE=BALANCE+? where ACCNO=?";
			PreparedStatement st= con.prepareStatement(q);
			st.setDouble(1,amnt );
			st.setLong(2, acc);
			int rs=st.executeUpdate();
			if(rs>0)
			{
				System.out.println("Rs."+ amnt+"credited succesfully");
				q="select BALANCE from BankAccount where ACCNO=?";
				st=con.prepareStatement(q);
				st.setDouble(1, acc);
				ResultSet rs1=st.executeQuery();
				if(rs1.next())
				{
				System.out.println("Current Balance is "+ rs1.getDouble("BALANCE"));
				}
				con.commit();
			}
			else
			{
				System.out.println("Transaction Failed");
				con.rollback();
			}
			con.setAutoCommit(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void transfer_money(long acc)
	{
		System.out.println();
		System.out.println("enter receiver account no ");
		long account=sc.nextLong();
		System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
		try
		{
			con.setAutoCommit(false);
			String q="select * from BankAccount where ACCNO=? ";
			PreparedStatement st=con.prepareStatement(q);
			st.setLong(1, acc);
			ResultSet rs=st.executeQuery();
			if(rs.next())
			{
				double balance2=rs.getDouble("BALANCE");
				if(amount<=balance2)
				{
					String credit="Update BankAccount SET BALANCE=BALANCE+? where ACCNO=?";
					String debit="Update BankAccount SET BALANCE=BALANCE-? where ACCNO=?";
					PreparedStatement st1= con.prepareStatement(credit);
					PreparedStatement st2= con.prepareStatement(debit);
					st1.setDouble(1,amount );
					st1.setLong(2, account);
					st2.setDouble(1,amount );
					st2.setLong(2, acc);
					int rs1=st1.executeUpdate();
					int rs2=st2.executeUpdate();
					if(rs1>0 && rs2>0)
					{
						System.out.println("Transaction Successful!");
                        System.out.println("Rs."+amount+" Transferred Successfully");
                        con.commit();
                        con.setAutoCommit(true);
                        return;
					}
					else
					{
						System.out.println("transaction failed");
						con.rollback();
						con.setAutoCommit(true);
					}
				}
				else
				{
					System.out.println("You don't have enough balance to transfer");
				}
			}
			else
			{
				System.out.println("invalid account no");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void check_balance(long acc)
	{
		try
		{
		String q="select BALANCE from BankAccount where ACCNO=?";
		PreparedStatement st=con.prepareStatement(q);
		st.setDouble(1, acc);
		ResultSet rs1=st.executeQuery();
		if(rs1.next())
		{
		System.out.println("Current Balance is "+ rs1.getDouble("BALANCE"));
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
