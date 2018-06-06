package com.example.android.projectfanta;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioGroup rg;
    private FriendsFragment.OnFragmentInteractionListener mListener;
    public View view;

    private SectionsPageAdapter myAdapters;
    private ViewPager myPagers;
    Button select;
    RadioButton nutrient;
    Button select_nutrient;
    Dialog dialog;
    public WeekFragment wf;



    public HistoryFragment() {
        // Required empty public constructor
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getFragmentManager());
        wf = new WeekFragment();
        adapter.addFragment(wf, "Week");
        adapter.addFragment(new MonthFragment(), "Month");
        adapter.addFragment(new YearFragment(), "Year");
        viewPager.setAdapter(adapter);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);
        select = (Button)view.findViewById(R.id.select);


        select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               dialog = new Dialog(getActivity());
               dialog.setContentView(R.layout.custom_select);
               dialog.show();
                select_nutrient = (Button)dialog.findViewById(R.id.nut);
                rg = (RadioGroup) dialog.findViewById(R.id.nutrientGraph);

                select_nutrient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int id = rg.getCheckedRadioButtonId();
                        nutrient = (RadioButton) dialog.findViewById(id);
                        nutrientChoose();
                        System.out.println(nutrient.getText().toString());
                        dialog.dismiss();
                    }
                });

            }
        });




        myAdapters = new SectionsPageAdapter(getFragmentManager());

        myPagers = (ViewPager) view.findViewById(R.id.historyContainer);
        setUpViewPager(myPagers);

        TabLayout tab = (TabLayout)view.findViewById(R.id.historyTabs);
        tab.setupWithViewPager(myPagers);

        return view;
    }

    public void nutrientChoose() {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .detach(wf)
                .attach(wf)
                .commit();

        switch (nutrient.getText().toString()) {
            case "Calories":
                WeekFragment.graphUpdate("calories", true);
                break;
            case "Total Fat":
                WeekFragment.graphUpdate("fat", true);
                break;
            case "Cholesterol":
                WeekFragment.graphUpdate("cholesterol", true);
                break;
            case "Sodium":
                WeekFragment.graphUpdate("sodium",true);
                break;
            case "Potassium":
                WeekFragment.graphUpdate("potassium",true);
                break;
            case "Total Carbs":
                WeekFragment.graphUpdate("carbs", true);
                break;
            case "Dietary Fiber":
                WeekFragment.graphUpdate("fiber",true);
                break;
            case "Sugars":
                WeekFragment.graphUpdate("sugar",true);
                break;
            case "Protein":
                WeekFragment.graphUpdate("protein",true);
                break;
            default:
                WeekFragment.graphUpdate("calories",true);
                break;


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
