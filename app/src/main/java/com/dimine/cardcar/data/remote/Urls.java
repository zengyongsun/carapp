package com.dimine.cardcar.data.remote;

import com.dimine.cardcar.data.local.LocalArguments;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/26 10:08
 * desc   :
 * version: 1.0
 */
public interface Urls {

    String host_ip = "http://" + LocalArguments.getInstance().serviceIp() + ":"
            + LocalArguments.getInstance().servicePort();
//    String host_ip = "http://106.54.93.215";

    /**
     * http://106.54.93.215:8080/SmartMine/TerminalManage/Terminal/Login?userName=001&passWord=e10adc3949ba59abbe56e057f20f883e&terminalNo=020
     */

    String url_login = "/SmartMine/TerminalManage/Terminal/Login?";

    String url_out = "/SmartMine/TerminalManage/Terminal/Logout?";


    String url_get_truck_weight_count = "/SmartMine/TerminalManage/Terminal/GetTruckWeightCount?";

    String url_get_digger_statistics = "/SmartMine/TerminalManage/Terminal/GetDiggerWeightCount?";

    String url_get_config = "/SmartMine/TerminalManage/Terminal/GetConfig?";

    String write_log_url = "/SmartMine/TerminalManage/Terminal/WriteLog";

    String url_get_production_task = "/SmartMine/TerminalManage/Terminal/GetProductionTask?";

}
