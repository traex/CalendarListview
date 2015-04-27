CalendarListview
================

![CalendarListview](https://github.com/traex/CalendarListview/blob/master/header.png)

CalendarListview provides a easy way to select dates with a calendar for API 10+. [You can find a sample](https://github.com/traex/CalendarListview/blob/master/sample/) that show how to add DatePickerView to your layout without customization. 

![CalendarListview GIF](https://github.com/traex/CalendarListview/blob/master/demo.gif)
 
### Integration
The lib is available on Maven Central, you can find it with [Gradle, please](http://gradleplease.appspot.com/#calendarlistview)
``` xml

dependencies {
    compile 'com.github.traex.calendarlistview:library:1.2.3'
}

```
 
### Usage
 
Declare a DayPickerView inside your layout XML file:
 
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

CalendarListview is fully customizable:

* app:colorCurrentDay [color def:#ff999999] --> The current day is always in bold but you can change its color
* app:colorSelectedDayBackground [color def:#E75F49] --> If you click on a day, a circle indicator or a rouded rectangle indicator will be draw.
* app:colorSelectedDayText [color def:#fff2f2f2] --> This is the text color of a selected day
* app:colorPreviousDay [color def:#ff999999] --> In the current month you can choose to have a specific color for the past days
* app:colorNormalDay [color def:#ff999999] --> Default text color for a day
* app:colorMonthName [color def:#ff999999] --> Month name and year text color
* app:colorDayName [color def:#ff999999] --> Day name text color
* app:textSizeDay [dimension def:16sp] --> Font size for numeric day
* app:textSizeMonth [dimension def:16sp] --> Font size for month name
* app:textSizeDayName [dimension def:10sp] --> Font size for day name
* app:headerMonthHeight [dimension def:50dip] --> Height of month name
* app:drawRoundRect [boolean def:false] --> Draw a rounded rectangle for selected days instead of a circle
* app:selectedDayRadius [dimension def:16dip] --> Set radius if you use default circle indicator
* app:calendarHeight [dimension def:270dip] --> Height of each month/row
* app:enablePreviousDay [boolean def:true] --> Enable past days in current month
* app:currentDaySelected [boolean def:false] --> Select current day by default
* app:firstMonth [enum def:-1] --> Start listview at the specified month
* app:lastMonth [enum def:-1] --> End listview at the specified month

### Contact

You can reach me at [+RobinChutaux](https://plus.google.com/+RobinChutaux) or for technical support feel free to open an issue [here](https://github.com/traex/CalendarListview/issues) :)

### Acknowledgements

Thanks to [Flavien Laurent](https://github.com/flavienlaurent) for his [DateTimePicker](https://github.com/flavienlaurent/datetimepicker).

### MIT License

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
