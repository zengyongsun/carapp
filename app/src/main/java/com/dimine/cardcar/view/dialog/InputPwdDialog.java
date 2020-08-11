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
public class InputPwdDialog extends Dialog {


    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btCancel)
    Button btCancel;

    TextView dialogTv;


    private TextView tvDesc;

    public InputPwdDialog(Context context, String title, String desc) {
        super(context, R.style.MyDialogStyle);
        setContentView(R.layout.dialog_input_password_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        dialogTv = findViewById(R.id.dialogTv);
        tvDesc = findViewById(R.id.tvPassword);
        dialogTv.setText(title);
        tvDesc.setText(desc);
        initValue();
    }

    private void initValue() {
        if (getContext().getString(R.string.dialog_card_title).equals(dialogTv.getText().toString())) {
            etPassword.setText(LocalArguments.getInstance().getCardId());
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            MyToast.showShort(getContext(), getContext().getString(R.string.hint_unable_empty));
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
        void confirm(String password);
    }

}
