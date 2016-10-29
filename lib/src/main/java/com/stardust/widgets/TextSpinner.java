package com.stardust.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.stardust.lib.R;
import com.stardust.tool.ViewTool;

/**
 * 一个比自带的Spinner稍微漂亮一点的Spinner，只支持文本
 */
public class TextSpinner extends LinearLayout implements OnClickListener {

    private String[] optionTexts;
    //弹出一个选择列表的窗口
    private PopupWindow selectListWindow;
    private ListView selectView;
    //弹出的选择列表背景色
    private int selectListBgColor;
    //当前选择选项显示的背景色
    private int bgColor;
    private int textColor;
    //当前被选中的显示出来的TextView
    private TextView currentTextView;
    private ItemOnClickListener onClickListener;
    private onItemSelectedListener mItemSelectedListener = null;

    public TextSpinner(Context context) {
        super(context);
        initializeDefaultColor();
    }

    public TextSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initializeDefaultColor();
    }

    /**
     * @param context
     * @param optionTexts 选项的文本
     */
    public TextSpinner(Context context, String[] optionTexts) {
        super(context);
        initializeDefaultColor();
        setOptionTexts(optionTexts);
    }

    /**
     * @param context
     * @param optionTexts       选项的文本
     * @param bgColor           当前选择选项显示的背景色
     * @param textColor         文本颜色
     * @param selectListBgColor 弹出的选择列表背景色
     */
    public TextSpinner(Context context, String[] optionTexts, int bgColor,
                       int textColor, int selectListBgColor) {
        super(context);
        this.bgColor = bgColor;
        this.selectListBgColor = selectListBgColor;
        this.textColor = textColor;
        setOptionTexts(optionTexts);
    }

    private void initialize() {

        super.setBackgroundColor(bgColor);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.btn_selector_general);

        onClickListener = new ItemOnClickListener();

        currentTextView = (TextView) View.inflate(getContext(),
                R.layout.degree_measure_select_item, null);
        // currentTextView.setBackgroundResource(R.drawable.btn_selector_general);
        currentTextView.setTextColor(Color.WHITE);
        currentTextView.setText(optionTexts[0]);
        addView(currentTextView);

        //文本旁边的小箭头，表示这里有选项
        ImageView tinyDownArrow = createTinyArrow(getContext(), R.drawable.down_arrow_white);
        addView(tinyDownArrow);

        setOnClickListener(this);
        initializeListView();
    }

    private void initializeListView() {
        selectView = new ListView(getContext());
        selectView.setBackgroundColor(selectListBgColor);
        selectView.setAdapter(new SelectListAdapter());
        selectView.setDividerHeight(0);
    }

    private void initializePopupWindow() {
        selectListWindow = new PopupWindow(this.getWidth()
                + ViewTool.dip2px(20), LayoutParams.WRAP_CONTENT);
        selectListWindow.setFocusable(true);
        selectListWindow.setTouchable(true);
        selectListWindow.setOutsideTouchable(true);
        selectListWindow.setBackgroundDrawable(getBackground());
        selectListWindow.setContentView(selectView);
    }

    private void initializeDefaultColor() {
        //默认颜色为主题色
        this.bgColor = ViewTool.getColor(getContext(), R.attr.colorPrimary);
        this.selectListBgColor = bgColor;
        this.textColor = Color.WHITE;
    }

    public void setOptionTexts(String[] optionTexts) {
        this.optionTexts = optionTexts;
        initialize();
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        selectView.setBackgroundColor(color);
        invalidate();
    }

    public void setOnItemSelectedListener(onItemSelectedListener onItemSelectedListener) {
        mItemSelectedListener = onItemSelectedListener;
    }

    public void setSelectedItemNoEvent(int position) {
        currentTextView.setText(optionTexts[position]);
    }

    private void setSelectedItem(int position) {
        currentTextView.setText(position);
        if (mItemSelectedListener != null) {
            mItemSelectedListener.onItemSelected(position);
        }
    }

    @Override
    public void onClick(View v) {
        if (selectListWindow == null) {
            initializePopupWindow();
        }
        selectListWindow.showAsDropDown(this);
    }

    private class SelectListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return optionTexts.length;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertvView, ViewGroup parent) {
            TextView textView;
            if (convertvView != null) {
                textView = (TextView) convertvView;
            } else {
                textView = (TextView) View.inflate(getContext(),
                        R.layout.degree_measure_select_item, null);
                textView.setBackgroundColor(selectListBgColor);
                textView.setTextColor(textColor);
                textView.setBackgroundResource(R.drawable.btn_selector_general);
                textView.setOnClickListener(onClickListener);
            }

            textView.setText(optionTexts[position]);
            textView.setId(position);
            textView.setPadding(0, ViewTool.dip2px(10), 0,
                    ViewTool.dip2px(10));
            return textView;
        }
    }

    private class ItemOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            currentTextView.setText(textView.getText());
            selectListWindow.dismiss();
            if (mItemSelectedListener != null) {
                mItemSelectedListener.onItemSelected(v.getId());
            }
        }

    }

    public interface onItemSelectedListener {
        void onItemSelected(int index);
    }

    public static ImageView createTinyArrow(Context context, int resId) {
        ImageView tinyDownArrow = new ImageView(context);
        tinyDownArrow.setScaleType(ScaleType.FIT_XY);
        LayoutParams layoutParams = new LayoutParams(ViewTool.dip2px(10),
                ViewTool.dip2px(10));
        tinyDownArrow.setLayoutParams(layoutParams);
        tinyDownArrow.setBackgroundResource(resId);
        tinyDownArrow.setPadding(ViewTool.dip2px(5), 0, 0, 0);
        return tinyDownArrow;
    }

}
