package edu.jsu.mcis.cs310.tas_fa22;

public class Department {
    private final int id, terminal_id;
    private final String description;



//Constructor
    public Department(int id, String description, int terminal_id) {
        this.id = id;
        this.description = description;
        this.terminal_id = terminal_id;
    }

    //Getter Methods
    public int getId() {
        return id;
    }

    public int getTerminal_id() {
        return terminal_id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("#%s (%s), Terminal ID: %d", getId(), getDescription(), getTerminal_id());
    }

}
