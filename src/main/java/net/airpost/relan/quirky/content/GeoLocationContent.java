package net.airpost.relan.quirky.content;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import net.airpost.relan.quirky.R;

class GeoLocationContent extends QrContent {

	private boolean mIsValidData;
	private String mOriginalGeoUri;

	public GeoLocationContent(Context c, String s) {
		super(c, s);
		mOriginalGeoUri = s;
		mText = toString();
	}

	public static boolean matches(String s) {
		return QrContent.matches(s, "geo:");
	}

	public int getTitleStringId() { return R.string.title_geo; }
	public int getActionStringId() { return R.string.action_geo; }

	public String toString() {
		String[] tokens = mText.substring(4).split("\\?q=");
		StringBuilder res = new StringBuilder();
		if (tokens.length == 2 && tokens[1].length() > 0) {
			res.append(mContext.getString(R.string.geo_qr_title_title) +
					" " + tokens[1] + "\n");
		}

		String[] params = tokens[0].split(",");
		if (params.length < 2 || params.length > 3) {
			return mContext.getString(R.string.unsupported_data_text);
		}

		try {
			float latitude = Float.parseFloat(params[0]);
			String southMark = mContext.getString(R.string.geo_qr_latitude_south);
			String northMark = mContext.getString(R.string.geo_qr_latitude_north);
			res.append(mContext.getString(R.string.geo_qr_latitude_title) +
					" " + Math.abs(latitude) + "\u00b0 " + (latitude < 0 ? southMark : northMark));
			float longtitude = Float.parseFloat(params[1]);
			String westMark = mContext.getString(R.string.geo_qr_longitude_west);
			String eastMark = mContext.getString(R.string.geo_qr_longitude_east);
			res.append("\n" + mContext.getString(R.string.geo_qr_longitude_title) +
					" " + Math.abs(longtitude) + "\u00b0 " + (longtitude < 0 ? westMark : eastMark));
			if (params.length == 3) {
				float altitude = Float.parseFloat(params[2]);
				res.append("\n" + mContext.getString(R.string.geo_qr_altitude_title) +
						" " + altitude + " " +
						mContext.getString(R.string.geo_qr_altitude_suffix));
			}
			mIsValidData = true;
			return res.toString();
		} catch (NumberFormatException e) {
			return mContext.getString(R.string.unsupported_data_text);
		}
	}

	public void action() {
		if (mIsValidData) {
			mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mOriginalGeoUri)));
		}
	}
}
