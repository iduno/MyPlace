package com.air.advantage.aaservice.data;

import java.util.Comparator;

/* compiled from: SnapshotComparator.java */
/* renamed from: com.air.advantage.aaservice.o.q */
/* loaded from: classes.dex */
public class SnapshotComparator implements Comparator<String> {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
    @Override // java.util.Comparator
    /* renamed from: a, reason: avoid collision after fix types in other method and merged with bridge method [inline-methods] */
    public int compare(String str, String str2) {
        if (str != null && str2 != null) {
            if (str.equals(str2)) {
                return 0;
            }
            try {
                if (Integer.parseInt(a(str)) > Integer.parseInt(a(str2))) {
                    return 1;
                }
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    private String a(String str) {
        return str.replace("p", "");
    }
}