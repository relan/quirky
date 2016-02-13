package net.airpost.relan.quirky.content;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;
import java.util.List;
import net.airpost.relan.quirky.R;

class WifiContent extends QrContent {

	public final static String MATCH = "WIFI:(.*)";

	private String mType;
	private String mNetworkSsid;
	private String mPassword;
	private boolean mSsidHidden;
	private String mOriginalUri;

	public WifiContent(Context c, String s) {
		super(c, s);
		mOriginalUri = s;
		mText = toString();
	}

	public int getTitleStringId() { return R.string.title_wifi; }
	public int getActionStringId() { return R.string.action_wifi; }

	private void parseWifi() {
		String wifi = mOriginalUri.substring(5);
		Log.d(tag, "wifi " + wifi);

		List<String> tokens = getTokens(wifi);
		for (String token : tokens) {
			if (token.startsWith("T:")) {
				mType = token.substring(2);
			}
			if (token.startsWith("S:")) {
				mNetworkSsid = token.substring(2);
			}
			if (token.startsWith("P:")) {
				mPassword = token.substring(2);
			}
			if (token.startsWith("H:")) {
				mSsidHidden = Boolean.valueOf(token.substring(2));
			}
		}

		if (mType == null) {
			mType = "nopass";
		}
	}

	public String toString() {
		parseWifi();

		StringBuilder res = new StringBuilder();
		String text;
		if (mType != null) {
			text = mContext.getString(R.string.wifi_qr_security_title);
			res.append(text + " " + mType + "\n");
		}
		if (mNetworkSsid != null) {
			text = mContext.getString(R.string.wifi_qr_ssid_title);
			res.append(text + " " + mNetworkSsid + "\n");
		}
		if (mPassword != null) {
			text = mContext.getString(R.string.wifi_qr_password_title);
			res.append(text + " " + mPassword + "\n");
		}

		return res.toString();
	}

	public void action() {
		WifiConfiguration conf = new WifiConfiguration();
		conf.SSID = "\"" + mNetworkSsid + "\"";

		if (mType.equals("WEP")) {
			if (mPassword.matches("[0-9A-Fa-f]+")) {
				conf.wepKeys[0] = mPassword;
			} else {
				conf.wepKeys[0] = "\"" + mPassword + "\"";
			}
			conf.wepTxKeyIndex = 0;

			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		} else if (mType.equals("WPA")) {
			conf.preSharedKey = "\""+ mPassword +"\"";
		} else if (mType.equals("nopass")) {
			conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		} else {
			Log.d(tag, "failed to read wifi configuration");
			return;
		}

		WifiManager wifiManager = (WifiManager) mContext
			.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled()) {
			Log.d(tag, "enable wifi");
			wifiManager.setWifiEnabled(true);
		} else {
			Log.d(tag, "wifi is already enabled");
		}

		int networkId = wifiManager.addNetwork(conf);
		wifiManager.saveConfiguration();
		if (networkId != -1) {
			Log.d(tag, "added new network " + mNetworkSsid + " successfully");
			if (wifiManager.enableNetwork(networkId, true)) {
				Toast.makeText(mContext,
					mContext.getString(R.string.alert_msg_wifi_connected),
					Toast.LENGTH_LONG).show();
				Log.d(tag, "connected to network " + mNetworkSsid + " successfully");
			} else {
				Toast.makeText(mContext,
					mContext.getString(R.string.alert_msg_wifi_failed),
					Toast.LENGTH_LONG).show();
				Log.d(tag, "failed to connect to network " + mNetworkSsid);
			}
		} else {
			Log.d(tag, "failed to add new wifi network");
		}
	}
}
