package com.gear2cam.official;



import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.gear2cam.official.R;
import com.parse.ParseFacebookUtils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticatedFragmentPortrait#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AuthenticatedFragmentLandscape extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AuthenticatedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthenticatedFragmentLandscape newInstance() {
        AuthenticatedFragmentLandscape fragment = new AuthenticatedFragmentLandscape();
        return fragment;
    }
    public AuthenticatedFragmentLandscape() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticated_landscape, container, false);
        CheckBox cbPublish = (CheckBox) view.findViewById(R.id.cbEnablePublishing);
        if(Settings.isPublishingEnabled(getActivity().getApplicationContext())) {
            cbPublish.setChecked(true);
        }
        else {
            cbPublish.setChecked(false);
        }

        cbPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox item = (CheckBox) view;
                if(!item.isChecked()) {
                    //Disable publishing
                    Settings.setPublishingEnabled(getActivity().getApplicationContext(), false);
                    boolean getIsSet = Settings.isPublishingEnabled(getActivity().getApplicationContext());
                    Log.d("Test", "" + getIsSet);
                }
                else {
                    //Check if we have publish_actions permission
                    boolean hasPublishPermissions = ParseFacebookUtils.getSession().isPermissionGranted("publish_actions");
                    if(hasPublishPermissions) {
                        //Simply toggle our internal flag
                        Settings.setPublishingEnabled(getActivity().getApplicationContext(), true);
                    }
                    else {
                        //We need to obtain permission from Facebook
                        InfoActivity activity = (InfoActivity) getActivity();
                        activity.getPublishPermissions();
                    }
                }
            }
        });

        return view;
    }


}
