package com.andexert.calendarlistview.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.Calendar;

public class DatePickerAttributes
{
	protected static final int MONTHS_IN_YEAR = 12;

	final int firstMonth;
	final int lastMonth;

	final boolean currentDaySelected;
	final boolean allowSingleDay;
	final boolean drawRect;
	final boolean isPrevDayEnabled;

	final int currentDayTextColor;
	final int monthTextColor;
	final int dayTextColor;
	final int dayNumColor;
	final int selectedDayTextColor;
	final int previousDayColor;
	final int selectedDayBgColor;

	final int dayTextSize;
	final int dayNameTextSize;
	final int monthHeaderHeight;
	final int monthLabelTextSize;
	final int selectedDayRadius;
	final int rowHeight;

	DatePickerAttributes(Context context, TypedArray typedArray)
	{
		Calendar calendar = Calendar.getInstance();
		Resources resources = context.getResources();

		firstMonth = typedArray.getInt(R.styleable.DayPickerView_firstMonth, calendar.get(Calendar.MONTH));
		lastMonth = typedArray.getInt(R.styleable.DayPickerView_lastMonth, (calendar.get(Calendar.MONTH) - 1) % MONTHS_IN_YEAR);

		currentDaySelected = typedArray.getBoolean(R.styleable.DayPickerView_currentDaySelected, false);
		allowSingleDay = typedArray.getBoolean(R.styleable.DayPickerView_allowSingleDay, true);
		isPrevDayEnabled = typedArray.getBoolean(R.styleable.DayPickerView_enablePreviousDay, true);
		drawRect = typedArray.getBoolean(R.styleable.DayPickerView_drawRoundRect, false);

		currentDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorCurrentDay, resources.getColor(R.color.normal_day));
		monthTextColor = typedArray.getColor(R.styleable.DayPickerView_colorMonthName, resources.getColor(R.color.normal_day));
		dayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorDayName, resources.getColor(R.color.normal_day));
		dayNumColor = typedArray.getColor(R.styleable.DayPickerView_colorNormalDay, resources.getColor(R.color.normal_day));
		previousDayColor = typedArray.getColor(R.styleable.DayPickerView_colorPreviousDay, resources.getColor(R.color.normal_day));
		selectedDayBgColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayBackground, resources.getColor(R.color.selected_day_background));
		selectedDayTextColor = typedArray.getColor(R.styleable.DayPickerView_colorSelectedDayText, resources.getColor(R.color.selected_day_text));

		dayTextSize = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDay, resources.getDimensionPixelSize(R.dimen.text_size_day));
		monthLabelTextSize = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeMonth, resources.getDimensionPixelSize(R.dimen.text_size_month));
		dayNameTextSize = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_textSizeDayName, resources.getDimensionPixelSize(R.dimen.text_size_day_name));
		monthHeaderHeight = typedArray.getDimensionPixelOffset(R.styleable.DayPickerView_headerMonthHeight, resources.getDimensionPixelOffset(R.dimen.header_month_height));
		selectedDayRadius = typedArray.getDimensionPixelSize(R.styleable.DayPickerView_selectedDayRadius, resources.getDimensionPixelOffset(R.dimen.selected_day_radius));

		rowHeight = ((typedArray.getDimensionPixelSize(R.styleable.DayPickerView_calendarHeight, resources.getDimensionPixelOffset(R.dimen.calendar_height)) - monthHeaderHeight) / 6);
	}
}
