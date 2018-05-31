package com.example.android.projectfanta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;


public class WeekFragment extends Fragment {
    //Instance variable so that view can be accessed by createGraphWeek method as well
    public View view;
    private static double ONE_DAY = 8.64*java.lang.Math.pow(10,7);

    /**
     * Binary Search for time boundaries
     * @param time time we are looking for (start/end)
     * @param intakes intakes of user
     * @return index of intake at time boundary
     */
    public int searchForIntervalBoundary(long time, ArrayList<Intake> intakes) {

        int intakeIndex = 0;
        int upper = intakes.size();
        int lower = 0;
        boolean found = false;
        int m;

        while (found == false) {

            if (upper < lower) return 0;

            m = lower + (upper - lower)/2;

            if (intakes.get(m).getCreationTime() < time) lower = m + 1;
            else if (intakes.get(m).getCreationTime() > time) upper = m - 1;
            else if (intakes.get(m).getCreationTime() == time) {
                intakeIndex = m;
                found = true;
            }

        }

        return intakeIndex;

    }

    /**
     * Sum of intakes over interval of time
     * @param start start of interval
     * @param end end of interval
     * @param numDays number of days to sum intakes
     * @return array of doubles the size of numDays with the sum of intakes for each day
     */
    public double[] intakeInterval(long start, long end, int numDays) {

        double[] nutrientIntake = new double[numDays];
        ArrayList<Intake> allIntakes = Information.information.getMyIntakes();
        int startTime = searchForIntervalBoundary(start, allIntakes);
        int endTime = searchForIntervalBoundary(end, allIntakes);
        double intakeSum = 0;
        long beginningOfDay = allIntakes.get(startTime).getCreationTime();
        int day = 0;

        // loop through piece returned by search
        for (int i = startTime; i <= endTime; i++) {

            // if current intake time exceeds a day from current day start
            if (allIntakes.get(i).getCreationTime() -  beginningOfDay > ONE_DAY) {
                // put current sum into array
                nutrientIntake[day] = intakeSum;
                day++;

                // set sum to 0
                intakeSum = allIntakes.get(i).getServings();

                // set current intake creation time as start of new day
                beginningOfDay = allIntakes.get(i).getCreationTime();

            }

            if (day == numDays) break; // check if we have intakes for the whole week

        }

        return nutrientIntake;

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
    public void createGraphWeek(int numDays, Calendar startDayCalendar, String nutrient,
    double[] nutrientIntake, double standardIntake)
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
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(35);
        graph.getGridLabelRenderer().setNumHorizontalLabels(numDays+1);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        //Axis Label
        graph.getViewport().setMinX(start);
        graph.getViewport().setMaxX(end);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

        //Legend
        graph.getGridLabelRenderer().setHorizontalAxisTitle("date");
        graph.getGridLabelRenderer().setVerticalAxisTitle("mg");

        //Graphing standard intake
        LineGraphSeries<DataPoint> standard = new LineGraphSeries<>(dpStd);
        standard.setTitle("Standard");
        standard.setColor(Color.GREEN);

        //Adding them to the view
        graph.addSeries(series);
        graph.addSeries(standard);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        graph.setTitle(nutrient+" "+"Intake");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_week, container, false);
        return view;
    }
}
