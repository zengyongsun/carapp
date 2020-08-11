package com.dimine.cardcar.domain.settings;

import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 10:54
 * desc   :
 * version: 1.0
 */
public interface SettingsContract {

    interface View extends BaseView<Presenter> {

        void showDeviceNumber(String number);

        void showLocalIp(String ip);

        void showLocalPort(String port);

        void showServerIp(String ip);

        void showServerPort(String port);

        void showMask(String mask);

        void showGateWay(String gateway);

        void showToastMessage(String message);

        void showCardId(String cardId);
    }

    interface Presenter extends BasePresenter {

        void loadLocalIp();

        void loadLocalPort();

        void loadDeviceNumber();

        void saveCardId(String cardId);

        void loadCardId();

        void loadServiceIp();

        void loadServicePort();

        void loadMask();

        void loadGateWay();

    }

}
