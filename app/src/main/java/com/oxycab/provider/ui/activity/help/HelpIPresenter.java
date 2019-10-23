package com.oxycab.provider.ui.activity.help;


import com.oxycab.provider.base.MvpPresenter;

public interface HelpIPresenter<V extends HelpIView> extends MvpPresenter<V> {
    void getHelp();
}
