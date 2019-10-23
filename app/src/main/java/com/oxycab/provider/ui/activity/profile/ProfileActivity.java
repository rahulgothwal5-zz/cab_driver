package com.oxycab.provider.ui.activity.profile;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.oxycab.provider.BuildConfig;
import com.oxycab.provider.R;
import com.oxycab.provider.base.BaseActivity;
import com.oxycab.provider.common.SharedHelper;
import com.oxycab.provider.data.network.model.Fleet;
import com.oxycab.provider.data.network.model.Panic;
import com.oxycab.provider.data.network.model.Service;
import com.oxycab.provider.data.network.model.User;
import com.oxycab.provider.ui.activity.change_password.ChangePasswordActivtiy;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.oxycab.provider.common.Constants.MULTIPLE_PERMISSION;
import static com.oxycab.provider.common.Constants.RC_MULTIPLE_PERMISSION_CODE;

public class ProfileActivity extends BaseActivity implements ProfileIView, EasyPermissions.PermissionCallbacks {

    private static final String TAG = "ProfileActivity";

    @BindView(R.id.imgProfile)
    CircleImageView imgProfile;
    @BindView(R.id.txtFirstName)
    TextView txtFirstName;
    @BindView(R.id.txtLastName)
    TextView txtLastName;
    @BindView(R.id.txtPhoneNumber)
    TextView txtPhoneNumber;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtService)
    TextView txtService;
    @BindView(R.id.btnSave)
    Button btnSave;

    @BindView(R.id.lblChangePassword)
    TextView lblChangePassword;
    @BindView(R.id.fleet_layout)
    RelativeLayout fleetLayout;
    @BindView(R.id.panic)
    SwitchCompat panicBtn;

    @BindView(R.id.emergency_mobile1)
    TextView emergencyMobile1;
    @BindView(R.id.emergency_mobile2)
    TextView emergencyMobile2;
    @BindView(R.id.fleet)
    TextView fleet;
    @BindView(R.id.rlShare)
    RelativeLayout rlShare;
    @BindView(R.id.tvReferralCode)
    TextView tvReferralCode;
    @BindView(R.id.radioMale)
    RadioButton radioMale;
    @BindView(R.id.radioFeMale)
    RadioButton radioFeMale;
    @BindView(R.id.etDOB)
    TextView etDOB;

    File imgFile = null;
    ProfilePresenter<ProfileActivity> presenter = new ProfilePresenter<>();
    @BindView(R.id.ivShare)
    ImageView ivShare;
    @BindView(R.id.iv_editFname)
    ImageView ivEditFname;
    @BindView(R.id.iv_editlname)
    ImageView ivEditlname;
    @BindView(R.id.radioOthers)
    RadioButton radioOthers;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.iv_editemail)
    ImageView ivEditemail;
    @BindView(R.id.iv_editemergency)
    ImageView ivEditemergency;
    @BindView(R.id.iv_editService)
    ImageView ivEditService;
    @BindView(R.id.iv_editfleet)
    ImageView ivEditfleet;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.change_password)
    TextView changePassword;
    private String gender = "MALE";

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.getProfile();
        setTitle(R.string.profile);
        radioMale.setChecked(true);
        panicBtn.setOnCheckedChangeListener((compoundButton, isPanic) -> {
            if (isPanic)
                presenter.profilePanic(1);
            else
                presenter.profilePanic(0);
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioMale.isChecked()) {
                    gender = "MALE";
                } else if (radioFeMale.isChecked()) {
                    gender = "FEMALE";
                } else {
                    gender = "OTHERS";
                }
            }
        });

        Calendar today = Calendar.getInstance(Locale.getDefault());
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


    @OnClick({R.id.btnSave, R.id.lblChangePassword, R.id.imgProfile, R.id.rlShare, R.id.tvReferralCode, R.id.ivShare, R.id.ll_dob, R.id.iv_editFname, R.id.iv_editlname, R.id.iv_editemail, R.id.iv_editemergency, R.id.iv_editService, R.id.iv_editfleet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                profileUpdate();
                break;
            case R.id.lblChangePassword:
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivtiy.class));
                break;
            case R.id.imgProfile:
                MultiplePermissionTask();
                break;
            case R.id.ivShare:
                break;
            case R.id.rlShare:
                break;
            case R.id.tvReferralCode:
                ShareCompat.IntentBuilder.from(this)
                        .setType("text/plain")
                        .setChooserTitle(R.string.app_name)
                        .setText("Hi, \nI am using Oxy Cab. This is my referral code: " + tvReferralCode.getText().toString())
                        .startChooser();
                break;

            case R.id.ll_dob:
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
                    etDOB.setText(String.format(Locale.getDefault(), "%d-%d-%d", dayOfMonth, month + 1, year));
                }, 2005, 12, 1);
                try {
                    Date date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse("01-12-2005");
                    mDatePickerDialog.getDatePicker().setMaxDate(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mDatePickerDialog.show();
                break;

            case R.id.iv_editFname:
                showEditDialog("First name", txtFirstName);
                break;
            case R.id.iv_editlname:
                showEditDialog("Last name", txtLastName);
                break;
            case R.id.iv_editemail:
                showEditDialog("Email ", txtEmail);
                break;
            case R.id.iv_editemergency:
                showEditDialog("Emergency no.", emergencyMobile1);
                break;
            case R.id.iv_editService:
                showEditDialog("Service ", txtService);
                break;
            case R.id.iv_editfleet:
                showEditDialog("Fleet ", fleet);
                break;
        }
    }


    void profileUpdate() {

//        gender = radioMale.isChecked() ? "MALE" : "FEMALE";
        String dob = etDOB.getText().toString();

        Map<String, RequestBody> map = new HashMap<>();
        map.put("first_name", toRequestBody(txtFirstName.getText().toString()));
        map.put("last_name", toRequestBody(txtLastName.getText().toString()));
        map.put("email", toRequestBody(txtEmail.getText().toString()));
        map.put("mobile", toRequestBody(txtPhoneNumber.getText().toString()));
        map.put("dob", toRequestBody(!TextUtils.isEmpty(dob) ? dob : ""));
        map.put("gender", toRequestBody(gender));
        map.put("emergency_contact1", toRequestBody(emergencyMobile1.getText().toString()));
        map.put("emergency_contact2", toRequestBody(emergencyMobile2.getText().toString()));

        MultipartBody.Part filePart = null;
        if (imgFile != null)
            filePart = MultipartBody.Part.createFormData("avatar", imgFile.getName(), RequestBody.create(MediaType.parse("image*//*"), imgFile));

        showLoading();
        presenter.profileUpdate(map, filePart);
    }


    @Override
    public void onSuccess(User user) {
        hideLoading();

        if (user.getReferralCode() != null) {
            rlShare.setVisibility(View.GONE);
            tvReferralCode.setText(user.getReferralCode().toUpperCase());
        }
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtPhoneNumber.setText(user.getMobile());
        txtEmail.setText(user.getEmail());
        emergencyMobile1.setText(user.getEmergencyContact1());
        emergencyMobile2.setText(user.getEmergencyContact2());
        txtService.setText("");

        if (!TextUtils.isEmpty(user.getGender())) {
            if (user.getGender().equals("MALE")) {
                radioMale.setChecked(true);
            } else if (user.getGender().equals("FEMALE")) {
                radioFeMale.setChecked(true);
            } else
                radioOthers.setChecked(true);
        }
        if (!TextUtils.isEmpty(user.getDob())) {
            etDOB.setText(user.getDob());
        }

        if (user.getPanicFlag().equalsIgnoreCase("0")) {
            panicBtn.setChecked(false);
        } else {
            panicBtn.setChecked(true);
        }

        Fleet fleet = user.getFleet();
        fleetLayout.setVisibility(fleet == null ? View.GONE : View.VISIBLE);
        if (fleet != null) {
            this.fleet.setText(fleet.getCompany());
            SharedHelper.putKey(activity(), "fleet_id", String.valueOf(fleet.getId()));
            if (user.getProviderVehicle() != null) {
                SharedHelper.putKey(activity(), "fleet_vehicle_id", String.valueOf(user.getProviderVehicle().getFleetVehicleId()));
            }
        }

        for (Service service : user.getService()) {
            txtService.append(service.getServiceType().getName() + ", ");
        }
        SharedHelper.putKey(this, "invite_code", user.getReferralCode());
        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        Glide.with(activity()).load(BuildConfig.BASE_IMAGE_URL + user.getAvatar()).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(imgProfile);
    }


    @Override
    public void onSuccessUser(User user) {
        hideLoading();
        Toasty.success(this, "Profile Updated!", Toast.LENGTH_SHORT, true).show();
        SharedHelper.putKey(this, "user_avatar", BuildConfig.BASE_IMAGE_URL + user.getAvatar());
        SharedHelper.putKey(this, "user_name", user.getFirstName() + " " + user.getLastName());
        finish();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
    }

    @Override
    public void onSuccessPanic(Panic panic) {
        if (panic.getPanic().equalsIgnoreCase("0")) {
            Toasty.success(this, "Panic mode off", Toast.LENGTH_SHORT, true).show();
        } else {
            Toasty.success(this, "Panic mode on", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, data.toString());

        EasyImage.handleActivityResult(requestCode, resultCode, data, ProfileActivity.this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                try {
                    imgFile = new Compressor(activity()).compressToFile(imageFiles.get(0));
                    Glide.with(activity()).load(Uri.fromFile(imgFile)).apply(RequestOptions.placeholderOf(R.drawable.user).dontAnimate().error(R.drawable.user)).into(imgProfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    private boolean hasMultiplePermission() {
        return EasyPermissions.hasPermissions(this, MULTIPLE_PERMISSION);
    }


    @AfterPermissionGranted(RC_MULTIPLE_PERMISSION_CODE)
    void MultiplePermissionTask() {
        if (hasMultiplePermission()) {
            pickImage();
        } else {
            EasyPermissions.requestPermissions(
                    this, "Please Accept All the Permission!",
                    RC_MULTIPLE_PERMISSION_CODE,
                    MULTIPLE_PERMISSION);
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void showEditDialog(String text, TextView textView) {
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity(), R.style.CustomBottomSheetDialogTheme);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();
        TextView tvTitle = bottomSheetDialog.findViewById(R.id.tv_hint);
        EditText etInput = bottomSheetDialog.findViewById(R.id.et_input);
        TextView tvSave = bottomSheetDialog.findViewById(R.id.tv_save);

        tvTitle.setText(getResources().getString(R.string.enter_your) + " " + text);
        etInput.setText(textView.getText().toString().trim());
        tvSave.setOnClickListener(v -> {
            textView.setText(etInput.getText().toString().trim());
            bottomSheetDialog.dismiss();
        });
    }

}
