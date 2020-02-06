// Program that is designed to help a clerk manage and modify electronic books in its database
// Create a database for the books to fetch and send data too; with mySQL
// Prompts the clerk to enter an option wanted then asks the clerk to enter the password to proceed
// If the password is approved the program will then run accordingly hence update, add or retrieve data from the database

import java.sql.*;
import java.util.Scanner;

public class eBookStore {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection ("jdbc:mysql://localhost:3306/electronic_library?useSSL=false", "root", "dillonlee3##");    // Allocating a database 'Connection' object for the program
             Statement sqlStatement = conn.createStatement ();
        ) {
            System.out.println ("\nGood Day!!! Welcome to the E-book store, how may I help you?\n");
            System.out.println ("1. Enter the book you would like to add");
            System.out.println ("2. Update books records recorded in the database");
            System.out.println ("3. Delete a book which does not exist anymore in the database");
            System.out.println ("4. Search books that you might be interested in");
            System.out.println ("0. Exit");
            System.out.println ();
            Scanner userChoice = new Scanner (System.in);

            String choice = userChoice.nextLine ();
            int choice_Int = Integer.parseInt (choice);

            while (choice_Int != 0) {
                /*
                 Prompts the clerk to enter in a password to make sure it is a clerk who is authorized to use the system
                 */
                int password = 1234;
                System.out.println ("Enter your work password:");
                Scanner inClerk_Password = new Scanner (System.in);
                int clerkPassword = inClerk_Password.nextInt ();


                if (clerkPassword == password) {


                    if (choice_Int == 1) {


                        System.out.println ("Please enter the ID of the book you would like to add: ");
                        String new_id = userChoice.nextLine ();
                        System.out.println ("Please enter the title of the book: ");
                        String new_Title = userChoice.nextLine ();
                        System.out.println ("Please enter the Author of the book: ");
                        String new_Author = userChoice.nextLine ();
                        System.out.println ("Please enter the quantity: ");
                        String new_Qty = userChoice.nextLine ();

                        String sqlInsert = "insert into ebooks (id, Title, Author, Qty) values ("
                                + "'" + new_id + "', "
                                + "'" + new_Title + "', "
                                + "'" + new_Author + "', "
                                + new_Qty + ")";

                        int countInserted = sqlStatement.executeUpdate (sqlInsert);
                        System.out.println (countInserted + " record inserted successfully into the database.\n");


                    }
                    if (choice_Int == 2) {
                        System.out.println ("Please enter the title of the book you would like to update: ");
                        String update_Title = userChoice.nextLine ();
                        System.out.println ("Please enter the updated Quantity for the specific book: ");
                        String updated_Qty = userChoice.nextLine ();

                        String sqlUpdate = "update ebooks set Qty = "
                                + updated_Qty +
                                " where Title = '" + update_Title + "' ";

                        int countUpdated = sqlStatement.executeUpdate (sqlUpdate);
                        System.out.println (countUpdated + " record/records is/are affected." + "\n");
                    }

                    if (choice_Int == 3) {
                        System.out.println ("Please enter the title of the book you would like to remove: ");
                        String delete_Title = userChoice.nextLine ();

                        String sqlDelete = "delete from ebooks where Title = '" + delete_Title + "'";

                        int countDeleted = sqlStatement.executeUpdate (sqlDelete);
                        System.out.println (countDeleted + " record/records is/are deleted.\n");
                    }

                    if (choice_Int == 4) {
                        System.out.println ("Please enter the title of the book you'd like to search: ");
                        String search_Title = userChoice.nextLine ();

                        String sqlSearch = "select * from ebooks where Title = '" + search_Title + "'";
                        ResultSet rset = sqlStatement.executeQuery (sqlSearch);
                        System.out.println ("\nThe information for the requested book is:");
                        int rowCount = 0;
                        // Move the cursor to the next row, return false if no more row
                        while (rset.next ()) {
                            int id = rset.getInt ("id");
                            String title = rset.getString ("Title");
                            String author = rset.getString ("Author");
                            int qty = rset.getInt ("Qty");
                            System.out.println (id + ", " + title + ", " + author + ", " + qty);
                            ++rowCount;
                        }
                        System.out.println ("Number of records found are: " + rowCount + "\n");
                    }
                }
                /*
                If the password does not match the system password the system will end and the following message will be displayed
                 */
                else {
                    System.out.println ("Make sure you entered the right password!");
                    break;
                }


                System.out.println ("\nChoose another option if necessary:\n");
                System.out.println ("1. Enter the book you would like to add");
                System.out.println ("2. Update books records recorded in the database");
                System.out.println ("3. Delete a book which does not exist anymore in the database");
                System.out.println ("4. Search books that you might be interested in");
                System.out.println ("0. Exit");
                System.out.println ();

                choice = userChoice.nextLine ();
                choice_Int = Integer.parseInt (choice);
            }

            userChoice.close ();

        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }
}