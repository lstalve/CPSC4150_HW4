package com.example.hw4;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetGestureFragment extends Fragment {

    // Added on Fri, Nov 8
    EditText contactET;
    Button startListeningButton;

    // Added on Wed, Nov 13
    TextView errorTV;

    ImageView listeningAnimIV;

    AnimationDrawable listeningAnimation;

    // Added on Sat, Nov 16
    String contact;

    // Added on Wed, Nov 13
    private final int MAX_DIGITS = 11;

    public GetGestureFragment() {
        // Required empty public constructor
    }

    // Edited on Wed, Nov 13
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_get_gesture, container, false);

        // Attaching contactET to contact_edit_text in fragment_get_gesture.xml
        contactET = myView.findViewById(R.id.contact_edit_text);

        // Attaching errorTV to error_message in fragment_get_gesture.xml
        errorTV = myView.findViewById(R.id.error_message);

        // Attaching listeningAnimIV to listening_animation in fragment_get_gesture.xml
        listeningAnimIV = myView.findViewById(R.id.listening_animation);

        // Added on Fri, Nov 15
        // Setting the ImageView background to the listening_anim animation list defined in
        // listening_anim.xml
        listeningAnimIV.setBackgroundResource(R.drawable.listening_anim);

        // Added on Fri, Nov 15
        // Attaching listeningAnimation Animation Drawable to listeningAnimIV's background, which
        // is listening_anim animation list
        listeningAnimation = (AnimationDrawable) listeningAnimIV.getBackground();

        // TODO: Add EditText checks
        // Added on Wed, Nov 13
        contactET.addTextChangedListener(new TextWatcher() {
            /**
             * Inspired by zyBooks 2.6: Text Widgets - EditText and
             * Java T Point: Android EditText with TextWatcher - Searching data from ListView
             * https://www.javatpoint.com/android-edittext-with-textwatcher
             *
             * This method detects EditText before text is entered. In this case, nothing happens
             * @param charSequence
             * @param i
             * @param i1
             * @param i2
             */
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            /**
             * Inspired by zyBooks 2.6: Text Widgets - EditText and
             * Java T Point: Android EditText with TextWatcher - Searching data from ListView
             * https://www.javatpoint.com/android-edittext-with-textwatcher
             *
             * This method detects EditText while text is entered
             * @param charSequence is the sequence of characters entered in the EditText
             * @param i
             * @param i1
             * @param i2
             * @pre EditText != null
             * @post Error message is displayed when charSequence.length() > MAX_DIGITS (10)
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listeningAnimIV.setVisibility(View.INVISIBLE);
                listeningAnimation.stop();

                startListeningButton.setVisibility(View.VISIBLE);

                if (charSequence.length() == 0) {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setText(R.string.error_out_of_range);
                }
                else if (charSequence.length() > MAX_DIGITS) {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setText(R.string.error_too_long);
                }
                else {
                    errorTV.setVisibility(View.INVISIBLE);
                }
            }
            /**
             * Inspired by zyBooks 2.6: Text Widgets - EditText and
             * Java T Point: Android EditText with TextWatcher - Searching data from ListView
             * https://www.javatpoint.com/android-edittext-with-textwatcher
             *
             * This method detects EditText after text is entered. In this case, nothing happens
             * @param editable
             */
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        // Attaching startListeningButton to start_listening_button in fragment_get_gesture.xml
        startListeningButton = myView.findViewById(R.id.start_listening_button);

        // TODO: Add startListeningButton OnClickListener
        // Edited on Sat, Nov 16
        startListeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactET.getText().length() != MAX_DIGITS) {
                    errorTV.setVisibility(View.VISIBLE);
                    errorTV.setText(R.string.error_out_of_range);
                }
                else {
                    contact = contactET.getText().toString();
                    // Removing animation step to see if app can switch fragments and transfer the
                    // contact number successfully
                    // Edited on Sat, Nov 16
                    transferContact(view);

                    //errorTV.setVisibility(View.VISIBLE);
                    //errorTV.setText("This is a valid phone number");

                    //listeningAnimIV.setVisibility(View.VISIBLE);
                    //listeningAnimation.start();

                    //startListeningButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        return myView;
    }

    // Added on Sat, Nov 16
    // Inspired by L16-BMI-code
    public void transferContact(View view) {
        //Every activity has the method getActivity to find the parent activity
        //remember, fragments should not talk to each other directly
        MainActivity theMainActivity = (MainActivity)getActivity();
        theMainActivity.lockContact(contact);
    }
}
