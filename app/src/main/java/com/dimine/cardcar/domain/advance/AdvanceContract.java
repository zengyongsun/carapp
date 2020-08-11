package com.dimine.cardcar.domain.advance;

import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 13:49
 * desc   :
 * version: 1.0
 */
public interface AdvanceContract {

    interface View extends BaseView<Presenter> {

        void showTitle(int title);

        void showDeviceTitle(String title);

        void showDeviceNumber(String number);

        void showDeviceId(String id);

        void showRemark(String remark);

        void showToastMessage(String message);

        void showDeviceType(int typeId);

        void showVoicePlayCount(String count);


    }

    interface Presenter extends BasePresenter {

        void loadTitle();

        void loadDeviceTitle();

        void loadDeviceType();

        void loadDeviceNumber();

        void loadDeviceId();

        void loadRemark();

        /**
         * 设备编号
         */
        void deviceIdChange(String number);

        void voicePlay(String count);

        /**
         * 终端标题
         */
        void deviceTitleChange(String title);

        /**
         * 终端备注
         */
        void deviceDesc(String desc);

        void loadVoicePlay();

        void sipAutoChange();

        boolean delData();
    }

}
