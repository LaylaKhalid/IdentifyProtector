/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.identifyprotector.identifyprotector.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.identifyprotector.identifyprotector.R2;
import com.identifyprotector.identifyprotector.data.AutofillDataBuilder;
import com.identifyprotector.identifyprotector.data.DataCallback;
import com.identifyprotector.identifyprotector.data.FakeAutofillDataBuilder;
import com.identifyprotector.identifyprotector.data.source.DefaultFieldTypesSource;
import com.identifyprotector.identifyprotector.data.source.PackageVerificationDataSource;
import com.identifyprotector.identifyprotector.data.source.local.DefaultFieldTypesLocalJsonSource;
import com.identifyprotector.identifyprotector.data.source.local.LocalAutofillDataSource;
import com.identifyprotector.identifyprotector.data.source.local.SharedPrefsPackageVerificationRepository;
import com.identifyprotector.identifyprotector.data.source.local.dao.AutofillDao;
import com.identifyprotector.identifyprotector.data.source.local.db.AutofillDatabase;
import com.identifyprotector.identifyprotector.model.DatasetWithFilledAutofillFields;
import com.identifyprotector.identifyprotector.model.FieldTypeWithHeuristics;
import com.identifyprotector.identifyprotector.util.AppExecutors;
import com.identifyprotector.identifyprotector.util.Util;

import java.util.List;

import static com.identifyprotector.identifyprotector.util.Util.DalCheckRequirement.AllUrls;
import static com.identifyprotector.identifyprotector.util.Util.DalCheckRequirement.Disabled;
import static com.identifyprotector.identifyprotector.util.Util.DalCheckRequirement.LoginOnly;
import static com.identifyprotector.identifyprotector.util.Util.logd;
import static com.identifyprotector.identifyprotector.util.Util.logw;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private static final int REQUEST_CODE_SET_DEFAULT = 1;
    private AutofillManager mAutofillManager;
    private LocalAutofillDataSource mLocalAutofillDataSource;
    private PackageVerificationDataSource mPackageVerificationDataSource;
    private MyPreferences mPreferences;
    private String mPackageName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R2.layout.multidataset_service_settings_activity);
        SharedPreferences localAfDataSourceSharedPrefs =
                getSharedPreferences(LocalAutofillDataSource.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        DefaultFieldTypesSource defaultFieldTypesSource =
                DefaultFieldTypesLocalJsonSource.getInstance(getResources(),
                        new GsonBuilder().create());
        AutofillDao autofillDao = AutofillDatabase.getInstance(
                this, defaultFieldTypesSource, new AppExecutors()).autofillDao();
        mPackageName = getPackageName();
        mLocalAutofillDataSource = LocalAutofillDataSource.getInstance(localAfDataSourceSharedPrefs,
                autofillDao, new AppExecutors());
        mAutofillManager = getSystemService(AutofillManager.class);
        mPackageVerificationDataSource =
                SharedPrefsPackageVerificationRepository.getInstance(this);
        mPreferences = MyPreferences.getInstance(this);
        setupSettingsSwitch(R2.id.settings_auth_responses_container,
                R2.id.settings_auth_responses_label,
                R2.id.settings_auth_responses_switch,
                mPreferences.isResponseAuth(),
                (compoundButton, isResponseAuth) -> mPreferences.setResponseAuth(isResponseAuth));
        setupSettingsSwitch(R2.id.settings_auth_datasets_container,
                R2.id.settings_auth_datasets_label,
                R2.id.settings_auth_datasets_switch,
                mPreferences.isDatasetAuth(),
                (compoundButton, isDatasetAuth) -> mPreferences.setDatasetAuth(isDatasetAuth));
        setupSettingsButton(R2.id.settings_add_data_container,
                R2.id.settings_add_data_label,
                R2.id.settings_add_data_icon,
                (view) -> buildAddDataDialog().show());
        setupSettingsButton(R2.id.settings_clear_data_container,
                R2.id.settings_clear_data_label,
                R2.id.settings_clear_data_icon,
                (view) -> buildClearDataDialog().show());
        setupSettingsButton(R2.id.settings_auth_credentials_container,
                R2.id.settings_auth_credentials_label,
                R2.id.settings_auth_credentials_icon,
                (view) -> {
                    if (mPreferences.getMasterPassword() != null) {
                        buildCurrentCredentialsDialog().show();
                    } else {
                        buildNewCredentialsDialog().show();
                    }
                });
        setupSettingsSwitch(R2.id.settingsSetServiceContainer,
                R2.id.settingsSetServiceLabel,
                R2.id.settingsSetServiceSwitch,
                mAutofillManager.hasEnabledAutofillServices(),
                (compoundButton, serviceSet) -> setService(serviceSet));
        RadioGroup loggingLevelContainer = findViewById(R2.id.loggingLevelContainer);
        Util.LogLevel loggingLevel = mPreferences.getLoggingLevel();
        Util.setLoggingLevel(loggingLevel);
        switch (loggingLevel) {
            case Off:
                loggingLevelContainer.check(R2.id.loggingOff);
                break;
            case Debug:
                loggingLevelContainer.check(R2.id.loggingDebug);
                break;
            case Verbose:
                loggingLevelContainer.check(R2.id.loggingVerbose);
                break;
        }
        loggingLevelContainer.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R2.id.loggingOff:
                    mPreferences.setLoggingLevel(Util.LogLevel.Off);
                    break;
                case R2.id.loggingDebug:
                    mPreferences.setLoggingLevel(Util.LogLevel.Debug);
                    break;
                case R2.id.loggingVerbose:
                    mPreferences.setLoggingLevel(Util.LogLevel.Verbose);
                    break;
            }
        });
        RadioGroup dalCheckRequirementContainer = findViewById(R2.id.dalCheckRequirementContainer);
        Util.DalCheckRequirement dalCheckRequirement = mPreferences.getDalCheckRequirement();
        switch (dalCheckRequirement) {
            case Disabled:
                dalCheckRequirementContainer.check(R2.id.dalDisabled);
                break;
            case LoginOnly:
                dalCheckRequirementContainer.check(R2.id.dalLoginOnly);
                break;
            case AllUrls:
                dalCheckRequirementContainer.check(R2.id.dalAllUrls);
                break;
        }
        dalCheckRequirementContainer.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R2.id.dalDisabled:
                    mPreferences.setDalCheckRequired(Disabled);
                    break;
                case R2.id.dalLoginOnly:
                    mPreferences.setDalCheckRequired(LoginOnly);
                    break;
                case R2.id.dalAllUrls:
                    mPreferences.setDalCheckRequired(AllUrls);
                    break;
            }
        });
    }

    private AlertDialog buildClearDataDialog() {
        return new AlertDialog.Builder(SettingsActivity.this)
                .setMessage(R2.string.settings_clear_data_confirmation)
                .setTitle(R2.string.settings_clear_data_confirmation_title)
                .setNegativeButton(R2.string.settings_cancel, null)
                .setPositiveButton(R2.string.settings_ok, (dialog, which) -> {
                    mLocalAutofillDataSource.clear();
                    mPackageVerificationDataSource.clear();
                    mPreferences.clearCredentials();
                    dialog.dismiss();
                })
                .create();
    }

    private AlertDialog buildAddDataDialog() {
        NumberPicker numberOfDatasetsPicker = LayoutInflater
                .from(SettingsActivity.this)
                .inflate(R2.layout.multidataset_service_settings_add_data_dialog, null)
                .findViewById(R2.id.number_of_datasets_picker);
        numberOfDatasetsPicker.setMinValue(0);
        numberOfDatasetsPicker.setMaxValue(10);
        numberOfDatasetsPicker.setWrapSelectorWheel(false);
        return new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(R2.string.settings_add_data_title)
                .setNegativeButton(R2.string.settings_cancel, null)
                .setMessage(R2.string.settings_select_number_of_datasets)
                .setView(numberOfDatasetsPicker)
                .setPositiveButton(R2.string.settings_ok, (dialog, which) -> {
                    int numOfDatasets = numberOfDatasetsPicker.getValue();
                    mLocalAutofillDataSource.getFieldTypes(new DataCallback<List<FieldTypeWithHeuristics>>() {
                        @Override
                        public void onLoaded(List<FieldTypeWithHeuristics> fieldTypes) {
                            boolean saved = buildAndSaveMockedAutofillFieldCollections(
                                    fieldTypes, numOfDatasets);
                            dialog.dismiss();
                            if (saved) {
                                Snackbar.make(findViewById(R2.id.settings_layout),
                                        getResources().getQuantityString(
                                                R2.plurals.settings_add_data_success,
                                                numOfDatasets, numOfDatasets),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onDataNotAvailable(String msg, Object... params) {

                        }
                    });
                })
                .create();
    }

    public boolean buildAndSaveMockedAutofillFieldCollections(List<FieldTypeWithHeuristics> fieldTypes,
            int numOfDatasets) {
        if (numOfDatasets < 0 || numOfDatasets > 10) {
            logw("Number of Datasets (%d) out of range.", numOfDatasets);
        }
        for (int i = 0; i < numOfDatasets; i++) {
            int datasetNumber = mLocalAutofillDataSource.getDatasetNumber();
            AutofillDataBuilder autofillDataBuilder =
                    new FakeAutofillDataBuilder(fieldTypes, mPackageName, datasetNumber);
            List<DatasetWithFilledAutofillFields> datasetsWithFilledAutofillFields =
                    autofillDataBuilder.buildDatasetsByPartition(datasetNumber);
            // Save datasets to database.
            mLocalAutofillDataSource.saveAutofillDatasets(datasetsWithFilledAutofillFields);
        }
        return true;
    }

    private AlertDialog.Builder prepareCredentialsDialog() {
        return new AlertDialog.Builder(SettingsActivity.this)
                .setTitle(R2.string.settings_auth_change_credentials_title)
                .setNegativeButton(R2.string.settings_cancel, null);
    }

    private AlertDialog buildCurrentCredentialsDialog() {
        final EditText currentPasswordField = LayoutInflater
                .from(SettingsActivity.this)
                .inflate(R2.layout.multidataset_service_settings_authentication_dialog, null)
                .findViewById(R2.id.master_password_field);
        return prepareCredentialsDialog()
                .setMessage(R2.string.settings_auth_enter_current_password)
                .setView(currentPasswordField)
                .setPositiveButton(R2.string.settings_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password = currentPasswordField.getText().toString();
                        if (mPreferences.getMasterPassword()
                                .equals(password)) {
                            buildNewCredentialsDialog().show();
                            dialog.dismiss();
                        }
                    }
                })
                .create();
    }

    private AlertDialog buildNewCredentialsDialog() {
        final EditText newPasswordField = LayoutInflater
                .from(SettingsActivity.this)
                .inflate(R2.layout.multidataset_service_settings_authentication_dialog, null)
                .findViewById(R2.id.master_password_field);
        return prepareCredentialsDialog()
                .setMessage(R2.string.settings_auth_enter_new_password)
                .setView(newPasswordField)
                .setPositiveButton(R2.string.settings_ok, (dialog, which) -> {
                    String password = newPasswordField.getText().toString();
                    mPreferences.setMasterPassword(password);
                    dialog.dismiss();
                })
                .create();
    }

    private void setupSettingsSwitch(int containerId, int labelId, int switchId, boolean checked,
            CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        ViewGroup container = findViewById(containerId);
        String switchLabel = ((TextView) container.findViewById(labelId)).getText().toString();
        final Switch switchView = container.findViewById(switchId);
        switchView.setContentDescription(switchLabel);
        switchView.setChecked(checked);
        container.setOnClickListener((view) -> switchView.performClick());
        switchView.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void setupSettingsButton(int containerId, int labelId, int imageViewId,
            final View.OnClickListener onClickListener) {
        ViewGroup container = findViewById(containerId);
        TextView buttonLabel = container.findViewById(labelId);
        String buttonLabelText = buttonLabel.getText().toString();
        ImageView imageView = container.findViewById(imageViewId);
        imageView.setContentDescription(buttonLabelText);
        container.setOnClickListener(onClickListener);
    }

    private void setService(boolean enableService) {
        if (enableService) {
            startEnableService();
        } else {
            disableService();
        }
    }

    private void disableService() {
        if (mAutofillManager != null && mAutofillManager.hasEnabledAutofillServices()) {
            mAutofillManager.disableAutofillServices();
            Snackbar.make(findViewById(R2.id.settings_layout),
                    R2.string.settings_autofill_disabled_message, Snackbar.LENGTH_SHORT).show();
        } else {
            logd("Sample service already disabled.");
        }
    }

    private void startEnableService() {
        if (mAutofillManager != null && !mAutofillManager.hasEnabledAutofillServices()) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
            intent.setData(Uri.parse("package:com.example.android.autofill.service"));
            logd(TAG, "enableService(): intent=%s", intent);
            startActivityForResult(intent, REQUEST_CODE_SET_DEFAULT);
        } else {
            logd("Sample service already enabled.");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        logd(TAG, "onActivityResult(): req=%s", requestCode);
        switch (requestCode) {
            case REQUEST_CODE_SET_DEFAULT:
                onDefaultServiceSet(resultCode);
                break;
        }
    }

    private void onDefaultServiceSet(int resultCode) {
        logd(TAG, "resultCode=%d", resultCode);
        switch (resultCode) {
            case RESULT_OK:
                logd("Autofill service set.");
                Snackbar.make(findViewById(R2.id.settings_layout),
                        R2.string.settings_autofill_service_set, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case RESULT_CANCELED:
                logd("Autofill service not selected.");
                Snackbar.make(findViewById(R2.id.settings_layout),
                        R2.string.settings_autofill_service_not_set, Snackbar.LENGTH_SHORT)
                        .show();
                break;
        }
    }
}
