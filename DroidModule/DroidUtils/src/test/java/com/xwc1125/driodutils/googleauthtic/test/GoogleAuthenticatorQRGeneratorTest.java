/*
 * Copyright (c) 2014-2015 Enrico M. Crisostomo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice, this
 *     list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *   * Neither the name of the author nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.xwc1125.driodutils.googleauthtic.test;

import com.xwc1125.driodutils.googleauthtic.GoogleAuthenticatorKey;
import com.xwc1125.driodutils.googleauthtic.GoogleAuthenticatorQRGenerator;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GoogleAuthenticatorQRGeneratorTest {

    private GoogleAuthenticatorKey credentials;

    @Before
    public void setUp() throws Exception {
        credentials = new GoogleAuthenticatorKey("secretKey", 123456, new ArrayList<Integer>());
    }

    @Test
    public void testGetOtpAuthURL() throws Exception {
        assertEquals(
                "https://chart.googleapis.com/chart?chs=200x200&chld=M%7C0&cht=qr&chl=otpauth%3A%2F%2Ftotp%2FAcme%3Aalice%40example.com%3Fsecret%3DsecretKey%26issuer%3DAcme",
                GoogleAuthenticatorQRGenerator.getOtpAuthURL("Acme", "alice@example.com", credentials));
    }

    @Test
    public void testGetOtpAuthTotpURL() throws Exception {
        assertEquals(
                "otpauth://totp/Acme:alice@example.com?secret=secretKey&issuer=Acme",
                GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("Acme", "alice@example.com", credentials));

        // issuer and user with spaces
        assertEquals(
                "otpauth://totp/Acme%20Inc:alice%20at%20Inc?secret=secretKey&issuer=Acme+Inc",
                GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("Acme Inc", "alice at Inc", credentials));

        assertEquals(
                "otpauth://totp/Acme%20&%20%3Cfriends%3E:alice%2523?secret=secretKey&issuer=Acme+%26+%3Cfriends%3E",
                GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("Acme & <friends>", "alice%23", credentials));
    }

}