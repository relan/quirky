package net.airpost.relan.quirky.content;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.airpost.relan.quirky.R;

class GooglePlayContent extends QrContent {

	public final static String MATCH = "market://(.*)";
	public final static String GOOGLEPLAY_ID = "market://details\\?id=(.*)";

	public GooglePlayContent(Context c, String s) {
		super(c, s);
		if (s.matches(GOOGLEPLAY_ID)) {
			Matcher m = Pattern.compile(GOOGLEPLAY_ID).matcher(s);
			m.find();
			mText = m.group(1);
		}
	}

	public int getTitleStringId() { return R.string.title_market; }
	public int getActionStringId() { return R.string.action_market; }

	public void action() {
		try {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mText)));
		} catch (ActivityNotFoundException e) {
			Toast.makeText(mContext, mContext.getResources()
					.getString(R.string.alert_msg_invalid_market_link),
					Toast.LENGTH_SHORT).show();
		}
	}
}
