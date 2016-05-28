package net.airpost.relan.quirky.content;

import android.content.Context;

public abstract class QrContentFactory {

	public static QrContent from(Context c, String s) {
		if (GooglePlayContent.matches(s)) {
			return new GooglePlayContent(c, s);
		} else if (WebUrlContent.matches(s)) {
			return new WebUrlContent(c, s);
		} else if (EmailContent.matches(s)) {
			return new EmailContent(c, s);
		} else if (PhoneNumberContent.matches(s)) {
			return new PhoneNumberContent(c, s);
		} else if (SmsContent.matches(s)) {
			return new SmsContent(c, s);
		} else if (ContactContent.matches(s)) {
			return new ContactContent(c, s);
		} else if (GeoLocationContent.matches(s)) {
			return new GeoLocationContent(c, s);
		} else if (WifiContent.matches(s)) {
			return new WifiContent(c, s);
		} else {
			return new PlainTextContent(c, s);
		}
	}
}
