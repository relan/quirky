package net.airpost.relan.quirky;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.widget.Toast;
import net.airpost.relan.quirky.content.QrContent;

public class MainActivity extends Activity implements CameraPreview.OnQrDecodedListener {

	private final static String tag = "MainActivity";
	private CameraPreview mCameraPreview;
	private String mLastKnownContent = "";
	private AlertDialog mDialog;

	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.act_camera);
		if (state != null) {
			if (state.getBoolean("dialogShowing"))
				onQrDecoded(state.getString("content"));
			else
				mLastKnownContent = state.getString("content");
		}
		mCameraPreview = (CameraPreview) findViewById(R.id.surface);
		mCameraPreview.setOnQrDecodedListener(this);
	}

	@Override
	public void onQrDecoded(String s) {
		if (mLastKnownContent.equals(s))
			return;
		mLastKnownContent = s;

		// parse QR content string
		final QrContent mQrContent = QrContent.from(this, s);

		// show dialog with QR content
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(mQrContent.getTitle());
		builder.setMessage(mQrContent.getText());
			String action = mQrContent.getAction();
		builder.setPositiveButton(mQrContent.getAction(),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							mQrContent.action();
						} catch (ActivityNotFoundException e) {
							showToastActivityNotFound();
						}
					}
				});
		builder.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// do nothing
					}
				});
		if (mDialog != null)
			mDialog.cancel();
		mDialog = builder.create();
		mDialog.show();
	}

	@Override
	public void onQrNotFound() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(tag, "onResume()");
		
		if (!mCameraPreview.acquireCamera(getWindowManager()
				.getDefaultDisplay().getRotation()))
			openAlertDialog();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(tag, "onPause()");

		mCameraPreview.releaseCamera();
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		Log.d(tag, "onSaveInstanceState()");

		state.putString("content", mLastKnownContent);
		state.putBoolean("dialogShowing",
				mDialog == null ? false : mDialog.isShowing());
	}

	/** 
	 * Sets up alert dialog views and presents the dialog to user
	 * when camera could not be opened
	 */
	private void openAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dlg_alert_msg);
		builder.setCancelable(false);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						MainActivity.this.finish();
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void showToastActivityNotFound() {
		Toast.makeText(this, R.string.alert_msg_activity_not_found,
				Toast.LENGTH_LONG).show();
	}
}
