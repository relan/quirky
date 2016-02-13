package net.airpost.relan.quirky.content;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public abstract class QrContent {

	public final static String tag = "QrContent";

	protected Context mContext;
	protected String mText;

	abstract int getTitleStringId();
	abstract int getActionStringId();
	abstract public void action();

	protected QrContent(Context c, String s) {
		mContext = c;
		mText = s;
	}

	public String getTitle() {
		return mContext.getString(getTitleStringId());
	}

	public String getAction() {
		return mContext.getString(getActionStringId());
	}

	public String getText() {
		return mText;
	}

	public static List<String> getTokens(String s) {
		List<String> tokens = new ArrayList<String>();

		int len = s.length();
		StringBuilder builder = new StringBuilder();
		boolean escaped = false;

		// treat a char sequence between two non-escaped semicolons
		// as a single token
		for (int i = 0; i < len; i++) {
			if (escaped) {
				builder.append(s.charAt(i));
				escaped = false;
			} else {
				if (s.charAt(i) == ';') {
					tokens.add(builder.toString());
					builder = new StringBuilder();
				} else if (s.charAt(i) == '\\') {
					escaped = true;
				} else {
					builder.append(s.charAt(i));
				}
			}
		}
		return tokens;
	}

	public static QrContent from(Context c, String s) {
		if (s.matches(GooglePlayContent.MATCH)) {
			return new GooglePlayContent(c, s);
		} else if (s.matches(WebUrlContent.MATCH)) {
			return new WebUrlContent(c, s);
		} else if (s.matches(EmailContent.MATCH)) {
			return new EmailContent(c, s);
		} else if (s.matches(PhoneNumberContent.MATCH)) {
			return new PhoneNumberContent(c, s);
		} else if (s.matches(SmsContent.MATCH)) {
			return new SmsContent(c, s);
		} else if (s.matches(ContactContent.MATCH)) {
			return new ContactContent(c, s);
		} else if (s.matches(GeoLocationContent.MATCH)) {
			return new GeoLocationContent(c, s);
		} else if (s.matches(WifiContent.MATCH)) {
			return new WifiContent(c, s);
		} else {
			return new PlainTextContent(c, s);
		}
	}
}
