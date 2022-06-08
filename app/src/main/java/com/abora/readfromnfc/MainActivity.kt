package com.abora.readfromnfc

import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        Toast.makeText(this,NFCUtil.retrieveNFCMessage(this.intent),Toast.LENGTH_LONG).show()


        btnNfcScanner.setOnClickListener {
        startActivity(Intent(this,NfcScannerActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.let {
            NFCUtil.enableNFCInForeground(it, this, javaClass)
        }
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter?.let {
            NFCUtil.disableNFCInForeground(it, this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val messageWrittenSuccessfully = NFCUtil.createNFCMessage(messageEditText.text.toString(), intent)
        Toast.makeText(this,messageWrittenSuccessfully.ifElse("Successful Written to Tag", "Something When wrong Try Again"),Toast.LENGTH_LONG).show()
    }
}

fun <T> Boolean.ifElse(primaryResult: T, secondaryResult: T) = if (this) primaryResult else secondaryResult