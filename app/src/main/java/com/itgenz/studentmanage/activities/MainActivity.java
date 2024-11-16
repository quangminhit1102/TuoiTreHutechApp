package com.itgenz.studentmanage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.widget.FrameLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import com.itgenz.studentmanage.R;
import com.itgenz.studentmanage.fragments.AccountFragment;
import com.itgenz.studentmanage.fragments.HomeFragment;
import com.itgenz.studentmanage.fragments.InfoFragment;
import com.itgenz.studentmanage.fragments.FacultyFragment;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private FragmentContainerView fragContainer;
    private MeowBottomNavigation bottomNavigation;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //============================================================================================================
        fragContainer = findViewById(R.id.fmContainer);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home_icon));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.faculty));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.account_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.info_home));
        bottomNavigation.show(1, true);
        // Fragment replace
        replaceFragment(new HomeFragment());
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId())
                {
                    case 1:
                        replaceFragment(new HomeFragment());
                        break;
                    case 2:
                        replaceFragment(new FacultyFragment());
                        break;
                    case 3:
                        replaceFragment(new AccountFragment(MainActivity.this));
                        break;
                    case 4:
                        replaceFragment(new InfoFragment());
                        break;
                }
                return null;
            }
        });
        // Login check
    }
    // ReplaceFragment
    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fmContainer, fragment);
        transaction.commit();
    }
}