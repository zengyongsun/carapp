package com.dimine.cardcar.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;

import com.dimine.cardcar.R;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/20 10:55
 * desc   :
 * version: 1.0
 */
public class SettingModifyDialog extends Dialog {

    private Context mContext;


    public SettingModifyDialog(@NonNull final Context context) {
        super(context, R.style.MyDialogStyle);
        mContext = context;
        setContentView(R.layout.dialog_setting_modify_layout);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        findViewById(R.id.localLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            }
        });

        findViewById(R.id.wlanLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            }
        });

        findViewById(R.id.showLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_DISPLAY_SETTINGS);
            }
        });

         findViewById(R.id.soundLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_SOUND_SETTINGS);

            }
        });

        findViewById(R.id.wifiLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_SETTINGS);

            }
        });

        findViewById(R.id.dateLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goIntent(Settings.ACTION_DATE_SETTINGS);

            }
        });
    }

    private void goIntent(String action) {
        Intent it = new Intent(action);
        it.putExtra("extra_prefs_show_button_bar", true);
        it.putExtra("extra_prefs_set_next_text", "");
        it.putExtra("extra_prefs_set_back_text", "返回");
        mContext.startActivity(it);
        dismiss();
    }


}
