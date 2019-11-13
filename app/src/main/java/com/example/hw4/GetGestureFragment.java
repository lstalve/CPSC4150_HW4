package com.example.hw4;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetGestureFragment extends Fragment {

    // Added on Fri, Nov 8
    EditText contactET;
    Button startListeningButton;

    public GetGestureFragment() {
        // Required empty public constructor
    }

    // Edited on Fri, Nov 8
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_get_gesture, container, false);

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_get_gesture, container, false);

        // Attaching contactET to contact_edit_text in fragment_get_gesture.xml
        contactET = myView.findViewById(R.id.contact_edit_text);
        // Attaching startListeningButton to start_listening_button in fragment_get_gesture.xml
        startListeningButton = myView.findViewById(R.id.start_listening_button);

        // TODO: Add EditText checks and startListeningButton OnClickListener

        return myView;
    }

}
