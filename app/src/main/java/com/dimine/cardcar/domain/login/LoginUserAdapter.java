package com.dimine.cardcar.domain.login;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dimine.cardcar.R;
import com.dimine.cardcar.data.bean.UserBean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/30 9:36
 * desc   :
 * version: 1.0
 */
public class LoginUserAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public LoginUserAdapter() {
        super(R.layout.adapter_list_dialog_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        helper.setText(R.id.tvNumber, item.UserId + "");
        helper.setText(R.id.tvName, item.UserName);
    }
}
