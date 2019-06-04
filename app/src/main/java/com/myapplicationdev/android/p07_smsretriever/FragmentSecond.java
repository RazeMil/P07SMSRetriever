package com.myapplicationdev.android.p07_smsretriever;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSecond extends Fragment {
    EditText etNum2;
    Button btnRetrieve2;
    TextView tvFrag2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        tvFrag2 = view.findViewById(R.id.tvFrag2);
        btnRetrieve2 = view.findViewById(R.id.btnAddTextFrag2);
        etNum2 = view.findViewById(R.id.etNum2);

        btnRetrieve2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri uri = Uri.parse("content://sms");
                String[] reqCols = new String[]{"date", "address", "body", "type"};

                // Get Content Resolver object from which to
                //  query the content provider
                ContentResolver cr = getActivity().getContentResolver();

                String filter="body LIKE ?";
                // The matches for the ?
                String[] filterArgs = {"%"+ etNum2.getText().toString() +"%"};


                // Fetch SMS Message from Built-in Content Provider
                Cursor cursor = cr.query(uri, reqCols, filter, filterArgs, null);
                // Fetch SMS Message from Built-in Content Provider
                String smsBody = "";
                if (cursor.moveToFirst()) {
                    do {
                        long dateInMillis = cursor.getLong(0);
                        String date = (String) DateFormat
                                .format("dd MMM yyyy h:mm:ss aa", dateInMillis);
                        String address = cursor.getString(1);
                        String body = cursor.getString(2);
                        String type = cursor.getString(3);
                        if (type.equalsIgnoreCase("1")) {
                            type = "Inbox:";
                        } else {
                            type = "Sent:";
                        }
                        smsBody += type + " " + address + "\n at " + date
                                + "\n\"" + body + "\"\n\n";
                    } while (cursor.moveToNext());
                }
                tvFrag2.setText(smsBody);
            }
        });

        String data = etNum2.getText().toString();
        tvFrag2.setText(data);
        return view;
    }

}
