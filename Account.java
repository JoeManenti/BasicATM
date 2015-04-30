package umsl.edu;

import java.io.*;
import java.text.*;
import java.util.*;

/*
	Homework 2
	Joseph Manenti
*/


public class Account 
{

    private double balance; 
    private String acctNum;
    private int initial_date;
    private int final_date;
    private boolean date_marker;
    private double interest_rate;
    private final Calendar date_1 = new GregorianCalendar();
    private final Calendar date_2 = new GregorianCalendar();
    private final Calendar year_1 = new GregorianCalendar();
    private final Calendar year_2 = new GregorianCalendar();
    private int initial_year;
    private int final_year;
    
    ATM myParent;
    
	public Account (double beg_balance) throws IOException 
        {
		balance = beg_balance;

	} 

        public void menu()throws IOException
        {
           
            while (true) 
            {
                System.out.println("");
                System.out.println("Please make a selection from the menu below:");
                System.out.println("1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. CheckBalance");
                System.out.println("4. Select Different Account or Exit");

                String input = myParent.br.readLine();
                char char1 = input.charAt (0);

                if (char1 == '1') 
                {
                    if (date_marker == true)
                    {
                        getDate_2();
                        getInterest();
                        deposit();
                    }    
                    else
                    {
                        getDate_1();
                        deposit();
                    }
                }
                else if (char1 == '2') 
                {
                    if (date_marker == true)
                    {
                        getDate_2();
                        getInterest();
                        withdraw();
                    }    
                    else
                    {
                        getDate_1();
                        withdraw();
                    }
                }
                else if (char1 == '3')
                {
                    if (date_marker != true)
                    {
                        getInterest();
                        System.out.println("");
                        System.out.println("Your current balance is: " + getBalance());
                        System.out.println("");
                    }
                    else
                    {    
                        getDate_2();
                        getInterest();
                        System.out.println("");
                        System.out.println("Your current balance is: " + getBalance());
                        System.out.println("");
                    }    
                }
                else if (char1 == '4')
                {
                    return;
                }    
                else
                {
                    System.out.println("");
                    System.out.println("Invalid Entry, Please Try Again!");
                    System.out.println("");
                }  
            }
        }
        //To Get Account Number
        public String getAcctNum() 
        {
            return acctNum;
        }
        
        //To Set Account Number
        public void setAcctNum(String acctNum) 
        {
            this.acctNum = acctNum;
        }
        
        //To Set The Balance
        public void setBalance(double balance) 
        {
            this.balance = balance;
        }
        
	//To Get The Balance

	public String getBalance()
        {
		NumberFormat currencyFormatter;
		String currencyOut;
		
		currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
		currencyOut = currencyFormatter.format(balance);

                return currencyOut;
	}

	// For Deposit

	private void deposit() throws IOException 
        {
		BufferedReader br;
		String entered_amount;

                System.out.println("");
                System.out.print("Please enter the amount you would like to deposit: ");
		br = new BufferedReader(new InputStreamReader(System.in));
		entered_amount = br.readLine();
		double amount = Double.parseDouble(entered_amount);
		balance = balance + amount;
                
                if (amount <= 0)
                {    
                            System.out.println("");
                            System.out.println("Can't deposit negative amounts.");
                            System.out.println("");
                }
                else
                 
                System.out.println("");    
                    System.out.println("Your updated balance is: " + getBalance());
                    System.out.println("");
		
	} 	

        //For Withdraw

	private void withdraw() throws IOException 
        {
		BufferedReader br;
		String entered_amount;

                System.out.println("");
		System.out.print("Please enter the amount you would like to withdraw: ");
		br = new BufferedReader(new InputStreamReader(System.in));
		entered_amount = br.readLine();
		double amount = Double.parseDouble(entered_amount);
		
		if (balance < amount) 
                {    
                        System.out.println("");
			System.out.println("Withdrawal Request Denied. Insufficient Funds!");
                        System.out.println("Please Enter New Amount.");
                        System.out.println("Your current balance is: " + getBalance());
                        System.out.println("");
                }        
		else  
                {    
			balance = balance - amount;
	
                        System.out.println("");        
                        System.out.println("Your updated balance is: " + getBalance());
                        System.out.println("");
                }
        }     
        
        //For Marking Initial Date
        
        private void getDate_1() throws IOException
        {
            System.out.println("");
            System.out.print("Please Enter Today's Date (mm/dd/yyyy): ");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(System.in));
            String inputText = br.readLine();
            
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            ParsePosition ypos = new ParsePosition(0);
            ParsePosition pos = new ParsePosition(0);
            
            Date date;
            Date year;
            year = formatter.parse(inputText, ypos);
            date = formatter.parse(inputText, pos);
            year_1.setTime(year);
            date_1.setTime(date);
            initial_year = year_1.get(Calendar.YEAR);
            initial_date = date_1.get(Calendar.DAY_OF_YEAR);
            date_marker = true;
        }
        
        //For Marking Final Date
        
        private void getDate_2() throws IOException
        {
            System.out.println("");
            System.out.print("Please Enter Today's Date (mm/dd/yyyy): ");
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(System.in));
            String inputText = br.readLine();
            
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            ParsePosition ypos = new ParsePosition(0);
            ParsePosition pos = new ParsePosition(0);
           
            Date date;
            Date year;
            year = formatter.parse(inputText, ypos);
            date = formatter.parse(inputText, pos);
            year_2.setTime(year);
            date_2.setTime(date);
            final_year = year_2.get(Calendar.YEAR);
            final_date = date_2.get(Calendar.DAY_OF_YEAR);
            
            if (initial_year > final_year)
            {
                System.out.println("Date entered must be a Future Date!");
                System.out.println("");
                getDate_2();
            }    
            else if ((initial_year == final_year) && (initial_date > final_date))
            {
                System.out.println("Date entered must be a Future Date!");
                System.out.println("");
                getDate_2();
            }    
        }
        
        //For Interest Calculation
        
        private void getInterest()
        {
            int year_gap = final_year - initial_year;
            int date_gap = final_date - initial_date;
            double year_tot = year_gap * 365;
            interest_rate = .05/365;
            double combo_gap = year_tot + date_gap;
            double new_rate = Math.pow(1+interest_rate,combo_gap);
            balance = balance * new_rate;
            initial_date = final_date;
            initial_year = final_year;
        }

}