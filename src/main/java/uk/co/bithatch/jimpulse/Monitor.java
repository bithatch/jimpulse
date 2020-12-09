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

/**
 * A simple test of {@link Impulse}.
 */
public class Monitor {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Impulse lib = new Impulse();
		lib.initImpulse();
		while (true) {
			for (double d : lib.getSnapshot(true)) {
				if(d >= 0.05)
					System.out.print(d + " ");
			}
		}
	}
}
