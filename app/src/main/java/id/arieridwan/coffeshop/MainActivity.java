package id.arieridwan.coffeshop;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText mEtName;

    @BindView(R.id.cb_menu_one)
    CheckBox mCbMenuOne;

    @BindView(R.id.cb_menu_two)
    CheckBox mCbMenuTwo;

    @BindView(R.id.btn_min)
    Button mBtnMin;

    @BindView(R.id.tv_qty)
    TextView mTvQty;

    @BindView(R.id.btn_plus)
    Button mBtnPlus;

    @BindView(R.id.btn_order)
    TextView mBtnOrder;

    @BindView(R.id.tv_price)
    TextView mTvPrice;

    private int qty = 0;
    private int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_min, R.id.btn_plus, R.id.btn_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_min:
                if (qty > 0) {
                    qty--;
                    mTvQty.setText(String.valueOf(qty));
                    price = 5 * qty;
                    mTvPrice.setText("Price: $" + price);
                }
                break;
            case R.id.btn_plus:
                qty++;
                mTvQty.setText(String.valueOf(qty));
                price = 5 * qty;
                mTvPrice.setText("Total price : $" + price);
                Log.wtf("PLUS", qty+"");
                break;
            case R.id.btn_order:
                sendEmail();
                break;
        }
    }

    protected void sendEmail() {

        String menu = "";

        if (mCbMenuOne.isChecked()) {
            menu += mCbMenuOne.getText().toString();
        }

        if (mCbMenuTwo.isChecked()) {
            menu += mCbMenuTwo.getText().toString();
        }

        String[] TO = {"arie@sweetescape.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Nama saya " + mEtName.getText() + ", saya memesan sebuah kopi dengan toping "
                + menu + " sebanyak " + mTvQty.getText()
                + " dengan total harga " + price);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_next)
    public void onClick() {
        Intent i = new Intent(this, AboutActivity.class);
        startActivity(i);
    }
}
