package net.airpost.relan.quirky;

import static junit.framework.Assert.*;
import android.test.AndroidTestCase;

public class QrContentTest extends AndroidTestCase {

	public void testGooglePlayMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "market://details?id=com.example");
		assertTrue(content instanceof QrContent.GooglePlayContent);
		assertEquals(content.getText(), "com.example");

		//
		// Other possible urls:
		//
		// market://search?pub=Trikita
		// market://search?q=SomeQuery&c=apps
		// market://apps/collection/editors_choice
	}

	public void testUrlMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "http://example.com");
		assertTrue(content instanceof QrContent.WebUrlContent);
		content = QrContent.from(getContext(), "https://example.com");
		assertTrue(content instanceof QrContent.WebUrlContent);
		content = QrContent.from(getContext(), "example.com");
		assertTrue(content instanceof QrContent.WebUrlContent);
	}

	public void testEmailMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "mailto:johndoe@example.com");
		assertTrue(content instanceof QrContent.EmailContent);
	}

	public void testSmsMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "smsto:+123456789");
		assertTrue(content instanceof QrContent.SmsContent);
	}

	public void testPhoneNumberMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "tel:+123456789");
		assertTrue(content instanceof QrContent.PhoneNumberContent);
	}

	public void testWifiMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "WIFI:S:Example;T:WPA;P:example123;;");
		assertTrue(content instanceof QrContent.WifiContent);
	}

	public void testContactMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "MECARD:N:John Doe;EMAIL:john@example.com;;");
		assertTrue(content instanceof QrContent.ContactContent);
	}

	public void testGeolocationMatcher() {
		QrContent content;

		content = QrContent.from(getContext(), "geo:0,0");
		assertTrue(content instanceof QrContent.GeoLocationContent);
	}
}
