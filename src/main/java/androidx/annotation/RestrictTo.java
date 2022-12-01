package androidx.annotation;

public @interface RestrictTo {
	RestrictTo.Scope[] value();

    public static enum Scope {
        LIBRARY,
        LIBRARY_GROUP,
        LIBRARY_GROUP_PREFIX,
        /** @deprecated */
        @Deprecated
        GROUP_ID,
        TESTS,
        SUBCLASSES;

        private Scope() {
        }
    }
}
