package com.air.advantage.data;

import android.content.Context;
import android.content.Intent;
import com.air.advantage.di.LocalBroadcaster;
import com.air.advantage.libraryairconlightjson.UartConstants;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;
import timber.log.Timber;

@PurelyImplements({"SMAP\nDataAlarm.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DataAlarm.kt\ncom/air/advantage/data/DataAlarm\n+ 2 Strings.kt\nkotlin/text/StringsKt__StringsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,148:1\n107#2:149\n79#2,22:150\n1603#3,9:172\n1855#3:181\n1856#3:183\n1612#3:184\n1#4:182\n*S KotlinDebug\n*F\n+ 1 DataAlarm.kt\ncom/air/advantage/data/DataAlarm\n*L\n19#1:149\n19#1:150,22\n118#1:172,9\n118#1:181\n118#1:183\n118#1:184\n118#1:182\n*E\n"})
/* renamed from: com.air.advantage.data.f */
/* loaded from: classes.dex */
public final class DataAlarm extends DataScene {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public DataAlarm() {
    }

    private final void doUpdate() {
        Timber.forest.d("data has been updated for LightAlarm " + this.id + " " + this.name, new Object[0]);
        Intent intent = new Intent(UartConstants.LIGHT_ALARM_UPDATE);
        intent.putExtra("alarmID", this.id);
        ((LocalBroadcaster) KoinJavaComponent.get$default(LocalBroadcaster.class, null, null, 6, null)).sendBroadcast(intent);
    }

    private final boolean isLightsCollectionEqualForAlarmPurpose(HashMap<String, DataLight> hashMap) {
        HashMap<String, DataLight> hashMap2 = this.lights;
        Intrinsics.checkNotNull(hashMap2);
        int size = hashMap2.size();
        Intrinsics.checkNotNull(hashMap);
        if (size != hashMap.size()) {
            return false;
        }
        HashMap<String, DataLight> hashMap3 = this.lights;
        Intrinsics.checkNotNull(hashMap3);
        for (DataLight dataLight : hashMap3.values()) {
            Collection<DataLight> values = hashMap.values();
            Intrinsics.checkNotNullExpressionValue(values, "<get-values>(...)");
            ArrayList<DataLight> arrayList = new ArrayList();
            for (DataLight dataLight2 : values) {
                if (dataLight2 != null) {
                    arrayList.add(dataLight2);
                }
            }
            for (DataLight dataLight3 : arrayList) {
                Intrinsics.checkNotNull(dataLight);
                if (!Intrinsics.areEqual(dataLight.id, dataLight3.id)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean update(@Nullable Context context, @Nullable DataAlarm dataAlarm, @Nullable DataManager dataManager) {
        return update(context, dataAlarm, dataManager, false);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public DataAlarm(@NotNull String id, @Nullable String str, @Nullable String str2) {
        Intrinsics.checkNotNullParameter(id, "id");
        this.id = id;
        this.name = str;
        if (str2 != null) {
            int length = str2.length() - 1;
            int i10 = 0;
            boolean z7 = false;
            while (i10 <= length) {
                boolean z10 = Intrinsics.compare(str2.charAt(!z7 ? i10 : length), 32) <= 0;
                if (z7) {
                    if (!z10) {
                        break;
                    } else {
                        length--;
                    }
                } else if (z10) {
                    i10++;
                } else {
                    z7 = true;
                }
            }
            this.canMessages = str2.subSequence(i10, length + 1).toString();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean update(@NotNull DataAlarm lightAlarmSource, @Nullable DataManager dataManager, boolean z7) {
        boolean z10;
        String str;
        Intrinsics.checkNotNullParameter(lightAlarmSource, "lightAlarmSource");
        String str2 = lightAlarmSource.id;
        boolean z11 = true;
        if (str2 == null || ((str = this.id) != null && Intrinsics.areEqual(str, str2))) {
            z10 = false;
        } else {
            this.id = lightAlarmSource.id;
            z10 = true;
        }
        String str3 = lightAlarmSource.name;
        if (str3 != null) {
            String str4 = this.name;
            if (str4 == null || !Intrinsics.areEqual(str4, str3)) {
                this.name = lightAlarmSource.name;
                if (dataManager != null) {
                    dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, lightAlarmSource.name);
                }
                z10 = true;
            }
        } else if (z7 && this.name != null) {
            if (dataManager != null) {
                dataManager.add(AppMeasurementSdk.ConditionalUserProperty.NAME, null);
            }
            z10 = true;
        }
        Boolean bool = lightAlarmSource.timerEnabled;
        if (bool != null) {
            Boolean bool2 = this.timerEnabled;
            if (bool2 == null || !Intrinsics.areEqual(bool2, bool)) {
                this.timerEnabled = lightAlarmSource.timerEnabled;
                if (dataManager != null) {
                    dataManager.add("timerEnabled", lightAlarmSource.timerEnabled);
                }
                z10 = true;
            }
        } else if (z7 && this.timerEnabled != null) {
            if (dataManager != null) {
                dataManager.add("timerEnabled", null);
            }
            z10 = true;
        }
        Integer num = lightAlarmSource.startTime;
        if (num != null) {
            Integer num2 = this.startTime;
            if (num2 == null || !Intrinsics.areEqual(num2, num)) {
                this.startTime = lightAlarmSource.startTime;
                if (dataManager != null) {
                    dataManager.add("startTime", lightAlarmSource.startTime);
                }
                z10 = true;
            }
        } else if (z7 && this.startTime != null) {
            if (dataManager != null) {
                dataManager.add("startTime", null);
            }
            z10 = true;
        }
        Integer num3 = lightAlarmSource.activeDays;
        if (num3 != null) {
            Integer num4 = this.activeDays;
            if (num4 == null || !Intrinsics.areEqual(num4, num3)) {
                this.activeDays = lightAlarmSource.activeDays;
                if (dataManager != null) {
                    dataManager.add("activeDays", lightAlarmSource.activeDays);
                }
                z10 = true;
            }
        } else if (z7 && this.activeDays != null) {
            if (dataManager != null) {
                dataManager.add("activeDays", null);
            }
            z10 = true;
        }
        HashMap<String, DataLight> hashMap = lightAlarmSource.lights;
        if (hashMap != null) {
            if (this.lights == null || !isLightsCollectionEqualForAlarmPurpose(hashMap)) {
                this.lights = lightAlarmSource.lights;
                if (dataManager != null) {
                    dataManager.addSetValue("lights", lightAlarmSource.lights);
                }
            } else {
                z11 = z10;
            }
        } else if (this.lights != null) {
            this.lights = hashMap;
            if (dataManager != null) {
                dataManager.add("lights", lightAlarmSource.lights);
            }
        }
        if (z11) {
            doUpdate();
        }
        return z11;
    }
}