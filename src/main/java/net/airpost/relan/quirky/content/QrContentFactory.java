package net.airpost.relan.quirky.content;

import android.content.Context;

public abstract class QrContentFactory {

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
