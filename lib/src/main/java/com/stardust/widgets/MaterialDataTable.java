package com.stardust.widgets;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stardust.lib.R;
import com.stardust.tool.ViewTool;

import java.util.List;

/**
 * Created by Stardust on 2016/10/26.
 */

// TODO support api 9
@RequiresApi(11)
public class MaterialDataTable extends RecyclerView {

    public interface SimpleDataProvider {
        String getText(int row, int column);

        int getRowCount();
    }

    public interface DataProvider {
        String getText(int row, int column);

        int getRowCount();

        boolean isSortable();

        void sort(int column, boolean isAscending);
    }

    public interface OnRowClickListener {
        void onClick(int row);
    }

    private class OnHeaderClickListener implements OnClickListener {
        int mColumn;
        boolean mIsAscending = true;
        View mUpTriangle, mDownTriangle;

        OnHeaderClickListener(int column, View upTriangle, View downTriangle) {
            mColumn = column;
            mDownTriangle = downTriangle;
            mUpTriangle = upTriangle;
        }

        @Override
        public void onClick(View v) {
            if (mClickedHeaderTriangle != null) {
                mClickedHeaderTriangle.setActivated(false);
            }
            mClickedHeaderTriangle = mIsAscending ? mDownTriangle : mUpTriangle;
            mClickedHeaderTriangle.setActivated(true);
            mDataProvider.sort(mColumn, mIsAscending);
            mIsAscending = !mIsAscending;
            getAdapter().notifyDataSetChanged();
        }
    }

    private int mColumnCount;
    private DataProvider mDataProvider;
    private OnRowClickListener mOnRowClickListener;
    private boolean mHeaderEnable = false;
    private View mClickedRowView;
    private int mClickedRow = -1;
    private View mClickedHeaderTriangle;
    private String[] mHeaders;
    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mClickedRowView != null) {
                mClickedRowView.setActivated(false);
            }
            mClickedRowView = v;
            mClickedRowView.setActivated(true);
            mClickedRow = (int) v.getTag();
            if (mOnRowClickListener != null) {
                mOnRowClickListener.onClick(mClickedRow);
            }
        }
    };


    public MaterialDataTable(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MaterialDataTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MaterialDataTable(Context context) {
        super(context);
        init();
    }

    private void init() {
        setAdapter(new Adapter());
        addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, 0x1fffffff));
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i0, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                for (int i = 0; i < mColumnCount; i++) {
                    int width = 0;
                    for (int j = 0; j < getChildCount(); j++) {
                        width = Math.max(width, ((LinearLayout) getChildAt(j)).getChildAt(i).getWidth());
                    }
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, ViewTool.dip2px(48));
                    for (int j = 0; j < getChildCount(); j++) {
                        ((LinearLayout) getChildAt(j)).getChildAt(i).setLayoutParams(lp);
                    }
                }
            }
        });
        // setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + ViewTool.dip2px(16));
    }

    public void setData(final List<List<String>> data, String[] headers, int columnCount) {
        setDataProvider(new SimpleDataProvider() {
            @Override
            public String getText(int row, int column) {
                return data.get(row).get(column);
            }

            @Override
            public int getRowCount() {
                return data.size();
            }
        }, headers, columnCount);
    }

    public void setData(List<List<String>> data, int columnCount) {
        setData(data, null, columnCount);
    }

    public void setDataProvider(DataProvider d, int columnCount) {
        setDataProvider(d, null, columnCount);
    }

    public void setDataProvider(SimpleDataProvider d, int columnCount) {
        setDataProvider(d, null, columnCount);
    }

    public void setDataProvider(final SimpleDataProvider d, String[] headers, int columnCount) {
        setDataProvider(new DataProvider() {
            @Override
            public String getText(int row, int column) {
                return d.getText(row, column);
            }

            @Override
            public int getRowCount() {
                return d.getRowCount();
            }

            @Override
            public boolean isSortable() {
                return false;
            }

            @Override
            public void sort(int column, boolean isAscending) {

            }
        }, headers, columnCount);
    }

    public void setDataProvider(DataProvider d, String[] headers, int columnCount) {
        setColumnCount(columnCount);
        mDataProvider = d;
        setHeaderEnable(headers != null);
        mHeaders = headers;
        getAdapter().notifyDataSetChanged();
    }


    public void setColumnCount(int columnCount) {
        mColumnCount = columnCount;
    }

    public void setHeaderEnable(boolean enable) {
        mHeaderEnable = enable;
    }

    public void setOnRowClickListener(OnRowClickListener listener) {
        mOnRowClickListener = listener;
    }

    private String getText(int row, int column) {
        if (row == 0 && mHeaderEnable)
            return mHeaders[column];
        return mDataProvider.getText(mHeaderEnable ? row - 1 : row, column);
    }

    private int getRowCount() {
        return mHeaderEnable ? mDataProvider.getRowCount() + 1 : mDataProvider.getRowCount();
    }

    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_ROW = 0;

    private class Adapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.material_data_table_row, parent, false);
            int itemRes = viewType == VIEW_TYPE_HEADER ? R.layout.material_data_table_header : R.layout.material_data_table_item;
            for (int i = 0; i < mColumnCount; i++) {
                linearLayout.addView(LayoutInflater.from(getContext()).inflate(itemRes, linearLayout, false));
            }
            return new MyViewHolder(linearLayout, viewType);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.itemView.setTag(position);
            if (position != 0 || !mHeaderEnable) {
                holder.itemView.setOnClickListener(mOnClickListener);
            } else {
                holder.itemView.setClickable(false);
            }
            holder.itemView.setActivated(position == mClickedRow);
            for (int i = 0; i < mColumnCount; i++) {
                onBindView(holder, holder.mTextViews[i], position, i);
            }
        }

        void onBindView(MyViewHolder holder, TextView textView, int row, int column) {
            if (row == 0 && mHeaderEnable) {
                if (mDataProvider.isSortable()) {
                    View columnView = holder.mItemView.getChildAt(column);
                    if (!columnView.isClickable()) {
                        columnView.setTag(holder);
                        columnView.setOnClickListener(new OnHeaderClickListener(column, holder.mHeaderUpTriangles[column], holder.mHeaderDownTriangles[column]));
                    }
                }
                textView.setTextColor(0x8a000000);
            } else {
                textView.setTextColor(0xde000000);
            }
            textView.setText(getText(row, column));
        }

        public int getItemViewType(int position) {
            if (mDataProvider.isSortable() && mHeaderEnable) {
                return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ROW;
            }
            return VIEW_TYPE_ROW;
        }

        @Override
        public int getItemCount() {
            return getRowCount();
        }
    }


    private class MyViewHolder extends ViewHolder {
        TextView[] mTextViews;
        View[] mHeaderDownTriangles;
        View[] mHeaderUpTriangles;
        LinearLayout mItemView;

        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            mItemView = (LinearLayout) itemView;
            mTextViews = new TextView[mColumnCount];
            if (viewType == VIEW_TYPE_HEADER) {
                mHeaderDownTriangles = new View[mColumnCount];
                mHeaderUpTriangles = new View[mColumnCount];
            }
            for (int i = 0; i < mTextViews.length; i++) {
                mTextViews[i] = (TextView) mItemView.getChildAt(i).findViewById(R.id.textView);
                if (viewType == VIEW_TYPE_HEADER) {
                    mHeaderUpTriangles[i] = mItemView.getChildAt(i).findViewById(R.id.up_triangle);
                    mHeaderDownTriangles[i] = mItemView.getChildAt(i).findViewById(R.id.down_triangle);
                }
            }
        }
    }
}