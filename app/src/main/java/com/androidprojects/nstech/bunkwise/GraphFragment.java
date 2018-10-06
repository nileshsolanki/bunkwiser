package com.androidprojects.nstech.bunkwise;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper;
import com.androidprojects.nstech.bunkwise.Utils.PrefHandler;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.COLUMNS;
import static com.androidprojects.nstech.bunkwise.Utils.DatesReaderDbHelper.TABLE_NAME;


public class GraphFragment extends Fragment {

    View v;
    GraphView graph;
    BarGraphSeries<DataPoint> series;
    LineGraphSeries<DataPoint> seriesTotal;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_graph, container, false);

        DataPoint[] dataPointsAttended = new DataPoint[COLUMNS.length];
        DataPoint[] dataPointsTotal = new DataPoint[COLUMNS.length];


        graph = (GraphView) v.findViewById(R.id.graph);
        graph.setBackgroundColor(getResources().getColor(R.color.offwhite));
        graph.setTitle("Attendance Record");
        graph.getGridLabelRenderer().setNumHorizontalLabels(COLUMNS.length);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);

//        setZoomableGraph(graph);






        PrefHandler pf = new PrefHandler(getContext());


        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(pf.getSubjectStats(COLUMNS[0])[2] + 20);
        graph.getViewport().setYAxisBoundsManual(true);
        int i = 0;
        for(String col : COLUMNS){

            int [] subStats = pf.getSubjectStats(col);
            dataPointsAttended[i] = new DataPoint(i+1, subStats[0]);
            dataPointsTotal[i] = new DataPoint(i+1, subStats[2]);
            i++;
        }


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(isValueX && value <= COLUMNS.length){
                    return COLUMNS[(int)(value) - 1];
                }
                return super.formatLabel(value, isValueX);
            }
        });




        series = new BarGraphSeries<>(dataPointsAttended);
        series.setSpacing(50);


        seriesTotal = new LineGraphSeries<>(dataPointsTotal);
        series.setSpacing(50);


        seriesTotal.setColor(R.color.colorAccent);

        addSeriesToGraph();
        graph.animate();

        // styling
//        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
//            }
//        });

        // draw values on top
        series.setDrawValuesOnTop(true);
        seriesTotal.setDrawDataPoints(true);
        series.setValuesOnTopColor(R.color.colorPrimaryDark);
        //series.setValuesOnTopSize(50);


        return v;
    }

    private void setZoomableGraph(GraphView graph) {

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(20);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(COLUMNS.length);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

    }

    public void addSeriesToGraph() {

        graph.addSeries(series);
        graph.addSeries(seriesTotal);

        series.setTitle("Attended");
        seriesTotal.setTitle("Total");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

    }
}
