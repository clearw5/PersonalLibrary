package com.stardust.widgets;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.stardust.calculator.R;
import com.stardust.calculator.ui.MainFragment;
import com.stardust.theme.ThemeColorMaterialDialog;
import com.stardust.tool.SharedPreferencesTool;
import com.stardust.tool.ViewTool;

/**
 * 一个简易的对话框辅助类，可以提供信息对话框，编辑框对话框简易的对话框
 */
public class DialogueBuilder {

    private static final String NO_LONGER_PROMPT_PREFERENCE_NAME = "no_longer_prompt";
    private static AlertDialog dialogue = null;
    private static LinearLayout infoView, editView, editLineView;
    private static TextView titleText;
    private static TextView infoText;
    private static TextView editInfoText;
    private static EditText editText;
    private static Button ok;
    private static LinearLayout buttonLine;

    /**
     * 显示一个信息对话框，提供确定和取消按钮
     *
     * @param context
     * @param text    信息内容
     * @param ocl     点击确定的监听者
     */
    public static void showInfoDialogue(Context context, String text,
                                        OnClickListener ocl) {
        getInfoTextView(context).setText(text);
        setOKButtonOnClickListener(context, ocl);
        getAlertDialog(context).setView(getInfoView(context), 0, 0, 0, 0);
        if (!getInfoView(context).isShown())
            getAlertDialog(context).show();

    }

    /**
     * 显示一个编辑框对话框，提供确定和取消按钮
     *
     * @param context
     * @param info        提醒信息
     * @param initialText 初始内容
     * @param oel         编辑完成时的监听者（也即按下确定键时）
     */
    public static void showEditTextDialogue(Context context, String info,
                                            String initialText, final onEditCompletedListener oel) {
        getEditInfoTextView(context).setText(info);
        getEditText(context).setText(initialText);
        setOKButtonOnClickListener(context, new OnClickListener() {

            @Override
            public void onClick(View v) {
                oel.onEditCompleted(editText.getEditableText().toString());
                dialogue.dismiss();
            }
        });
        getAlertDialog(context).setView(getEditView(context), 0, 0, 0, 0);
        //if (!getInfoView(context).isShown())
        getAlertDialog(context).show();
    }

    public static void showImageDialogue(Context context, ImageView imageView) {
        getAlertDialog(context).setView(imageView, 0, 0, 0, 0);
        getAlertDialog(context).show();
    }

    public static void showNoLongerPromptDialog(final Context c, final String title, String content, final String key) {
        if (!SharedPreferencesTool.readBoolean(c, NO_LONGER_PROMPT_PREFERENCE_NAME, key, false))
            new ThemeColorMaterialDialog.Builder(c)
                    .title(title)
                    .content(content)
                    .positiveText("确定")
                    .positiveColor(MainFragment.getInstance().getThemeColor())
                    .neutralText("不再提示")
                    .onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            SharedPreferencesTool.writeBoolean(c, NO_LONGER_PROMPT_PREFERENCE_NAME, key, true);
                        }
                    }).show();
    }

    /**
     * 编辑完成的监听者
     */
    public interface onEditCompletedListener {
        /**
         * @param text 编辑框内容
         */
        void onEditCompleted(String text);
    }

    private static View getEditView(Context context) {
        if (editView == null) {
            editView = new LinearLayout(context);
            editView.setOrientation(LinearLayout.VERTICAL);
            editView.setBackgroundColor(MainFragment.getInstance()
                    .getThemeColor());
            editView.addView(getTitleTextView(context, "编辑"));
            editView.addView(getEditText(context));
            editView.addView(getButtonLineLayout(context));
        }
        return editView;
    }


    private static View getEditLinetView(Context context) {
        if (editLineView == null) {
            editLineView = new LinearLayout(context);
            editLineView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            editView.setOrientation(LinearLayout.HORIZONTAL);
            editLineView.setBackgroundColor(Color.WHITE);
            //editLineView.addView(getEditInfoTextView(context));
            //editLineView.addView(getEditText(context));
        }
        return editLineView;
    }

    private static EditText getEditText(Context context) {
        if (editText == null) {
            editText = new EditText(context);
            //	LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            //		0, LayoutParams.WRAP_CONTENT, 1.0f);
            //editText.setLayoutParams(layoutParams);
            editText.setBackgroundColor(Color.WHITE);
        }
        return editText;
    }

    private static TextView getEditInfoTextView(Context context) {
        if (editInfoText == null) {
            editInfoText = new TextView(context);
            editInfoText.setPadding(ViewTool.dip2px(20), 0, 0, 0);
        }
        return editInfoText;
    }


    private static AlertDialog getAlertDialog(Context context) {
        if (dialogue == null) {
            Builder builder = new Builder(context);
            dialogue = builder.create();
        }
        return dialogue;
    }

    private static void setOKButtonOnClickListener(Context context,
                                                   OnClickListener ocl) {
        if (ok == null) {
            getButtonLineLayout(context);
        }
        ok.setOnClickListener(ocl);
    }

    private static View getInfoView(Context context) {
        if (infoView == null) {
            infoView = new LinearLayout(context);
            infoView.setOrientation(LinearLayout.VERTICAL);
            infoView.setBackgroundColor(MainFragment.getInstance()
                    .getThemeColor());
            infoView.addView(getTitleTextView(context, "提示"));
            infoView.addView(getInfoTextView(context));
            infoView.addView(getButtonLineLayout(context));
        }
        return infoView;
    }

    private static TextView getTitleTextView(Context context, String titleString) {
        if (titleText == null) {
            titleText = new TextView(context);
            titleText.setGravity(Gravity.CENTER_HORIZONTAL);
            titleText.setTextSize(ViewTool.dip2px(15));
            titleText.setTextColor(Color.WHITE);
        }
        titleText.setText(titleString);
        return titleText;
    }

    private static TextView getInfoTextView(Context context) {
        if (infoText == null) {
            infoText = new TextView(context);
            infoText.setGravity(Gravity.CENTER);
            infoText.setTextSize(ViewTool.dip2px(10));
            infoText.setBackgroundColor(Color.WHITE);
            infoText.setPadding(0, ViewTool.dip2px(20), 0,
                    ViewTool.dip2px(20));
        }
        return infoText;
    }

    public static void dismiss() {
        dialogue.dismiss();
    }

    private static LinearLayout getButtonLineLayout(Context context) {
        if (buttonLine != null)
            return buttonLine;
        buttonLine = new LinearLayout(context);
        buttonLine.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, LayoutParams.WRAP_CONTENT, 1.0f);
        ok = new Button(context);
        ok.setText("确定");
        ok.setBackgroundResource(R.drawable.btn_selector_general);
        ok.setLayoutParams(layoutParams);
        ok.setTextColor(Color.WHITE);
        buttonLine.addView(ok);
        Button cancel = new Button(context);
        cancel.setText("取消");
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogue.dismiss();
            }

        });
        cancel.setBackgroundColor(Color.GRAY);
        cancel.setLayoutParams(layoutParams);
        buttonLine.addView(cancel);
        return buttonLine;
    }

}
