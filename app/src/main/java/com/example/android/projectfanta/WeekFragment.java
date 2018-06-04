package com.example.android.projectfanta;

import android.graphics.Color;
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


public class WeekFragment extends Fragment{
    //Instance variable so that view can be accessed by createGraphWeek method as well
    public static GraphView graph;
    public static String nutrient = "calories";
    public static double recNutrient;
    public static View global_view;


    public static LineGraphSeries<DataPoint> series;
    public static LineGraphSeries<DataPoint> standard;

    private static long start, end;
    private static Calendar day1;

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
    public static void createGraphWeek(int numDays, Calendar startDayCalendar, String nutrient,
                                       double[] nutrientIntake, double standardIntake,View view)
    {
        Calendar calendar1 = (Calendar)startDayCalendar.clone();
        Calendar calendar2 = (Calendar)startDayCalendar.clone();

        DataPoint dp[] = new DataPoint[numDays];
        long start = calendar1.getTimeInMillis();       //Use for the viewingWindow

        //Create Data set for user line
        for(int i = 0; i < dp.length; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntake[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long end = calendar1.getTimeInMillis();         //Use for the viewingWindow

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
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(35);
        graph.getGridLabelRenderer().setNumHorizontalLabels(numDays+1);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        //Axis Label
        graph.getViewport().setMinX(start);
        graph.getViewport().setMaxX(end);
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

    // Creates array and graphs it
    public  static void graphUpdate(String s, boolean rewrite) {

        setNutrient(s);

        if(rewrite) graph.removeAllSeries();

        double[] intakes = Information.information.intakeInterval(start, end,nutrient);

        /*Fragment frg = null;
        FragmentManager frg = getSupportFragmentManager().findFragmentByTag("Your_Fragment_TAG");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/

        createGraphWeek(7,day1,nutrient,intakes,recNutrient,global_view);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        global_view = inflater.inflate(R.layout.fragment_week, container, false);

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

        graphUpdate( nutrient, false);
        return global_view;
    }

}

