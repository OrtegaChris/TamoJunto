package com.renatoandrade.tamojunto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.renatoandrade.tamojunto.DBManagement.BusinessController;
import com.renatoandrade.tamojunto.DBManagement.DBCreator;

public class BusinessListActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BusinessController crud = new BusinessController(getBaseContext());
        Cursor cursor = crud.listAll();
        String[] nomeCampos = new String[]{DBCreator.ID, DBCreator.NAME};
        int[] idViews = new int[]{R.id.businessId, R.id.businessName};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.business_grid, cursor, nomeCampos, idViews, 0);
        list = (ListView) findViewById(R.id.businessListView);
        list.setAdapter(adapter);
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
