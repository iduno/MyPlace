package com.air.advantage.data;

import java.util.Comparator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.Nullable;

/* renamed from: com.air.advantage.data.q1 */
/* loaded from: classes.dex */
public final class SortOrder implements Comparator<String> {
    private final String retrieveSnapshotNumberFromId(String str) {
        return StringsKt__StringsJVMKt.replace$default(str, "p", "", false, 4, null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
    @Override // java.util.Comparator
    public int compare(@Nullable String str, @Nullable String str2) {
        if (str == null || str2 == null) {
            return -1;
        }
        if (Intrinsics.areEqual(str, str2)) {
            return 0;
        }
        try {
            return Integer.parseInt(retrieveSnapshotNumberFromId(str)) > Integer.parseInt(retrieveSnapshotNumberFromId(str2)) ? 1 : -1;
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}