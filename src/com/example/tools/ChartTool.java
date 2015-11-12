package com.example.tools;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChartTool {
	
	private Context context;
	
	private LinearLayout temperautreForOneDay;
	
	private LinearLayout temperautreForAll;


	public ChartTool(Context context, LinearLayout temperautreForOneDay,
			LinearLayout temperautreForAll) {
		super();
		this.context = context;
		this.temperautreForOneDay = temperautreForOneDay;
		this.temperautreForAll = temperautreForAll;
	}


	public void TemperautreForOneDay(){
		//ͬ������Ҫ����dataset����ͼ��Ⱦ��renderer  
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();  
        XYSeries  series = new XYSeries("�¶�����");  
        series.add(1, 6);  
        series.add(2, 5);  
        series.add(3, 7);  
        series.add(4, 4); 
        mDataset.addSeries(series);  
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();  
        //����ͼ���X��ĵ�ǰ����  
        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);  
        mRenderer.setXTitle("����");//����ΪX��ı���  
        mRenderer.setYTitle("�۸�");//����y��ı���  
        mRenderer.setAxisTitleTextSize(20);//����������ı���С  
        mRenderer.setChartTitle("һ���¶�����ͼ");//����ͼ�����  
        mRenderer.setChartTitleTextSize(30);//����ͼ��������ֵĴ�С  
        mRenderer.setLabelsTextSize(18);//���ñ�ǩ�����ִ�С  
        mRenderer.setLegendTextSize(20);//����ͼ���ı���С  
        mRenderer.setPointSize(10f);//���õ�Ĵ�С  
        mRenderer.setYAxisMin(0);//����y����Сֵ��0  
        mRenderer.setYAxisMax(15);  
        mRenderer.setYLabels(10);//����Y��̶ȸ�����ò�Ʋ�̫׼ȷ��  
        mRenderer.setXAxisMax(5);  
        mRenderer.setShowGrid(true);//��ʾ����  
        //��x��ǩ��Ŀ��ʾ�磺1,2,3,4�滻Ϊ��ʾ1�£�2�£�3�£�4��  
        mRenderer.addXTextLabel(1, "��1��");  
        mRenderer.addXTextLabel(2, "��2��");  
        mRenderer.addXTextLabel(3, "��3��");  
        mRenderer.addXTextLabel(4, "��4��"); 
        mRenderer.addXTextLabel(5, "��5��"); 
        mRenderer.addXTextLabel(6, "��6��"); 
        mRenderer.addXTextLabel(7, "��7��");  
        mRenderer.setXLabels(0);//����ֻ��ʾ��1�£�2�µ��滻��Ķ���������ʾ1,2,3��  
        mRenderer.setBackgroundColor(Color.TRANSPARENT);
        mRenderer.setMargins(new int[] { 120, 30, 15, 20 });//������ͼλ��  
        XYSeriesRenderer r = new XYSeriesRenderer();//(������һ���߶���)  
        r.setColor(Color.RED);//������ɫ  
        r.setPointStyle(PointStyle.CIRCLE);//���õ����ʽ  
        r.setFillPoints(true);//���㣨��ʾ�ĵ��ǿ��Ļ���ʵ�ģ�  
        r.setDisplayChartValues(true);//�����ֵ��ʾ����  
        r.setChartValuesSpacing(10);//��ʾ�ĵ��ֵ��ͼ�ľ���  
        r.setChartValuesTextSize(25);//���ֵ�����ִ�С  
        r.setLineWidth(3);//�����߿�  
        mRenderer.addSeriesRenderer(r);  
        GraphicalView  view = ChartFactory.getLineChartView(context, mDataset, mRenderer);  
        view.setBackgroundColor(Color.TRANSPARENT); 
        temperautreForOneDay.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, 500));
	}
	
	public void TemperautreForAll(){
		//ͬ������Ҫ����dataset����ͼ��Ⱦ��renderer  
        XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();  
        XYSeries  series = new XYSeries("�¶�����");  
        
        series.add(1, 6);  
        series.add(2, 5);  
        series.add(3, 7);  
        series.add(4, 4); 
        series.add(5, 5);  
        series.add(6, 7);  
        series.add(7, 4); 
        mDataset.addSeries(series);  
        XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();  
        //����ͼ���X��ĵ�ǰ����  
        mRenderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);  
        mRenderer.setXTitle("ʱ��");//����ΪX��ı���  
        mRenderer.setYTitle("�¶�");//����y��ı���  
        mRenderer.setAxisTitleTextSize(20);//����������ı���С  
        mRenderer.setChartTitle("15���¶�����ͼ");//����ͼ�����  
        mRenderer.setChartTitleTextSize(30);//����ͼ��������ֵĴ�С  
        mRenderer.setLabelsTextSize(18);//���ñ�ǩ�����ִ�С  
        mRenderer.setLegendTextSize(20);//����ͼ���ı���С  
        mRenderer.setPointSize(10f);//���õ�Ĵ�С  
        mRenderer.setYAxisMin(0);//����y����Сֵ��0  
        mRenderer.setYAxisMax(15);  
        mRenderer.setYLabels(10);//����Y��̶ȸ�����ò�Ʋ�̫׼ȷ��  
        mRenderer.setXAxisMax(5);  
        mRenderer.setShowGrid(true);//��ʾ����  
        mRenderer.addXTextLabel(1, "��1��");  
        mRenderer.addXTextLabel(2, "��2��");  
        mRenderer.addXTextLabel(3, "��3��");  
        mRenderer.addXTextLabel(4, "��4��"); 
        mRenderer.addXTextLabel(5, "��5��"); 
        mRenderer.addXTextLabel(6, "��6��"); 
        mRenderer.addXTextLabel(7, "��7��"); 
        mRenderer.setXLabels(0);//����ֻ��ʾ��1�£�2�µ��滻��Ķ���������ʾ1,2,3��  
        mRenderer.setMargins(new int[] { 120, 30, 15, 20 });//������ͼλ��  
        mRenderer.setBackgroundColor(Color.TRANSPARENT);
        XYSeriesRenderer r = new XYSeriesRenderer();//(������һ���߶���)  
        r.setColor(Color.YELLOW);//������ɫ  
        r.setPointStyle(PointStyle.CIRCLE);//���õ����ʽ  
        r.setFillPoints(true);//���㣨��ʾ�ĵ��ǿ��Ļ���ʵ�ģ�  
        r.setDisplayChartValues(true);//�����ֵ��ʾ����  
        r.setChartValuesSpacing(10);//��ʾ�ĵ��ֵ��ͼ�ľ���  
        r.setChartValuesTextSize(25);//���ֵ�����ִ�С  
        r.setLineWidth(3);//�����߿�  
        mRenderer.addSeriesRenderer(r);  
        GraphicalView  view = ChartFactory.getLineChartView(context, mDataset, mRenderer);  
        view.setBackgroundColor(Color.TRANSPARENT); 
        temperautreForAll.addView(view, new LayoutParams(LayoutParams.FILL_PARENT, 500));
	}

	
	
}
