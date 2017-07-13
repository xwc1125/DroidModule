/**
 * Copyright © YOLANDA. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xwc1125.droidutils.thread.test;

import java.io.Serializable;

/**
 * Created in Aug 3, 2015 2:18:12 PM
 * 
 * @author YOLANDA
 */
public class Paramer implements Serializable {

	private static final long serialVersionUID = 1L;

	private int what;
	private int value;

	/**
	 * 
	 */
	public Paramer() {
		super();
	}

	/**
	 * @return the what
	 */
	public int getWhat() {
		return what;
	}

	/**
	 * @param what the what to set
	 */
	public void setWhat(int what) {
		this.what = what;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @param what
	 * @param value
	 */
	public Paramer(int what, int value) {
		super();
		this.what = what;
		this.value = value;
	}
}
