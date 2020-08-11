package com.dimine.cardcar.domain.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.view.dialog.InputPwdDialog;
import com.dimine.cardcar.view.dialog.LocalIpDialog;
import com.dimine.cardcar.view.dialog.ServiceIpDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsContract.View {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @BindView(R.id.tvTerminalNumber)
    TextView tvTerminalNumber;
    @BindView(R.id.tvLocalIp)
    TextView tvLocalIp;
    @BindView(R.id.tvLocalPort)
    TextView tvLocalPort;
    @BindView(R.id.tvServerIp)
    TextView tvServerIp;
    @BindView(R.id.tvServerPort)
    TextView tvServerPort;
    @BindView(R.id.tvMask)
    TextView tvMask;
    @BindView(R.id.tvGetWay)
    TextView tvGetWay;
    @BindView(R.id.tvCardId)
    TextView tvCardId;

    private SettingsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting_layout);
        ButterKnife.bind(this);
        new SettingsPresenter(new SettingModel(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showToastMessage(String message) {
        MyToast.showShort(this, message);
    }


    @Override
    public void showCardId(String cardId) {
        tvCardId.setText(cardId);
    }

    @OnClick(R.id.ipLayout)
    public void onLocalIpLayoutClicked() {
        LocalIpDialog dialog = new LocalIpDialog(this);
        dialog.setListener(new LocalIpDialog.LocalIpListener() {
            @Override
            public void confirm(boolean result) {
                if (result) {
                    showToastMessage(getString(R.string.hint_modify_ok));
                } else {
                    showToastMessage(getString(R.string.hint_modify_failure));
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mPresenter.loadLocalIp();
                mPresenter.loadLocalPort();
                mPresenter.loadMask();
                mPresenter.loadGateWay();
                dialog.dismiss();
            }

        });
        dialog.show();
    }

    @OnClick(R.id.serverLayout)
    public void onServerIpLayoutClicked() {
        ServiceIpDialog dialog = new ServiceIpDialog(this);
        dialog.setListener(new ServiceIpDialog.ServiceIpListener() {
            @Override
            public void confirm(boolean result) {
                if (result) {
                    showToastMessage(getString(R.string.hint_modify_ok));
                    mPresenter.loadServiceIp();
                    mPresenter.loadServicePort();
                } else {
                    showToastMessage(getString(R.string.hint_modify_failure));
                }
                Helper.getHelper().getApp().modifyServiceIp();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void showDeviceNumber(String number) {
        tvTerminalNumber.setText(number);
    }

    @Override
    public void showLocalIp(String ip) {
        tvLocalIp.setText(ip);

    }

    @Override
    public void showLocalPort(String port) {
        tvLocalPort.setText(port);
    }

    @Override
    public void showServerIp(String ip) {
        tvServerIp.setText(ip);
    }

    @Override
    public void showServerPort(String port) {
        tvServerPort.setText(port);
    }

    @Override
    public void showMask(String mask) {
        tvMask.setText(mask);
    }

    @Override
    public void showGateWay(String gateway) {
        tvGetWay.setText(gateway);
    }


    @OnClick(R.id.tvName)
    public void onIvBackViewClicked() {
        finish();
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.btCardLayout)
    public void onCardViewClicked() {
        InputPwdDialog inputDialog = new InputPwdDialog(this,
                getString(R.string.dialog_card_title),
                getString(R.string.dialog_card_desc));

        inputDialog.setListener(new InputPwdDialog.LoginDialogClickListener() {
            @Override
            public void confirm(String cardId) {
                mPresenter.saveCardId(cardId);
                inputDialog.dismiss();
            }
        });
        inputDialog.show();
    }


}
