package com.stardust.view;

import android.content.Context;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stardust on 2016/7/23.
 */
public class EnterableRecyclerView extends RecyclerView {
    List<String> items = new ArrayList<>();

    public EnterableRecyclerView(Context context) {
        super(context);
        initialize();
    }

    public EnterableRecyclerView(Context context, String[] items) {
        super(context);
        for (String item : items) {
            this.items.add(item);
        }
        initialize();
    }

    public EnterableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public EnterableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public void addOnItemClickListener(RecyclerItemClickListener.OnItemClickListener onClickListener) {
        addOnItemTouchListener(new RecyclerItemClickListener(getContext(), onClickListener));
    }


    private void initialize() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new EnterableRecyclerViewAdapter());
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        setItemAnimator(new DefaultItemAnimator());
    }

    private class EnterableRecyclerViewAdapter extends Adapter {
        private final int mBackground;
        private final TypedValue mTypedValue = new TypedValue();

        public EnterableRecyclerViewAdapter() {
            getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enterable_recycler_view_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mTextView.setText(items.get(position));
            myViewHolder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private class MyViewHolder extends ViewHolder {
            TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setBackgroundResource(mBackground);
                mTextView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }
}
