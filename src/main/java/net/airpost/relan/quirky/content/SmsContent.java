package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import net.airpost.relan.quirky.R;

class SmsContent extends QrContent {

	public final static String MATCH = "smsto:(.*)";
	private String mOriginalUri;

	public SmsContent(Context c, String s) {
		super(c, s);
		mOriginalUri = s;
		mText = toString();
	}

	public int getTitleStringId() { return R.string.title_sms; }
	public int getActionStringId() { return R.string.action_sms; }

	public void action() {
		String[] s = mOriginalUri.split(":");
		String uri= s[0] + ":" + s[1];
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
		intent.putExtra("compose_mode", true);

		if (s.length > 2) {
			intent.putExtra("sms_body", s[2]);
		}
		mContext.startActivity(intent);
	}

	public String toString() {
		String[] s = mText.split(":");
		String text = mContext.getString(R.string.sms_qr_phone_title);
		String res = text + " " + s[1];
		if (s.length > 2) {
			text = mContext.getString(R.string.sms_qr_message_title);
			res = res + "\n" + text + " " + s[2];
		}
		return res;
	}
}
