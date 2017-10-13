package ua.rd.cm.domain;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum TalkStatus {

    NEW("New"),
    IN_PROGRESS("In Progress"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private String name;

    TalkStatus(String name) {
        this.name = name;
    }

    public static TalkStatus getStatusByName(String statusName) {
        for (TalkStatus status : TalkStatus.values()) {
            if (status.getName().equals(statusName)) {
                return status;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean isStatusName(String name) {
        return this.name.equals(name);
    }

    public boolean canChangeTo(TalkStatus status) {
        return Transition.transitions.get(this).contains(status);
    }

    private enum Transition {

        FROM_NEW(NEW, IN_PROGRESS, APPROVED, REJECTED),
        FROM_IN_PROGRESS(IN_PROGRESS, IN_PROGRESS, APPROVED, REJECTED),
        FROM_APPROVED(APPROVED),
        FROM_REJECTED(REJECTED);

        static final Map<TalkStatus, Set<TalkStatus>> transitions = new EnumMap<>(TalkStatus.class);

        static {
            for (Transition transition : Transition.values()) {
                transitions.put(transition.from, EnumSet.copyOf(transition.to));
            }
        }

        TalkStatus from;
        EnumSet<TalkStatus> to;

        Transition(TalkStatus from, TalkStatus... to) {
            this.from = from;
            this.to = EnumSet.noneOf(TalkStatus.class);
            this.to.addAll(Arrays.asList(to));
        }
    }
}
