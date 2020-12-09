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
#include <jni.h>
#include <string.h>
#include <stdint.h>
#include <Impulse.h>
#include "uk_co_bithatch_jimpulse_Impulse.h"

JNIEXPORT void JNICALL Java_uk_co_bithatch_jimpulse_Impulse_setSourceIndex
  (JNIEnv *env, jobject object, jint index) {
  im_setSourceIndex((uint32_t)index);
}

JNIEXPORT void JNICALL Java_uk_co_bithatch_jimpulse_Impulse_initImpulse
  (JNIEnv *env, jobject object) {
  im_start();
}

JNIEXPORT jdoubleArray JNICALL Java_uk_co_bithatch_jimpulse_Impulse_getSnapshot
  (JNIEnv *env, jobject object, jboolean fft) {
	double *m = im_getSnapshot( fft );

	// allocate the jdouble Array
	jdoubleArray output = (*env)->NewDoubleArray(env, 256);

	// check if it was allocated correctly
	if (NULL == output) return NULL;

	// commit the contents of the double* array (sourceBuffer in this example) into the Java array
	(*env)->SetDoubleArrayRegion(env, output, 0, 256, m);
	return output;
}
