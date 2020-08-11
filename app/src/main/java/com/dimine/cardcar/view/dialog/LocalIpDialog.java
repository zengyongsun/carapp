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

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.IPUtils;
import com.dimine.cardcar.utils.MyLog;
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
public class LocalIpDialog extends Dialog {

    @BindView(R.id.btCancel)
    Button btCancel;
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
    @BindView(R.id.etMaskOne)
    EditText etMaskOne;
    @BindView(R.id.etMaskTwo)
    EditText etMaskTwo;
    @BindView(R.id.etMaskThree)
    EditText etMaskThree;
    @BindView(R.id.etMaskFour)
    EditText etMaskFour;
    @BindView(R.id.etGatewayOne)
    EditText etGatewayOne;
    @BindView(R.id.etGatewayTwo)
    EditText etGatewayTwo;
    @BindView(R.id.etGatewayThree)
    EditText etGatewayThree;
    @BindView(R.id.etGatewayFour)
    EditText etGatewayFour;

    private Context mContext;

    public LocalIpDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input_loacl_ip_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);
        applyCompat();
        initValue();
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
        String ip = getLocalIp();
        String[] ipSplit = ip.split("\\.");
        if (ipSplit.length > 3) {
            etIpOne.setText(ipSplit[0]);
            etIpTwo.setText(ipSplit[1]);
            etIpThree.setText(ipSplit[2]);
            etIpFour.setText(ipSplit[3]);
        }

        etPort.setText(LocalArguments.getInstance().localPort());

        String mask = getLocalMask();

        String[] maskSplit = mask.split("\\.");
        if (maskSplit.length > 3) {
            etMaskOne.setText(maskSplit[0]);
            etMaskTwo.setText(maskSplit[1]);
            etMaskThree.setText(maskSplit[2]);
            etMaskFour.setText(maskSplit[3]);
        }
        String gateway = getLocalGateWay();
        String[] gatewaySplit = gateway.split("\\.");
        if (gateway.length() > 3) {
            etGatewayOne.setText(gatewaySplit[0]);
            etGatewayTwo.setText(gatewaySplit[1]);
            etGatewayThree.setText(gatewaySplit[2]);
            etGatewayFour.setText(gatewaySplit[3]);
        }
        etPort.setEnabled(false);
    }

    public String getLocalIp() {
        String ip = IPUtils.getLocalIpAddress();
        if (ip.isEmpty()) {
            ip = LocalArguments.getInstance().localIp();
        }
        MyLog.d("settings_ip", "getLocalIp" + ip);
        return ip;
    }


    public String getLocalGateWay() {
        String gateway = IPUtils.getGateWay();
        if ("dev".equals(gateway)) {
            gateway = LocalArguments.getInstance().localGateway();
        }
        MyLog.d("settings_ip", "getGateWay" + gateway);
        return gateway;
    }

    public String getLocalMask() {
        String mask = IPUtils.getIpAddrMaskForInterfaces("eth0");
        if ("255.0.0.0".equals(mask)) {
            mask = LocalArguments.getInstance().localMask();
        }
        MyLog.d("settings_ip", "getMask" + mask);
        return mask;
    }

    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        String ip = getIp();
        String port = etPort.getText().toString();
        String mask = getMask();
        String gateway = getGateway();

        //判断字段合法性
        if (!PingUtil.isIp(ip)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_input_ip));
            return;
        }
        if (TextUtils.isEmpty(port)) {
            MyToast.showShort(mContext, mContext.getString(R.string.hint_port_not_empty));
            return;
        }
        LocalArguments.getInstance().saveLocalIp(ip);
        LocalArguments.getInstance().saveLocalMask(mask);
        LocalArguments.getInstance().saveLocalGetWay(gateway);

        int localIp = IPUtils.ipStr2int(ip);
        int localMask = IPUtils.ipStr2int(mask);
        int localWg = IPUtils.ipStr2int(gateway);
        MyLog.d("IpModifyDialog", "onBtConfirmClicked: ip" + IPUtils.ipInt2str(localIp));
        LocalArguments.getInstance().modifyLocalPort(port);
        if (listener != null) {

        }

        dismiss();
    }

    private String getIp() {
        return etIpOne.getText().toString() + "." + etIpTwo.getText().toString() + "." +
                etIpThree.getText().toString() + "." + etIpFour.getText().toString();
    }

    private String getMask() {
        return etMaskOne.getText().toString() + "." + etMaskTwo.getText().toString() + "." +
                etMaskThree.getText().toString() + "." + etMaskFour.getText().toString();
    }

    private String getGateway() {
        return etGatewayOne.getText().toString() + "." + etGatewayTwo.getText().toString() + "." +
                etGatewayThree.getText().toString() + "." + etGatewayFour.getText().toString();
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }

    public LocalIpListener listener;

    public void setListener(LocalIpListener listener) {
        this.listener = listener;
    }

    public interface LocalIpListener {
        void confirm(boolean isOk);
    }

}
