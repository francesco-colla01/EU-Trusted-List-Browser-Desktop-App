package progetto.graphics.demo;

import java.util.Vector;

public class StatusCheckBoxController {

    private Vector<String> selectedStatuses = new Vector<>();
    private Vector<String> allStatuses;

    public StatusCheckBoxController(Vector<String> allStatuses) {
        this.allStatuses = allStatuses;
    }

    public void onCheckBoxMark(String status) {
        selectedStatuses.add(status);
    }

    public void onCheckBoxUnmark(String countryName) {
        selectedStatuses.remove(countryName);
    }

    public Vector<String> getSelectedStatuses() {
        if (selectedStatuses.isEmpty())
            return allStatuses;
        return selectedStatuses;
    }
}
