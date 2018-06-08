package com.example.android.projectfanta;

import android.graphics.Color;
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


public class MonthFragment extends Fragment {
    public static String nutrient = "calories";
    public static double recNutrient;
    public static View global_view;
    public static GraphView graph;

    public static LineGraphSeries<DataPoint> series;
    public static LineGraphSeries<DataPoint> standard;

    private static long start, end;
    private static Calendar day1;

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

    /**
     * numDays The number of days including starting day to end day. E.g from 12/10 - 12/12, numDays
     * would be 3.
     * startDayCalendar The Calendar Object for starting day
     * nutrient The name of the nutrient
     * double[] An array of nutrient intake
     * standardIntake I assume the intake is constant throughout... If not please tell me to fix it
     * I didn't check for nutrientIntake.length = numDays, I assume they are equal
     *
     * How to create and set a Calendar instance
     * Calendar calendar = Calendar.getInstance();
     * Month Field 0-11 Represent January to December
     * calendar.set(2017,11,1);
     */
    public static void createGraphMonth(int numDays, Calendar startDayCalendar, String nutrient,
                                 double[] nutrientIntake, double standardIntake,View view){

        Calendar calendar1 = (Calendar)startDayCalendar.clone();
        Calendar calendar2 = (Calendar)startDayCalendar.clone();

        DataPoint dp[] = new DataPoint[numDays];
        long startTime = calendar1.getTimeInMillis();
        for(int i = 0; i < numDays; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntake[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long endTime = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[numDays];
        for(int i = 0; i < numDays; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntake);
            calendar2.add(Calendar.DATE,1);
        }

        graph = (GraphView) view.findViewById(R.id.graph);
        series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        // set date label formatter
        final FragmentActivity activity = (FragmentActivity)view.getContext();
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity));
        graph.getGridLabelRenderer().setTextSize(36);

        //Disable horizontal label since there might be a lot
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(startTime);
        graph.getViewport().setMaxX(endTime);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

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

    // Creates array and graphs it
    public static void graphUpdateMonth(String s, boolean rewrite) {

        setNutrient(s);

        if(rewrite) graph.removeAllSeries();

        double[] intakes = Information.information.intakeInterval(start, end,nutrient);

        /*Fragment frg = null;
        FragmentManager frg = getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/

        day1.setTimeInMillis(start);
        createGraphMonth(31,day1,nutrient,intakes,recNutrient,global_view);
        graph.onDataChanged(false,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        global_view = inflater.inflate(R.layout.fragment_month, container, false);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        end = today.getTimeInMillis();
        start = end - 31*864*(long)java.lang.Math.pow(10,5);
        day1 = Calendar.getInstance();
        day1.setTimeInMillis(start);

        graphUpdateMonth( nutrient, false);
        return global_view;
    }

}
