package rsoni.Utils;

import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class Utils {
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static void call(Activity activity, String no) {
		if (no.isEmpty())
			return;

		if (no.contains(",")) {
			String[] nos = no.split(",");
			no = nos[0];
		}
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no));
		activity.startActivity(intent);
	}

	private void startActivity(Intent intent) {
		// TODO Auto-generated method stub

	}
}