package courier.kishore.com.test;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Contact_Fragment extends Fragment {
    View rootview;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    rootview = inflater.inflate(R.layout.contact_layout, container, false);

        
        return rootview;
    }

}
