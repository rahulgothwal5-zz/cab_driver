package com.oxycab.provider.ui.activity.summary;

import android.animation.ValueAnimator;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxycab.provider.MvpApplication;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.Utilities;
import com.oxycab.provider.data.network.model.Summary;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends BaseActivity implements SummaryIView {


    @BindView(R.id.card_layout)
    LinearLayout cardLayout;
    @BindView(R.id.rides_value)
    TextView ridesValue;
    @BindView(R.id.rides_card)
    CardView ridesCard;
    @BindView(R.id.revenue_value)
    TextView revenueValue;
    @BindView(R.id.revenue_card)
    CardView revenueCard;
    @BindView(R.id.scheduled_value)
    TextView scheduledValue;
    @BindView(R.id.scheduled_card)
    CardView scheduledCard;
    @BindView(R.id.canceled_value)
    TextView canceledValue;
    @BindView(R.id.canceled_card)
    CardView canceledCard;
    SummaryPresenter<SummaryActivity> presenter = new SummaryPresenter<>();
    NumberFormat numberFormat;
    @Override
    public int getLayoutId() {
        return R.layout.activity_summary;
    }

    @Override
    public void initView() {

        ButterKnife.bind(this);
        numberFormat = MvpApplication.getNumberFormat();
        presenter.attachView(this);
        presenter.getSummary();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSuccess(Summary summary) {
        Utilities.printV("onSuccess==>", summary.getCancelRides() + "");
        Animation slideUp = AnimationUtils.loadAnimation(activity(), R.anim.slide_up_slow);
        cardLayout.startAnimation(slideUp);
        cardLayout.setVisibility(View.VISIBLE);

        slideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animateTextView(0, summary.getRides(), ridesValue);
                animateTextView(0, summary.getCancelRides(), canceledValue);
                animateTextView(0, summary.getScheduledRides(), scheduledValue);
                animateTextViewFloat(0.0f, convertToFloat(summary.getRevenue()), revenueValue);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    public static Float convertToFloat(double doubleValue) {
        return (float) doubleValue;
    }

    @Override
    public void onError(Throwable e) {
        Utilities.printV("onError==>", e.toString());
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (textview.getId() == R.id.revenue_value)
                    textview.setText(numberFormat.getCurrency().getCurrencyCode() + valueAnimator.getAnimatedValue().toString());
                else
                    textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }


    public void animateTextViewFloat(float initialValue, float finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (textview.getId() == R.id.revenue_value)
                    textview.setText(numberFormat.getCurrency().getCurrencyCode() + valueAnimator.getAnimatedValue().toString());
                else
                    textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }

}
