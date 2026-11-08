package my.edu.uitm.baggagefee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layoutWeight, layoutRate;
    private TextInputEditText etWeight, etRate;
    private RadioGroup rgClass;
    private View cardResult;
    private TextView tvExcessWeight, tvExcessCharge, tvTotal, tvAllowanceHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutWeight = findViewById(R.id.layoutWeight);
        layoutRate = findViewById(R.id.layoutRate);
        etWeight = findViewById(R.id.etWeight);
        etRate = findViewById(R.id.etRate);
        rgClass = findViewById(R.id.rgClass);
        cardResult = findViewById(R.id.cardResult);
        tvExcessWeight = findViewById(R.id.tvExcessWeight);
        tvExcessCharge = findViewById(R.id.tvExcessCharge);
        tvTotal = findViewById(R.id.tvTotal);
        tvAllowanceHint = findViewById(R.id.tvAllowanceHint);

        Button btnCalculate = findViewById(R.id.btnCalculate);
        Button btnReset = findViewById(R.id.btnReset);

        updateAllowanceHint();
        rgClass.setOnCheckedChangeListener((group, checkedId) -> updateAllowanceHint());
        btnCalculate.setOnClickListener(v -> calculate());
        btnReset.setOnClickListener(v -> reset());
    }

    private boolean isBusiness() {
        return rgClass.getCheckedRadioButtonId() == R.id.rbBusiness;
    }

    private void updateAllowanceHint() {
        double x = BaggageCalculator.allowanceKg(isBusiness());
        tvAllowanceHint.setText(getString(R.string.allowance_hint,
                isBusiness() ? getString(R.string.business) : getString(R.string.economy), x));
    }

    private void calculate() {
        layoutWeight.setError(null);
        layoutRate.setError(null);

        String weightStr = textOf(etWeight);
        String rateStr = textOf(etRate);
        boolean valid = true;

        double weight = 0, rate = 0;

        if (weightStr.isEmpty()) {
            layoutWeight.setError(getString(R.string.err_weight_empty));
            valid = false;
        } else {
            try {
                weight = Double.parseDouble(weightStr);
                if (weight < 0) {
                    layoutWeight.setError(getString(R.string.err_weight_negative));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                layoutWeight.setError(getString(R.string.err_number_invalid));
                valid = false;
            }
        }

        if (rateStr.isEmpty()) {
            layoutRate.setError(getString(R.string.err_rate_empty));
            valid = false;
        } else {
            try {
                rate = Double.parseDouble(rateStr);
                if (rate < 0) {
                    layoutRate.setError(getString(R.string.err_rate_negative));
                    valid = false;
                }
            } catch (NumberFormatException e) {
                layoutRate.setError(getString(R.string.err_number_invalid));
                valid = false;
            }
        }

        if (!valid) {
            cardResult.setVisibility(View.GONE);
            Toast.makeText(this, R.string.toast_fix_errors, Toast.LENGTH_SHORT).show();
            return;
        }

        hideKeyboard();

        double excessKg = BaggageCalculator.excessWeight(weight, isBusiness());
        double charge = BaggageCalculator.excessCharge(excessKg, rate);
        double total = BaggageCalculator.totalPayable(charge);

        tvExcessWeight.setText(String.format(Locale.US, "%.2f kg", excessKg));
        tvExcessCharge.setText(String.format(Locale.US, "RM %.2f", charge));
        tvTotal.setText(String.format(Locale.US, "RM %.2f", total));
        cardResult.setVisibility(View.VISIBLE);

        if (excessKg == 0) {
            Toast.makeText(this, R.string.toast_no_excess, Toast.LENGTH_LONG).show();
        }
    }

    private void reset() {
        etWeight.setText("");
        etRate.setText("");
        layoutWeight.setError(null);
        layoutRate.setError(null);
        rgClass.check(R.id.rbEconomy);
        cardResult.setVisibility(View.GONE);
        etWeight.requestFocus();
    }

    private String textOf(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent send = new Intent(Intent.ACTION_SEND);
            send.setType("text/plain");
            send.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            send.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_text, getString(R.string.app_url)));
            startActivity(Intent.createChooser(send, getString(R.string.share_via)));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
