package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import net.airpost.relan.quirky.R;

class EmailContent extends QrContent {

	// "^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$"
	public final static String MATCH = "mailto:(.*)";

	public EmailContent(Context c, String s) {
		super(c, s);
	}

	public int getTitleStringId() { return R.string.title_email; }
	public int getActionStringId() { return R.string.action_email; }

	public void action() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mText.substring(7)});
		String text = mContext.getString(R.string.email_qr_send_dlg_title);
		mContext.startActivity(Intent.createChooser(intent, text));
	}
}
