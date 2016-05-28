package net.airpost.relan.quirky.content;

import static junit.framework.Assert.*;
import android.test.AndroidTestCase;

public class QrContentTest extends AndroidTestCase {

	public void testGooglePlayMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(),
				"market://details?id=com.example");
		assertTrue(content instanceof GooglePlayContent);
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

		content = QrContentFactory.from(getContext(), "http://example.com");
		assertTrue(content instanceof WebUrlContent);
		content = QrContentFactory.from(getContext(), "https://example.com");
		assertTrue(content instanceof WebUrlContent);
		content = QrContentFactory.from(getContext(), "example.com");
		assertTrue(content instanceof WebUrlContent);
	}

	public void testEmailMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(),
				"mailto:johndoe@example.com");
		assertTrue(content instanceof EmailContent);
	}

	public void testSmsMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(), "smsto:+123456789");
		assertTrue(content instanceof SmsContent);
	}

	public void testPhoneNumberMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(), "tel:+123456789");
		assertTrue(content instanceof PhoneNumberContent);
	}

	public void testWifiMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(),
				"WIFI:S:Example;T:WPA;P:example123;;");
		assertTrue(content instanceof WifiContent);
	}

	public void testContactMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(),
				"MECARD:N:John Doe;EMAIL:john@example.com;;");
		assertTrue(content instanceof ContactContent);
	}

	public void testGeolocationMatcher() {
		QrContent content;

		content = QrContentFactory.from(getContext(), "geo:0,0");
		assertTrue(content instanceof GeoLocationContent);
	}
}
