package com.andexert.calendarlistview.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

public class DayPickerView extends ListView implements AbsListView.OnScrollListener
{
    protected Context mContext;
	protected SimpleMonthAdapter mAdapter;
	private DatePickerController mController;
    protected int mCurrentScrollState = 0;
	private boolean mPerformingScroll;
	protected long mPreviousScrollPosition;
	protected int mPreviousScrollState = 0;
    protected float mFriction = 1.0F;
    private final AttributeSet attrs;

    public DayPickerView(Context context)
    {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public DayPickerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setDrawSelectorOnTop(false);
        init(context);
    }

    public void setmController(DatePickerController mController)
    {
        this.mController = mController;
        setUpAdapter();
        setAdapter(mAdapter);
    }


	public void init(Context paramContext) {
		mContext = paramContext;
		setUpListView();
	}

	protected void layoutChildren() {
		super.layoutChildren();
		if (mPerformingScroll) {
			mPerformingScroll = false;
		}
	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        SimpleMonthView child = (SimpleMonthView) view.getChildAt(0);
        if (child == null) {
            return;
        }

        long currScroll = view.getFirstVisiblePosition() * child.getHeight() - child.getBottom();
        mPreviousScrollPosition = currScroll;
        mPreviousScrollState = mCurrentScrollState;
	}

	public void onScrollStateChanged(AbsListView absListView, int scroll) {
	}

	protected void setUpAdapter() {
		if (mAdapter == null) {
			mAdapter = new SimpleMonthAdapter(getContext(), mController, attrs);
        }
		mAdapter.notifyDataSetChanged();
	}

	protected void setUpListView() {
		setCacheColorHint(0);
		setDivider(null);
		setItemsCanFocus(true);
		setFastScrollEnabled(false);
		setVerticalScrollBarEnabled(false);
		setOnScrollListener(this);
		setFadingEdgeLength(0);
		setFrictionIfSupported(ViewConfiguration.getScrollFriction() * mFriction);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void setFrictionIfSupported(float friction) {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setFriction(friction);
		}
	}

    public SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> getSelectedDays()
    {
        return mAdapter.getSelectedDays();
    }
}