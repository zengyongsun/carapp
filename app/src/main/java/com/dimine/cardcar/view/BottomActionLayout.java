package com.dimine.cardcar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/20 8:49
 * desc   :
 * version: 1.0
 */
public class BottomActionLayout extends LinearLayout {

    public static final String TAG = BottomActionLayout.class.getSimpleName();

    @BindView(R.id.ivOne)
    ImageView ivOne;
    @BindView(R.id.tvOne)
    TextView tvOne;
    @BindView(R.id.ivTwo)
    ImageView ivTwo;
    @BindView(R.id.tvTwo)
    TextView tvTwo;
    @BindView(R.id.ivThree)
    ImageView ivThree;
    @BindView(R.id.tvThree)
    TextView tvThree;
    @BindView(R.id.ivFour)
    ImageView ivFour;
    @BindView(R.id.tvFour)
    TextView tvFour;
    @BindView(R.id.ivFive)
    ImageView ivFive;
    @BindView(R.id.tvFive)
    TextView tvFive;
    @BindView(R.id.layoutOne)
    LinearLayout layoutOne;
    @BindView(R.id.layoutTwo)
    LinearLayout layoutTwo;
    @BindView(R.id.layoutThree)
    LinearLayout layoutThree;
    @BindView(R.id.layoutFour)
    LinearLayout layoutFour;
    @BindView(R.id.layoutFive)
    LinearLayout layoutFive;
    @BindView(R.id.lineOne)
    ImageView lineOne;

    public BottomActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.bottom_action_layout, this);
        ButterKnife.bind(this, view);
        if (LocalArguments.getInstance().carTypeId() == CarType.Forklift.getTypeId()) {
            layoutTwo.setVisibility(GONE);
            lineOne.setVisibility(GONE);
        }
    }

    @OnClick(R.id.layoutOne)
    public void onLayoutOneClicked() {
        MyLog.d(TAG, "onLayoutOneClicked: 调用了");
        setClickStyle(BottomType.One, true);
    }

    @OnClick(R.id.layoutTwo)
    public void onLayoutTwoClicked() {
        MyLog.d(TAG, "onLayoutTwoClicked: 调用了");
        setClickStyle(BottomType.Two, true);
    }

    @OnClick(R.id.layoutThree)
    public void onLayoutThreeClicked() {
        MyLog.d(TAG, "onLayoutThreeClicked: 调用了");
        setClickStyle(BottomType.Three, true);
    }

    @OnClick(R.id.layoutFour)
    public void onLayoutFourClicked() {
        MyLog.d(TAG, "onLayoutFourClicked: 调用了");
        setClickStyle(BottomType.Four, true);
    }

    @OnClick(R.id.layoutFive)
    public void onLayoutFiveClicked() {
        MyLog.d(TAG, "onLayoutFiveClicked: 调用了");
        setClickStyle(BottomType.Five, true);
    }

    /**
     * 设置点击时间的颜色
     *
     * @param bottomType
     * @param isClick    是否触发点击回调
     */
    private void setClickStyle(BottomType bottomType, boolean isClick) {
        switch (bottomType) {
            case One:
                tvOne.setTextColor(getResources().getColor(R.color.menu_text_select_color));
                tvTwo.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvThree.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFour.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFive.setTextColor(getResources().getColor(R.color.menu_text_color));

                layoutOne.setBackgroundResource(R.mipmap.icon_menu_select_bg);
                layoutTwo.setBackgroundResource(0);
                layoutThree.setBackgroundResource(0);
                layoutFour.setBackgroundResource(0);
                layoutFive.setBackgroundResource(0);

                ivOne.setImageResource(R.mipmap.icon_status_select);
                ivTwo.setImageResource(R.mipmap.icon_map);
                ivThree.setImageResource(R.mipmap.icon_guzhang);
                ivFour.setImageResource(R.mipmap.icon_statistics);
                ivFive.setImageResource(R.mipmap.icon_settings);
                if (isClick) {
                    itemClickListener.onClick(BottomType.One);
                }
                break;
            case Two:
                tvOne.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvTwo.setTextColor(getResources().getColor(R.color.menu_text_select_color));
                tvThree.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFour.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFive.setTextColor(getResources().getColor(R.color.menu_text_color));

                layoutOne.setBackgroundResource(0);
                layoutTwo.setBackgroundResource(R.mipmap.icon_menu_select_bg);
                layoutThree.setBackgroundResource(0);
                layoutFour.setBackgroundResource(0);
                layoutFive.setBackgroundResource(0);


                ivOne.setImageResource(R.mipmap.icon_status);
                ivTwo.setImageResource(R.mipmap.icon_map_select);
                ivThree.setImageResource(R.mipmap.icon_guzhang);
                ivFour.setImageResource(R.mipmap.icon_statistics);
                ivFive.setImageResource(R.mipmap.icon_settings);
                if (isClick) {
                    itemClickListener.onClick(BottomType.Two);
                }
                break;
            case Three:
                tvOne.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvTwo.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvThree.setTextColor(getResources().getColor(R.color.menu_text_select_color));
                tvFour.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFive.setTextColor(getResources().getColor(R.color.menu_text_color));

                layoutOne.setBackgroundResource(0);
                layoutTwo.setBackgroundResource(0);
                layoutThree.setBackgroundResource(R.mipmap.icon_menu_select_bg);
                layoutFour.setBackgroundResource(0);
                layoutFive.setBackgroundResource(0);

                ivOne.setImageResource(R.mipmap.icon_status);
                ivTwo.setImageResource(R.mipmap.icon_map);
                ivThree.setImageResource(R.mipmap.icon_guzhang_select);
                ivFour.setImageResource(R.mipmap.icon_statistics);
                ivFive.setImageResource(R.mipmap.icon_settings);
                if (isClick) {
                    itemClickListener.onClick(BottomType.Three);
                }
                break;
            case Four:
                tvOne.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvTwo.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvThree.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFour.setTextColor(getResources().getColor(R.color.menu_text_select_color));
                tvFive.setTextColor(getResources().getColor(R.color.menu_text_color));

                layoutOne.setBackgroundResource(0);
                layoutTwo.setBackgroundResource(0);
                layoutThree.setBackgroundResource(0);
                layoutFour.setBackgroundResource(R.mipmap.icon_menu_select_bg);
                layoutFive.setBackgroundResource(0);

                ivOne.setImageResource(R.mipmap.icon_status);
                ivTwo.setImageResource(R.mipmap.icon_map);
                ivThree.setImageResource(R.mipmap.icon_guzhang);
                ivFour.setImageResource(R.mipmap.icon_statistics_select);
                ivFive.setImageResource(R.mipmap.icon_settings);
                if (isClick) {
                    itemClickListener.onClick(BottomType.Four);
                }
                break;
            case Five:
                tvOne.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvTwo.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvThree.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFour.setTextColor(getResources().getColor(R.color.menu_text_color));
                tvFive.setTextColor(getResources().getColor(R.color.menu_text_select_color));

                layoutOne.setBackgroundResource(0);
                layoutTwo.setBackgroundResource(0);
                layoutThree.setBackgroundResource(0);
                layoutFour.setBackgroundResource(0);
                layoutFive.setBackgroundResource(R.mipmap.icon_menu_select_bg);

                ivOne.setImageResource(R.mipmap.icon_status);
                ivTwo.setImageResource(R.mipmap.icon_map);
                ivThree.setImageResource(R.mipmap.icon_guzhang);
                ivFour.setImageResource(R.mipmap.icon_statistics);
                ivFive.setImageResource(R.mipmap.icon_settings_select);
                if (isClick) {
                    itemClickListener.onClick(BottomType.Five);
                }
                break;
            default:
                break;
        }
    }

    public void setSelectButton(BottomType type) {
        setClickStyle(type, false);
    }

    public BottomActionClickListener itemClickListener;

    public void setItemClickListener(BottomActionClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface BottomActionClickListener {
        void onClick(BottomType bottomType);
    }

}
