package com.vaadin.integration.eclipse;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class VaadinPlugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "com.vaadin.integration.eclipse";

    public static final String VAADIN_PACKAGE_PREFIX = "com.vaadin.";
    public static final String TOOLKIT_PACKAGE_PREFIX = "com.itmill.toolkit.";
    public static final String APPLICATION_CLASS_NAME = "Application";

    public static final String ID_COMPILE_WS_APP = "compilews";

    // preference store keys

    // "true"/"false"/missing - if missing, check if >1 widgetset exists
    public static final String PREFERENCES_WIDGETSET_DIRTY = PLUGIN_ID + "."
            + "widgetsetDirty";
    // "OBF"/"PRETTY"/"DETAILED" or missing (default to "OBF")
    public static final String PREFERENCES_WIDGETSET_STYLE = PLUGIN_ID + "."
            + "widgetsetStyle";
    // a number of threads to use (-localWorkers) or missing
    public static final String PREFERENCES_WIDGETSET_PARALLELISM = PLUGIN_ID
            + "." + "widgetsetParallelism";

    private static VaadinPlugin instance = null;

    public VaadinPlugin() {
        instance = this;
    }

    public static VaadinPlugin getInstance() {
        return instance;
    }

}
