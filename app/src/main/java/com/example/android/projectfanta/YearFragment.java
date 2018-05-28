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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_year, container, false);

        // generate Dates
        Calendar calendar = Calendar.getInstance();

        // Month Field 0-11 Represent January to December
        calendar.set(2017,0,1);

        Calendar calendar1 = (Calendar)calendar.clone();
        Calendar calendar2 = (Calendar)calendar.clone();

        DataPoint dp[] = new DataPoint[12];
        long start = calendar1.getTimeInMillis();
        for(int i = 0; i < 12; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(),i+0.5);
            dp[i] = point;
            calendar1.add(Calendar.MONTH,1);
            Log.e("hLabel",calendar1.get(Calendar.MONTH)+" "+calendar1.get(Calendar.DATE)+"");
        }
        long end = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[12];
        for(int i = 0; i < 12; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),4);
            calendar2.add(Calendar.MONTH,1);
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels
        // set date label formatter
        /*StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"January", "February","March",
                "April","May","June", "July","August","September",
                "October","November","December"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);*/
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
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
        graph.setTitle("Protein Intake");

        return view;
    }

}
