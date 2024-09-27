package com.trinhvu.order.utils;

public final class Constants {
    public final class ErrorCode {
        public static final String STOCK_NOT_FOUND = "STOCK_NOT_FOUND";
        public static final String STOCK_SYMBOL_NOT_FOUND = "Stock symbol %s is not found";
        public static final String STOCK_ATTRIBUTE_NOT_FOUND = "Stock attribute %s is not found";
        public static final String STOCK_ATTRIBUTE_GROUP_NOT_FOUND = "Stock attribute group %s is not found";

        public static final String EXCHANGE_NOT_FOUND = "EXCHANGE_NOT_FOUND";
        public static final String COMPANY_NOT_FOUND = "COMPANY_NOT_FOUND";
        public static final String PARENT_COMPANY_NOT_FOUND = "PARENT_COMPANY_NOT_FOUND";
        public static final String ENSURE_STOCK_DOES_NOT_HAVE_CHILDREN = "ENSURE_STOCK_DOES_NOT_HAVE_CHILDREN";
        public static final String ENSURE_STOCK_HAS_NO_ACTIVE_TRADES = "ENSURE_STOCK_HAS_NO_ACTIVE_TRADES";
        public static final String PARENT_COMPANY_CANNOT_BE_ITSELF = "PARENT_COMPANY_CANNOT_BE_ITSELF";
        public static final String STOCK_PRICE_VALUE_NOT_FOUND = "STOCK_PRICE_VALUE_NOT_FOUND";
        public static final String STOCK_ATTRIBUTE_NOT_EXIST = "STOCK_ATTRIBUTE_NOT_EXIST";
        public static final String STOCK_ATTRIBUTE_IS_NOT_FOUND = "STOCK_ATTRIBUTE_IS_NOT_FOUND";
        public static final String SYMBOL_IS_DUPLICATED = "SYMBOL_IS_DUPLICATED";
        public static final String STOCK_TEMPLATE_NOT_FOUND = "STOCK_TEMPLATE_NOT_FOUND";

        public static final String ENSURE_COMPANY_HAS_NO_STOCKS = "ENSURE_COMPANY_HAS_NO_STOCKS";
        public static final String ENSURE_STOCK_ATTRIBUTE_GROUP_HAS_NO_ATTRIBUTES =
                "Ensure stock attribute group has no stock attributes";
        public static final String SYMBOL_ALREADY_EXISTS = "SYMBOL_ALREADY_EXISTS";
        public static final String STOCK_HAS_NO_VARIATIONS = "STOCK_HAS_NO_VARIATIONS";
        public static final String SYMBOL_ALREADY_EXISTS_OR_DUPLICATED = "SYMBOL_ALREADY_EXISTS_OR_DUPLICATED";
        public static final String SKU_ALREADY_EXISTS_OR_DUPLICATED = "SKU_ALREADY_EXISTS_OR_DUPLICATED";
        public static final String ISIN_ALREADY_EXISTS_OR_DUPLICATED = "ISIN_ALREADY_EXISTS_OR_DUPLICATED";
        public static final String STOCK_OPTION_VALUE_NOT_FOUND = "STOCK_OPTION_VALUE_NOT_FOUND";
        public static final String STOCK_COMBINATION_PROCESS_FAILED = "STOCK_COMBINATION_PROCESS_FAILED";
        public static final String NO_MATCHING_STOCK_OPTIONS = "NO_MATCHING_STOCK_OPTIONS";
    }
}