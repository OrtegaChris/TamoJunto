package com.renatoandrade.tamojunto;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import com.renatoandrade.tamojunto.DBManagement.BusinessController;
import com.renatoandrade.tamojunto.DBManagement.DBCreator;

public class UpdateBusinessActivity extends AppCompatActivity {

    private EditText txtName;
    private Spinner spnCategory;
    private EditText txtDescription;
    private EditText txtLocation;
    private EditText txtPhone;
    private Button btnDelete;
    private Button searchWeb;
    private Cursor cursor;
    private BusinessController bc;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TamoJunto);
        setContentView(R.layout.activity_update_business);
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



        Button searchWeb = (Button) findViewById(R.id.searchWeb);




        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.business_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);

        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        code = this.getIntent().getStringExtra("code");

        bc = new BusinessController(getBaseContext());

        txtName = (EditText) findViewById(R.id.txtName);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtLocation = (EditText) findViewById(R.id.txtLocation);

        cursor = bc.returnBusinessById(Integer.parseInt(code));
        txtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.NAME)));

        adapter = (ArrayAdapter) spnCategory.getAdapter();
        String category = cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.CATEGORY));
        int selectedPosition = adapter.getPosition(category);
        spnCategory.setSelection(selectedPosition);

        txtDescription.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.DESCRIPTION)));
        txtLocation.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.LOCATION)));
        txtPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBCreator.PHONE)));

        searchWeb.setOnClickListener(new View.OnClickListener() {  // Button for class
            @Override
            public void onClick(View v) {
                String searchURL = "https://www.google.com/search?q=";
                String searchName = txtName.getText().toString();
                String searchAddress = txtLocation.getText().toString();
                searchURL += searchName.replaceAll("\\s ","+");
                searchURL += " "+ searchAddress.replaceAll("\\s","+");
                Intent search = new Intent(Intent.ACTION_VIEW, Uri.parse(searchURL));
                startActivity(search);


            }
        });

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
                            deleteBusiness();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            return true;
        }
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }
        if (id == R.id.action_done) {
            updateBusiness();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateBusiness() {
        String result;

        result = bc.update(Integer.parseInt(code), txtName.getText().toString(), spnCategory.getSelectedItem().toString()
                , txtDescription.getText().toString(), txtLocation.getText().toString(), txtPhone.getText().toString());

        Intent intent = new Intent(UpdateBusinessActivity.this, BusinessListActivity.class);
        intent.putExtra("category", spnCategory.getSelectedItem().toString());
        startActivity(intent);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }

    private void deleteBusiness(){
        String result;

        result = bc.delete(Integer.parseInt(code));

        Intent intent = new Intent(UpdateBusinessActivity.this, BusinessListActivity.class);
        intent.putExtra("category", spnCategory.getSelectedItem().toString());
        startActivity(intent);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }
}
