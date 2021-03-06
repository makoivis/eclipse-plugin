package com.vaadin.integration.eclipse.wizards;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jst.j2ee.web.project.facet.IWebFacetInstallDataModelProperties;
import org.eclipse.jst.servlet.ui.project.facet.WebFacetInstallPage;
import org.eclipse.jst.servlet.ui.project.facet.WebProjectFirstPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelSynchHelper;

import com.vaadin.integration.eclipse.IVaadinFacetInstallDataModelProperties;
import com.vaadin.integration.eclipse.VaadinFacetUtils;
import com.vaadin.integration.eclipse.configuration.VaadinFacetInstallDataModelProvider;
import com.vaadin.integration.eclipse.properties.VaadinVersionComposite;

/**
 * The first (main) page of the Vaadin top-level project creation wizard.
 *
 * This replaces WebProjectFirstPage when creating a Vaadin project through its
 * own wizard, and collects the key configuration items on the first page so
 * that the user can typically just click Finish after the first page.
 *
 * Some settings are omitted when creating a project through the Vaadin wizard.
 */
public class VaadinProjectFirstPage extends WebProjectFirstPage implements
        IVaadinFacetInstallDataModelProperties {

    private Combo projectTypeCombo;

    // private Text applicationNameField;
    // private Text applicationClassField;
    // private Text applicationPackageField;

    public VaadinProjectFirstPage(IDataModel model, String pageName) {
        super(model, pageName);

        setTitle("Vaadin Project");
        setDescription("Create a Vaadin Dynamic Web project.");
        // setImageDescriptor(J2EEUIPlugin.getDefault().getImageDescriptor(
        // J2EEUIPluginIcons.WEB_PROJECT_WIZARD_BANNER));
    }

    @Override
    protected String getModuleFacetID() {
        return VaadinFacetUtils.VAADIN_FACET_ID;
    }

    @Override
    protected Composite createTopLevelComposite(Composite parent) {
        Composite top = new Composite(parent, SWT.NONE);
        top.setLayout(new GridLayout());
        top.setLayoutData(new GridData(GridData.FILL_BOTH));
        createProjectGroup(top);
        createServerTargetComposite(top);
        createPrimaryFacetComposite(top);
        createPresetPanel(top);

        // Vaadin key settings on the first page
        createVaadinComposite(top);

        // createWorkingSetGroupPanel(top, new String[] { RESOURCE_WORKING_SET,
        // JAVA_WORKING_SET });

        return top;
    }

    // this is partly duplicated in VaadinCoreFacetInstallPage
    protected Composite createVaadinComposite(final Composite parent) {
        final Group group = new Group(parent, SWT.NONE);
        group.setLayoutData(gdhfill());
        group.setLayout(new GridLayout(1, false));
        group.setText("Vaadin"); //$NON-NLS-1$

        // synchronize fields with the Vaadin facet data model instead of the
        // project data model
        FacetDataModelMap map = (FacetDataModelMap) model
                .getProperty(FACET_DM_MAP);
        IDataModel vaadinFacetDataModel = map
                .getFacetDataModel(VaadinFacetUtils.VAADIN_FACET_ID);
        DataModelSynchHelper vaadinFacetSynchHelper = new DataModelSynchHelper(
                vaadinFacetDataModel);

        Label label = new Label(group, SWT.NONE);
        label.setLayoutData(gdhfill());
        label.setText("Deployment configuration:");

        projectTypeCombo = new Combo(group, SWT.READ_ONLY);
        projectTypeCombo.setLayoutData(gdhfill());
        for (String projectType : VaadinFacetInstallDataModelProvider.PROJECT_TYPES) {
            projectTypeCombo.add(projectType);
        }
        // first item is the default item
        projectTypeCombo.select(0);
        vaadinFacetSynchHelper.synchCombo(projectTypeCombo,
                VAADIN_PROJECT_TYPE, new Control[] { label });

        // Label label = new Label(group, SWT.NONE);
        // label.setLayoutData(gdhfill());
        // label.setText("Application name:");
        //
        // applicationNameField = new Text(group, SWT.BORDER);
        // applicationNameField.setLayoutData(gdhfill());
        // vaadinFacetSynchHelper.synchText(applicationNameField,
        // APPLICATION_NAME, new Control[] { label });
        //
        // label = new Label(group, SWT.NONE);
        // label.setLayoutData(gdhfill());
        // label.setText("Base package name:");
        //
        // applicationPackageField = new Text(group, SWT.BORDER);
        // applicationPackageField.setLayoutData(gdhfill());
        // vaadinFacetSynchHelper.synchText(applicationPackageField,
        // APPLICATION_PACKAGE, new Control[] { label });
        //
        // label = new Label(group, SWT.NONE);
        // label.setLayoutData(gdhfill());
        // label.setText("Application class name:");
        //
        // applicationClassField = new Text(group, SWT.BORDER);
        // applicationClassField.setLayoutData(gdhfill());
        // vaadinFacetSynchHelper.synchText(applicationClassField,
        // APPLICATION_CLASS, new Control[] { label });

        // Vaadin version selection
        VaadinVersionComposite versionComposite = new VaadinVersionComposite(
                group, SWT.NULL);
        versionComposite.createContents();

        versionComposite.setProject(null);
        versionComposite.selectLatestLocalVersion();

        // synch version string to model
        synchHelper.synchCombo(versionComposite.getVersionCombo(),
                VAADIN_VERSION, new Control[] {});

        return group;
    }

    @Override
    protected String[] getValidationPropertyNames() {
        String[] superProperties = super.getValidationPropertyNames();
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(superProperties));
        // validation of these relies on nested models in the project level
        // model - see VaadinProjectCreationDataModelProvider
        arrayList.add(APPLICATION_NAME);
        arrayList.add(APPLICATION_PACKAGE);
        arrayList.add(APPLICATION_CLASS);
        arrayList.add(IWebFacetInstallDataModelProperties.CONTEXT_ROOT);
        // validating this leads to strange behavior for Finish button
        // enabling/disabling when changing the value
        // arrayList.add(IWebFacetInstallDataModelProperties.CONFIG_FOLDER);
        return (String[]) arrayList.toArray(new String[0]);
    }

    private static final class WebFacetResources extends NLS {
        public static String pageTitle;
        // public static String pageDescription;
        public static String contextRootLabel;
        public static String contextRootLabelInvalid;
        public static String contentDirLabel;
        public static String contentDirLabelInvalid;

        static {
            initializeMessages(WebFacetInstallPage.class.getName(),
                    WebFacetResources.class);
        }
    }

}
