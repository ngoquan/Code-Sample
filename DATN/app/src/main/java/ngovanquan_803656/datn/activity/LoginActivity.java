package ngovanquan_803656.datn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.activity.menu.MenuActivity;
import ngovanquan_803656.datn.asynctask.qtht.GetInformationSystemAsyncTask;
import ngovanquan_803656.datn.asynctask.qtht.GetUserByLoginID;
import ngovanquan_803656.datn.model.QLND_ett;
import ngovanquan_803656.datn.utils.ConnectionDetector;
import ngovanquan_803656.datn.utils.Constants;
import ngovanquan_803656.datn.utils.Helper;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_username, txt_password;
    private TextInputLayout input_layout_username, input_layout_password;
    private Button btn_login, btn_cancel;
    private TextView txt_tenSNV, txt_tenCCVT;
    private ScrollView scrollView;

    ConnectionDetector cd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cd = new ConnectionDetector(this);
        if (!cd.isConnectingToInternet()) {
            Helper.notificationDialog(this, "No internert connection", "You don't have internet connection", false);
            return;
        }

        addControls();

        try {
            String tenSNV = new GetInformationSystemAsyncTask(this, Constants.FUNCTION_GET_TEN_SNV).execute().get();
            txt_tenSNV.setText(tenSNV);
            String tenCCVT = new GetInformationSystemAsyncTask(this, Constants.FUNCTION_GET_TEN_CCVT).execute().get();
            txt_tenCCVT.setText(tenCCVT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        addEvents();
    }

    private void addControls() {
        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
        input_layout_username = (TextInputLayout) findViewById(R.id.input_layout_username);
        input_layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        txt_tenSNV = (TextView) findViewById(R.id.txt_tenSNV);
        txt_tenCCVT = (TextView) findViewById(R.id.txt_tenCCVT);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validateUsername() {
        if (txt_username.getText().toString().trim().isEmpty()) {
            input_layout_username.setError(getString(R.string.message_username));
            requestFocus(txt_username);
            return false;
        } else {
            input_layout_username.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (txt_password.getText().toString().trim().isEmpty()) {
            input_layout_password.setError(getString(R.string.message_password));
            requestFocus(txt_password);
            return false;
        } else {
            input_layout_password.setErrorEnabled(false);
        }
        return true;
    }

    private void getLogin() throws ExecutionException, InterruptedException {
        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();

        if (validateUsername() && validatePassword()) {
            if (username.equals(Constants.ADMIN_NAME)) {
                String pass = new GetInformationSystemAsyncTask(this, Constants.FUNCTION_GET_PASSWORD).execute().get();
                if (Helper.sha1(password).equals(pass)) {
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Mật khẩu bạn nhập KHÔNG đúng...!", Toast.LENGTH_SHORT).show();
                }
            } else {
                QLND_ett qlnd_ett = new GetUserByLoginID(this, username).execute().get();
                if (qlnd_ett != null) {
                    if (qlnd_ett.getMatKhau().equals(Helper.sha1(password))) {
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        intent.putExtra("user", qlnd_ett);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Mật khẩu bạn nhập KHÔNG đúng...!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản bạn nhập KHÔNG đúng ...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void addEvents() {
        txt_username.addTextChangedListener(new MyTextWatcher(txt_username));
        txt_password.addTextChangedListener(new MyTextWatcher(txt_password));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getLogin();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_username.setText("");
                txt_password.setText("");
            }
        });
        scrollView.scrollTo(0, scrollView.getBottom());
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.txt_username:
                    validateUsername();
                    break;
                case R.id.txt_password:
                    validatePassword();
                    break;
            }
        }
    }

}
