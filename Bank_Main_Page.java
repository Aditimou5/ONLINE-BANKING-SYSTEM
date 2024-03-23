package Bank;
import java.sql.*;
import java.util.Scanner;
public class Bank_Main_Page

{
	private static final String url="jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username="system";
	private static final String password="1234";
	public static void main(String a[]) throws SQLException
	{
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			Connection con=DriverManager.getConnection(url,username,password);
			Scanner sc=new Scanner(System.in);
			long accountno;
			String email;
			User user=new User(con,sc);
			Accounts account=new Accounts(con,sc);
			ManagingAccount accmanager=new ManagingAccount(con,sc);
			while(true)
			{
				System.out.println("====WELCOME TO ONLINE BANKING SYSTEM====");
				System.out.println("====1. REGISTER ====");
				System.out.println("====2. LOGIN ====");
				System.out.println("====3. EXIT ====");
				System.out.println("==== SELECT AN OPTION ====");
				int option=sc.nextInt();
				switch(option)
				{
				case 1:
					user.register();
					break;
				case 2:
					email = user.login();
                    if(email!=null)
                    {
                        System.out.println();
                        System.out.println("User Logged In!");
                        if(!account.account_exist(email)){
                            System.out.println();
                            System.out.println("1. Open a new Bank Account");
                            System.out.println("2. Exit");
                            if(sc.nextInt() == 1) {
                                accountno = account.open_account(email);
                                System.out.println("Account Created Successfully");
                                System.out.println("Your Account Number is: " + accountno);
                            }else
                            {
                               break;
                            }

                        }
                        accountno = account.getAccountNumber(email);
                        int choice=0;
                        while(choice!=5)
                        {
                        	System.out.println();
                        	System.out.println("====1 . Debit Money ====");
                        	System.out.println("====2 . Credit Money ====");
                        	System.out.println("====3 . Transfer Money ====");
                        	System.out.println("====4 . Check balance  ====");
                        	System.out.println("====5 . Log out ====");
                        	choice = sc.nextInt();
                        	switch(choice)
                        	{
                        	case 1:
                        		accmanager.debit_money(accountno);
                        		break;
                        	case 2:
                        		accmanager.credit_money(accountno);
                        		break;
                        	case 3:
                        		
                        		accmanager.transfer_money(accountno);
                        		break;
                        	case 4:
                        		accmanager.check_balance(accountno);
                        		break;
                        	case 5:
                        		break;
                        	default:
                        		System.out.println("Enter a valid choice");
                        		break;
                        	}
                        }
                        System.out.println("Logged out Succesfully");
                        System.out.println();
                        break;
                        
                    }
                    else
                    {
                    	System.out.println("Incorrect Email or Password!");
                    }
				case 3:
					System.out.println("Thanks for using Bank Accout");
					return;
				default:
					System.out.println("Enter a valid choice");
					break;
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
	
	
	
	

