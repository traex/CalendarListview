package com.andexert.calendarlistview.library;

import com.andexert.calendarlistview.library.SimpleMonthAdapter.SelectedDays;

import java.util.Calendar;

public class DatePickerControllerAdapter implements DatePickerController
{
	@Override
	public int getMaxYear()
	{
		return Calendar.getInstance().get(Calendar.YEAR) + 1;
	}

	@Override
	public void onDayOfMonthSelected(int year, int month, int day) {}

	@Override
	public void onDateRangeSelected(SelectedDays selectedDays) {}
}
