package co.id.franknco.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.id.franknco.R;


/**
 * Created by GSS-NB-2016-0012 on 9/4/2017.
 */

public class BottomNav3 extends Fragment {
    public BottomNav3() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category3, container, false);
        return rootView;
    }
}