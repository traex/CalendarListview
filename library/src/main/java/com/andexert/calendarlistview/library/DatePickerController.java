package com.andexert.calendarlistview.library;

public interface DatePickerController {
	public abstract int getMaxYear();

	public abstract int getMinYear();

	public abstract void onDayOfMonthSelected(int year, int month, int day);
}