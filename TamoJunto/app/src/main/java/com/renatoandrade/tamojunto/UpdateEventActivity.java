package com.renatoandrade.tamojunto;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.renatoandrade.tamojunto.DBManagement.DBCreator;
import com.renatoandrade.tamojunto.DBManagement.EventController;

public class UpdateEventActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtDate;
    private EditText txtDescription;
    private EditText txtLocation;
    private EditText txtTime;
    private Button btnDelete;
    private Cursor cursor;
    private EventController ec;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TamoJunto);
        setContentView(R.layout.activity_update_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        code = this.getIntent().getStringExtra("code");

        ec = new EventController(getBaseContext());

        txtName = (EditText) findViewById(R.id.txtName);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtDate = (EditText) findViewById(R.id.txtDate);

        cursor = ec.returnEventById(Integer.parseInt(code));
        txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.NAME)));
        txtDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.DATE)));
        txtLocation.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.LOCATION)));
        txtDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.DESCRIPTION)));
        txtTime.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.TIME)));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.confirm_delete))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteEvent();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return true;
        }
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }
        if (id == R.id.action_done) {
            updateEvent();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateEvent() {
        String result;

        result = ec.update(Integer.parseInt(code), txtName.getText().toString(), txtDate.getText().toString()
                , txtDescription.getText().toString(), txtLocation.getText().toString(), txtTime.getText().toString());

        Intent intent = new Intent(UpdateEventActivity.this, EventListActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }

    private void deleteEvent(){
        String result;

        result = ec.delete(Integer.parseInt(code));

        Intent intent = new Intent(UpdateEventActivity.this, EventListActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }
}

