package net.airpost.relan.quirky.content;

import android.content.ClipboardManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import net.airpost.relan.quirky.R;

class QrMixedContent extends QrContent {

	public QrMixedContent(Context c, String s) {
		super(c, s);
	}

	public int getTitleStringId() { return R.string.title_text; }
	public int getActionStringId() { return R.string.action_text; }

	public void action() {
		Log.d(tag, "action: copy to clipboard " + mText);
		ClipboardManager clippy = (ClipboardManager)
				mContext.getSystemService(Context.CLIPBOARD_SERVICE);
		clippy.setPrimaryClip(ClipData.newPlainText(
				ClipDescription.MIMETYPE_TEXT_PLAIN, mText));
		Toast.makeText(mContext, mContext.getString(R.string.text_qr_action_name),
				Toast.LENGTH_LONG).show();
	}
}
