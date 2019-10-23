package com.oxycab.provider.ui.bottomsheetdialog.invoice_show;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oxycab.provider.MvpApplication;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseBottomSheetDialogFragment;
import com.oxycab.provider.data.network.model.HistoryDetail;
import com.oxycab.provider.data.network.model.Payment;
import com.oxycab.provider.data.network.model.RentalHourPackage;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.oxycab.provider.base.BaseActivity.DATUM_history_detail;


public class InvoiceShowDialogFragment extends BaseBottomSheetDialogFragment implements InvoiceShowDialogIView {

    @BindView(R.id.btnClose)
    Button btnClose;

    @BindView(R.id.booking_id)
    TextView bookingId;
    @BindView(R.id.total_amount)
    TextView totalAmount;
    @BindView(R.id.payable_amount)
    TextView payableAmount;
    @BindView(R.id.total_distance)
    TextView totalDistance;
    @BindView(R.id.travel_time)
    TextView travelTime;
    @BindView(R.id.fixed)
    TextView fixed;
    @BindView(R.id.distance_fare)
    TextView distanceFare;
    @BindView(R.id.peek_hour_charges)
    TextView peekHourCharges;
    @BindView(R.id.night_fare)
    TextView nightFare;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.discount_layout)
    LinearLayout discountLayout;
    @BindView(R.id.tax2)
    TextView tax2;
    @BindView(R.id.commission)
    TextView commission;
    @BindView(R.id.tds)
    TextView tds;
    @BindView(R.id.provider_earnings)
    TextView providerEarnings;

    @BindView(R.id.layout_normal_flow)
    LinearLayout layout_normal_flow;
    @BindView(R.id.layout_rental_flow)
    LinearLayout layout_rental_flow;
    @BindView(R.id.rental_normal_price)
    TextView rentalNormalPrice;
    @BindView(R.id.rental_total_distance_km)
    TextView rentalTotalDistance;
    @BindView(R.id.rental_extra_hr_km_price)
    TextView rentalExtraHrKmPrice;
    @BindView(R.id.rental_travel_time)
    TextView rentalTravelTime;
    @BindView(R.id.rental_hours)
    TextView rentalHours;

    @BindView(R.id.layout_outstation_flow)
    LinearLayout layout_outstation_flow;
    @BindView(R.id.outstation_distance_travelled)
    TextView outstationDistanceTravelled;
    @BindView(R.id.outstation_distance_fare)
    TextView outstationDistanceFare;
    @BindView(R.id.outstation_driver_beta)
    TextView outstationDriverBeta;
    @BindView(R.id.outstation_round_single)
    TextView outstationRoundSingle;
    @BindView(R.id.outstation_no_of_days)
    TextView outstationNoOfDays;

    @BindView(R.id.start_date)
    TextView startDate;
    @BindView(R.id.end_date)
    TextView endDate;
    Unbinder unbinder;

    NumberFormat numberFormat;

    public InvoiceShowDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_invoice_show_dialog;
    }

    @Override
    public void initView(View view) {
        unbinder = ButterKnife.bind(this, view);
        numberFormat = MvpApplication.getNumberFormat();
        init();
    }

    private void init() {

        HistoryDetail datum = DATUM_history_detail;

        if (datum != null) {
            bookingId.setText(datum.getBookingId());
            startDate.setText(convertDateFormat(datum.getStartedAt()));
            endDate.setText(convertDateFormat(datum.getFinishedAt()));
            totalDistance.setText(String.valueOf(datum.getDistance() + " km"));
            travelTime.setText(getString(R.string._min, datum.getTravelTime()));

            Payment payment = DATUM_history_detail.getPayment();
            if (payment != null) {
                fixed.setText(numberFormat.format(payment.getFixed()));
                Double total = payment.getTotal() - payment.getTax();
                totalAmount.setText(numberFormat.format(total));
                peekHourCharges.setText(numberFormat.format(payment.getPeakPrice()));
                payableAmount.setText(numberFormat.format(payment.getPayable()));
                distanceFare.setText(numberFormat.format(payment.getDistance()));
                tax.setText(numberFormat.format(payment.getTax()));
                discountLayout.setVisibility(payment.getDiscount() > 0 ? View.VISIBLE : View.GONE);
                discount.setText(numberFormat.format(payment.getDiscount()));
                tax2.setText(numberFormat.format(payment.getTax()));
                nightFare.setText(numberFormat.format(payment.getNightFare()));
                commission.setText(numberFormat.format(payment.getProviderCommission()));
                providerEarnings.setText(numberFormat.format(payment.getProviderPay()));


                //rental
                RentalHourPackage rentalHourPackage = datum.getRentalPackage();
                if(rentalHourPackage != null){
                    rentalHours.setText(String.format("%s (%s Hrs)", getString(R.string.rental_normal_price), rentalHourPackage.getHour()));
                }
                rentalNormalPrice.setText(numberFormat.format(payment.getMinute()));
                rentalTravelTime.setText(getString(R.string._min, datum.getTravelTime()));
                rentalTotalDistance.setText(String.valueOf(datum.getDistance() + " km"));
                if(payment.getRentalExtraHrPrice()!=null && payment.getRentalExtraKmPrice()!=null)
                    rentalExtraHrKmPrice.setText(numberFormat.format(payment.getRentalExtraHrPrice() + payment.getRentalExtraKmPrice()));


                //outstation
                outstationDriverBeta.setText(String.format("(%s Days) %s", payment.getOutstationDays(), numberFormat.format(payment.getDriverBeta())));

                //outstationDriverBeta.setText(numberFormat.format(payment.getDriverBeta()));

                outstationDistanceFare.setText(numberFormat.format(payment.getDistance()));
                outstationDistanceTravelled.setText(String.valueOf(datum.getDistance() + " km"));
                outstationRoundSingle.setText(datum.getDay());

                if(payment.getOutstationDays()!=null)
                    outstationNoOfDays.setText(String.format("%s Days", payment.getOutstationDays()));
                else
                    outstationNoOfDays.setText("-");

                String serviceReq = datum.getServiceRequired();
                switch (serviceReq) {
                    case "none":
                        layout_normal_flow.setVisibility(View.VISIBLE);
                        break;
                    case "rental":
                        layout_rental_flow.setVisibility(View.VISIBLE);
                        break;
                    case "outstation":
                        layout_outstation_flow.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    @OnClick(R.id.btnClose)
    public void onViewClicked() {
        dismissAllowingStateLoss();
    }

    @Override
    public void onError(Throwable e) {

    }

    String convertDateFormat(String date) {
        String newDateString = null;
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            Date newDate = spf.parse(date);
            newDateString = new SimpleDateFormat("dd-MMM-yyyy hh:mm a", Locale.getDefault()).format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }
}
