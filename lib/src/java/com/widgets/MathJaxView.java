package com.stardust.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.stardust.calculator.math.giac.GiacComputer;
import com.stardust.tool.ColorTool;
import com.stardust.tool.FileTool;
import com.stardust.tool.Logger;
import com.stardust.tool.ThreadTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * Created by Stardust on 2016/8/10.
 */
public class MathJaxView extends WebView {

    private static final String TAG = "MathJaxView";
    private String mInput;
    private String[] mResult;
    private boolean mPageFinished = false;
    private String mExpressionColor = "00f";
    private String mResultColor = "0f0";
    private String mData;
    private static final String HTML_FILE_PATH = "file:///android_asset/mathframe.html";
    private static final String CSS_FILE_PATH = FileTool.getPath() + "html/mathframe.css";

    public MathJaxView(Context context) {
        super(context);
        init();
    }

    public MathJaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MathJaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MathJaxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        try {
            InputStream is = getContext().getAssets().open("mathframe.html");
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            mData = new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setSupportMultipleWindows(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        final ThreadTool.ProgressDialogDismissHandler handler = ThreadTool.showOnProgressDialog(getContext());
        setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    String err = "error:" +
                            error.getErrorCode() + " detail:" + error.getDescription().toString() + "\n" +
                            request.getUrl().toString();
                    Logger.log(err);
                    Log.e(TAG, err);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mPageFinished = true;
                syncExpression();
                handler.dismissDialog();
            }
        });
    }


    public void setExpression(String input, String[] results) {
        if (mInput != null || mResult != null) {
            throw new IllegalStateException("");
        }
        Vector<String> vector = new Vector<>();
        mInput = input.replace("\\", "\\\\");
        for (int i = 0; i < results.length; i++) {
            if (results[i] != null) {
                results[i] = results[i].replace("\\", "\\\\");
                vector.addElement(results[i]);
            }
        }
        mResult = new String[vector.size()];
        vector.copyInto(mResult);
        syncExpression();
    }

    public void setMathExpression(String input, String[] results) {
        for (int i = 0; i < results.length; i++) {
            if (results[i] != null) {
                if (StringTool.containsChinese(results[i]))
                    results[i] = "Error: Unexpected character.";
                results[i] = GiacComputer.parseTex(results[i]);
                int j = results[i].indexOf("$$");
                if (j >= 0 && results[i].length() > 2) {
                    results[i] = "$$=" + results[i].substring(2);
                }
            }
        }
        if (!GiacComputer.isGiacPluginInstalled(getContext())) {
            setExpression("错误: 请检查SD卡读写权限/mathframe文件/giac插件", results);
        } else
            setExpression(GiacComputer.parseTex(input), results);
    }

    public void setColor(int expressionColor, int resultColor) {
        mExpressionColor = ColorTool.toNoTransparentString(expressionColor);
        mResultColor = ColorTool.toNoTransparentString(resultColor);
    }

    public void load() {
        //copyFile();
        loadUrl(HTML_FILE_PATH);
        //loadDataWithBaseURL(FileTool.getPath() + "html/", mData, "text/html", "UTF-8", null);
    }


    private synchronized void syncExpression() {
        if (mPageFinished) {
            switch (mResult.length) {
                case 3:
                    loadUrl("javascript:(function() {setExpression('" + mInput + "','" + mResult[0] + "','" + mResult[1] + "','" + mResult[2] + "')})()");
                    break;
                case 2:
                    loadUrl("javascript:(function() {setExpression('" + mInput + "','" + mResult[0] + "','" + mResult[1] + "')})()");
                    break;
                case 1:
                    loadUrl("javascript:(function() {setExpression('" + mInput + "','" + mResult[0] + "')})()");
                    break;
            }
        }
    }

    public void checkHtmlFile() {
        File file = new File(HTML_FILE_PATH);
        if (!file.exists()) {
            copyFile();
        }
        file = new File(CSS_FILE_PATH);
        if (!file.exists()) {
            copyFile();
        }
    }

    private void copyFile() {
        try {
            FileTool.checkFolder("html/");

            File file = new File(CSS_FILE_PATH);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = getContext().getAssets().open("css/mathframe_uncolour.css");
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            String text = new String(data);
            text = text.replace("$color_expression$", mExpressionColor).replace("$color_result$", mResultColor);
            fos.write(text.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
