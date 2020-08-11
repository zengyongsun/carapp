package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
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
public class InputTitleDialog extends Dialog {


    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btCancel)
    Button btCancel;

    @BindView(R.id.tvPassword)
    TextView tvPassword;

    TextView dialogTv;

    private Context mContext;

    public static int desc_type = 0;
    public static int title_type = 1;

    public InputTitleDialog(Context context, int type) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_input_string_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        dialogTv = findViewById(R.id.dialogTv);
        dialogTv.setText(mContext.getString(R.string.dialog_high_setting_title));

        initValue(type);
    }

    private void initValue(int type) {
        if (type == desc_type) {
            etPassword.setText(LocalArguments.getInstance().getSettingsDesc());
        }

        if (type == title_type) {
            etPassword.setText(LocalArguments.getInstance().deviceTitle());
        }
    }

    public void setinputName(String str) {
        tvPassword.setText(str);
    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String password = etPassword.getText().toString();
        if ("".equals(password)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_unable_empty));
            return;
        }
        if (password.length() > 12) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_title_length_count));
            return;
        }

        listener.confirm(password);
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }

    public LoginDialogClickListener listener;

    public void setListener(LoginDialogClickListener listener) {
        this.listener = listener;
    }

    public interface LoginDialogClickListener {
        void confirm(String content);
    }

}
