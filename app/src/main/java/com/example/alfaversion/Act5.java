package com.example.alfaversion;


import static android.icu.text.MessagePattern.ArgType.SELECT;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.health.connect.datatypes.Record;
import android.icu.text.MessagePattern;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class Act5 extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    IntentFilter intentFilter1;
    IntentFilter[] readFilter;
    PendingIntent pendingIntent;
    Context context;
    TextView tV;
    Button btn_act5;
    int counter = 0;
    AlertDialog ad;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act5);

        tV = findViewById(R.id.tV);
        btn_act5 = findViewById(R.id.btn_act5);
        context = this;

        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        try {
            intentFilter1 = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED, "text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException(e);
        }
        readFilter = new IntentFilter[]{intentFilter1};
    }

    private void enableRead() {
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    private void disableRead() {
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableRead();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processNFC(intent);
        ad.dismiss();
    }

    public void writeNdefMessage(Tag tag) {
        try {
            NdefRecord record = createRecord("albert the king");
            NdefMessage message = new NdefMessage(new NdefRecord[] { record });

            Ndef ndef = Ndef.get(tag);
            ndef.connect();
            ndef.writeNdefMessage(message);
            ndef.close();

            Toast.makeText(this, "data written successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("error", e.getMessage().toString());
            Toast.makeText(this, "error during writing data to the tag", Toast.LENGTH_SHORT).show();
        }
    }

    private NdefRecord createRecord(String text) {
        byte[] langBytes = "en".getBytes(StandardCharsets.UTF_8);
        byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
        int utfBit = 0;
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes,
                0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT,
                langBytes, data);
    }

    @SuppressLint("NewApi")
    public void processNFC(Intent intent) {
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (messages != null) {
            for (Parcelable message : messages) {
                NdefMessage ndefMessage1 = (NdefMessage) message;
                for (NdefRecord record : ndefMessage1.getRecords()) {
                    switch (record.getTnf()) {
                        case NdefRecord.TNF_WELL_KNOWN:
                            if (Arrays.equals(record.getType(), NdefRecord.RTD_TEXT)) {
                                counter++;
                                tV.setText("plain text:" + new String(record.getPayload()).substring(3) + " " + counter);
                            }
                    }
                }
            }
        }
}


    public void clicked_Act5(View view) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setMessage("Waiting for NFC tag...");

        ad = adb.create();
        enableRead();
        ad.show();
    }
}