package edu.jsu.mcis.cs310.tas_fa22;

public class Department {
    private int id, terminal_id;
    private String description;



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
        String results = "#"+ getId() + " ("+getDescription()+"), "+ "Terminal ID: "+ getTerminal_id();
        return results;
    }

}
