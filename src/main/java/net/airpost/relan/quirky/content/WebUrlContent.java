package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import net.airpost.relan.quirky.R;

class WebUrlContent extends QrContent {

	public WebUrlContent(Context c, String s) {
		super(c, s);
	}

	public static boolean matches(String s) {
		return QrContent.matches(s, "http://")
			|| QrContent.matches(s, "https://");
	}

	public int getTitleStringId() { return R.string.title_url; }
	public int getActionStringId() { return R.string.action_url; }

	public void action() {
		String s = mText;
		if (!s.startsWith("http:") && !s.startsWith("https:") && !s.startsWith("ftp:")) {
			s = "http://" + s;
		}
		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(s)));
	}
}
