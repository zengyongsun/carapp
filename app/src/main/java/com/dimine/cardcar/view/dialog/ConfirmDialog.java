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
 * date   : 2019/6/20 10:55
 * desc   :
 * version: 1.0
 */
public class ConfirmDialog extends Dialog {

    private Context mContext;
    private TextView message;


    public ConfirmDialog(@NonNull Context context, String msg) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_confirm_layout);
        setCanceledOnTouchOutside(false);
        TextView dialogTv = findViewById(R.id.dialogTv);
        dialogTv.setText(mContext.getString(R.string.hint_message));
        message = findViewById(R.id.tvMessage);
        message.setText(msg);
        findViewById(R.id.btConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialogClickListener.confirm();
                dismiss();
            }
        });
        findViewById(R.id.btCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private ConfirmDialogClickListener confirmDialogClickListener;

    public void setConfirmDialogClickListener(ConfirmDialogClickListener confirmDialogClickListener) {
        this.confirmDialogClickListener = confirmDialogClickListener;
    }

    public interface ConfirmDialogClickListener {

        void confirm();

    }
}
