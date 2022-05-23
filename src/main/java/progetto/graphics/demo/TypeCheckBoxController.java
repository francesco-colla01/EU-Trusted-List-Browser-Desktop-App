package progetto.graphics.demo;

import java.util.Map;
import java.util.Vector;

public class TypeCheckBoxController {

    private Vector<String> selectdTypes = new Vector<>();
    private Vector<String> allTypes;

    public TypeCheckBoxController(Vector<String> allTypes) {
        this.allTypes = allTypes;
    }

    public void onCheckBoxMark(String countryName) {
        selectdTypes.add(countryName);
    }

    public void onCheckBoxUnmark(String countryName) {
        selectdTypes.remove(countryName);
    }

    public Vector<String> getSelectdTypes() {
        if (selectdTypes.isEmpty())
            return allTypes;
        return selectdTypes;
    }
}
