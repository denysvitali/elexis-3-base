package at.medevit.elexis.ehc.ui.vacdoc.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import at.medevit.elexis.ehc.ui.vacdoc.service.MeineImpfungenServiceComponent;
import at.medevit.elexis.ehc.vacdoc.service.MeineImpfungenService;
import ch.elexis.core.data.activator.CoreHub;
import ch.elexis.core.ui.preferences.SettingsPreferenceStore;
import ch.elexis.core.ui.preferences.inputs.PasswordFieldEditor;

public class MeineImpfungenPreferences extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	
	@Override
	public void init(IWorkbench workbench){
		setPreferenceStore(new SettingsPreferenceStore(CoreHub.mandantCfg));
		setDescription("Mandanten spezifische Einstellungen für meineimpfungen.");
	}
	
	@Override
	protected void createFieldEditors(){
		FieldEditor editor;
		editor = new FileFieldEditor(MeineImpfungenService.CONFIG_TRUSTSTORE_PATH, "Truststore",
			getFieldEditorParent());
		addField(editor);
		
		editor = new PasswordFieldEditor(MeineImpfungenService.CONFIG_TRUSTSTORE_PASS,
			"Truststore Passwort", getFieldEditorParent());
		addField(editor);
		
		editor = new FileFieldEditor(MeineImpfungenService.CONFIG_KEYSTORE_PATH, "Keystore",
			getFieldEditorParent());
		addField(editor);
		
		editor = new PasswordFieldEditor(MeineImpfungenService.CONFIG_KEYSTORE_PASS,
			"Keystore Passwort", getFieldEditorParent());
		addField(editor);
	}
	
	@Override
	public boolean performOk(){
		boolean ret = super.performOk();
		if (ret) {
			boolean configOk = MeineImpfungenServiceComponent.getService().updateConfiguration();
			if (!configOk) {
				MessageDialog.openError(getShell(), "meineimpfungen",
					"Es ist ein Fehler aufgetreten, bitte überprüfen Sie die Konfiguration.");
				return false;
			}
		}
		return ret;
	}
}
