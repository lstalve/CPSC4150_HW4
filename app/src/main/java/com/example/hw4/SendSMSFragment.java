package com.example.hw4;


import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Added on Sat, Nov 16
 */
public class SendSMSFragment extends Fragment {
    // Added on Sat, Nov 16
    TextView lockedContactTV;
    ImageView emojiIV;
    Button sendButton;

    String contact;

    // Added on Wed, Nov 20
    String message;

    public SendSMSFragment() {
        // Required empty public constructor
    }

    // Added on Sat, Nov 16
    // Inspired by Figure 5.3.3: Updated DetailsFragment class
    public static SendSMSFragment newInstance(String inContact) {
        SendSMSFragment fragment = new SendSMSFragment();
        Bundle args = new Bundle();
        args.putString("contact", inContact);
        fragment.setArguments(args);
        return fragment;
    }

    // Edited on Sat, Nov 16
    // Inspired by Figure 5.3.3: Updated DetailsFragment class
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the contact from the intent in MainActivity
        contact = "";
        if (getArguments() != null) {
            contact = getArguments().getString("contact");
        }
    }

    // Edited on Sat, Nov 16
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_send_sms, container, false);
        View myView = inflater.inflate(R.layout.fragment_send_sms, container, false);

        // Attaching lockedContactTV to locked_contact_text_view in fragment_send_sms.xml
        lockedContactTV = myView.findViewById(R.id.locked_contact_text_view);
        lockedContactTV.setText(contact);

        // Attaching emojiIV to emoji_image_view in fragment_send_sms.xml
        emojiIV = myView.findViewById(R.id.emoji_image_view);

        // Attaching sendButton to send_button in fragment_send_sms.xml
        sendButton = myView.findViewById(R.id.send_button);

        // Issue: This doesn't respond on the first click. It responds after the second click
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "Wassup";
                sendSMS();
            }
        });

        return myView;
    }

    // Added on Sat, Nov 16
    //public void setContact(String inContact) {
        //contact = inContact;

        // Sets the TextView to the contact number entered
        //lockedContactTV.setText(contact);
    //}

    // Added on Wed, Nov 20
    protected void sendSMS() {
        Uri uri = Uri.parse("smsto: " + contact );
        Intent intent = new Intent (Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

}
