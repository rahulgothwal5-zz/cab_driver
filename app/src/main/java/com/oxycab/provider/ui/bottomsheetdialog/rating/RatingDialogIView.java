package com.oxycab.provider.ui.bottomsheetdialog.rating;

import com.oxycab.provider.base.MvpView;
import com.oxycab.provider.data.network.model.Rating;

public interface RatingDialogIView extends MvpView {
    void onSuccess(Rating rating);
}
