package lt.viko.eif.kskrebe.candlebusiness.dto;

import java.math.BigDecimal;

/**
 * Vadovo suvestines duomenys puslapiui.
 */
public class ManagerDashboardResponse {

    /** Produktu kiekis. */
    private long productCount;

    /** Ingredientu kiekis. */
    private long ingredientCount;

    /** Klientu kiekis. */
    private long clientCount;

    /** Darbuotoju kiekis. */
    private long employeeCount;

    /** Bendra apyvarta. */
    private BigDecimal totalRevenue;

    /** Bendros islaidos. */
    private BigDecimal totalExpenses;

    /** Prognozuojamas likutis: apyvarta minus islaidos. */
    private BigDecimal balance;

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public long getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(long ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public long getClientCount() {
        return clientCount;
    }

    public void setClientCount(long clientCount) {
        this.clientCount = clientCount;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(long employeeCount) {
        this.employeeCount = employeeCount;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
