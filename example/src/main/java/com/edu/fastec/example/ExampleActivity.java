package com.edu.fastec.example;

import com.edu.latte.activitys.ProxyActivity;
import com.edu.latte.delegates.LatteDelegate;
import com.edu.latte.ec.launcher.LauncherDelegate;

public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }


}
