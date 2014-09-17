/**
 * Author	: Arun Kumar Konduru Chandra 
 * NetID	: axk138230 
 * Date 	: 03/10/2014
 * Class	: CS6301.013
 * Purpose	: Assignment 3 for UI Design course.
 * Description: This is the main file that gets loaded when the activity is launched. 
 * 				It contains onCreate() call and other methods to load and handle menus.
 */


package project.mynotes;

import java.lang.reflect.Field;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

//MyNoteActivity contains methods to create and handle the Action Bar elements.
@SuppressLint("NewApi")
public class MyNoteActivity extends TextActivity{
    
	/* Description 	: onCreate method is loaded when the activity starts.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_area);
		getOverflowMenu();
		createNewFile();	//Creates a new file when activity is started.
	}
	
	/* Description	: This method displays the overflow menu by default. 
	 */
	private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.
	        		getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	    	Toast toast = Toast.makeText(getApplicationContext(),
	    			"Menu Overflow Error! Contact the technical team."
	    			,Toast.LENGTH_SHORT);
	    	toast.show();
	    }
	}

	/* Description	: This method inflates the action bar with the menu items.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note, menu);
		return true;
	}
	
	/* Description	: This method handles the menu options when 
	 * 					the user has pressed the menu items. 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	 	switch(item.getItemId()){ //Switches to the case on menu selection by user.
			default:
				return super.onOptionsItemSelected(item);
			case R.id.action_new:
				newTextFile();
				return true;
			case R.id.action_recent:
				showRecentFileNames();
				return true;
			case R.id.action_save:
				saveFile();
				return true;
			case R.id.action_saveas:
				saveAs();
				return true;
			case R.id.action_open:
				showAllFileNames();
				return true;
			case R.id.action_delete:
				deleteFile();
				return true;
			case R.id.action_exit:
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
		}
	}
		 
}
