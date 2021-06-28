package electrodynamics.common.item.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeOxide implements ISubtype {
    vanadium, disulfur, trisulfur, calciumcarbonate, chromite, dititanium, sodiumcarbonate;

    @Override
    public String tag() {
	return "oxide" + name();
    }

    @Override
    public String forgeTag() {
	return "oxide/" + name();
    }

    @Override
    public boolean isItem() {
	return true;
    }
}
