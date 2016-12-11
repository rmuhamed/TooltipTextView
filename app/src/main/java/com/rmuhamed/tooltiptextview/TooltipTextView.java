package com.rmuhamed.tooltiptextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.rmuhamed.tooltiptextview.helper.ConverstionHelper;

/**
 * Created by rmuhamed on 02/12/2016.
 */

public class TooltipTextView extends View {
	public static final int TEXT_SIZE_DEF = 15;
	private int textSize;
	//text & background colors
	private int textColor;
	private int backgroundColor;
	//label text
	private String tooltipText;
	//paint for drawing custom view
	private Paint rectanglePaint;
	private Paint trianglePaint;
	private Rect aRectangle;
	private Path aPath;

	private int rectangleHeight;
	private int rectangleWidth;

	private Point aTrianglePoint1;
	private Point aTrianglePoint2;
	private Point aTrianglePoint3;

	public TooltipTextView(Context context) {
		super(context);

		this.initialize();
	}

	public TooltipTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		//get the attributes specified in attrs.xml
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TooltipTextView, 0, 0);
		try {
			this.textColor = a.getInteger(R.styleable.TooltipTextView_tooltipTextColor, 0);
			this.textSize = a.getDimensionPixelSize(R.styleable.TooltipTextView_tooltipTextSize, TEXT_SIZE_DEF);
			this.tooltipText = a.getString(R.styleable.TooltipTextView_tooltipText);
			this.backgroundColor = a.getInteger(R.styleable.TooltipTextView_tooltipBackgroundColor, 0);
		} finally {
			a.recycle();
		}

		this.initialize();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentHeight = ConverstionHelper.toDP(MeasureSpec.getSize(heightMeasureSpec), this.getContext());
		int parentWidth = ConverstionHelper.toDP(MeasureSpec.getSize(widthMeasureSpec), this.getContext());

		this.rectangleHeight = (int) (parentHeight * 0.30);
		this.rectangleWidth = parentWidth;

		this.aTrianglePoint1.set((int) (this.rectangleWidth * 0.40), this.rectangleHeight);
		this.aTrianglePoint2.set((int) (this.rectangleWidth * 0.60), this.rectangleHeight);
		this.aTrianglePoint3.set((int) (this.rectangleWidth * 0.5), (this.rectangleHeight - parentHeight) * -1);

		this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int textPositionX = this.rectangleWidth/2;
		int textPositionY = this.rectangleHeight/2;

		this.drawRectangle(canvas, this.aRectangle, this.rectanglePaint, this.backgroundColor);
		this.drawText(canvas, this.rectanglePaint, this.textColor, this.textSize, textPositionX, textPositionY);
		this.drawTriangle(canvas, this.trianglePaint, this.backgroundColor);
	}

	private void drawTriangle(Canvas canvas, Paint trianglePaint, int backgroundColor) {
		trianglePaint.setColor(backgroundColor);
		trianglePaint.setStyle(Paint.Style.FILL);

		this.aPath.setFillType(Path.FillType.EVEN_ODD);
		this.aPath.moveTo(this.aTrianglePoint1.x, this.aTrianglePoint1.y);
		this.aPath.lineTo(this.aTrianglePoint2.x, this.aTrianglePoint2.y);
		this.aPath.lineTo(this.aTrianglePoint3.x, this.aTrianglePoint3.y);
		this.aPath.lineTo(this.aTrianglePoint1.x, this.aTrianglePoint1.y);
		this.aPath.close();

		canvas.drawPath(this.aPath, this.trianglePaint);
	}

	private void drawText(Canvas canvas, Paint aPaint, int textColor, int textSize, int x, int y) {
		aPaint.setColor(textColor);
		aPaint.setTextSize(textSize);
		aPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(this.tooltipText, x, y, aPaint);
	}

	private void drawRectangle(Canvas canvas, Rect aRectangle, Paint rectanglePaint, int backgroundColor) {
		aRectangle.set(0, 0, this.rectangleWidth, this.rectangleHeight);

		rectanglePaint.setColor(backgroundColor);
		rectanglePaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(aRectangle, rectanglePaint);
	}

	private void initialize() {
		this.rectanglePaint = new Paint();
		this.trianglePaint = new Paint();

		this.aRectangle = new Rect();

		this.aPath = new Path();

		this.aTrianglePoint1 = new Point();
		this.aTrianglePoint2 = new Point();
		this.aTrianglePoint3 = new Point();
	}
}
