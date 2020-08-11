package com.dimine.cardcar.domain.chanFragment.mainChan;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.domain.mainactivity.MainActivity;
import com.dimine.cardcar.manager.WriteLogManager;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.utils.PlayVoiceUtil;

import java.math.BigDecimal;

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
public class MainChanFragment extends Fragment implements ChanMainContract.View {

    @BindView(R.id.tvPlanCount)
    TextView tvPlanCount;
    @BindView(R.id.tvCompleteCount)
    TextView tvCompleteCount;
    @BindView(R.id.tvCommand)
    TextView tvCommand;
    @BindView(R.id.tvMode)
    TextView tvMode;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvWorkTime)
    TextView tvWorkTime;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivAddClick)
    ImageView ivAddClick;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.ivComplete)
    ImageView ivComplete;
    @BindView(R.id.ivCompleteClick)
    ImageView ivCompleteClick;
    @BindView(R.id.tvComplete)
    TextView tvComplete;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    private ChanMainContract.Presenter mPresenter;
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_chan_layout, container, false);
        unbinder = ButterKnife.bind(this, root);
        new MainChanPresenter(new MainChanModel(), this);
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
    public void showNightModeView(boolean isNight) {
        if (!isNight) {
            tvMode.setText(getContext().getString(R.string.settings_night_mode));
        } else {
            tvMode.setText(getContext().getString(R.string.settings_day_mode));
        }
    }

    @Override
    public void showOperationView(boolean isStart) {
        if (isStart) {
            ivAdd.setImageResource(R.mipmap.icon_zhuang_start_no);
            ivAddClick.setVisibility(View.VISIBLE);
            tvAdd.setTextColor(getResources().getColor(R.color.text_color_no_click));
            ivComplete.setImageResource(R.mipmap.icon_zhuang_complete_yes);
            ivCompleteClick.setVisibility(View.GONE);
            tvComplete.setTextColor(getResources().getColor(R.color.menu_text_select_color));
        } else {
            ivAdd.setImageResource(R.mipmap.icon_zhuang_start_yes);
            ivAddClick.setVisibility(View.GONE);
            tvAdd.setTextColor(getResources().getColor(R.color.menu_text_select_color));
            ivComplete.setImageResource(R.mipmap.icon_zhuang_complete_no);
            ivCompleteClick.setVisibility(View.VISIBLE);
            tvComplete.setTextColor(getResources().getColor(R.color.text_color_no_click));
        }
    }

    @Override
    public void showWorkTime(String minuteToHour) {
        tvWorkTime.setText(minuteToHour);
    }

    @Override
    public Context viewContext() {
        return getContext();
    }

    @Override
    public void showCurrentSchedule(String schedule) {
        tvCommand.setText(schedule);
        voice(PlayVoiceUtil.playCount(schedule));
    }

    public void voice(String message) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showVoice(message);
        }
    }


    @Override
    public void showMessage(String message) {
        tvMessage.setText(message);
        voice(PlayVoiceUtil.playCount(message));
    }

    @Override
    public void showPlanTotal(String plan, String complete) {
        tvPlanCount.setText(plan);
        if (TextUtils.isEmpty(plan)) {
            return;
        }
        try {
            int max = Integer.parseInt(plan);
            int progress = Integer.parseInt(complete);
            progressBar.setMax(max);
            progressBar.setProgress(progress);
            double percentage = 0.0;
            if (max != 0) {
                percentage = progress * 1.0 / max;
                BigDecimal bg = new BigDecimal(percentage);
                percentage = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

            }
            tvProgress.setText(percentage + "%");
        } catch (Exception e) {
            WriteLogManager.getInstance().writeLog("Exception#showPlanTotal" + e.toString());
        }
    }

    @Override
    public void showCompletionTotal(String total) {
        tvCompleteCount.setText(total);
    }


    @OnClick({R.id.startAdd, R.id.addComplete, R.id.nightModeLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startAdd:
                if (!mPresenter.isStart()) {
                    mPresenter.operationChange();
                }
                break;
            case R.id.addComplete:
                if (mPresenter.isStart()) {
                    mPresenter.operationChange();
                }
                break;
            case R.id.nightModeLayout:
                boolean current = mPresenter.loadNightMode();
                if (getActivity() != null) {
                    ((MainActivity) getActivity()).showNightModel(!current);
                }
                break;
            default:
                break;

        }
    }

    @Override
    public void setPresenter(ChanMainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void onScheduling(SchedulingBean schedulingBean) {
        MyLog.d("onScheduling", schedulingBean.toString());
        mPresenter.loadCurrentSchedule(schedulingBean);
    }

    public void onCarStatus(CarStatusBean truck) {
        mPresenter.loadServerTruck(truck);
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