CalendarListview
================

![CalendarListview](https://github.com/traex/CalendarListview/blob/master/header.png)

CalendarListview provides a easy way to select dates with a calendar.
 
---
 
### Usage
 
 To use it, you just have to put a `DayPickerView` in your layout
 
``` xml

  <com.andexert.calendarlistview.library.DayPickerView
         android:id="@+id/pickerView"
         xmlns:calendar="http://schemas.android.com/apk/res-auto"
         android:layout_width="match_parent"
         android:layout_height="match_parent"/>
         
```

Next, you have to implement `DatePickerController` in your Activity or your Fragment. You will have to set `getMaxYear` and `onDayOfMonthSelected`. The first one is the max year between the current one and this maxYear. The second one is called every time user selects a new date.

``` java

    @Override
    public int getMaxYear()
    {
        return 2015;
    }
    
    @Override
    public void onDayOfMonthSelected(int year, int month, int day)
    {
        Log.e("Day Slected", day + " / " + month + " / " + year);
    }
    
```