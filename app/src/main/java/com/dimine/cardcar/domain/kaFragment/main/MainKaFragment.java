package com.dimine.cardcar.domain.kaFragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.domain.mainactivity.MainActivity;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.utils.PlayVoiceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 11:28
 * desc   :
 * version: 1.0
 */
public class MainKaFragment extends Fragment implements KaMainContract.View {

    @BindView(R.id.tvYield)
    TextView tvYield;

    @BindView(R.id.tvCommand)
    TextView tvCommand;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvRunEmpty)
    TextView tvRunEmpty;
    @BindView(R.id.tvHeavyTransport)
    TextView tvHeavyTransport;
    @BindView(R.id.ivMode)
    ImageView ivMode;
    @BindView(R.id.tvMode)
    TextView tvMode;
    private KaMainContract.Presenter mPresenter;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_layout, container, false);
        unbinder = ButterKnife.bind(this, root);
        new MainKaPresenter(new MainKaModel(), this);

        MyLog.d("life_cycle", "MainKaFragment ====> onCreateView");
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showToastMessage(String message) {
        MyToast.showShort(getContext(), message);
    }

    @Override
    public Context viewContext() {
        return getContext();
    }

    @Override
    public void showNightModeView(boolean isNight) {
        if (!isNight) {
            tvMode.setText(getContext().getString(R.string.settings_night_mode));
        } else {
            tvMode.setText(getContext().getString(R.string.settings_day_mode));
        }
    }

    @Override
    public void voice(String message) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showVoice(message);
        }
    }

    @Override
    public void showCurrentSchedule(String schedule) {
        tvCommand.setText(schedule);
        voice(PlayVoiceUtil.playCount(schedule));
    }


    @Override
    public void showMessage(String message) {
        tvMessage.setText(message);
        voice(PlayVoiceUtil.playCount(message));
    }

    @Override
    public void showCompletionTotal(String total) {
        if (tvYield != null) {
            tvYield.setText(total);
        }
    }

    @Override
    public void showEmptyRunTime(String time) {
        if (tvRunEmpty != null) {
            tvRunEmpty.setText(time);
        }
    }

    @Override
    public void showHeavyRunTime(String time) {
        if (tvHeavyTransport != null) {
            tvHeavyTransport.setText(time);
        }
    }

    @Override
    public void setPresenter(KaMainContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MyLog.d("life_cycle", "MainKaFragment ====> onDestroyView");
    }

    @OnClick(R.id.nightModeLayout)
    public void onViewNightModeLayoutClicked() {
        boolean current = mPresenter.loadNightMode();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showNightModel(!current);
        }
    }


    public void onCarStatus(CarStatusBean truck) {
        mPresenter.loadServerTruck(truck);
    }

    public void onTruckScheduling(SchedulingBean schedulingBean) {
        MyLog.d("onTruckScheduling", schedulingBean.toString());
        mPresenter.loadCurrentSchedule(schedulingBean);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.commandLayout)
    public void onCommandLayoutClicked() {
        String command = tvCommand.getText().toString();
        if (!TextUtils.isEmpty(command)) {
            voice(command);
        }
    }

    @OnClick(R.id.messageLayout)
    public void onMessageLayoutClicked() {
        String message = tvMessage.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            voice(message);
        }
    }
}