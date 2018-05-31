package com.example.android.projectfanta;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class YearFragment extends Fragment {
    public View view;

    /**
     * numMonths The number of days including starting month to end month. E.g from May to July, numMonths
     * would be 3.
     * startMonthCalendar The Calendar Object for starting day
     * nutrient The name of the nutrient
     * double[] An array of nutrient intake
     * standardIntake I assume the intake is constant throughout... If not please tell me to fix it
     */
    public void createGraphYear(int numMonths, Calendar startMonthCalendar, String nutrient,
                                double[] nutrientIntakeAverage, double standardIntakeAverage)
    {
        Calendar calendar1 = (Calendar)startMonthCalendar.clone();
        Calendar calendar2 = (Calendar)startMonthCalendar.clone();

        DataPoint dp[] = new DataPoint[numMonths];
        long start = calendar1.getTimeInMillis();
        for(int i = 0; i < numMonths; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntakeAverage[i]);
            dp[i] = point;
            calendar1.add(Calendar.MONTH,1);
        }
        long end = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[numMonths];
        for(int i = 0; i < numMonths; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntakeAverage);
            calendar2.add(Calendar.MONTH,1);
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        //Arbitrary keep the label amount small
        graph.getGridLabelRenderer().setNumHorizontalLabels(numMonths/2);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(15);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(start);
        graph.getViewport().setMaxX(end);
        graph.getViewport().setXAxisBoundsManual(true);

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
        view = inflater.inflate(R.layout.fragment_year, container, false);
        return view;
    }

}
