package sticko.app.Miscellaneous;

import android.app.Activity;
import android.nfc.NfcAdapter;

public class NFCManager {
    private Activity activity;
    private NfcAdapter nfcAdpt;

    public NFCManager(Activity activity) {
        this.activity = activity;
    }

    public void verifyNFC() throws NFCNotSupported, NFCNotEnabled {
        nfcAdpt = NfcAdapter.getDefaultAdapter(activity);
        if (nfcAdpt == null)
            throw new NFCNotSupported();
        if (!nfcAdpt.isEnabled())
            throw new NFCNotEnabled();
    }

    public static class NFCNotEnabled extends Exception {
    }

    public static class NFCNotSupported extends Exception {
    }
}
