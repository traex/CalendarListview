CalendarListview
================

![CalendarListview](https://github.com/traex/CalendarListview/blob/master/header.png)

CalendarListview provides a easy way to select dates with a calendar. There is a sample that show how to add DatePickerView to your layout without customization

![CalendarListview GIF](https://github.com/traex/CalendarListview/blob/master/demo.gif)
 
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
        Log.e("Day Selected", day + " / " + month + " / " + year);
    }
    
```

---

### Customization

You can use it with default parameters or your can change text color and font size.

``` xml

    <declare-styleable name="DayPickerView">
        <attr name="colorCurrentDay" format="color"/>
        <attr name="colorSelectedDayBackground" format="color"/>
        <attr name="colorSelectedDayText" format="color"/>
        <attr name="colorPreviousDay" format="color"/>
        <attr name="colorNormalDay" format="color" />
        <attr name="colorMonthName" format="color" />
        <attr name="colorDayName" format="color" />
        <attr name="maxYear" format="integer" />
        <attr name="textSizeDay" format="dimension"/>
        <attr name="textSizeMonth" format="dimension" />
        <attr name="textSizeDayName" format="dimension" />
        <attr name="headerMonthHeight" format="dimension" />
        <attr name="selectedDayRadius" format="dimension" />
        <attr name="calendar_height" format="dimension" />
    </declare-styleable>

```

### License

```
    The MIT License (MIT)
    
    Copyright (c) 2014 Robin Chutaux
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
```