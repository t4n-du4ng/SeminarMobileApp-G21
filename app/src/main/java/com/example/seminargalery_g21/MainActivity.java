package com.example.seminargalery_g21;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seminargalery_g21.database.AlbumDataSource;
import com.example.seminargalery_g21.helper.ImageAdapter;
import com.example.seminargalery_g21.helper.ImagesGallery;
import com.example.seminargalery_g21.helper.Photo;
import com.example.seminargalery_g21.helper.StateManager;
import com.example.seminargalery_g21.helper.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public boolean  permission = false;
    TextView album;
    TextView gallery;
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    List<String> images;
    String albumName = "";
    List<String> loadImagesPhone;
    AlbumDataSource albumDataSource;
    private static final int MY_READ_PERMISSION_CODE = 101;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // Check form permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);
        } else {
            permission = true;
        }

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

        if (permission) {
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            mViewPager.setAdapter(viewPagerAdapter);

            mTabLayout.setupWithViewPager(mViewPager);
        }
        // L???y context ????? t???i theme
        context = MainActivity.this;
        loadTheme();

//        //gallery_number = findViewById(R.id.gallery_number);
//        recyclerView = findViewById(R.id.recyclerview_gallery_images);



//        // Thay ?????i m??u cho textView ??? tr??n thanh taskbar
//        gallery = findViewById(R.id.gallery);
//        SpannableString content = new SpannableString("Gallery");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        gallery.setText(content);
//        gallery.setTextColor(Color.parseColor("#FF038f3d"));
//
//        // X??? l?? chuy???n qua m??n h??nh AlbumActivity
//        album = (TextView) findViewById(R.id.album);
//        album.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), AlbumActivity.class);
//                startActivity(intent);
//            }
//        });
//
        // X??? l?? chuy???n qua m??n h??nh SharingActivity
        ImageButton ic_image = (ImageButton) findViewById(R.id.ic_image);
        ic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SharingActivity.class);
                startActivity(intent);
            }
        });

        // X??? l?? chuy???n qua m??n h??nh SettingsActivity
        ImageButton ic_settings = (ImageButton) findViewById(R.id.ic_settings);
        ic_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
    // T???i theme v?? ?????t tr???ng th??i n??y cho ???ng d???ng
    private void loadTheme() {
        int themeMode = StateManager.getThemeMode(context);
        if (themeMode == AppCompatDelegate.MODE_NIGHT_NO)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
//
    // T???i ???nh t??? b??? nh??? v?? th??m v??o database
//    private void loadImages() {
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//
//        loadImagesPhone = ImagesGallery.listOfImages(this, ""); // T???i ???nh t??? b??? nh???
//        images = new ArrayList<>();
//        insertSqlite(loadImagesPhone);  // Th??m ???nh v??o DB
//        imageAdapter = new ImageAdapter(this, loadImagesPhone, new ImageAdapter.PhotoListener() {
//            @Override
//            public void onPhotoClick(String path) {
//                // Do something with photo
//                Intent intent = new Intent(getApplicationContext(), FullScreenImageActivity.class);
//                intent.putExtra("path", path);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(imageAdapter);  // D??ng recyclerView ????? setAdapter
//    }

    // Xin ph??p quy???n truy c???p v??o b??? nh???
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read external storage permission granted", Toast.LENGTH_SHORT).show();
//                //loadImages();
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                mViewPager.setAdapter(viewPagerAdapter);
                mTabLayout.setupWithViewPager(mViewPager);
                permission = true;
            } else {
                Toast.makeText(this, "Read external storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // H??m ch??n v??a DB
//    public void insertSqlite(List<String> loadImagesPhone) {
//
//        albumDataSource = new AlbumDataSource(context);
//        List<Photo> photos = albumDataSource.getPhotos();
//
//        for (int i = 0; i < loadImagesPhone.size(); i++) {
//            boolean check = false;
//            for (int j = 0; j < photos.size(); j++) {
//                if (photos.get(j).getPath().equals(loadImagesPhone.get(i)))
//                    check = true;
//            }
//            if (!check) {
//                long r = albumDataSource.insert(loadImagesPhone.get(i));
//            }
//        }
//    }
}