package com.renatoandrade.tamojunto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import com.renatoandrade.tamojunto.DBManagement.BusinessController;

public class CreateBusiness extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TamoJunto);
        setContentView(R.layout.activity_create_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new DrawerBuilder().withActivity(this).build();
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName(R.string.drawer_item_home);

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem().withIdentifier(0)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Intent home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(home);
                        return true;
                    }
                })
                .build();

        Spinner spinner = (Spinner) findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.business_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }
        if (id == R.id.action_done) {
            insertBusiness();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertBusiness() {
        BusinessController crud = new BusinessController(getBaseContext());

        String name = ((EditText)findViewById(R.id.txtName)).getText().toString();
        String description = ((EditText)findViewById(R.id.txtDescription)).getText().toString();
        String category = ((Spinner)findViewById(R.id.spnCategory)).getSelectedItem().toString();
        String location = ((EditText)findViewById(R.id.txtLocation)).getText().toString();
        String phone = ((EditText)findViewById(R.id.txtPhone)).getText().toString();

        String result;
        result = crud.insert(name, category, description, location, phone);
        Intent intent = new Intent(CreateBusiness.this, BusinessListActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }

}
