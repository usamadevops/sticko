package sticko.app.ActivationScreen;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import sticko.app.HomeScreen.HomeScreenActivity;
import sticko.app.Miscellaneous.NFCManager;
import sticko.app.R;

public class ActivationActivity extends AppCompatActivity {
    private final String PATH = "https://profile.sticko.fr/user/";
    public static String username;
    private AppCompatButton btn_activate_sticko;
    private ImageView btn_add;
    private ImageButton btn_close, btn_done, btn_close_start;
    public static final String ERROR_DETECTED = "Impossible de détecter la balise NFC";
    public static final String WRITE_SUCCESS = "NFC activé avec succès";
    PendingIntent pendingIntent;
    Tag myTag;
    private RelativeLayout main_container;
    public Toolbar toolbar;
    private String themeSelected;
    NFCManager nfcManager;
    Intent intent;
    NfcAdapter nfcAdapter;
    IntentFilter[] intentFiltersArray;
    String[][] techList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activation);
        btn_activate_sticko = findViewById(R.id.btn_activate_sticko);
        toolbar = findViewById(R.id.toolbar_secondary);
        getSupportActionBar();
        btn_add = toolbar.findViewById(R.id.btn_add);
        btn_close_start = toolbar.findViewById(R.id.btn_close_start);
        btn_done = toolbar.findViewById(R.id.btn_done);
        btn_close = findViewById(R.id.btn_close);
        btn_close.setVisibility(View.GONE);
        btn_add.setVisibility(View.GONE);
        btn_close_start.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.GONE);
        main_container = findViewById(R.id.main_container);

        // set theme color
        SharedPreferences themeColor = getSharedPreferences("themeColor",
                Context.MODE_PRIVATE);
        themeSelected = themeColor.getString("theme", "");
        if (themeSelected.equals("#") || themeSelected == null || themeSelected.trim().equals("null") || themeSelected.trim()
                .length() <= 0) {
            themeSelected = "#09122A";

        }

        configureNFC();

        toolbar.setBackgroundColor(Color.parseColor(themeSelected));
        main_container.setBackgroundColor(Color.parseColor(themeSelected));

        btn_close_start.setOnClickListener(view -> {
            Intent intent = new Intent(ActivationActivity.this , HomeScreenActivity.class);
            startActivity(intent);
        });

        btn_activate_sticko.setOnClickListener(view -> {
            if (myTag == null) {
                Toast.makeText(ActivationActivity.this, ERROR_DETECTED, Toast.LENGTH_LONG).show();
            } else {
                writeTag(myTag, createMessage(PATH + username));
            }

        });
    }

    private void configureNFC() {
        nfcManager = new NFCManager(this);
        intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        try {
            nfcManager.verifyNFC();
        } catch (NFCManager.NFCNotSupported nfcNotSupported) {
            nfcNotSupported.printStackTrace();
        } catch (NFCManager.NFCNotEnabled nfcNotEnabled) {
            nfcNotEnabled.printStackTrace();
        }

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        intentFiltersArray = new IntentFilter[]{tagDetected};
        techList = new String[][]{
                {android.nfc.tech.Ndef.class.getName()},
                {android.nfc.tech.NdefFormatable.class.getName()}
        };

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
    }

    /******************************************************************************
     **********************************Write to NFC Tag****************************
     ******************************************************************************/
    public void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);
                if (ndefTag == null) {
                    // Let's try to format the Tag in NDEF
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                    }
                } else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                }

                ActivatedFragment profileFragment = new ActivatedFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_container, profileFragment, "tag")
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(ActivationActivity.this, WRITE_SUCCESS, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public NdefMessage createMessage(String content) {
        Uri uri = Uri.parse(content);
        NdefRecord recordNFC = NdefRecord.createUri(uri);
        return new NdefMessage(recordNFC);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
    }
}


