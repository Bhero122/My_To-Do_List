package com.example.myto_dolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener =
                new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                // Now have the position of where the user has long pressed
                // Delete the item from the model
                items.remove(position);

                //Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();

                saveItems();
            }
        };


        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("Main Activity", "Single click at position"+ position);
                // Create New Activity
                // An intent is like a request to the android system
                    // takes in the context of where we're calling from and destination
                    // MainActivity.this refers to current instance that we're in now
                Intent i = new Intent(MainActivity.this, EditActivity.class);

                // Pass data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, items.get(position));

                // Display the activity
                // Use startActivityForResult cause expect a result which is updated to-do list item
                // request code - uniquely identifies the request for an activity
                    // need it to distinguish diff intents if have multiple intents
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };


        //Create new itemsAdapter
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        //Set the items on the recycler view
        rvItems.setAdapter(itemsAdapter);
        // Put items in a vertical orientation
        rvItems.setLayoutManager(new LinearLayoutManager(this));


        // What to do when the button is clicked
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toDoItem = etItem.getText().toString();

                // Add item to the model
                items.add(toDoItem);

                // Notify the adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);

                //Clear the edit text box
                etItem.setText("");

                // Show a small pop-up that tells the user the item was successfully added
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();

                saveItems();
            }
        });
    }

    // Handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Check it result code is correct
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // Retrieve the updated text value
            // The extras that wer passed in from edit activity show up in the intent data
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);

            // Extract the original position from the edited item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // Update the model at the right position with new item text
            items.set(position, itemText);

            // Notify the adapter
            itemsAdapter.notifyItemChanged(position);

            // Persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully",
                    Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    // These methods which pertain to the commons library in build.gradle folder for the app can
    // be private since they will only be called by MainActivity

    private File getDataFile(){
        // Returns the file that stores the list of items
        // Pass in directory of the app and the name of file
        return new File(getFilesDir(), "data.txt");
    }



    // Loads items by reading every line of the data file
    // Only used 1x which is when app starts up
    // Bootstrap list of strings to be whatever is in data.txt (where the list of items were saved)
    private void loadItems(){
        try{
            // Read file and populate lines into an arraylist
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }

        catch (IOException e){
            // Use logging to note errors so developer can see what's happening in program
            // Name of tag is usually the class name
            Log.e("MainActivity", "Error reading items", e);

            // Set items as an array so there is something to build off of
            items = new ArrayList<>();
        }
    }


    // Write the file
    // Called whenever a change is made to the to-do items (when item added/removed)
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}