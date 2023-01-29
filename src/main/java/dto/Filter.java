package dto;

import command.Operation;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return Objects.equals(column, filter.column) && Objects.equals(values, filter.values) && operations == filter.operations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, values, operations);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("column", column)
                .append("values", values)
                .append("operations", operations)
                .toString();
    }
}
