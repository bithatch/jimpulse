/**
 * JImpulse - A Java port of impulse.
 * Copyright © 2020 Bithatch (tanktarta@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package uk.co.bithatch.jimpulse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Start a new capture of data from Pulse Audio. To use, created a new
 * {@link #Impulse()} instance, and invoke {@#initImpulse()}. Then you may then
 * call {@link #getSnapshot(boolean)} at any time to get the current snapshot.
 * This method accepts a parameter which enables Fast Fourier Transform on the
 * data. See the source <code>Impulse.c</code> for the algorithm used.
 * <p>
 * You can select an alternative Pulse sourcce by
 * using{@link #setSourceIndex(int)}.
 */
public class Impulse {

	static {
		try {
			System.load(Impulse.locateLibrary());
		} catch (Exception e) {
			System.loadLibrary("jimpulse");
		}
	}

	/**
	 * Sets the source index.
	 *
	 * @param index the new source index
	 */
	public native void setSourceIndex(int index);

	/**
	 * Inits the impulse.
	 */
	public native void initImpulse();

	/**
	 * Gets the snapshot. Call this at a fixed rate to analyse the sound stream. The
	 * returned array will be 256 bytes long.
	 *
	 * @param fft enable Fast Fourier Transform
	 * @return audio analysis
	 */
	public native double[] getSnapshot(boolean fft);

	static String locateLibrary() {
		final String resourceName = getResourceName();
		try {
			URL resource = Impulse.class.getResource(resourceName);
			if (resource == null) {
				for (String f : System.getProperty("java.library.path", "").split("\\" + File.pathSeparatorChar)) {
					File file = new File(new File(f), resourceName.substring(resourceName.lastIndexOf("/") + 1));
					if (file.exists())
						return file.getAbsolutePath();
				}
				throw new RuntimeException(
						"The appropriate jimpulse library could not be found either on the classpath or anywhere in java.library.path. Please correct this.");
			} else {
				File tf = File.createTempFile("lib", resourceName.replace("/", "_"));
				tf.deleteOnExit();
				try (FileOutputStream out = new FileOutputStream(tf)) {
					try (InputStream in = resource.openStream()) {
						in.transferTo(out);
					}
				}
				return tf.getAbsolutePath();
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	static String getResourceName() {
		if (System.getProperty("os.arch").startsWith("amd64"))
			return "linux/x86_64/libjimpulse.so";
		else
			return "linux/x86/libjimpulse.so";
	}

}
