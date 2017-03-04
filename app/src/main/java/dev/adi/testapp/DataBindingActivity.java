package dev.adi.testapp;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import dev.adi.testapp.databinding.ActivityDataBindingBinding;

public class DataBindingActivity extends AppCompatActivity {


    public class ClickData extends BaseObservable {
        public String times_clicked;
        public String button_text;
        private int initialCount;

        public ClickData(int initialCount, String buttonText) {
            this.initialCount = initialCount;
            button_text = buttonText;
            times_clicked = String.valueOf(initialCount);
        }

        public void add(View view) {
            this.initialCount = this.initialCount + 1;
            times_clicked = String.valueOf(this.initialCount);
            notifyPropertyChanged(BR._all);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        ClickData clickData = new ClickData(1, "CLK ME");
        binding.setClickData(clickData);

        getSupportActionBar().setTitle("DataBinding example");
//        setContentView(R.layout.activity_data_binding);
    }
}
