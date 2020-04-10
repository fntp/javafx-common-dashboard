package de.vitox.ratolotl.database.row;

import java.util.Collections;
import java.util.List;

public class Rows {

    private List<Row> rows;

    public Rows(List<Row> rows) {
        this.rows = rows;
    }

    public Rows() {
        this(Collections.emptyList());
    }

    public Row first() {
        if (this.rows.size() > 0) {
            return this.rows.get(0);
        }
        return null;
    }

    public Row get(int index) {
        if (index < 0 || index >= this.rows.size()) {
            return null;
        }
        return this.rows.get(index);
    }

    public List<Row> all() {
        return this.rows;
    }
}
