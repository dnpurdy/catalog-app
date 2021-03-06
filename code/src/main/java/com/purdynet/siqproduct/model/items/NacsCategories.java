package com.purdynet.siqproduct.model.items;

import java.util.Arrays;
import java.util.Optional;

public enum NacsCategories {
    C_01_00_00("01-00-00","Fuel Products","Fuel Products"),
    C_01_01_00("01-01-00","Fuel Products","Unleaded - Regular"),
    C_01_02_00("01-02-00","Fuel Products","Unleaded - Mid"),
    C_01_03_00("01-03-00","Fuel Products","Unleaded - Premium"),
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
    C_07_05_00("07-05-00","Packaged Beverages (Non-alcoholic)","Bottled Water"),
    C_07_06_00("07-06-00","Packaged Beverages (Non-alcoholic)","Other Packaged Beverages (Non-alcoholic)"),
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
    C_15_09_00("15-09-00","Salty Snacks","Mixed"),
    C_16_00_00("16-00-00","Packaged Sweet Snacks","Packaged Sweet Snacks"),
    C_16_01_00("16-01-00","Packaged Sweet Snacks","Snack Cakes/Pastries/Desserts"),
    C_16_02_00("16-02-00","Packaged Sweet Snacks","Muffins/Donuts"),
    C_16_03_00("16-03-00","Packaged Sweet Snacks","Cookies"),
    C_17_00_00("17-00-00","Alternative Snacks","Alternative Snacks"),
    C_17_01_00("17-01-00","Alternative Snacks","Meat Snacks"),
    C_17_02_00("17-02-00","Alternative Snacks","Granola/Fruit Snacks"),
    C_17_03_00("17-03-00","Alternative Snacks","Health/Energy/Protein Bars"),
    C_17_04_00("17-04-00","Alternative Snacks","Other Alternative Snacks"),
    C_18_00_00("18-00-00","Perishable Grocery","Perishable Grocery"),
    C_18_01_00("18-01-00","Perishable Grocery","Fruits"),
    C_18_02_00("18-02-00","Perishable Grocery","Vegetables"),
    C_18_03_00("18-03-00","Perishable Grocery","Service Deli Meats"),
    C_18_04_00("18-04-00","Perishable Grocery","Service Deli Cheese"),
    C_18_05_00("18-05-00","Perishable Grocery","Service Deli Salads"),
    C_18_06_00("18-06-00","Perishable Grocery","Other Service Deli"),
    C_19_00_00("19-00-00","Edible Grocery","Edible Grocery"),
    C_19_01_00("19-01-00","Edible Grocery","Packaged Coffee/Tea"),
    C_19_02_00("19-02-00","Edible Grocery","Breakfast Cereal"),
    C_19_03_00("19-03-00","Edible Grocery","Condiments"),
    C_19_04_00("19-04-00","Edible Grocery","Other Edible Grocery"),
    C_19_05_00("19-05-00","Edible Grocery","Water/Beverage Enhancers"),
    C_20_00_00("20-00-00","Non-edible Grocery","Non-edible Grocery"),
    C_20_01_00("20-01-00","Non-edible Grocery","Laundry Care"),
    C_20_02_00("20-02-00","Non-edible Grocery","Dish Care"),
    C_20_03_00("20-03-00","Non-edible Grocery","Household Care"),
    C_20_04_00("20-04-00","Non-edible Grocery","Paper/Plastic/Foil Products"),
    C_20_05_00("20-05-00","Non-edible Grocery","Pet Care"),
    C_20_06_00("20-06-00","Non-edible Grocery","Other Non-edible Grocery"),
    C_21_00_00("21-00-00","Health & Beauty Care","Health & Beauty Care"),
    C_21_01_00("21-01-00","Health & Beauty Care","Analgesics"),
    C_21_02_00("21-02-00","Health & Beauty Care","Cough & Cold Remedies"),
    C_21_03_00("21-03-00","Health & Beauty Care","Stomach Remedies"),
    C_21_04_00("21-04-00","Health & Beauty Care","Vitamins/Supplements"),
    C_21_05_00("21-05-00","Health & Beauty Care","Other Internal OTC Medications"),
    C_21_06_00("21-06-00","Health & Beauty Care","Grooming Aids"),
    C_21_07_00("21-07-00","Health & Beauty Care","Feminine Hygiene"),
    C_21_08_00("21-08-00","Health & Beauty Care","Family Planning"),
    C_21_09_00("21-09-00","Health & Beauty Care","Baby Care"),
    C_21_10_00("21-10-00","Health & Beauty Care","Skin Care/Lotions/External Care"),
    C_21_11_00("21-11-00","Health & Beauty Care","Cosmetics"),
    C_21_12_00("21-12-00","Health & Beauty Care","Other HBC"),
    C_21_13_00("21-13-00","Health & Beauty Care","Liquid Vitamins, Supplements & Energy Shots"),
    C_21_14_00("21-14-00","Health & Beauty Care","Smoking Cessation"),
    C_22_00_00("22-00-00","General Merchandise","General Merchandise"),
    C_22_02_00("22-02-00","General Merchandise","Batteries"),
    C_22_03_00("22-03-00","General Merchandise","Film/Photo"),
    C_22_04_00("22-04-00","General Merchandise","School/Office Supplies"),
    C_22_05_00("22-05-00","General Merchandise","Greeting/Gift/Novelties/Toys/Recreational Equipment"),
    C_22_06_00("22-06-00","General Merchandise","Trading Cards"),
    C_22_07_00("22-07-00","General Merchandise","Wearables/Apparel"),
    C_22_08_00("22-08-00","General Merchandise","Smoking Accessories"),
    C_22_09_00("22-09-00","General Merchandise","Video/Audio Tapes"),
    C_22_10_00("22-10-00","General Merchandise","Hardware/Tools/Housewares"),
    C_22_11_00("22-11-00","General Merchandise","Floral"),
    C_22_12_00("22-12-00","General Merchandise","Seasonal"),
    C_22_13_00("22-13-00","General Merchandise","Other GM"),
    C_22_14_00("22-14-00","General Merchandise","Telecommunications Hardware"),
    C_22_15_00("22-15-00","General Merchandise","Propane Exchanges"),
    C_23_00_00("23-00-00","Publications","Publications"),
    C_23_01_00("23-01-00","Publications","Newspapers"),
    C_23_02_00("23-02-00","Publications","Magazines/Tabloids"),
    C_23_03_00("23-03-00","Publications","Adult Magazines"),
    C_23_04_00("23-04-00","Publications","Paperbacks/Books"),
    C_23_05_00("23-05-00","Publications","Comics"),
    C_23_06_00("23-06-00","Publications","Traders"),
    C_23_07_00("23-07-00","Publications","Maps"),
    C_23_08_00("23-08-00","Publications","Other Publications"),
    C_24_00_00("24-00-00","Automotive Products","Automotive Products"),
    C_24_01_00("24-01-00","Automotive Products","Motor Oil"),
    C_24_02_00("24-02-00","Automotive Products","Anti-freeze/Coolants/Window Solvents"),
    C_24_03_00("24-03-00","Automotive Products","Transmission/Brake Fluids"),
    C_24_04_00("24-04-00","Automotive Products","Car Care"),
    C_24_05_00("24-05-00","Automotive Products","Other Additives"),
    C_24_06_00("24-06-00","Automotive Products","Other Automotive"),
    C_25_00_00("25-00-00","Automotive Services","Automotive Services"),
    C_25_01_00("25-01-00","Automotive Services","Lube/Oil Change"),
    C_25_02_00("25-02-00","Automotive Services","Vacuum/Air/Water"),
    C_25_03_00("25-03-00","Automotive Services","Car Wash"),
    C_25_04_00("25-04-00","Automotive Services","Auto Repair"),
    C_25_05_00("25-05-00","Automotive Services","Inspection Services"),
    C_25_06_00("25-06-00","Automotive Services","Other Automotive Services"),
    C_26_00_00("26-00-00","Store Services","Store Services"),
    C_26_01_00("26-01-00","Store Services","Pay Phones"),
    C_26_02_00("26-02-00","Store Services","Video Rental"),
    C_26_03_00("26-03-00","Store Services","Money Orders/Money Grams"),
    C_26_04_00("26-04-00","Store Services","Check Cashing"),
    C_26_05_00("26-05-00","Store Services","ATM"),
    C_26_06_00("26-06-00","Store Services","Copy/Fax"),
    C_26_07_00("26-07-00","Store Services","Postal/UPS"),
    C_26_08_00("26-08-00","Store Services","Vending"),
    C_26_09_00("26-09-00","Store Services","Tickets"),
    C_26_10_00("26-10-00","Store Services","Home Delivery"),
    C_26_11_00("26-11-00","Store Services","Amusements"),
    C_26_12_00("26-12-00","Store Services","Licenses"),
    C_26_13_00("26-13-00","Store Services","Other Store Services"),
    C_26_14_00("26-14-00","Store Services","Pre-paid Cards (Fee-only)"),
    C_27_00_00("27-00-00","Lottery/Gaming","Lottery/Gaming"),
    C_27_01_00("27-01-00","Lottery/Gaming","Lotto"),
    C_27_02_00("27-02-00","Lottery/Gaming","Scratch Tickets"),
    C_27_03_00("27-03-00","Lottery/Gaming","Video Gaming Revenue"),
    C_27_04_00("27-04-00","Lottery/Gaming","Slot Machine Revenue"),
    C_28_00_00("28-00-00","Ice","Ice"),
    C_29_01_02("29-01-02","Foodservice Prepared On-site","Chicken (Proprietary/Control Brand)"),
    C_29_02_02("29-02-02","Foodservice Prepared On-site","Mexican (Proprietary/Control Brand)"),
    C_29_03_02("29-03-02","Foodservice Prepared On-site","Pizza (Proprietary/Control Brand)"),
    C_29_05_02("29-05-02","Foodservice Prepared On-site","Hot Dogs/Roller Grill Products (Proprietary/Control Brand)"),
    C_29_06_02("29-06-02","Foodservice Prepared On-site","Hamburgers (Proprietary/Control Brand)"),
    C_29_07_02("29-07-02","Foodservice Prepared On-site","Sandwiches/Wraps (Proprietary/Control Brand)"),
    C_29_08_02("29-08-02","Foodservice Prepared On-site","Frozen Treats (Proprietary/Control Brand)"),
    C_29_09_02("29-09-02","Foodservice Prepared On-site","Bakery (Proprietary/Control Brand)"),
    C_29_10_02("29-10-02","Foodservice Prepared On-site","Soup and Salad (Proprietary/Control Brand)"),
    C_29_11_02("29-11-02","Foodservice Prepared On-site","Other Cuisine (Proprietary/Control Brand)"),
    C_30_00_00("30-00-00","Hot Dispensed Beverages","Hot Dispensed Beverages"),
    C_30_01_00("30-01-00","Hot Dispensed Beverages","Coffee"),
    C_30_02_00("30-02-00","Hot Dispensed Beverages","Hot Tea"),
    C_30_03_00("30-03-00","Hot Dispensed Beverages","Hot Chocolate"),
    C_30_04_00("30-04-00","Hot Dispensed Beverages","Cappuccino/Specialty Coffee Drinks"),
    C_30_05_00("30-05-00","Hot Dispensed Beverages","Refills"),
    C_30_06_00("30-06-00","Hot Dispensed Beverages","Coffee Club Mugs"),
    C_30_07_00("30-07-00","Hot Dispensed Beverages","Other Hot Dispensed Beverages"),
    C_31_00_00("31-00-00","Cold Dispensed Beverages","Cold Dispensed Beverages"),
    C_31_01_00("31-01-00","Cold Dispensed Beverages","Fountain–Carbonated"),
    C_31_02_00("31-02-00","Cold Dispensed Beverages","Fountain–Non-carbonated"),
    C_31_03_00("31-03-00","Cold Dispensed Beverages","Fountain–Sports Drinks"),
    C_31_04_00("31-04-00","Cold Dispensed Beverages","Refills"),
    C_31_05_00("31-05-00","Cold Dispensed Beverages","Fountain Club Mugs"),
    C_31_06_00("31-06-00","Cold Dispensed Beverages","Other Cold Dispensed Beverages"),
    C_32_00_00("32-00-00","Frozen Dispensed Beverages","Frozen Dispensed Beverages"),
    C_32_01_00("32-01-00","Frozen Dispensed Beverages","Frozen Carbonated Beverages"),
    C_32_02_00("32-02-00","Frozen Dispensed Beverages","Frozen Non-Carbonated"),
    C_33_00_00("33-00-00","Pre-paid Cards","Pre-paid Cards"),
    C_33_01_00("33-01-00","Pre-paid Cards","Pre-paid Telecommunications"),
    C_33_02_00("33-02-00","Pre-paid Cards","Other Pre-paid Cards"),
    C_98_00_00("98-00-00","Service Charges","Service Charges"),
    C_99_00_00("99-00-00","Store Use/Supply","Store Use/Supply"),
    ;

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

        return Arrays.stream(NacsCategories.values()).filter(c -> categoryCode!= null && categoryCode.equals(c.getCategoryCode())).findFirst();
    }
}
