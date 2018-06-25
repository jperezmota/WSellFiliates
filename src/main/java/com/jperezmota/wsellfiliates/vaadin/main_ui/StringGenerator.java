package com.jperezmota.wsellfiliates.vaadin.main_ui;

import com.vaadin.shared.util.SharedUtil;

public class StringGenerator {
    static String[] strings = { "lorem", "ipsum", "dolor", "sit", "amet",
            "consectetur", "quid", "securi", "etiam", "tamquam", "eu", "fugiat",
            "nulla", "pariatur" };
    int stringCount = -1;

    String nextString(boolean capitalize) {
        if (++stringCount >= strings.length) {
            stringCount = 0;
        }
        return capitalize ? SharedUtil.capitalize(strings[stringCount])
                : strings[stringCount];
    }

}