package edu.jsu.mcis.cs310.tas_fa22;

import java.time.LocalDate;

public class Absenteeism {
    private final Employee employee;
    private final LocalDate payperiod;
    private final Double percentage;


    public Absenteeism(Employee employee, LocalDate payperiod, Double percentage) {
        this.employee = employee;
        this.payperiod = payperiod;
        this.percentage = percentage;
    }


    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getPayperiod() {
        return payperiod;
    }

    public Double getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        String result = String.format("#%s (Pay Period Starting %s): %.2f%%",
                employee.getBadge().getId(), payperiod.toString(), percentage*100 );

        return result;
    }
}
