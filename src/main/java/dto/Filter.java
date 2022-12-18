package dto;

import command.Operation;

import java.util.List;

public class Filter {
    private String column;
    private List<String> values;
    private Operation operations;

    public Filter(String column, List<String> values, Operation operations) {
        this.column = column;
        this.values = values;
        this.operations = operations;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Operation getOperations() {
        return operations;
    }

    public void setOperations(Operation operations) {
        this.operations = operations;
    }
}
