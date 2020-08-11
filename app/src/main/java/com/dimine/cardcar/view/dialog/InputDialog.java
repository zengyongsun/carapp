package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/20 9:31
 * desc   :
 * version: 1.0
 */
public class InputDialog extends Dialog {

    public static final int NUMBER_TYPE = 0;
    public static final int SIP_TYPE = 1;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.tvPassword)
    TextView tvPassword;
    TextView dialogTv;

    private Context mContext;


    public InputDialog(Context context, int type) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_input_password_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        dialogTv = findViewById(R.id.dialogTv);
        dialogTv.setText(mContext.getString(R.string.dialog_high_setting_title));
        initValue(type);
    }

    private void initValue(int type) {
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (type == NUMBER_TYPE) {
            etPassword.setText(LocalArguments.getInstance().getVoiceCount());
        }
        if (type == SIP_TYPE) {
            etPassword.setText(LocalArguments.getInstance().sipCallNumber());
        }
    }

    public void setInputName(String str) {
        tvPassword.setText(str);
    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_unable_empty));
        } else {
            listener.confirm(password);
        }
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }

    public InputDialogClickListener listener;

    public void setListener(InputDialogClickListener listener) {
        this.listener = listener;
    }

    public interface InputDialogClickListener {
        void confirm(String content);
    }

}
