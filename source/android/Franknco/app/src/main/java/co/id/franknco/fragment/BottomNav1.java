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

public class BottomNav1 extends Fragment {
    public BottomNav1() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category1, container, false);
//        FloatingActionButton myFab = (FloatingActionButton) rootView.findViewById(R.id.fab_items);
//        myFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Snackbar.make(v, "Replace with your own action settings", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        return rootView;
    }
}
