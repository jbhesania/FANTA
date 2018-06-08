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
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserWeekFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserWeekFragment extends Fragment {

    public static GraphView graph;
    public static String nutrient = "calories";
    public static double recNutrient;
    public static View global_view;


    public static LineGraphSeries<DataPoint> series;
    public static LineGraphSeries<DataPoint> standard;

    private static long start, end;
    private static Calendar day1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserWeekFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserWeekFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserWeekFragment newInstance(String param1, String param2) {
        UserWeekFragment fragment = new UserWeekFragment();
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
        global_view = inflater.inflate(R.layout.fragment_user_week, container, false);
        System.out.println("IN USER WEEK ON CREATE VIEW ******************");

        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        end = today.getTimeInMillis();
        start = end - 7*864*(long)java.lang.Math.pow(10,5);
        day1 = Calendar.getInstance();
        day1.setTimeInMillis(start);

        graphUpdateWeek( nutrient, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_user_week, container, false);
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    // Creates array and graphs it
    public static void graphUpdateWeek(String s, boolean rewrite) {

        setNutrient(s);

        if(rewrite) graph.removeAllSeries();

        double[] intakes = SearchFragment.intakeInterval(start, end,nutrient);

        /*Fragment frg = null;
        FragmentManager frg = getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/

        day1.setTimeInMillis(start);
        createGraphWeek(7,day1,nutrient,intakes,recNutrient,global_view);
        graph.onDataChanged(false,false);

    }

    public static void createGraphWeek(int numDays, Calendar startDayCalendar, String nutrient,
                                       double[] nutrientIntake, double standardIntake,View view)
    {
        Calendar calendar1 = (Calendar)startDayCalendar.clone();
        Calendar calendar2 = (Calendar)startDayCalendar.clone();

        DataPoint dp[] = new DataPoint[numDays];
        long startTime = calendar1.getTimeInMillis();       //Use for the viewingWindow

        //Create Data set for user line
        for(int i = 0; i < dp.length; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntake[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long endTime = calendar1.getTimeInMillis();         //Use for the viewingWindow

        //Create Data set for standard
        DataPoint dpStd[] = new DataPoint[numDays];
        for(int i = 0; i < numDays; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntake);
            calendar2.add(Calendar.DATE,1);
        }

        //Drawing the graph on View
        series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        // set date label formatter
        final FragmentActivity activity = (FragmentActivity)view.getContext();
        graph = (GraphView) global_view.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity));
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);
        graph.getGridLabelRenderer().setNumHorizontalLabels(numDays+1);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        //Axis Label
        graph.getViewport().setMinX(startTime);
        graph.getViewport().setMaxX(endTime);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);


        /*double max = nutrientIntake[0];
        for (int ktr = 0; ktr < nutrientIntake.length; ktr++) {
            if (nutrientIntake[ktr] > max) {
                max = nutrientIntake[ktr];
            }
        }
        System.out.println("MAX = " + max);
        double min = nutrientIntake[0];
        for (int ktr = 0; ktr < nutrientIntake.length; ktr++) {
            if (nutrientIntake[ktr] < min) {
                min = nutrientIntake[ktr];
            }
        }
        System.out.println("MIN = " + min);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(min);
        graph.getViewport().setMaxY(max);*/

        //Legend
        graph.getGridLabelRenderer().setHorizontalAxisTitle("date");
        graph.getGridLabelRenderer().setVerticalAxisTitle("mg");

        //Graphing standard intake
        standard = new LineGraphSeries<>(dpStd);
        standard.setTitle("Standard");
        standard.setColor(Color.GREEN);

        //Show data onClicked
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((long)dataPoint.getX());
                Toast.makeText(activity, "Date: "
                                + calendar.get(Calendar.DATE) + "/"
                                + (calendar.get(Calendar.MONTH)+1) + "/"
                                + calendar.get(Calendar.YEAR)+ "\n"
                                + graph.getTitle()+": "+ dataPoint.getY() + " "
                                + graph.getGridLabelRenderer().getVerticalAxisTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        /*graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);*/


        //Adding them to the view
        graph.addSeries(series);
        graph.addSeries(standard);
        graph.getLegendRenderer().setVisible(false);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.setTitle(nutrient+" "+"Intake");
    }

    public static void setNutrient(String input)
    {
        UserInfo user = Information.information.getInfo();
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
}
