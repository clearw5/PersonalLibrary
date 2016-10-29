package com.stardust.tool;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.stardust.theme.ThemeColorMaterialDialog;

/**
 * Created by Stardust on 2016/7/11.
 */
public class ThreadTool {
    public static void AsyncExecute(long delay, Task task) {
        new DelayTask(delay, task).execute();
    }

    public static void executeOnProgress(Context context, final Task task, final Task finish, final Task cancel) {
        final Dialog dialog;
        if (cancel != null) {
            dialog = new ThemeColorMaterialDialog.Builder(context).progress(true, 0)
                    .content("处理中...")
                    .cancelable(true).cancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            cancel.execute();
                        }
                    }).show();
        } else {
            dialog = new ThemeColorMaterialDialog.Builder(context).progress(true, 0)
                    .content("处理中...")
                    .cancelable(false).show();
        }
        final ProgressDialogDismissHandler handler = new ProgressDialogDismissHandler(context, dialog, finish);
        new Thread(new Runnable() {
            @Override
            public void run() {
                task.execute();
                handler.dismissDialog();
            }
        }).start();
    }

    public interface Task {
        void execute();
    }

    public static void executeOnProgress(Context context, final Task task) {
        executeOnProgress(context, task, null, null);
    }

    public static void executeOnProgress(Context context, final Task task, final Task finish) {
        executeOnProgress(context, task, finish, null);
    }

    public static ProgressDialogDismissHandler executeUntil(Context context, final Task task) {
        final Dialog dialog = new ThemeColorMaterialDialog.Builder(context).progress(true, 0)
                .content("处理中...")
                .cancelable(false)
                .show();
        final ProgressDialogDismissHandler handler = new ProgressDialogDismissHandler(context, dialog, null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        }).start();
        return handler;
    }

    public static ProgressDialogDismissHandler showOnProgressDialog(Context context) {
        final Dialog dialog = new ThemeColorMaterialDialog.Builder(context).progress(true, 0)
                .content("处理中...")
                .cancelable(false)
                .show();
        final ProgressDialogDismissHandler handler = new ProgressDialogDismissHandler(context, dialog, null);
        return handler;
    }

    private static class DelayTask extends AsyncTask<Void, Void, Void> {

        private long delay;
        private Task task;

        public DelayTask(long delay, Task task) {
            this.delay = delay;
            this.task = task;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            task.execute();
        }
    }

    public static class ProgressDialogDismissHandler extends Handler {

        public static final int DISMISS = 1;
        private Dialog mDialog;
        private Task mTask;

        public ProgressDialogDismissHandler(Context context, Dialog dialog, Task finish) {
            super(context.getMainLooper());
            mDialog = dialog;
            mTask = finish;
        }

        public void dismissDialog() {
            sendEmptyMessage(DISMISS);
        }

        @Override
        public void handleMessage(Message message) {
            if (message.what == DISMISS) {
                mDialog.dismiss();
                if (mTask != null) {
                    mTask.execute();
                }
            }
        }
    }
}
