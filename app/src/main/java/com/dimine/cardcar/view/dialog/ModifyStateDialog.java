package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyToast;

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
public class ModifyStateDialog extends Dialog {

    @BindView(R.id.btCancel)
    Button btCancel;
    @BindView(R.id.etRam)
    EditText etRam;
    @BindView(R.id.etRom)
    EditText etRom;


    private Context mContext;

    public ModifyStateDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_modify_state_layout);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);

    }


    @OnClick(R.id.btConfirm)
    public void onBtConfirmClicked() {
        //数据判断
        String ram = etRam.getText().toString();
        String rom = etRom.getText().toString();
        if ("".equals(ram)) {
            MyToast.showShort(mContext, "运行内存不能为空");
            return;
        }

        if ("".equals(rom)) {
            MyToast.showShort(mContext, "存储空间不能为空");
            return;
        }

        LocalArguments.getInstance().saveRam(ram);
        LocalArguments.getInstance().saveRom(rom);
        dismiss();
    }

    @OnClick(R.id.btCancel)
    public void onBtCancelClicked() {
        dismiss();
    }


}
