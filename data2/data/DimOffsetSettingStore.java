package com.air.advantage.data;

import com.air.advantage.jsondata.MasterStore;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@PurelyImplements({"SMAP\nDimOffsetSettingStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DimOffsetSettingStore.kt\ncom/air/advantage/data/DimOffsetSettingStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,38:1\n1#2:39\n*E\n"})
/* renamed from: com.air.advantage.data.b1 */
/* loaded from: classes.dex */
public final class DimOffsetSettingStore {

    @NotNull
    @JvmField
    public ArrayList<Item> dataStoreItemsDimmableList = new ArrayList<>();

    public final void clear() {
        synchronized (MasterStore.class) {
            this.dataStoreItemsDimmableList.clear();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Nullable
    public final Item getItemAtPositionForDimOffsetSetup(int i10) {
        synchronized (MasterStore.class) {
            if (i10 < this.dataStoreItemsDimmableList.size()) {
                return this.dataStoreItemsDimmableList.get(i10);
            }
            Unit unit = Unit.INSTANCE;
            return null;
        }
    }

    public final void initList() {
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            this.dataStoreItemsDimmableList.clear();
            helper.dataFavourites.lightStore.initDMLightsForDimOffsetSetup();
            helper.dataFavourites.thingStore.initDMThingsForDimOffsetSetup();
            Iterator<DataLight> it = helper.dataFavourites.lightStore.getLightsDimmableInDM().iterator();
            while (it.hasNext()) {
                this.dataStoreItemsDimmableList.add(new Item(it.next()));
            }
            Iterator<Item> it2 = helper.dataFavourites.thingStore.getThingsDimmableInDM().iterator();
            while (it2.hasNext()) {
                this.dataStoreItemsDimmableList.add(new Item(it2.next()));
            }
            Unit unit = Unit.INSTANCE;
        }
    }
}