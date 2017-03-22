package ua.rd.cm.domain;

import java.util.*;

public enum TalkStatus {
    NEW("New"), IN_PROGRESS("In Progress"), APPROVED("Approved"), REJECTED("Rejected");

    TalkStatus(String name) {
        this.name = name;
    }

    private String name;

    private enum Transition {

        FROM_NEW(NEW, IN_PROGRESS, APPROVED, REJECTED),
        FROM_IN_PROGRESS(IN_PROGRESS, IN_PROGRESS, APPROVED, REJECTED),
        FROM_APPROVED(APPROVED),
        FROM_REJECTED(REJECTED);

        TalkStatus from;
        EnumSet<TalkStatus> to;

        Transition(TalkStatus from, TalkStatus... to) {
            this.from = from;
            this.to = EnumSet.noneOf(TalkStatus.class);
            this.to.addAll(Arrays.asList(to));
        }

        static final Map<TalkStatus, Set<TalkStatus>> transitions = new EnumMap<>(TalkStatus.class);

        static {
            for (Transition transition : Transition.values()) {
                transitions.put(transition.from, EnumSet.copyOf(transition.to));
            }
        }
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

    public static TalkStatus getStatusByName(String statusName) {
        for (TalkStatus status : TalkStatus.values()) {
            if (status.getName().equals(statusName)) {
                return status;
            }
        }
        return null;
    }
}
