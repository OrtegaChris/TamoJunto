package com.renatoandrade.tamojunto;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.renatoandrade.tamojunto.DBManagement.EventController;
import com.renatoandrade.tamojunto.DBManagement.DBCreator;

public class EventListActivity extends AppCompatActivity {

    private ListView list;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TamoJunto);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(i);
            }
        });
        EventController crud = new EventController(getBaseContext());
        cursor = crud.listAll();
        String[] nomeCampos = new String[]{DBCreator.ID, DBCreator.NAME};
        int[] idViews = new int[]{R.id.eventId, R.id.eventName};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.event_grid, cursor, nomeCampos, idViews, 0);
        list = (ListView) findViewById(R.id.eventListView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code;
                cursor.moveToPosition(position);
                code = cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.ID));
                Intent intent = new Intent(EventListActivity.this, UpdateEventActivity.class);
                intent.putExtra("code", code);
                finish();
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
