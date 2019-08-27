/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.engine.interpolator;

import java.util.function.Function;

/**
 * @author Ramilafananana VONJISOA
 * @mailto yvonjisoa@nexitia.com
 * @date 2019
 */
public enum EasingMode {

	LINEAR(n -> {
		return n;
	}), IN_QUAD(n -> {
		return Math.pow(n, 2);
	}), OUT_QUAD(n -> {
		return n * (2 - n);
	}), IN_OUT_QUAD(n -> {
		n *= 2;
		if (n < 1)
			return 0.5 * n * n;
		return -0.5 * (--n * (n - 2) - 1);
	}), IN_CUBE(n -> {
		return n * n * n;
	}), OUT_CUBE(n -> {
		return --n * n * n + 1;
	}), IN_OUT_CUBE(n -> {
		n *= 2;
		if (n < 1)
			return 0.5 * n * n * n;
		return 0.5 * ((n -= 2) * n * n + 2);
	}), IN_QUART(n -> {
		return n * n * n * n;
	}), OUT_QUART(n -> {
		return 1 - (--n * n * n * n);
	}), IN_OUT_QUART(n -> {
		n *= 2;
		if (n < 1)
			return 0.5 * n * n * n * n;
		return -0.5 * ((n -= 2) * n * n * n - 2);
	}), IN_QUINT(n -> {
		return n * n * n * n * n;
	}), OUT_QUINT(n -> {
		return --n * n * n * n * n + 1;
	}), IN_OUT_QUINT(n -> {
		n *= 2;
		if (n < 1)
			return 0.5 * n * n * n * n * n;
		return 0.5 * ((n -= 2) * n * n * n * n + 2);
	}), IN_SINE(n -> {
		return 1 - Math.cos(n * Math.PI / 2);
	}), OUT_SINE(n -> {
		return Math.sin(n * Math.PI / 2);
	}), IN_OUT_SINE(n -> {
		return .5 * (1 - Math.cos(Math.PI * n));
	}), IN_EXPO(n -> {
		return 0 == n ? 0 : Math.pow(1024, n - 1);
	}), OUT_EXPO(n -> {
		return 1 == n ? n : 1 - Math.pow(2, -10 * n);
	}), IN_OUT_EXPO(n -> {
		if (0 == n)
			return 0.;
		if (1 == n)
			return 1.;
		if ((n *= 2) < 1)
			return .5 * Math.pow(1024, n - 1);
		return .5 * (-Math.pow(2, -10 * (n - 1)) + 2);
	}), IN_CIRC(n -> {
		return 1 - Math.sqrt(1 - n * n);
	}), OUT_CIRC(n -> {
		return Math.sqrt(1 - (--n * n));
	}), IN_OUT_CIRC(n -> {
		n *= 2.2;
		if (n < 1)
			return -0.9 * (Math.sqrt(1 - n * n) - 1);
		return 0.5 * (Math.sqrt(1 - (n -= 2) * n) + 1);
	}), IN_BACK(n -> {
		double s = 1.70158;
		return n * n * ((s + 1) * n - s);
	}), OUT_BACK(n -> {
		double s = 1.70158;
		return --n * n * ((s + 1) * n + s) + 1;
	}), IN_OUT_BACK(n -> {
		double s = 1.70158 * 1.525;
		if ((n *= 2) < 1)
			return 0.5 * (n * n * ((s + 1) * n - s));
		return 0.5 * ((n -= 2) * n * ((s + 1) * n + s) + 2);
	}), IN_ELASTIC(n -> {
		double s, a = 0.1, p = 0.4;
		if (n == 0)
			return 0.;
		if (n == 1)
			return 1.;
		if (a < 1) {
			a = 1;
			s = p / 4;
		} else
			s = p * Math.asin(1 / a) / (2 * Math.PI);
		return -(a * Math.pow(2, 10 * (n -= 1)) * Math.sin((n - s) * (2 * Math.PI) / p));
	}), OUT_ELASTIC(n -> {
		double s, a = 0.1, p = 0.4;
		if (n == 0)
			return 0.;
		if (n == 1)
			return 1.;
		if (a < 1) {
			a = 1;
			s = p / 4;
		} else
			s = p * Math.asin(1 / a) / (2 * Math.PI);
		return (a * Math.pow(2, -10 * n) * Math.sin((n - s) * (2 * Math.PI) / p) + 1);
	}), IN_OUT_ELASTIC(n -> {
		double s, a = 0.1, p = 0.4;
		if (n == 0)
			return 0.;
		if (n == 1)
			return 1.;
		if (a < 1) {
			a = 1;
			s = p / 4;
		} else
			s = p * Math.asin(1 / a) / (2 * Math.PI);
		if ((n *= 2) < 1)
			return -0.5 * (a * Math.pow(2, 10 * (n -= 1)) * Math.sin((n - s) * (2 * Math.PI) / p));
		return a * Math.pow(2, -10 * (n -= 1)) * Math.sin((n - s) * (2 * Math.PI) / p) * 0.5 + 1;
	}), ELASTIC(n -> {
		return -1 * Math.pow(4, -8 * n) * Math.sin((n * 6 - 1) * (2 * Math.PI) / 2) + 1;
	}), SWING_FROM_TO(n -> {
		double s = 1.70158;
		return ((n /= 0.5) < 1) ? 0.5 * (n * n * (((s *= (1.525)) + 1) * n - s))
				: 0.5 * ((n -= 2) * n * (((s *= (1.525)) + 1) * n + s) + 2);
	}), SWING_FROM(n -> {
		double s = 1.70158;
		return n * n * ((s + 1) * n - s);
	}),

	SWING_TO(n -> {
		double s = 1.70158;
		return (n -= 1) * n * ((s + 1) * n + s) + 1;
	});

	private Function<Double, Double> func;

	private EasingMode(Function<Double, Double> func) {
		this.func = func;
	}

	public Function<Double, Double> function() {
		return func;
	}
}
