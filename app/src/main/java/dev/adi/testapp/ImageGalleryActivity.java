package dev.adi.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import dev.adi.testapp.adapter.ImageGalleryAdapter;

public class ImageGalleryActivity extends AppCompatActivity {

    RecyclerView tvImageGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        tvImageGrid = (RecyclerView) findViewById(R.id.rv_image_grid);
        tvImageGrid.setLayoutManager(new GridLayoutManager(this, 2));
        ImageGalleryAdapter imageGalleryAdapter = new ImageGalleryAdapter(this);
        tvImageGrid.setAdapter(imageGalleryAdapter);
//        tvImageGrid.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//                Toast.makeText(getApplicationContext(), "Inside alert", Toast.LENGTH_SHORT).show();
//
//                View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_image_full, null, false);
//
//                v.findViewById(R.id.iv_btn_close).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "Close Clicked", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                new AlertDialog.Builder(getApplicationContext())
//                    .setView(v)
//                    .show();
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
    }
}
