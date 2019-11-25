package com.example.hw4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    String contact;
    SendSMSFragment sendSMSFragment;

    //FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //public static final String EXTRA_CONTACT = "contact";

    // Edited on Sun, Nov 17
    // Inspired by Figure 5.1.2: Activity adds HelloFragment with a FragmentTransaction
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test
        //test

        // Begin a new FragmentTransaction for adding a HelloFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Add the HelloFragment to the fragment container in the activity layout
        GetGestureFragment fragment = new GetGestureFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    // Added on Sat, Nov 16
    // Inspired by Android Developers: Communicate with other fragments
    // https://developer.android.com/training/basics/fragments/communicating
    public void lockContact(String inContact) {
        contact = inContact;

        if (sendSMSFragment == null) {
            // Use band ID from ListFragment to instantiate DetailsFragment
            //contact = getIntent().getStringExtra(EXTRA_CONTACT);
            sendSMSFragment = SendSMSFragment.newInstance(inContact);

            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            fragmentTransaction.replace(R.id.fragment_container, sendSMSFragment);

            // Commit the transaction
            fragmentTransaction.commit();
        }
    }

    public void gestureDetector() {
        GestureDetectorFragment fragment = new GestureDetectorFragment();
        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
