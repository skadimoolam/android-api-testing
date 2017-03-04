package dev.adi.testapp;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class SearchBarActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private MenuItem menuSearchAction;
    private boolean isSearchOpened = false;
    private EditText etMainSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        toolbar = getSupportActionBar();
        toolbar.setHomeButtonEnabled(true);
        toolbar.setDisplayHomeAsUpEnabled(true);
        toolbar.setCustomView(R.layout.search_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                handleMenuSearch();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleMenuSearch() {
        ActionBar action_bar = getSupportActionBar();

        if(isSearchOpened) {
            action_bar.setDisplayShowCustomEnabled(false);
            action_bar.setDisplayShowTitleEnabled(true);

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etMainSearch.getWindowToken(), 0);

            menuSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));
            isSearchOpened = false;
        } else {
            action_bar.setDisplayShowCustomEnabled(true);
            action_bar.setCustomView(R.layout.search_bar);
            action_bar.setDisplayShowTitleEnabled(false);

            etMainSearch = (EditText) action_bar.getCustomView().findViewById(R.id.etMainSearch);
            etMainSearch.requestFocus();
            etMainSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Log.w("Search String", (String) textView.getText());
                        return true;
                    }
                    return false;
                }
            });

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etMainSearch, InputMethodManager.SHOW_IMPLICIT);

            menuSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_clear));
            isSearchOpened = true;
        }
    }
}
