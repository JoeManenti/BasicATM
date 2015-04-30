package umsl.edu;

import java.io.*;
import java.util.*;

/*
	Homework 2
	Joseph Manenti
*/

public class ATM 
{
    Account myAccounts[] = new Account[3];   
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String args[]) throws IOException
    {
        System.out.println("Welcome to M&M Bank!");
        ATM myATM = new ATM();
        // Populate the accounts with default data
        myATM.populateAccts();
        
        // Read account data from disk
        myATM.readAcct();
      
        // Enter the main choose account loop
        myATM.ChooseAcct();
        
        // Save account data to disk
        myATM.writeAcct();
         
        //Exit message with account balance totals
        System.out.println("");
        System.out.println("Account Balances:");
        System.out.println("");
        myATM.showAccts();
        System.out.println("");
        System.out.println("Thank you for Choosing M&M Bank.");
        System.out.println("Goodbye.");
    }

    public void showAccts()
    {
        for(int i = 0; i < 3; i++)
        {
            System.out.println(myAccounts[i].getAcctNum() + " " + myAccounts[i].getBalance());
        }

    }

    public void populateAccts() throws IOException
    {
        for(int i =0; i<3; i++)
        {
            myAccounts[i] = new Account(100.00);
            // added
            myAccounts[i].myParent = this;
            String s = Integer.toString(i);
            myAccounts[i].setAcctNum(s);
        }
    }

    public void readAcct()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("accountINFO.txt"));
            int counter = 0;

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) 
            {
                    System.out.println(sCurrentLine);
                    StringTokenizer st = new StringTokenizer(sCurrentLine, "|");

                    while(st.hasMoreTokens())
                    {
                        for(int i = 0; i < 2; i++)
                        {

                            if(i == 0)
                            {
                                String tempAcct = st.nextToken();
                                myAccounts[counter].setAcctNum(tempAcct);
                            }
                            else
                            {
                                myAccounts[counter].setBalance(Double.parseDouble(st.nextToken()));
                            }

                        }
                    }
                    counter++;
            }

        } 

        catch (IOException e)
        {

        }

    }

    public void writeAcct()
    {
        try 
        {
            File file = new File("accountINFO.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) 
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(int input=0;input<3;input++) {
                String tempString = myAccounts[input].getAcctNum();
                String tempStringNum = myAccounts[input].getBalance();

                bw.write(tempString);
                bw.write("|");
                bw.write(tempStringNum);
                bw.newLine();
            }

        } 
        
        catch (IOException e) 
        {
            
        }
    }

    public void ChooseAcct() throws IOException
    {   
        
        int input;
       
        do
        {   
            System.out.println("");
            System.out.println("Please Select an Account Number: ");
            System.out.println("Account 0, Enter 0");
            System.out.println("Account 1, Enter 1");
            System.out.println("Account 2, Enter 2");
            System.out.println("To Exit, Enter 3");
                        
            Scanner sc = new Scanner(System.in);
            input = sc.nextInt();
            
            if(input == 3) 
            {
                break;
            }
            
            if(input >=0 || input <= 2) 
            {
                myAccounts[input].menu();
            }   
    
        }     
        while (true);        
    
                
        
    }
}