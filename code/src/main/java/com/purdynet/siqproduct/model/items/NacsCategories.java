package com.purdynet.siqproduct.model.items;

import java.util.Arrays;
import java.util.Optional;

public enum NacsCategories {
    C_01_00_00("01-00-00","Fuel Products","Fuel Products"),
    C_01_01_00("01-01-00","Fuel Products","Unleaded–Regular"),
    C_01_02_00("01-02-00","Fuel Products","Unleaded–Mid"),
    C_01_03_00("01-03-00","Fuel Products","Unleaded–Premium"),
    C_01_04_00("01-04-00","Fuel Products","Diesel"),
    C_01_05_00("01-05-00","Fuel Products","Propane Fuel"),
    C_01_06_00("01-06-00","Fuel Products","Kerosene Fuel"),
    C_01_07_00("01-07-00","Fuel Products","Other Fuel Products"),
    C_01_08_00("01-08-00","Fuel Products","Ethanol"),
    C_01_09_00("01-09-00","Fuel Products","Biodiesel"),
    C_02_00_00("02-00-00","Cigarettes","Cigarettes"),
    C_02_01_00("02-01-00","Cigarettes","Premium"),
    C_02_02_00("02-02-00","Cigarettes","Branded Discount"),
    C_02_03_00("02-03-00","Cigarettes","Sub-generic/Private Label"),
    C_02_04_00("02-04-00","Cigarettes","Imports"),
    C_02_05_00("02-05-00","Cigarettes","Fourth Tier"),
    C_03_00_00("03-00-00","Other Tobacco Products","Other Tobacco Products"),
    C_03_01_00("03-01-00","Other Tobacco Products","Smokeless"),
    C_03_02_00("03-02-00","Other Tobacco Products","Cigars"),
    C_03_03_00("03-03-00","Other Tobacco Products","Papers"),
    C_03_04_00("03-04-00","Other Tobacco Products","Pipes"),
    C_03_05_00("03-05-00","Other Tobacco Products","Pipe/Cigarette Tobacco"),
    C_03_06_00("03-06-00","Other Tobacco Products","Other Tobacco"),
    C_03_07_00("03-07-00","Other Tobacco Products","E-cigarettes"),
    C_04_00_00("04-00-00","Beer","Beer"),
    C_04_01_00("04-01-00","Beer","Super Premium"),
    C_04_02_00("04-02-00","Beer","Premium"),
    C_04_03_00("04-03-00","Beer","Popular"),
    C_04_04_00("04-04-00","Beer","Budget"),
    C_04_05_00("04-05-00","Beer","Imports"),
    C_04_06_00("04-06-00","Beer","Microbrews/Craft"),
    C_04_07_00("04-07-00","Beer","Malt Liquor"),
    C_04_08_00("04-08-00","Beer","Non-alcoholic"),
    C_04_09_00("04-09-00","Beer","Flavored Malt"),
    C_05_00_00("05-00-00","Wine","Wine"),
    C_05_01_00("05-01-00","Wine","Table/Varietal Wine"),
    C_05_02_00("05-02-00","Wine","Champagne/Sparkling Wine"),
    C_05_03_00("05-03-00","Wine","Coolers/Wine Cocktails"),
    C_05_04_00("05-04-00","Wine","Fortified Wine"),
    C_05_05_00("05-05-00","Wine","Other Wine"),
    C_06_00_00("06-00-00","Liquor","Liquor"),
    C_06_01_00("06-01-00","Liquor","Distilled Spirits"),
    C_06_02_00("06-02-00","Liquor","Prepared Cocktails"),
    C_06_03_00("06-03-00","Liquor","Cordials/Brandy/Cognac"),
    C_06_04_00("06-04-00","Liquor","Cocktail Mixes"),
    C_06_05_00("06-05-00","Liquor","Other Liquor"),
    C_07_00_00("07-00-00","Packaged Beverages (Non-alcoholic)","Packaged Beverages (Non-alcoholic)"),
    C_07_01_00("07-01-00","Packaged Beverages (Non-alcoholic)","Carbonated Soft Drinks"),
    C_07_02_00("07-02-00","Packaged Beverages (Non-alcoholic)","Iced Tea (Ready-to-drink)"),
    C_07_03_00("07-03-00","Packaged Beverages (Non-alcoholic)","Sports Drinks"),
    C_07_04_00("07-04-00","Packaged Beverages (Non-alcoholic)","Juice/Juice Drinks"),
    C_07_05_00("07-05-00","Packaged Beverages (Non-alcoholic)","Bottled Water Carbonated, still, flavored (non-fortified)"),
    C_07_06_00("07-06-00","Packaged Beverages (Non-alcoholic)","Other Packaged Beverages"),
    C_07_07_00("07-07-00","Packaged Beverages (Non-alcoholic)","Energy Drinks"),
    C_07_08_00("07-08-00","Packaged Beverages (Non-alcoholic)","Enhanced Water"),
    C_08_00_00("08-00-00","Candy","Candy"),
    C_08_01_00("08-01-00","Candy","Gum"),
    C_08_02_00("08-02-00","Candy","Candy Rolls, Mints, Drops"),
    C_08_03_00("08-03-00","Candy","Chocolate Bars/Packs"),
    C_08_04_00("08-04-00","Candy","Non-chocolate Bars/Packs"),
    C_08_05_00("08-05-00","Candy","Bagged or Repacked Peg Candy"),
    C_08_06_00("08-06-00","Candy","Novelties/Seasonal"),
    C_08_07_00("08-07-00","Candy","Change Makers/Penny Counter Goods"),
    C_08_08_00("08-08-00","Candy","Bulk Candy"),
    C_09_00_00("09-00-00","Fluid Milk Products","Fluid Milk Products"),
    C_09_01_00("09-01-00","Fluid Milk Products","Whole Milk"),
    C_09_02_00("09-02-00","Fluid Milk Products","2% Milk"),
    C_09_03_00("09-03-00","Fluid Milk Products","1% Milk"),
    C_09_04_00("09-04-00","Fluid Milk Products","Skim/Nonfat Milk"),
    C_09_05_00("09-05-00","Fluid Milk Products","Flavored Milk"),
    C_09_06_00("09-06-00","Fluid Milk Products","Cream/Creamer Products"),
    C_09_07_00("09-07-00","Fluid Milk Products","Other Ready-to-drink Fluid Milk Products"),
    C_10_00_00("10-00-00","Other Dairy & Deli Products","Other Dairy & Deli Products"),
    C_10_01_00("10-01-00","Other Dairy & Deli Products","Packaged Cheese"),
    C_10_02_00("10-02-00","Other Dairy & Deli Products","Eggs"),
    C_10_03_00("10-03-00","Other Dairy & Deli Products","Butter/Margarine"),
    C_10_04_00("10-04-00","Other Dairy & Deli Products","Cottage/Cream Cheese/Sour Cream"),
    C_10_05_00("10-05-00","Other Dairy & Deli Products","Yogurt"),
    C_10_06_00("10-06-00","Other Dairy & Deli Products","Other Dairy"),
    C_10_07_00("10-07-00","Other Dairy & Deli Products","Packaged Luncheon Meat"),
    C_10_08_00("10-08-00","Other Dairy & Deli Products","Other Packaged Meats"),
    C_10_09_00("10-09-00","Other Dairy & Deli Products","Lunch Packs"),
    C_11_00_00("11-00-00","Commissary & Other Packaged Products","Commissary & Other Packaged Products"),
    C_11_01_00("11-01-00","Commissary & Other Packaged Products","Sandwiches"),
    C_11_05_00("11-05-00","Commissary & Other Packaged Products","Salads & Sides"),
    C_11_06_00("11-06-00","Commissary & Other Packaged Products","Thaw, Heat & Eat"),
    C_11_07_00("11-07-00","Commissary & Other Packaged Products","Meals Ready-to-Eat"),
    C_12_00_00("12-00-00","Packaged Ice Cream/Novelties","Packaged Ice Cream/Novelties"),
    C_12_01_00("12-01-00","Packaged Ice Cream/Novelties","Premium Ice Cream"),
    C_12_02_00("12-02-00","Packaged Ice Cream/Novelties","Ice Cream"),
    C_12_03_00("12-03-00","Packaged Ice Cream/Novelties","Frozen Yogurt/Sherbet/Sorbet"),
    C_12_04_00("12-04-00","Packaged Ice Cream/Novelties","Frozen Novelties"),
    C_13_00_00("13-00-00","Frozen Foods","Frozen Foods"),
    C_13_01_00("13-01-00","Frozen Foods","Frozen Dinners/Entrees/Meals"),
    C_13_02_00("13-02-00","Frozen Foods","Frozen Pizza"),
    C_13_03_00("13-03-00","Frozen Foods","Other Frozen Foods"),
    C_14_00_00("14-00-00","Packaged Bread","Packaged Bread"),
    C_15_00_00("15-00-00","Salty Snacks","Salty Snacks"),
    C_15_01_00("15-01-00","Salty Snacks","Potato Chips"),
    C_15_02_00("15-02-00","Salty Snacks","Tortilla/Corn Chips"),
    C_15_03_00("15-03-00","Salty Snacks","Pretzels"),
    C_15_04_00("15-04-00","Salty Snacks","Nuts/Seeds"),
    C_15_05_00("15-05-00","Salty Snacks","Packaged Ready-to-eat Popcorn"),
    C_15_06_00("15-06-00","Salty Snacks","Crackers"),
    C_15_07_00("15-07-00","Salty Snacks","Other Salty Snacks"),
    C_15_08_00("15-08-00","Salty Snacks","Puffed Cheese"),
    C_15_09_00("15-09-00","Salty Snacks","Mixed");

    private final String categoryCode;
    private final String category;
    private final String subCategory;

    NacsCategories(String categoryCode, String category, String subCategory) {
        this.categoryCode = categoryCode;
        this.category = category;
        this.subCategory = subCategory;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public static NacsCategories fromName(String name) {
        for (NacsCategories nacsCategories : NacsCategories.values()) {
            if (nacsCategories.name().equalsIgnoreCase(name)) {
                return nacsCategories;
            }
        }
        return null;
    }
    public static Optional<NacsCategories> matchCategoryCode(final String categoryCode) {
        return Arrays.stream(NacsCategories.values()).filter(c -> categoryCode.equals(c.getCategoryCode())).findFirst();
    }
}
