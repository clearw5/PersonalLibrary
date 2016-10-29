package com.stardust.view;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.stardust.calculator.R;
import com.unkown.widgets.DividerItemDecoration;
import com.unkown.widgets.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/7/16.
 */
public class SettingView extends RecyclerView {

    private static final int VIEW_TYPE_SWITCH = 1;
    private static final int VIEW_TYPE_TEXT = 2;
    private static final boolean isAndroidL = Build.VERSION.SDK_INT > 21;

    List<String> titles = new ArrayList<>();
    Map<Integer, Boolean> switchCheckedMap = new TreeMap<>();
    List<Integer> viewTypes = new ArrayList<>();
    boolean notCallCheckChangedListener = false;

    private OnItemClickListener mOnItemClickListener;
    private OnItemCheckedChangeListenerInner mOnItemCheckedChangeListenerInner;

    public SettingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new SettingViewAdapter());
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        setItemAnimator(new DefaultItemAnimator());
    }

    public SettingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        mOnItemClickListener = onClickListener;
        addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new onItemClickListenerInner(onClickListener)));
        mOnItemCheckedChangeListenerInner = new OnItemCheckedChangeListenerInner(onClickListener);
    }

    public void addItem(String title) {
        titles.add(title);
        viewTypes.add(VIEW_TYPE_TEXT);
        getAdapter().notifyDataSetChanged();
    }


    public void addItems(String[] titles) {
        for (String title : titles) {
            this.titles.add(title);
            viewTypes.add(VIEW_TYPE_TEXT);
        }
        getAdapter().notifyDataSetChanged();

    }

    public void addSwitch(String title, boolean checked) {
        int position = viewTypes.size();
        titles.add(title);
        viewTypes.add(VIEW_TYPE_SWITCH);
        switchCheckedMap.put(position, checked);
        getAdapter().notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemCheckedChanged(View view, int position, boolean isChecked);
    }

    private class OnItemCheckedChangeListenerInner implements CompoundButton.OnCheckedChangeListener {
        private OnItemClickListener mOnItemClickListener;

        public OnItemCheckedChangeListenerInner(OnItemClickListener listener) {
            mOnItemClickListener = listener;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!notCallCheckChangedListener) {
                switchCheckedMap.put((Integer) buttonView.getTag(), isChecked);
                mOnItemClickListener.onItemCheckedChanged(buttonView, (Integer) buttonView.getTag(), isChecked);
            }
        }
    }

    private static class onItemClickListenerInner implements RecyclerItemClickListener.OnItemClickListener {
        private OnItemClickListener mOnItemClickListener;

        public onItemClickListenerInner(OnItemClickListener listener) {
            mOnItemClickListener = listener;
        }

        @Override
        public void onItemClick(View view, int position) {
            mOnItemClickListener.onItemClick(view, position);
        }
    }

    private class SettingViewAdapter extends Adapter {

        private final int mBackground;
        private final TypedValue mTypedValue = new TypedValue();

        public SettingViewAdapter() {
            getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case VIEW_TYPE_SWITCH: {
                    //if (isAndroidL) {
                        view = LayoutInflater.from(getContext()).inflate(R.layout.setting_view_item_switch, parent, false);
                    //} else {
                        view = LayoutInflater.from(getContext()).inflate(R.layout.setting_view_item_switch_button, parent, false);
                    //}
                    view.setBackgroundResource(mBackground);
                    return new SwitchViewHolder(view);
                }
                case VIEW_TYPE_TEXT: {
                    view = LayoutInflater.from(getContext()).inflate(R.layout.select_text_list_item_view, parent, false);
                    view.setBackgroundResource(mBackground);
                    return new TextViewHolder(view);
                }
            }
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof TextViewHolder) {
                ((TextViewHolder) holder).textView.setText(titles.get(position));
                return;
            }
            if (holder instanceof SwitchViewHolder) {
                SwitchViewHolder switchViewHolder = (SwitchViewHolder) holder;
                switchViewHolder.mCompoundButton.setTag(position);
                setCheckNotCallListener(switchViewHolder.mCompoundButton, switchCheckedMap.get(position));
                switchViewHolder.mCompoundButton.setOnCheckedChangeListener(new OnItemCheckedChangeListenerInner(mOnItemClickListener));
                switchViewHolder.textView.setText(titles.get(position));
            }
        }

        private void setCheckNotCallListener(CompoundButton button, boolean isCheck) {
            notCallCheckChangedListener = true;
            if (button instanceof SwitchButton)
                ((SwitchButton) button).setCheckedImmediatelyNoEvent(isCheck);
            else
                button.setChecked(isCheck);
            notCallCheckChangedListener = false;
        }

        @Override
        public int getItemCount() {
            return viewTypes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return viewTypes.get(position);
        }

        private class SwitchViewHolder extends ViewHolder {
            public TextView textView;
            public CompoundButton mCompoundButton;

            public SwitchViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.headline);
                mCompoundButton = (CompoundButton) itemView.findViewById(R.id.switch_button);
                mCompoundButton.setOnCheckedChangeListener(mOnItemCheckedChangeListenerInner);
            }
        }


    }

    private class TextViewHolder extends ViewHolder {
        public TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
