package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.DataJudgment;
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
public class InputCarIdDialog extends Dialog {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.tvPassword)
    TextView tvPassword;
    TextView dialogTv;

    private Context mContext;

    public static final int PASSWORD_TYPE = 0;
    public static final int TITLE_TYPE = 0;
    public static final int REMARK_TYPE = 0;

    public int type = PASSWORD_TYPE;

    public InputCarIdDialog(Context context, int type) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_input_password_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        dialogTv = findViewById(R.id.dialogTv);
        dialogTv.setText(mContext.getString(R.string.dialog_high_setting_title));
        this.type = type;
        initValue();
    }

    private void initValue() {
        etPassword.setText(LocalArguments.getInstance().deviceNumber());
        etPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setInputName(String str) {
        tvPassword.setText(str);
    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String password = etPassword.getText().toString();
        if (type == PASSWORD_TYPE) {
            String result = DataJudgment.judgeDeviceData(password,mContext);
            if ("".equals(result)) {
                listener.confirm(password);
            } else {
                MyToast.showShort(mContext, result);
            }
        }

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
