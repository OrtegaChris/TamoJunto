package com.renatoandrade.tamojunto;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.renatoandrade.tamojunto.DBManagement.BusinessController;
import com.renatoandrade.tamojunto.DBManagement.DBCreator;

public class UpdateBusinessActivity extends AppCompatActivity {

    private EditText txtName;
    private Spinner spnCategory;
    private EditText txtDescription;
    private EditText txtLocation;
    private EditText txtPhone;
    private Button btnDelete;
    private Cursor cursor;
    private BusinessController bc;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        finish();
    }
}
