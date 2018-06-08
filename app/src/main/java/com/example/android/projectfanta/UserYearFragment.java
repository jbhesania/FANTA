package com.example.android.projectfanta;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserYearFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserYearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserYearFragment extends Fragment {

    public static View global_view;
    public static GraphView graph;
    public static String nutrient = "calories";
    public static double recNutrient = -1;

    public static LineGraphSeries<DataPoint> series;
    public static LineGraphSeries<DataPoint> standard;

    private static long start, end;
    private static Calendar day1;

    private static boolean unloaded = true;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserYearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserYearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserYearFragment newInstance(String param1, String param2) {
        UserYearFragment fragment = new UserYearFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        unloaded = false;

        // Inflate the layout for this fragment
        global_view = inflater.inflate(R.layout.fragment_user_year, container, false);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        end = today.getTimeInMillis();
        start = end - 365*864*(long)java.lang.Math.pow(10,5);
        day1 = Calendar.getInstance();
        day1.setTimeInMillis(start);

        graphUpdateYear(nutrient, false);
        return global_view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public static void setNutrient(String input)
    {
        UserInfo user = SearchFragment.getTheirInfo();
        nutrient = input;
        switch(input){
            case "calories":
                recNutrient = user.getRecCalories();
                break;
            case "carbs":
                recNutrient = user.getRecCarbs();
                break;
            case "fat":
                recNutrient = user.getRecFat();
                break;
            case "protein":
                recNutrient = user.getRecProtein();
                break;
            case "sodium":
                recNutrient = user.getRecSodium();
                break;
            case "sugar":
                recNutrient = user.getRecSugars();
                break;
            case "cholesterol":
                recNutrient = user.getRecCholesterol();
                break;
            case "potassium":
                recNutrient = user.getRecPotassium();
                break;
            case "fiber":
                recNutrient = user.getRecFiber();
                break;
            default:
                recNutrient = user.getRecCalories();
        }
    }

    public static void graphUpdateYear(String s, boolean rewrite) {

        setNutrient(s);

        if(unloaded) return;

        if(rewrite) graph.removeAllSeries();

        double[] intakes = SearchFragment.intakeInterval(start, end,nutrient);

        day1.setTimeInMillis(start);
        createGraphYear(365, day1,nutrient,intakes,recNutrient,global_view);
        graph.onDataChanged(false,false);

    }

    public static void createGraphYear(int numMonths, Calendar startMonthCalendar, String nutrient,
                                       double[] nutrientIntakeAverage, double standardIntakeAverage, View view)
    {
        Calendar calendar1 = (Calendar)startMonthCalendar.clone();
        Calendar calendar2 = (Calendar)startMonthCalendar.clone();

        DataPoint dp[] = new DataPoint[numMonths];
        long startTime = calendar1.getTimeInMillis();
        for(int i = 0; i < numMonths; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntakeAverage[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long endTime = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[numMonths];
        for(int i = 0; i < numMonths; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntakeAverage);
            calendar2.add(Calendar.DATE,1);
        }

        graph = (GraphView) view.findViewById(R.id.graph);
        series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        final FragmentActivity activity = (FragmentActivity)view.getContext();
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity));

        //Arbitrary keep the label amount small
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(startTime);
        graph.getViewport().setMaxX(endTime + 31 * 8.64*java.lang.Math.pow(10,7));
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("date");
        graph.getGridLabelRenderer().setVerticalAxisTitle("mg");

        standard = new LineGraphSeries<>(dpStd);
        standard.setTitle("Standard");
        standard.setColor(Color.GREEN);

        graph.addSeries(series);
        graph.addSeries(standard);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graph.getLegendRenderer().setVisible(false);
        graph.setTitle(nutrient + " Intake");
    }
}
