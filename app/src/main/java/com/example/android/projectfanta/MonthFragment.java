package com.example.android.projectfanta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

import static com.example.android.projectfanta.WeekFragment.recNutrient;


public class MonthFragment extends Fragment {
    public View view;
    public static String nutrient;
    public static double recNutrient;

    public static void setNutrient(String input)
    {
        nutrient = input;
        switch(input){
            case "calories":
                recNutrient = 200.0;
                break;
            case "carbs":
                recNutrient = 200.0;
                break;
            case "fat":
                recNutrient = 200.0;
                break;
            case "protein":
                recNutrient = 200.0;
                break;
            case "sodium":
                recNutrient = 200.0;
                break;
            case "sugar":
                recNutrient = 200.0;
                break;
            case "cholesterol":
                recNutrient = 200.0;
                break;
            case "potassium":
                recNutrient = 200.0;
                break;
            case "fiber":
                recNutrient = 200.0;
                break;
            default:
                recNutrient = 200.0;
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
    public void createGraphMonth(int numDays, Calendar startDayCalendar, String nutrient,
                                 double[] nutrientIntake, double standardIntake,View view){

        Calendar calendar1 = (Calendar)startDayCalendar.clone();
        Calendar calendar2 = (Calendar)startDayCalendar.clone();

        DataPoint dp[] = new DataPoint[numDays];
        long start = calendar1.getTimeInMillis();
        for(int i = 0; i < numDays; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntake[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long end = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[numDays];
        for(int i = 0; i < numDays; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntake);
            calendar2.add(Calendar.DATE,1);
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setTextSize(36);

        //Disable horizontal label since there might be a lot
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(start);
        graph.getViewport().setMaxX(end);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getGridLabelRenderer().setHorizontalAxisTitle("date");
        graph.getGridLabelRenderer().setVerticalAxisTitle("mg");

        LineGraphSeries<DataPoint> standard = new LineGraphSeries<>(dpStd);
        standard.setTitle("Standard");
        standard.setColor(Color.GREEN);

        graph.addSeries(series);
        graph.addSeries(standard);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graph.setTitle(nutrient + " Intake");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        long end = today.getTimeInMillis()+864*(long)java.lang.Math.pow(10,5);
        //long end = System.currentTimeMillis();
        long start = end - 31*864*(long)java.lang.Math.pow(10,5);
        //createGraphWeek(7, , "Protein",
        Calendar test = Calendar.getInstance();
        test.setTimeInMillis(start-864*(long)java.lang.Math.pow(10,5));

        double[] intakes = Information.information.intakeInterval(start, end,"calories");

        createGraphMonth(7,test,"calories",intakes,recNutrient,view);
        return view;
    }

}
