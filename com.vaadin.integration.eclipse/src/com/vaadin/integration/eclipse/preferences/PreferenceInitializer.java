package com.vaadin.integration.eclipse.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.vaadin.integration.eclipse.VaadinPlugin;

/**
 * Initializes the preferences to their default values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = VaadinPlugin.getInstance()
                .getPreferenceStore();
        store.setDefault(
                PreferenceConstants.UPDATE_NOTIFICATIONS_IN_NEW_PROJECTS, true);
        store.setDefault(PreferenceConstants.DISABLE_ALL_UPDATE_NOTIFICATIONS,
                false);
    }
}