package ch.heigvd.wordoff.Util;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Challenge;
import ch.heigvd.wordoff.common.Model.Side;
import ch.heigvd.wordoff.common.Model.Slots.Slot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ChallengeFactory {
    List<Slot> slots;

    Side side;

    public ChallengeFactory(Side side) {
        this.side = side;
        this.slots = new ArrayList<>();
    }

    public ChallengeFactory add(Class<? extends Slot> slotClass) {
        try {
            Constructor<? extends Slot> ctor = slotClass.getConstructor(Side.class, Short.class);
            Slot slot = ctor.newInstance(side, (short)(slots.size() + 1));
            slots.add(slot);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return this;
    }

    public ChallengeFactory addAll(List<Class<? extends Slot>> slotsClass) {
        for(Class<? extends Slot> slotClass : slotsClass) {
            add(slotClass);
        }
        return this;
    }

    public Challenge create() {
        return new Challenge(slots);
    }
}
