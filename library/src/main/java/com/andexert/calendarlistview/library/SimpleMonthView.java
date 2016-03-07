/***********************************************************************************
 * The MIT License (MIT)

 * Copyright (c) 2014 Robin Chutaux

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.andexert.calendarlistview.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

class SimpleMonthView extends View
{
    public static final String VIEW_PARAMS_HEIGHT               = "height";
    public static final String VIEW_PARAMS_MONTH                = "month";
    public static final String VIEW_PARAMS_YEAR                 = "year";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_DAY   = "selected_begin_day";
    public static final String VIEW_PARAMS_SELECTED_LAST_DAY    = "selected_last_day";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_MONTH = "selected_begin_month";
    public static final String VIEW_PARAMS_SELECTED_LAST_MONTH  = "selected_last_month";
    public static final String VIEW_PARAMS_SELECTED_BEGIN_YEAR  = "selected_begin_year";
    public static final String VIEW_PARAMS_SELECTED_LAST_YEAR   = "selected_last_year";
    public static final String VIEW_PARAMS_WEEK_START           = "week_start";

    private static final String SANS_SERIF = "sans-serif";

    protected static       int SELECTED_CIRCLE_ALPHA = 128;
    protected static       int DEFAULT_HEIGHT        = 32;
    protected static final int DEFAULT_NUM_ROWS      = 6;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static int MIN_HEIGHT = 10;

    protected int mPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitleBGPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;

	protected DatePickerAttributes mAttributes;

    protected boolean mHasToday           = false;
    protected boolean mIsPrev             = false;
    protected int     mSelectedBeginDay   = -1;
    protected int     mSelectedLastDay    = -1;
    protected int     mSelectedBeginMonth = -1;
    protected int     mSelectedLastMonth  = -1;
    protected int     mSelectedBeginYear  = -1;
    protected int     mSelectedLastYear   = -1;
    protected int     mToday              = -1;
    protected int     mWeekStart          = 1;
    protected int     mNumDays            = 7;
    protected int     mNumCells           = mNumDays;
    private   int     mDayOfWeekStart     = 0;
    protected int     mMonth;
    protected int     mWidth;
	protected int     mRowHeight = DEFAULT_HEIGHT;

	protected int      mYear;
    final     Calendar today;

    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;

    private int mNumRows = DEFAULT_NUM_ROWS;

    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    private OnDayClickListener mOnDayClickListener;

    public SimpleMonthView(Context context, DatePickerAttributes attrs)
    {
        super(context);

		mAttributes = attrs;
		mRowHeight = mAttributes.rowHeight;
		
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        today = Calendar.getInstance();
        mDayOfWeekTypeface = SANS_SERIF;
        mMonthTitleTypeface = SANS_SERIF;

        initView();
    }

    private int calculateNumRows() {
        return (int)Math.ceil((findDayOffset() + mNumCells) / (double)mNumDays);
	}

	private void drawMonthDayLabels(Canvas canvas) {
        int y = mAttributes.monthHeaderHeight - (mAttributes.dayNameTextSize / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].toUpperCase(Locale.getDefault()), x, y, mMonthDayLabelPaint);
        }
	}

	private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (mAttributes.monthHeaderHeight - mAttributes.dayNameTextSize) / 2 + (mAttributes.monthLabelTextSize / 3);
        StringBuilder stringBuilder = new StringBuilder(getMonthAndYearString().toLowerCase());
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        canvas.drawText(stringBuilder.toString(), x, y, mMonthTitlePaint);
	}

	private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
	}

	private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
        long millis = mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, flags);
    }

	private void onDayClick(SimpleMonthAdapter.CalendarDay calendarDay) {
		if (mOnDayClickListener == null) {
			return;
		}

		if (!mAttributes.isPrevDayEnabled && prevDay(calendarDay.getCalendar())) {
			return;
		}

		mOnDayClickListener.onDayClick(this, calendarDay);
	}

	private boolean sameDay(int monthDay) {
		Calendar cal = Calendar.getInstance();
		cal.set(mYear, mMonth, monthDay);
		return CalendarUtils.isSameDay(cal, today);
	}

	private boolean prevDay(int monthDay) {
		Calendar cal = Calendar.getInstance();
		cal.set(mYear, mMonth, monthDay);
		return prevDay(cal);
	}

    private boolean prevDay(Calendar cal) {
        return ((cal.get(Calendar.YEAR) < today.get(Calendar.YEAR))) ||
				(cal.get(Calendar.YEAR) == today.get(Calendar.YEAR) && cal.get(Calendar.DAY_OF_YEAR) < today.get(Calendar.DAY_OF_YEAR));
    }

    protected void drawMonthNums(Canvas canvas)
    {
        int y = (mRowHeight + mAttributes.dayTextSize) / 2 - DAY_SEPARATOR_WIDTH + mAttributes.monthHeaderHeight;
        int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
        int dayOffset = findDayOffset();
        int day = 1;

        while (day <= mNumCells)
        {
            int x = paddingDay * (1 + dayOffset * 2) + mPadding;

            boolean isFirst = (mMonth == mSelectedBeginMonth && mSelectedBeginDay == day && mSelectedBeginYear == mYear);
            boolean isLast = (mMonth == mSelectedLastMonth && mSelectedLastDay == day && mSelectedLastYear == mYear);

            // Draw Circle/Rect background for selected days
            if (isFirst || isLast)
            {
                // Now draw selected day rect or circle
                if (mAttributes.drawRect)
                {
                    RectF rectF = new RectF(x - mAttributes.selectedDayRadius, (y - mAttributes.dayTextSize / 3) - mAttributes.selectedDayRadius, x + mAttributes.selectedDayRadius, (y - mAttributes.dayTextSize / 3) + mAttributes.selectedDayRadius);
                    canvas.drawRoundRect(rectF, 10.0f, 10.0f, mSelectedCirclePaint);
                }
                else
                {
                    canvas.drawCircle(x, y - mAttributes.dayTextSize / 3, mAttributes.selectedDayRadius, mSelectedCirclePaint);
                }
            }

            // Make today BOLD, previous days italic, otherwise normal
            if (mHasToday && (mToday == day))
            {
                mMonthNumPaint.setColor(mAttributes.currentDayTextColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            else if (!mAttributes.isPrevDayEnabled && prevDay(day))
            {
                mMonthNumPaint.setColor(mAttributes.previousDayColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            }
            else
            {
                mMonthNumPaint.setColor(mAttributes.dayNumColor);
                mMonthNumPaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }

            // Set color for day
            if (isFirst || isLast)
            {
                if (isFirst && isLast)
                {
                    // Same Begin and End day
                    mMonthNumPaint.setColor(mAttributes.selectedDayBgColor);
                }
                else
                {
                    mMonthNumPaint.setColor(mAttributes.selectedDayTextColor);
                }
            }
            else if (mSelectedBeginDay != -1 && mSelectedLastDay != -1)
            {
				boolean isSelectedDay = false;

                // There is a selected range
                if (mSelectedBeginYear == mSelectedLastYear && mYear == mSelectedBeginYear)
                {
					if (mMonth == mSelectedBeginMonth && mSelectedBeginMonth == mSelectedLastMonth)
					{
						if (day > mSelectedBeginDay && day < mSelectedLastDay)
						{
							// Same month, days between begin day and end day
							isSelectedDay = true;
						}
					}
					else if (mMonth > mSelectedBeginMonth && mMonth < mSelectedLastMonth)
					{
						// All days for months between begin month and end month
						isSelectedDay = true;
					}
                    else if ((mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) ||
                             (mMonth == mSelectedLastMonth && day < mSelectedLastDay))
                    {
                        // Days of month between begin day and end day
						isSelectedDay = true;
					}
                }
				else if (mSelectedBeginYear != mSelectedLastYear)
				{
					if ((mYear == mSelectedBeginYear && mMonth > mSelectedBeginMonth) ||
						(mYear == mSelectedLastYear && mMonth < mSelectedLastMonth))
					{
						// All days for months between begin month and end month
						isSelectedDay = true;
					}
					else if ((mYear == mSelectedBeginYear && mMonth == mSelectedBeginMonth && day > mSelectedBeginDay) ||
						     (mYear == mSelectedLastYear && mMonth == mSelectedLastMonth && day < mSelectedLastDay))
					{
						// Days of month between begin day and end day
						isSelectedDay = true;
					}
				}

				if (isSelectedDay)
				{
					mMonthNumPaint.setColor(mAttributes.selectedDayBgColor);
				}
            }

            canvas.drawText(String.format("%d", day), x, y, mMonthNumPaint);

            dayOffset++;
            if (dayOffset == mNumDays)
            {
                dayOffset = 0;
                y += mRowHeight;
            }
            day++;
        }
    }

	public SimpleMonthAdapter.CalendarDay getDayFromLocation(float x, float y) {
		int padding = mPadding;
		if ((x < padding) || (x > mWidth - mPadding)) {
			return null;
		}

		int yDay = (int) (y - mAttributes.monthHeaderHeight) / mRowHeight;
		int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

        if (mMonth > 11 || mMonth < 0 || CalendarUtils.getDaysInMonth(mMonth, mYear) < day || day < 1)
            return null;

		return new SimpleMonthAdapter.CalendarDay(mYear, mMonth, day);
	}

	protected void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(mAttributes.monthLabelTextSize);
        mMonthTitlePaint.setTypeface(Typeface.create(mMonthTitleTypeface, Typeface.BOLD));
        mMonthTitlePaint.setColor(mAttributes.monthTextColor);
        mMonthTitlePaint.setTextAlign(Align.CENTER);
        mMonthTitlePaint.setStyle(Style.FILL);

        mMonthTitleBGPaint = new Paint();
        mMonthTitleBGPaint.setFakeBoldText(true);
        mMonthTitleBGPaint.setAntiAlias(true);
        mMonthTitleBGPaint.setColor(mAttributes.selectedDayTextColor);
        mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        mMonthTitleBGPaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(mAttributes.selectedDayBgColor);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);
        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(mAttributes.dayNameTextSize);
        mMonthDayLabelPaint.setColor(mAttributes.dayTextColor);
        mMonthDayLabelPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(mAttributes.dayTextSize);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);
	}

	protected void onDraw(Canvas canvas) {
		drawMonthTitle(canvas);
		drawMonthDayLabels(canvas);
		drawMonthNums(canvas);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + mAttributes.monthHeaderHeight);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
	}

	public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            SimpleMonthAdapter.CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
	}

	public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
		requestLayout();
	}

	public void setMonthParams(HashMap<String, Integer> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
		setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
			mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
				mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_DAY)) {
            mSelectedBeginDay = params.get(VIEW_PARAMS_SELECTED_BEGIN_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_DAY)) {
            mSelectedLastDay = params.get(VIEW_PARAMS_SELECTED_LAST_DAY);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_MONTH)) {
            mSelectedBeginMonth = params.get(VIEW_PARAMS_SELECTED_BEGIN_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_MONTH)) {
            mSelectedLastMonth = params.get(VIEW_PARAMS_SELECTED_LAST_MONTH);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_BEGIN_YEAR)) {
            mSelectedBeginYear = params.get(VIEW_PARAMS_SELECTED_BEGIN_YEAR);
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_LAST_YEAR)) {
            mSelectedLastYear = params.get(VIEW_PARAMS_SELECTED_LAST_YEAR);
        }

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        mHasToday = false;
        mToday = -1;

		mCalendar.set(Calendar.MONTH, mMonth);
		mCalendar.set(Calendar.YEAR, mYear);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = CalendarUtils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day)) {
                mHasToday = true;
                mToday = day;
            }

            mIsPrev = prevDay(day);
        }

        mNumRows = calculateNumRows();
	}

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
		mOnDayClickListener = onDayClickListener;
	}

	public interface OnDayClickListener {
		void onDayClick(SimpleMonthView simpleMonthView, SimpleMonthAdapter.CalendarDay calendarDay);
	}
}