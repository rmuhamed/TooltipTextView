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

	public TooltipTextView(Context context) {
		super(context);
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
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int rectangleHeight = (int) (this.getMeasuredHeight() * 0.75);
		int rectangleWidth = this.getMeasuredWidth();
		int textPositionX = rectangleWidth/2;
		int textPositionY = rectangleHeight/2;

		this.drawRectangle(canvas, this.aRectangle, this.rectanglePaint, this.backgroundColor, rectangleHeight, rectangleWidth);
		this.drawText(canvas, this.rectanglePaint, this.textColor, this.textSize, textPositionX, textPositionY, Paint.Align.CENTER);
		this.drawTriangle(canvas, this.trianglePaint, this.backgroundColor, rectangleHeight);
	}

	private void initialize() {
		this.rectanglePaint = new Paint();
		this.trianglePaint = new Paint();

		this.aRectangle = new Rect();

		this.aPath = new Path();
		this.aPath.setFillType(Path.FillType.EVEN_ODD);
	}

	private void drawTriangle(Canvas canvas, Paint trianglePaint, int backgroundColor, int rectangleHeight) {
		trianglePaint.setColor(backgroundColor);
		trianglePaint.setStyle(Paint.Style.FILL);

		Point aTrianglePoint1 = new Point((int) (this.getMeasuredWidth() * 0.40), rectangleHeight);
		Point aTrianglePoint2 = new Point((int) (this.getMeasuredWidth() * 0.60), rectangleHeight);
		Point aTrianglePoint3 = new Point((int) (this.getMeasuredWidth() * 0.5), this.getMeasuredHeight());

		this.aPath.moveTo(aTrianglePoint1.x, aTrianglePoint1.y);
		this.aPath.lineTo(aTrianglePoint2.x, aTrianglePoint2.y);
		this.aPath.lineTo(aTrianglePoint3.x, aTrianglePoint3.y);
		this.aPath.lineTo(aTrianglePoint1.x, aTrianglePoint1.y);
		this.aPath.close();

		canvas.drawPath(this.aPath, this.trianglePaint);
	}

	private void drawText(Canvas canvas, Paint aPaint, int textColor, int textSize, int x, int y, Paint.Align align) {
		aPaint.setColor(textColor);
		aPaint.setTextSize(textSize);
		aPaint.setTextAlign(align);
		canvas.drawText(this.tooltipText, x, y, aPaint);
	}

	private void drawRectangle(Canvas canvas, Rect aRectangle, Paint rectanglePaint, int backgroundColor, int rectangleHeight, int rectangleWidth) {
		aRectangle.set(0, 0, rectangleWidth, rectangleHeight);

		rectanglePaint.setColor(backgroundColor);
		rectanglePaint.setStyle(Paint.Style.FILL);
		canvas.drawRect(aRectangle, rectanglePaint);
	}
}
