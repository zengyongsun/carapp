package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.utils.PingUtil;

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
public class ServiceIpDialog extends Dialog {


    @BindView(R.id.btCancel)
    Button btCancel;

    TextView dialogTv;
    @BindView(R.id.etIpOne)
    EditText etIpOne;
    @BindView(R.id.etIpTwo)
    EditText etIpTwo;
    @BindView(R.id.etIpThree)
    EditText etIpThree;
    @BindView(R.id.etIpFour)
    EditText etIpFour;
    @BindView(R.id.etPort)
    EditText etPort;
    private Context mContext;


    public ServiceIpDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_input_service_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        initValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyCompat();
    }

    private void applyCompat() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initValue() {
        String ip = LocalArguments.getInstance().serviceIp();
        String[] ipSplit = ip.split("\\.");
        etIpOne.setText(ipSplit[0]);
        etIpTwo.setText(ipSplit[1]);
        etIpThree.setText(ipSplit[2]);
        etIpFour.setText(ipSplit[3]);
        etPort.setText(LocalArguments.getInstance().servicePort());
    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        String ip = getIp();
        String port = etPort.getText().toString();
        //判断字段合法性
        if (!PingUtil.isIp(ip)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_input_ip));
            return;
        }
        if (TextUtils.isEmpty(port)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_port_not_empty));
            return;
        }
        listener.confirm(modifyService(ip, port));
    }

    private boolean modifyService(String ip, String port) {
        LocalArguments.getInstance().modifyServiceIp(ip);
        LocalArguments.getInstance().modifyServicePort(port);
        return true;
    }

    private String getIp() {
        return etIpOne.getText().toString() + "." + etIpTwo.getText().toString() + "." +
                etIpThree.getText().toString() + "." + etIpFour.getText().toString();
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }

    public ServiceIpListener listener;

    public void setListener(ServiceIpListener listener) {
        this.listener = listener;
    }

    public interface ServiceIpListener {
        void confirm(boolean isOk);
    }

}
