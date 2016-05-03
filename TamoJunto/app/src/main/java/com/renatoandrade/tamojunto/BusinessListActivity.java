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
import android.widget.Toast;

import com.renatoandrade.tamojunto.DBManagement.BusinessController;
import com.renatoandrade.tamojunto.DBManagement.DBCreator;

public class BusinessListActivity extends AppCompatActivity {

    private ListView list;

    private Cursor cursor;
    private String name;
    private String category;
    private String description;
    private String location;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TamoJunto);
        setContentView(R.layout.activity_business_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateBusiness.class);
                startActivity(i);
            }
        });

        getSearchParameters();
        BusinessController crud = new BusinessController(getBaseContext());
        cursor = crud.search(name, category, description, location, phone);

        String[] nomeCampos = new String[]{DBCreator.ID, DBCreator.NAME};
        int[] idViews = new int[]{R.id.businessId, R.id.businessName};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.business_grid, cursor, nomeCampos, idViews, 0);
        list = (ListView) findViewById(R.id.businessListView);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code;
                cursor.moveToPosition(position);
                code = cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.ID));
                Intent intent = new Intent(BusinessListActivity.this, UpdateBusinessActivity.class);
                intent.putExtra("code", code);
                finish();
                startActivity(intent);
            }
        });
        
    }

    private void getSearchParameters() {
        name = this.getIntent().getStringExtra("name");
        category = this.getIntent().getStringExtra("category");
        description = this.getIntent().getStringExtra("description");
        location = this.getIntent().getStringExtra("location");
        phone = this.getIntent().getStringExtra("phone");
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
