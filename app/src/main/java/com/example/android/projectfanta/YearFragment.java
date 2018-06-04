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

import java.util.Calendar;

public class YearFragment extends Fragment {
    public View view;
    public static String nutrient;
    public static double recNutrient;

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
     * numMonths The number of days including starting month to end month. E.g from May to July, numMonths
     * would be 3.
     * startMonthCalendar The Calendar Object for starting day
     * nutrient The name of the nutrient
     * double[] An array of nutrient intake
     * standardIntake I assume the intake is constant throughout... If not please tell me to fix it
     */
    public void createGraphYear(int numMonths, Calendar startMonthCalendar, String nutrient,
                                double[] nutrientIntakeAverage, double standardIntakeAverage,View view)
    {
        Calendar calendar1 = (Calendar)startMonthCalendar.clone();
        Calendar calendar2 = (Calendar)startMonthCalendar.clone();

        DataPoint dp[] = new DataPoint[numMonths];
        long start = calendar1.getTimeInMillis();
        for(int i = 0; i < numMonths; i++)
        {
            DataPoint point = new DataPoint(calendar1.getTime(), nutrientIntakeAverage[i]);
            dp[i] = point;
            calendar1.add(Calendar.DATE,1);
        }
        long end = calendar1.getTimeInMillis();

        DataPoint dpStd[] = new DataPoint[numMonths];
        for(int i = 0; i < numMonths; i++)
        {
            dpStd[i] = new DataPoint(calendar2.getTime(),standardIntakeAverage);
            calendar2.add(Calendar.DATE,1);
        }

        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setTitle("User");

        // set date label formatter
        // use static labels for horizontal and vertical labels

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        //Arbitrary keep the label amount small
        graph.getGridLabelRenderer().setNumHorizontalLabels(6);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(30);
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.getGridLabelRenderer().setTextSize(36);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(start);
        graph.getViewport().setMaxX(end + 31 * 8.64*java.lang.Math.pow(10,7));
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
        graph.getLegendRenderer().setVisible(false);
        graph.setTitle(nutrient + " Intake");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_year, container, false);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, 1);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        long end = today.getTimeInMillis();
        long start = end - 365*864*(long)java.lang.Math.pow(10,5);
        Calendar test = Calendar.getInstance();
        test.setTimeInMillis(start);

        double[] intakes = Information.information.intakeInterval(start, end,"calories");

        createGraphYear(365,test,"calories",intakes,recNutrient,view);
        return view;
    }

}
