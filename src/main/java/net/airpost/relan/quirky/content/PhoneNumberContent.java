package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import net.airpost.relan.quirky.R;

class PhoneNumberContent extends QrContent {

	private String mOriginalUri;

	public PhoneNumberContent(Context c, String s) {
		super(c, s);
		mOriginalUri = s;
		mText = s.substring(4);
	}

	public static boolean matches(String s) {
		return QrContent.matches(s, "tel:");
	}

	public int getTitleStringId() { return R.string.title_phone; }
	public int getActionStringId() { return R.string.action_phone; }

	public void action() {
		mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(mOriginalUri)));
	}
}
