package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import java.util.List;
import net.airpost.relan.quirky.R;

class ContactContent extends QrContent {

	public final static String MATCH = "MECARD:(.*)";

	private String mName;
	private String mPhone;
	private String mAddress;
	private String mEmail;
	private String mCompany;

	public ContactContent(Context c, String s) {
		super(c, s);
		mText = toString();
	}

	public int getTitleStringId() { return R.string.title_contact; }
	public int getActionStringId() { return R.string.action_contact; }

	private void parseContact() {
		String contact = mText.substring(7);
		Log.d(tag, "contact " + contact);

		List<String> tokens = getTokens(contact);
		for (String token : tokens) {
			if (token.startsWith("N:")) {
				mName = token.substring(2);
			}
			if (token.startsWith("TEL:")) {
				mPhone = token.substring(4);
			}
			if (token.startsWith("ADR:")) {
				mAddress = token.substring(4);
			}
			if (token.startsWith("EMAIL:")) {
				mEmail = token.substring(6);
			}
			if (token.startsWith("ORG:")) {
				mCompany = token.substring(4);
			}
		}
	}

	public String toString() {
		parseContact();

		StringBuilder res = new StringBuilder();
		String text;
		if (mName != null) {
			text = mContext.getString(R.string.contact_qr_name_title);
			res.append(text + " " + mName + "\n");
		}
		if (mPhone != null) {
			text = mContext.getString(R.string.contact_qr_phone_title);
			res.append(text + " " + mPhone + "\n");
		}
		if (mAddress != null) {
			text = mContext.getString(R.string.contact_qr_address_title);
			res.append(text + " " + mAddress + "\n");
		}
		if (mEmail != null) {
			text = mContext.getString(R.string.contact_qr_email_title);
			res.append(text + " " + mEmail + "\n");
		}
		if (mCompany != null) {
			text = mContext.getString(R.string.contact_qr_company_title);
			res.append(text + " " + mCompany);
		}
		return res.toString();
	}

	public void action() {
		Intent intent = new Intent(Intent.ACTION_INSERT);
		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
		if (mName != null) {
			intent.putExtra(ContactsContract.Intents.Insert.NAME, mName);
		}
		if (mPhone != null) {
			intent.putExtra(ContactsContract.Intents.Insert.PHONE, mPhone);
		}
		if (mAddress != null) {
			intent.putExtra(ContactsContract.Intents.Insert.POSTAL, mAddress);
		}
		if (mEmail != null) {
			intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mEmail);
		}
		if (mCompany != null) {
			intent.putExtra(ContactsContract.Intents.Insert.COMPANY, mCompany);
		}

		mContext.startActivity(intent);
	}
}
