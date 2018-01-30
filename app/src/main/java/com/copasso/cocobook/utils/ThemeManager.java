package com.copasso.cocobook.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.copasso.cocobook.R;

/**
 * Created by zhouas666 on 18-1-23. on 2017/12/27.
 */
public class ThemeManager {

    private String[] mThemes = {"胖次蓝", "酷炫黑", "原谅绿", "少女红", "基佬紫", "活力橙", "大地棕"};
    private String THEME="theme";
    private String EXTRA_IS_UPDATE_THEME  = "com.copasso.cocobook.IS_UPDATE_THEME";

    private static ThemeManager instance;

    public static ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public String[] getThemes(){
        return mThemes;
    }

    /**
     * 设置主题色
     * @param context   activity
     * @param theme     主题名称
     */
    public void setTheme(Activity context, String theme){
        String curTheme = SharedPreUtils.getInstance().getString(THEME);
        if(curTheme != null && curTheme.equals(theme)){
            return;
        }

        SharedPreUtils.getInstance().putString(THEME,theme);

        context.finish();
        Intent intent = context.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_IS_UPDATE_THEME, true);
        context.startActivity(intent);
    }

    /**
     * 获取当前主题名
     * @return 如: 少女粉
     */
    public String getCurThemeName(){
        return SharedPreUtils.getInstance().getString(THEME);
    }



    public void init(Context context) {
        String theme = SharedPreUtils.getInstance().getString(THEME);
        if(theme.equals(mThemes[0])){
            context.setTheme(R.style.AppTheme);
        }else if(theme.equals(mThemes[1])){
            context.setTheme(R.style.AppTheme_Black);
        }else if(theme.equals(mThemes[2])){
            context.setTheme(R.style.AppTheme_Green);
        }else if(theme.equals(mThemes[3])){
            context.setTheme(R.style.AppTheme_Red);
        }else if(theme.equals(mThemes[4])){
            context.setTheme(R.style.AppTheme_Purple);
        }else if(theme.equals(mThemes[5])){
            context.setTheme(R.style.AppTheme_Orange);
        }else if(theme.equals(mThemes[6])){
            context.setTheme(R.style.AppTheme_Brown);
        }
    }
}

