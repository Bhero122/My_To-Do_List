package com.example.myto_dolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editTextItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextItem = findViewById(R.id.editTextItem);
        btnSave = findViewById(R.id.btnSave);

        // Let's user know where they are in the application
        // So, the title bar will say "Edit Item" now
        getSupportActionBar().setTitle("Edit Item");

        // Retrieve data and pre-populate what the edit text should have
        editTextItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // When user is done editing, they click the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent which has the results
                // Leave as empty constructor since using it just to pass data
                Intent intent = new Intent();

                // Pass the results
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editTextItem.getText().toString());

                // Pass in position so main activity can figure out at what point list should be
                // updated
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,
                        getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // Set the result of the intent
                setResult(RESULT_OK, intent);

                // Finish activity and close the screen to go back
                finish();
            }
        });
    }
}