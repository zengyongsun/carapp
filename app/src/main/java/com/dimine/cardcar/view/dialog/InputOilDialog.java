package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dimine.cardcar.R;
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
public class InputOilDialog extends Dialog {


    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btCancel)
    Button btCancel;

    @BindView(R.id.tvPassword)
    TextView tvPassword;

    TextView dialogTv;

    private Context mContext;

    public InputOilDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_input_oil_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        dialogTv = findViewById(R.id.dialogTv);
        dialogTv.setText("加油量");
    }

    public void setinputName(String str) {
        tvPassword.setText(str);
    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            MyToast.showShort(mContext, "不能为空");
            return;
        }
        listener.confirm(password);
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }

    public OilDialogClickListener listener;

    public void setListener(OilDialogClickListener listener) {
        this.listener = listener;
    }

    public interface OilDialogClickListener {
        void confirm(String oil);
    }

}
