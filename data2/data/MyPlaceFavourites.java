package com.air.advantage.data;

import android.content.Context;
import com.air.advantage.SharedPreferencesStore;
import com.air.advantage.jsondata.MasterStore;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PurelyImplements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.koin.java.KoinJavaComponent;

@PurelyImplements({"SMAP\nMyPlaceFavourites.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MyPlaceFavourites.kt\ncom/air/advantage/data/MyPlaceFavourites\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,239:1\n1603#2,9:240\n1855#2:249\n1856#2:251\n1612#2:252\n1603#2,9:253\n1855#2:262\n1856#2:264\n1612#2:265\n1603#2,9:266\n1855#2:275\n1856#2:277\n1612#2:278\n1603#2,9:279\n1855#2:288\n1856#2:290\n1612#2:291\n1#3:250\n1#3:263\n1#3:276\n1#3:289\n*S KotlinDebug\n*F\n+ 1 MyPlaceFavourites.kt\ncom/air/advantage/data/MyPlaceFavourites\n*L\n63#1:240,9\n63#1:249\n63#1:251\n63#1:252\n88#1:253,9\n88#1:262\n88#1:264\n88#1:265\n165#1:266,9\n165#1:275\n165#1:277\n165#1:278\n179#1:279,9\n179#1:288\n179#1:290\n179#1:291\n63#1:250\n88#1:263\n165#1:276\n179#1:289\n*E\n"})
/* renamed from: com.air.advantage.data.m1 */
/* loaded from: classes.dex */
public final class MyPlaceFavourites {

    @NotNull
    public static final a Companion = new a(null);
    private static final String LOG_TAG = MyPlaceFavourites.class.getSimpleName();
    public static final int MAX_NO_OF_FAVOURITE_GROUPS = 10;
    private boolean favouritesPaused;

    @NotNull
    private final List<String> myPlaceFavourites = new ArrayList();

    @NotNull
    private final List<Integer> myPlaceFavouritesItemType = new ArrayList();

    @Nullable
    private c onMyPlaceFavouriteChangeListener;

    /* renamed from: com.air.advantage.data.m1$a */
    public static final class a {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] (LINE:1) call: com.air.advantage.data.m1.a.<init>():void type: THIS */
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        private a() {
        }
    }

    /* renamed from: com.air.advantage.data.m1$b */
    private static final class b implements Comparator<Item> {

        @NotNull
        private final WeakReference<Context> contextWeakReference;

        public b(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            this.contextWeakReference = new WeakReference<>(context);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        /* JADX DEBUG: Method merged with bridge method: compare(Ljava/lang/Object;Ljava/lang/Object;)I */
        @Override // java.util.Comparator
        public int compare(@NotNull Item item1, @NotNull Item item2) {
            int thingClick;
            Intrinsics.checkNotNullParameter(item1, "item1");
            Intrinsics.checkNotNullParameter(item2, "item2");
            Context context = this.contextWeakReference.get();
            if (context == null) {
                return 1;
            }
            Integer num = item1.itemType;
            int i10 = 0;
            if (num != null && num.intValue() == 1) {
                thingClick = ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, item1.id);
            } else {
                Integer num2 = item1.itemType;
                thingClick = (num2 != null && num2.intValue() == 2) ? ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getThingClick(context, item1.id) : 0;
            }
            Integer num3 = item2.itemType;
            if (num3 != null && num3.intValue() == 1) {
                i10 = ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, item2.id);
            } else {
                Integer num4 = item2.itemType;
                if (num4 != null && num4.intValue() == 2) {
                    i10 = ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getThingClick(context, item2.id);
                }
            }
            return Intrinsics.compare(i10, thingClick);
        }
    }

    /* renamed from: com.air.advantage.data.m1$c */
    public interface c {
        void onMyPlaceFavouriteAdded(@Nullable String str, int i10, int i11);

        void onMyPlaceFavouriteMoved(int i10, int i11);

        void onMyPlaceFavouriteRemoved(@Nullable String str, int i10, int i11);

        void onMyPlaceFavouriteUpdated(@Nullable String str, int i10);
    }

    private final int getMyPlaceFavouriteLightType(int i10) {
        DataLight lightFavourite = getLightFavourite(i10);
        if (lightFavourite != null) {
            return lightFavourite.typeFavourite();
        }
        return 0;
    }

    private final int getMyPlaceFavouriteThingType(int i10) {
        Item thingFavourite = getThingFavourite(i10);
        if (thingFavourite != null) {
            return thingFavourite.getFavouriteType();
        }
        return 0;
    }

    private final void workOutMyPlaceFavourites(Context context, MasterStore masterStore) {
        DataFavourites dataFavourites = masterStore.dataFavourites;
        this.myPlaceFavourites.clear();
        this.myPlaceFavouritesItemType.clear();
        String firstGarageItemIdFromTheItemList = dataFavourites.thingStore.getFirstGarageItemIdFromTheItemList();
        ArrayList arrayList = new ArrayList();
        if (firstGarageItemIdFromTheItemList == null || masterStore.dataFavourites.thingStore.getItemList().size() - masterStore.dataFavourites.thingStore.numberOfGroups() != 1) {
            List<String> itemList = dataFavourites.thingStore.getItemList();
            ArrayList<String> arrayList2 = new ArrayList();
            for (String str : itemList) {
                if (str != null) {
                    arrayList2.add(str);
                }
            }
            for (String str2 : arrayList2) {
                Item item = dataFavourites.thingStore.getItem(str2);
                boolean z7 = false;
                if (item != null && item.getFavouriteType() == 12) {
                    z7 = true;
                }
                if (z7) {
                    if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).hasThingsGroup(context, str2)) {
                        if (Intrinsics.areEqual(item.buttonType, "none")) {
                            ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).o1(context, str2);
                        } else {
                            List<String> list = this.myPlaceFavourites;
                            list.add(list.size(), str2);
                            this.myPlaceFavouritesItemType.add(item.itemType);
                        }
                    }
                } else if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).getThingClick(context, str2) > 0 && !Intrinsics.areEqual(str2, firstGarageItemIdFromTheItemList)) {
                    arrayList.add(arrayList.size(), new Item(item));
                }
            }
        }
        DataSystem dataSystem = masterStore.dataMaster.system;
        if (dataSystem.drawThingsTab != null && Intrinsics.areEqual(dataSystem.drawLightsTab, Boolean.TRUE)) {
            List<String> lightList = dataFavourites.lightStore.getLightList();
            ArrayList<String> arrayList3 = new ArrayList();
            for (String str3 : lightList) {
                if (str3 != null) {
                    arrayList3.add(str3);
                }
            }
            for (String str4 : arrayList3) {
                DataLight light = dataFavourites.lightStore.getLight(str4);
                Intrinsics.checkNotNull(light);
                if (light.typeFavourite() == 4) {
                    if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).E0(context, str4)) {
                        List<String> list2 = this.myPlaceFavourites;
                        list2.add(list2.size(), str4);
                        this.myPlaceFavouritesItemType.add(1);
                    }
                } else if (((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).J0(context, light.id) > 0) {
                    arrayList.add(arrayList.size(), new Item(light));
                }
            }
        }
        Collections.sort(arrayList, new b(context));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Item item2 = (Item) it.next();
            List<String> list3 = this.myPlaceFavourites;
            list3.add(list3.size(), item2.id);
            this.myPlaceFavouritesItemType.add(item2.itemType);
            if (this.myPlaceFavourites.size() >= 10) {
                return;
            }
        }
    }

    @Nullable
    public final DataLight getLightFavourite(int i10) {
        String str;
        DataLight light;
        if (i10 < 0 || i10 >= this.myPlaceFavourites.size() || (str = this.myPlaceFavourites.get(i10)) == null) {
            return null;
        }
        synchronized (MasterStore.class) {
            light = MasterStore.helper.getInstance().dataFavourites.lightStore.getLight(str);
        }
        return light;
    }

    public final int getMyPlaceFavouriteType(int i10) {
        Integer num = this.myPlaceFavouritesItemType.get(i10);
        if (num != null && num.intValue() == 1) {
            return getMyPlaceFavouriteLightType(i10);
        }
        if (num != null && num.intValue() == 2) {
            return getMyPlaceFavouriteThingType(i10);
        }
        return 0;
    }

    public final int getNumbersOfFavouriteGroup(@Nullable Context context, @NotNull MasterStore masterStore) {
        Intrinsics.checkNotNullParameter(masterStore, "masterStore");
        DataFavourites dataFavourites = masterStore.dataFavourites;
        List<String> itemList = dataFavourites.thingStore.getItemList();
        ArrayList<String> arrayList = new ArrayList();
        for (String str : itemList) {
            if (str != null) {
                arrayList.add(str);
            }
        }
        int i10 = 0;
        for (String str2 : arrayList) {
            Item item = dataFavourites.thingStore.getItem(str2);
            if ((item != null && item.getFavouriteType() == 12) && ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).hasThingsGroup(context, str2) && !Intrinsics.areEqual(item.buttonType, "none")) {
                i10++;
            }
        }
        DataSystem dataSystem = masterStore.dataMaster.system;
        if (dataSystem.drawThingsTab != null && Intrinsics.areEqual(dataSystem.drawLightsTab, Boolean.TRUE)) {
            List<String> lightList = dataFavourites.lightStore.getLightList();
            ArrayList<String> arrayList2 = new ArrayList();
            for (String str3 : lightList) {
                if (str3 != null) {
                    arrayList2.add(str3);
                }
            }
            for (String str4 : arrayList2) {
                DataLight light = dataFavourites.lightStore.getLight(str4);
                Intrinsics.checkNotNull(light);
                if (light.typeFavourite() == 4 && ((SharedPreferencesStore) KoinJavaComponent.get$default(SharedPreferencesStore.class, null, null, 6, null)).E0(context, str4)) {
                    i10++;
                }
            }
        }
        return i10;
    }

    @Nullable
    public final Item getThingFavourite(int i10) {
        String str;
        Item item;
        if (i10 < 0 || i10 >= this.myPlaceFavourites.size() || (str = this.myPlaceFavourites.get(i10)) == null) {
            return null;
        }
        synchronized (MasterStore.class) {
            item = MasterStore.helper.getInstance().dataFavourites.thingStore.getItem(str);
        }
        return item;
    }

    public final int numberOfMyPlaceFavourites() {
        if (this.favouritesPaused) {
            return 0;
        }
        int size = this.myPlaceFavourites.size();
        if (size <= 10) {
            return size;
        }
        return 10;
    }

    public final void setMyPlaceFavouritesPaused(@NotNull Context context, boolean z7) {
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (MasterStore.class) {
            MasterStore helper = MasterStore.helper.getInstance();
            if (z7) {
                int numberOfMyPlaceFavourites = numberOfMyPlaceFavourites();
                workOutMyPlaceFavourites(context, helper);
                this.favouritesPaused = true;
                c cVar = this.onMyPlaceFavouriteChangeListener;
                if (cVar != null) {
                    Intrinsics.checkNotNull(cVar);
                    cVar.onMyPlaceFavouriteRemoved("Paused", 0, numberOfMyPlaceFavourites);
                }
            } else {
                this.favouritesPaused = false;
                workOutMyPlaceFavourites(context, helper);
                if (this.onMyPlaceFavouriteChangeListener != null) {
                    int numberOfMyPlaceFavourites2 = numberOfMyPlaceFavourites();
                    c cVar2 = this.onMyPlaceFavouriteChangeListener;
                    Intrinsics.checkNotNull(cVar2);
                    cVar2.onMyPlaceFavouriteAdded("Paused", 0, numberOfMyPlaceFavourites2);
                }
            }
            Unit unit = Unit.INSTANCE;
        }
    }

    public final void setOnMyPlaceFavouriteChangeListener(@Nullable c cVar) {
        this.onMyPlaceFavouriteChangeListener = cVar;
    }
}