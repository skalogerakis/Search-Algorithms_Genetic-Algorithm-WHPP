package MainPackage;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.util.ArrayList;


public class Chart implements ExampleChart<XYChart> {
    //todo simply choose how to visualize everything in the end

    ArrayList<Double> best;
    ArrayList<Double> avg;
    ArrayList<Double> stat;
    String name;

    public Chart(ArrayList<Double> best, ArrayList<Double> avg){
        this.best = best;
        this.avg = avg;
    }

    public Chart(ArrayList<Double> stat, String name){
        this.stat = stat;
        this.name = name;
    }

    //This can be used to visualise everything in one graph. Choose the other constructor if we want that
//    @Override
//    public XYChart getChart() {
//
//        // Create Chart
//        XYChart chart =
//                new XYChartBuilder()
//                        .width(800)
//                        .height(600)
//                        .title("Genetic Algorithm Evaluation")
//                        .xAxisTitle("Generations")
//                        .yAxisTitle("Penalty score")
//                        .build();
//
//        // Customize Chart
//        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
//        chart.getStyler().setChartBackgroundColor(Color.white);
//        chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
//        chart.getStyler().setAxisTitlesVisible(true);
//        chart.getStyler().setChartFontColor(Color.blue);
//
//        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
//        chart.getStyler().setPlotGridLinesVisible(false);//
//        chart.getStyler().setCursorEnabled(true);
//        chart.getStyler().setAxisTitlePadding(10);
//        chart.getStyler().setAxisTickPadding(15);
//
//        chart.getStyler().setAxisTickMarkLength(10);
//
//        chart.getStyler().setPlotMargin(20);
//
//        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
//        //chart.getStyler().setLegendSeriesLineLength(12);
//        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
//        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));
//        chart.getStyler().setYAxisMin(45000d);
//
//
//        ArrayList<Integer> xData = new ArrayList<Integer>();
//
//        for(int i = 0; i < this.best.size(); i++){
//            xData.add(i);
//        }
//
//
//        // Series
//        XYSeries series =chart.addSeries("Average", xData, avg);
//        XYSeries series2 = chart.addSeries("Best", xData, best);
//
//        series.setLineColor(XChartSeriesColors.RED);
//        series.setMarkerColor(Color.BLACK);
//        series.setMarker(SeriesMarkers.CIRCLE);
//        series.setLineStyle(SeriesLines.SOLID);
//        series2.setLineColor(XChartSeriesColors.GREEN);
//        series2.setMarkerColor(Color.MAGENTA);
//
//        series2.setMarker(SeriesMarkers.DIAMOND);
//        series2.setLineStyle(SeriesLines.SOLID);
//
//        return chart;
//    }

    @Override
    public XYChart getChart() {

        // Create Chart
        XYChart chart =
                new XYChartBuilder()
                        .width(800)
                        .height(600)
                        .title("Genetic Algorithm "+this.name+ " Case Evaluation")
                        .xAxisTitle("Generations")
                        .yAxisTitle("Penalty score")
                        .build();

        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.GREY));
        chart.getStyler().setChartBackgroundColor(Color.white);
        chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setChartFontColor(Color.blue);

        chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
        chart.getStyler().setPlotGridLinesVisible(false);

        chart.getStyler().setCursorEnabled(true);
        chart.getStyler().setAxisTitlePadding(10);
        chart.getStyler().setAxisTickPadding(15);

        chart.getStyler().setAxisTickMarkLength(10);

        chart.getStyler().setPlotMargin(20);

        chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
        chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 11));


        ArrayList<Integer> xData = new ArrayList<Integer>();

        for(int i = 0; i < this.stat.size(); i++){
            xData.add(i);
        }


        // Series
        XYSeries series =chart.addSeries(this.name, xData, stat);

        if(this.name.compareTo("Best") == 0){
            series.setLineColor(XChartSeriesColors.RED);
            series.setMarkerColor(Color.BLACK);
            series.setMarker(SeriesMarkers.CIRCLE);
            series.setLineStyle(SeriesLines.SOLID);
        }else{
            series.setLineColor(XChartSeriesColors.GREEN);
            series.setMarkerColor(Color.MAGENTA);
            series.setMarker(SeriesMarkers.DIAMOND);
            series.setLineStyle(SeriesLines.SOLID);
        }

        return chart;
    }
}