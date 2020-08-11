package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.dimine.cardcar.R;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/1 10:16
 * desc   :
 * version: 1.0
 */
public class ShowMessageDialog extends Dialog {

    private Context mContext;
    private TextView mTitle;
    private TextView mShowContent;

    public ShowMessageDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_show_message_layout);
        //设置外部触摸时是否消失
        setCanceledOnTouchOutside(false);
        initViews();
    }

    private void initViews() {
        mTitle = findViewById(R.id.tvTitle);
        mShowContent = findViewById(R.id.tvShowMessage);
        findViewById(R.id.btDefine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageDialogListener != null) {
                    messageDialogListener.yes();
                    dismiss();
                }
            }
        });
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageDialogListener != null) {
                    messageDialogListener.no();
                    dismiss();
                }
            }
        });
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setShowContent(String message) {
        mShowContent.setText(message);
    }

    private ShowMessageDialogListener messageDialogListener;

    public void setMessageDialogListener(ShowMessageDialogListener messageDialogListener) {
        this.messageDialogListener = messageDialogListener;
    }

    public interface ShowMessageDialogListener {
        void yes();

        void no();
    }

}
