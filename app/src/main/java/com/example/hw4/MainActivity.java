package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    String contact;
    SendSMSFragment sendSMSFragment;

    GetGestureFragment getGestureFragment;

    FragmentTransaction fragmentTransaction;

    // Edited on Sun, Nov 17
    // Inspired by Figure 5.1.2: Activity adds HelloFragment with a FragmentTransaction
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin a new FragmentTransaction for adding a HelloFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the HelloFragment to the fragment container in the activity layout
        GetGestureFragment fragment = new GetGestureFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    // Added on Sat, Nov 23
    // Inspired by ZyBooks 4.1: App Bar
    // Inspired by Android Developers: Action Bar
    // http://www.androiddocs.com/guide/topics/ui/actionbar.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Added on Sat, Nov 23
    // Inspired by ZyBooks 4.1: App Bar
    // Inspired by Android Developers: Add and handle actions
    // https://developer.android.com/training/appbar/actions.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Inspired by Android Developers: Build a flexible UI
            // https://developer.android.com/training/basics/fragments/fragment-ui
            case R.id.action_refresh:
                // User chose the "Home" item, switch to GetGestureFragment to start over...
                getGestureFragment = new GetGestureFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                fragmentTransaction.replace(R.id.fragment_container, getGestureFragment);
                //fragmentTransaction.addToBackStack(null);

                // Commit the transaction
                fragmentTransaction.commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Edited on Sat, Nov 23
    // Inspired by Android Developers: Communicate with other fragments
    // https://developer.android.com/training/basics/fragments/communicating
    // Inspired by Android Developers: Build a flexible UI
    // https://developer.android.com/training/basics/fragments/fragment-ui
    public void lockContact(String inContact) {
        contact = inContact;

        // Create a new instance of SendSMSFragment with the entered contact number
        sendSMSFragment = SendSMSFragment.newInstance(inContact);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        fragmentTransaction.replace(R.id.fragment_container, sendSMSFragment);
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    public void gestureDetector(String inContact) {
        GestureDetectorFragment fragment = GestureDetectorFragment.newInstance(inContact);
        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
