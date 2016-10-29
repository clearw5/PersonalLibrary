package com.stardust.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stardust.calculator.R;
import com.unkown.widgets.DividerItemDecoration;
import com.unkown.widgets.RecyclerItemClickListener;

import java.util.Vector;

/**
 * Created by Stardust on 2016/5/21.
 * <p/>
 * 这是一个只有文本选择的RecyclerView，并且带按钮波纹
 */
public class SelectTextListView extends RecyclerView {

    private Vector<String> options = new Vector<String>();
    private Activity mActivity;

    public SelectTextListView(Context context) {
        super(context);
        mActivity = (Activity) context;
        initialize();
    }

    public SelectTextListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mActivity = (Activity) context;
        initialize();
    }

    /**
     * 设置选项和Listener，这将覆盖之前的选项
     * 在Listener的OnClick方法中通过view.getId()获取是第几个选项
     *
     * @param options
     * @param onClickListener
     */
    public void setOptions(String[] options, RecyclerItemClickListener.OnItemClickListener onClickListener) {
        setOptions(options);
        addOnItemClickListener(onClickListener);
    }

    /**
     * 设置选项，这将覆盖之前的选项
     *
     * @param options
     */
    public void setOptions(String[] options) {
        this.options.clear();
        for (int i = 0; i < options.length; i++) {
            this.options.addElement(options[i]);
        }
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 增加选项
     *
     * @param options
     */
    public void addOptions(String[] options) {
        for (int i = 0; i < options.length; i++) {
            this.options.addElement(options[i]);
        }
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 增加选项
     *
     * @param option
     */
    public void addOption(String option) {
        options.addElement(option);
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 移除选项
     *
     * @param location
     */
    public void removeOption(int location) {
        options.remove(location);
        getAdapter().notifyDataSetChanged();
    }


    /**
     * 设置Listener
     * 在Listener的OnClick方法中通过view.getId()获取是第几个选项
     *
     * @param onClickListener
     */
    public void addOnItemClickListener(RecyclerItemClickListener.OnItemClickListener onClickListener) {
        addOnItemTouchListener(new RecyclerItemClickListener(mActivity, onClickListener));
    }

    private void initialize() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new SelectListAdapter());
        addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        setItemAnimator(new DefaultItemAnimator());
    }

    private class SelectListAdapter extends RecyclerView.Adapter {

        private final int mBackground;
        private final TypedValue mTypedValue = new TypedValue();

        public SelectListAdapter() {
            mActivity.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.select_text_list_item_view, parent, false);
            view.setBackgroundResource(mBackground);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.textView.setText(options.elementAt(position));
        }

        @Override
        public int getItemCount() {
            return options.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }
}
