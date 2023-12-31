package com.example.myapplication;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class TemplateActivity extends AppCompatActivity {
    FirebaseAuth mauth = FirebaseAuth.getInstance();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Toolbar myToolbar;

    public void setupToolbar(String title){
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(title);
    }

    public void setupBackBtn(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) TemplateActivity.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(TemplateActivity.this.getComponentName()));
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if(item.getItemId() == R.id.menuItemProfile) {
            Intent toProfile = new Intent(this, ProfileActivity.class);
            startActivity(toProfile);
        }
        return super.onOptionsItemSelected(item);
    }
}
