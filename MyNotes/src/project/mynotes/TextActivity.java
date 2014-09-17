/**
 * Authors	: Arun Kumar Konduru Chandra 
 * NetID	: axk138230 
 * Date 	: 03/12/2014
 * Class	: CS6301.013
 * Purpose	: Assignment 3 for UI Design course.
 * Description: This activity contains methods necessary for implementing all the 
 * 				menu items in Action Bar of the application.
 */

package project.mynotes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class TextActivity extends Activity{
	
	File file;
    File fileNames;
    File external = Environment.getExternalStorageDirectory();
    String sdcardPath = external.getPath(); //Path in external sdcard to save text file.

    /* Description	: This method is used to create a new file.
	 */
    public void createNewFile()
	{
		try
	    {
			TextView txtFileName = (TextView) findViewById(R.id.txtFileName);
			file = new File(sdcardPath + "/Documents/new.txt");
	        file.createNewFile();
	        file.deleteOnExit();
	        showFile(file);
	        txtFileName.setText("new.txt"); //Sets the title of text file name.
	    }
	    catch(Exception e)
	    {
	    	Toast toast = Toast.makeText(getApplicationContext(),"Unable to create new file.",Toast.LENGTH_SHORT);
	    	toast.show(); 	    	
	    }
	}
    
    /* Description	: This method is called when user presses new menu in the action bar.
	 */
    public void newTextFile()
	{
		AlertDialog.Builder dialog = new AlertDialog
				.Builder(this);//Alert Dialog box to prompt user to save file before closing.
		dialog.setTitle("Warning");
		dialog.setMessage("Do you want to save current file before opening a new file.");
		dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface d, int whichButton) {
				  saveAs();	//Saves file before closing.
				  createNewFile(); //Creates new file.
			  }
			});
		dialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface d, int whichButton) {
				  createNewFile(); //Discards the file and open a new file.
			  }
			});
		dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface d, int whichButton) {
			    //Cancelled.
			  }
			});
		dialog.show();
	}
	
    /* Description	: This method is used to display the file and its content.
	 */
	public void showFile(File file)
	{
		try
	    {
			String txt="";
			EditText textArea = (EditText) findViewById(R.id.textArea);
			FileReader reader = new FileReader(file);
			Scanner scanner = new Scanner(reader);
			//Display all the lines in text file into the text field.
			while(scanner.hasNext())	
			{
				txt += scanner.nextLine()+"\n";
			}
			textArea.setText(txt);
			scanner.close();
			reader.close();
	    }
	    catch(Exception e)
	    {
	    	Toast toast = Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
	    	toast.show(); 	    	
	    }
	}
	
	/* Description	: This method is used to add the text from text field into
	 * 					the text file.
	 */
	public void addTextIntoFile(File file)
	{
		BufferedWriter out;
		EditText et = (EditText) findViewById(R.id.textArea);
	    String txt = et.getText().toString();
		try{
			FileWriter fileWriter= new FileWriter(file);
			out = new BufferedWriter(fileWriter);
			//Writes the text into the text file.
			out.write(txt);
			out.close();
		}
		catch(Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT);
	    	toast.show();
		}
	}
	
	/* Description	: This method is called when the save menu button is pressed.
	 * 					It is used to save the file in same name. It prompts the 
	 * 					user to save the file similar to "Save As" if the name of
	 * 					the file is "new.txt"
	 */
	public void saveFile()
	{
		TextView txtFileName = (TextView) findViewById(R.id.txtFileName);
		if(txtFileName.getText().toString().contentEquals("new.txt")
				||txtFileName.getText().toString().contentEquals(""))
			saveAs(); // saveFile() calls saveAs() if text file name is "new.txt"
		else	//Saves the file if the file name is not "new.txt"
		{
			file.delete();
			try {
				file.createNewFile();
				addTextIntoFile(file);
				Toast toast = Toast.makeText(getApplicationContext(),"Text File Saved."
						,Toast.LENGTH_SHORT);
		    	toast.show();
			} catch (IOException e) {
				Toast toast = Toast.makeText(getApplicationContext(),e.toString()
						,Toast.LENGTH_SHORT);
		    	toast.show();
			}
		}
	}
	
	/* Description	: This method is used to save the file with the name a user enters.
	 */
	public void saveAs()
	{
		//Creates a dialog box to get the file name from user.
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Save As");
		dialog.setMessage("Enter file name:");
		final EditText input = new EditText(this);
		dialog.setView(input);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface d, int whichButton)
	        {
	        	String fileName = input.getText().toString()+".txt";
	    		TextView txtFileName = (TextView) findViewById(R.id.txtFileName);
	       		try
	   	    	{
	   				File newFile = new File(sdcardPath + "/Documents/"+fileName);
	   				fileNames = new File(sdcardPath + "/Documents/FileNames.txt");
	   				addTextIntoFile(newFile);
	   				txtFileName.setText(newFile.getName());
	   				file = newFile;
	   				//Calls the addTextIntoFile() method to load the text into text file.
	   				addTextIntoFile(file);
	   				PrintWriter out = new PrintWriter(new BufferedWriter(
	   						new FileWriter(fileNames,true)));
	   				//Write the name of the file into FileNames.txt
	   				out.println(newFile.getName());
	   				out.close();
					Toast toast = Toast.makeText(getApplicationContext()
							,"Text File Saved.",Toast.LENGTH_SHORT);
			    	toast.show();
	   	    	}
	   	    	catch(Exception e)
	   	    	{
	   	    		Toast toast = Toast.makeText(getApplicationContext()
	   	    				,e.toString(),Toast.LENGTH_SHORT);
	   	    		toast.show(); 	    	
	   	    	} 
	        }
	    });
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			  public void onClick(DialogInterface d, int whichButton) {
			    //Cancelled.
			  }
			});
		dialog.show();
	}
	
	/* Description	: This method is used to show recent files and handle them when
	 * 					user selects a file.
	 */
	public void showRecentFileNames()
	{
		fileNames = new File(sdcardPath + "/Documents/FileNames.txt");
		ArrayList<String> allFile = new ArrayList<String>();
		try{
			FileInputStream fstream = new FileInputStream(fileNames);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			//Adds all the file names from text file into ArrayList.
			while((line=br.readLine())!=null)
			{
				allFile.add(line);
			}
			br.close();
			in.close();
			fstream.close();
		}
		catch(Exception e)
	    {
	    	Toast toast = Toast.makeText(getApplicationContext(),
	    			e.toString(),Toast.LENGTH_SHORT);
	    	toast.show(); 	    	
	    }
		//Adds and handles the recent file elements into dialog list.
		if(allFile.size()>0)
		{
			int length;
			if(allFile.size()<5)
				length = allFile.size();
			else
				length = 5;
			//Reverse the array list to sort based on the most recently used file.
			Collections.reverse(allFile);
			final String[] items = (String[]) allFile.toArray(new String[length]);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Recent Files");
			dialog.setItems(items,new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int item) {
		        	if(items.length!=0)
		        		openFile(items[item].toString());
		        }
		    });
			dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
				  public void onClick(DialogInterface d, int whichButton) {
				    //Cancelled.
				  }
				});
			dialog.show();
		}
		else
		{
			Toast toast = Toast.makeText(getApplicationContext(),
					"No recent files to display.",Toast.LENGTH_SHORT);
	    	toast.show();
		}
	}
	
	/* Description	: This method is used to delete the file. It is called when the 
	 * 					user presses the delete menu in the action bar.
	 */
	public void deleteFile()
	{
		if(file.exists())
		{
			Toast toast = Toast.makeText(getApplicationContext(),
					"File deleted.",Toast.LENGTH_SHORT);
			toast.show();
			try
			{
				File tempFile = new File(sdcardPath + "/Documents/FileNames.tmp");
				fileNames = new File(sdcardPath + "/Documents/FileNames.txt");
				BufferedReader reader = new BufferedReader(new FileReader(fileNames));
	            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
	            String strLine;
	            TextView txtFileName = (TextView) findViewById(R.id.txtFileName);
	            String fname = txtFileName.getText().toString();
	            //Removes the file name from FileNames text file.
	            while((strLine = reader.readLine())!= null)
	            {
	            	if(!strLine.contentEquals(fname))
	            	{
	            		/*Writes all those lines that do not match the name 
	            		to be deleted into temporary file.*/
	            		writer.println(strLine);	
	            	}
	            }
	            reader.close();
	            writer.close();
	            fileNames.delete();	//Deletes the text file.
	            tempFile.renameTo(fileNames);	//Temporary file is renamed to the text file.
			}
			catch(Exception e)
		    {
		    	Toast toast1 = Toast.makeText(getApplicationContext(),
		    			"File deletion error.",Toast.LENGTH_SHORT);
		    	toast1.show(); 	    	
		    }
			//Deletes the file and creates a new file.
			file.delete();
			createNewFile();
		}
		else
		{
			Toast toast = Toast.makeText(getApplicationContext(),"File cannot be deleted.",Toast.LENGTH_SHORT);
	    	toast.show();
		}
	}
	
	/* Description	: This method is used to show all the file names in the directory.
	 * 					This method is used to display file names for Open File menu 
	 * 					in action bar.
	 */
	public void showAllFileNames()
	{
		fileNames = new File(sdcardPath + "/Documents/FileNames.txt");
		ArrayList<String> allFile = new ArrayList<String>();
		try{
			FileInputStream fstream = new FileInputStream(fileNames);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			//Adds all file names into the array list.
			while((line=br.readLine())!=null)
			{
				allFile.add(line);
			}
			br.close();
			in.close();
			fstream.close();
			//Displays the file names as dialog list when user presses Open File.
			if(allFile.size()>0)
			{
				final String[] items = (String[]) allFile.toArray(new String[allFile.size()]);
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("Open File");
				dialog.setItems(items,new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int item) {
			        	openFile(items[item].toString());
			        }
			    });
				dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					  public void onClick(DialogInterface d, int whichButton) {
					    //Cancelled.
					  }
					});
				dialog.show();
			}
			else
			{
				Toast toast = Toast.makeText(getApplicationContext(),
						"No text files in the directory.",Toast.LENGTH_SHORT);
		    	toast.show();
			}
		}
		catch(Exception e)
	    {
	    	Toast toast = Toast.makeText(getApplicationContext(),
	    			e.toString(),Toast.LENGTH_SHORT);
	    	toast.show(); 	    	
	    }

	}
	
	/* Description	: This method is used to open a file when the file name is given.
	 */
	public void openFile(String fileName)
	{
		TextView txtFileName = (TextView) findViewById(R.id.txtFileName);
		file = new File(sdcardPath+"/Documents/"+fileName);
		txtFileName.setText(fileName);
		showFile(file);
	}
	
	/* Description	: This method is used to save all the names of text files 
	 * 					into the TextFile.txt file.
	 */
	public void saveTextFileName()
	{
		boolean exists=false;
		try{
			FileWriter fileWriter= new FileWriter(fileNames);
			BufferedWriter out = new BufferedWriter(fileWriter);
			FileReader reader = new FileReader(fileNames);
			Scanner scanner = new Scanner(reader);
			//Checks if file is already available.
			while(scanner.hasNext())
			{
				String files = scanner.nextLine();
				if(files==file.getName())
				{
					exists=true;
					break;
				}
			}
			//Saves the file if it does not exist and its length is not 0.
			if(exists==false&&fileNames.getName().length()!=0)
			{
				out.newLine();
				out.write(fileNames.getName());
			}
			out.close();
			scanner.close();
			reader.close();
			fileWriter.close();
		}
		catch(Exception e)
	    {
	    	Toast toast = Toast.makeText(getApplicationContext(),
	    			e.toString(),Toast.LENGTH_SHORT);
	    	toast.show(); 	    	
	    } 
	}

	
}