package com.jperezmota.wsellfiliates.utilities;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public interface HashingUtil {
	public static String hashString(String password) {
		HashFunction hashFunction = Hashing.sha256();
		String hashedPassword = hashFunction.hashString(password, Charsets.UTF_8).toString();
		
		return hashedPassword;
	}
}
