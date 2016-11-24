class WebService
{
    public static deliveryData = `${window.location.origin}/rest/deliveryservice`;
    public static shoppingCard = `${window.location.origin}/rest/admin/shoppingcard`;
    public static adminProductCatalog = `${window.location.origin}/rest/admin/productcatalog`;
    public static productCatalogActive = `${window.location.origin}/rest/productcatalog/active`;
    public static productCatalog = `${window.location.origin}/rest/productcatalog`;
    public static bulkOrder = `${window.location.origin}/rest/bulkorder`;
    public static user = `${window.location.origin}/rest/user`;
    public static currentUser = `${window.location.origin}/rest/user/current`;
    public static addition = `${window.location.origin}/rest/additional`;
    public static order = `${window.location.origin}/rest/user/order`;
}

class UserManagementSelectors
{
    public static newUserContainer = ".um-newUser_container";
    public static newUserIsAdmin = ".um-newUser_isAdmin";
    public static newUserFirstName = ".um-newUser_firstName";
    public static newUserLastName = ".um-newUser_lastName";
    public static newUserEMail = ".um-newUser_email";
    public static newUserDiscount = ".um-newUser_discount";
    public static newUserWarningLabel = ".um-newUser_warningLabel";
    public static newUserSubmit = ".um-newUser_submit";

    public static inputFieldFindUser = ".um-inputField_findUser";

    public static userTable = ".um-userTable";
    public static userTableRow = ".um-userTable tr";
    public static userTableCell = ".um-userTable td";
    public static userTableDeleteUser = ".um-userTable_deleteUser";
    public static userTableConfirmEdit = ".um-userTable_confirmEdit";
    public static userTableAboardEdit = ".um-userTable_aboardEdit";

    public static confirmationDialogContainer = ".confirmationDialog_container";

    public static confirmationDialogMessage = ".confirmationDialog_messageDiv";
    public static confirmationDialogAccept = ".confirmationDialog_acceptButton";
    public static confirmationDialogCancel = ".confirmationDialog_cancelButton";
    public static confirmationDialogClose = ".confirmationDialog_closeButton";
}

class AdminOverviewSelectors
{
    public static adminTimePicker = ".admin-inputField_time";
    public static adminTimePickerLabel = ".admin-errorlabel_time";
    public static adminCardButton = ".admin-button_card";
    public static adminUploadCatalogButton = ".admin-button_uploadcatalog";

    public static adminCatalogPrefixLabel = ".admin-label_catalogPrefix";
    public static adminCatalogPostfixLabel = ".admin-label_catalogPostfix";
    public static adminCatalogComboBox = ".admin-combobox_productCatalogs";

    public static adminActivateCatalogButton = ".admin-submitButton_activateCtlg";
    public static adminOpenInputDialogButton = ".admin-button_print";

    public static adminCardDiv = ".admin-container_cardDiv";
    public static adminUserManagementDiv = ".admin-container_umnmtDiv";
    public static adminBulkOrderDiv = ".admin-container_bulkOrderDiv";
    public static adminPrintDiv = ".admin-container_printDiv";
    public static adminCatalogDiv = ".admin-container_catalogDiv";

    public static adminFileInput= ".admin-inputField_file";

    public static adminGetSimpleCatalogButton= ".admin-submitButton_simple";
    public static adminGetExpertCatalogButton= ".admin-submitButton_expert";

}

class ShoppingCardSelectors {
    public static orderTable = ".card-orderTable";
    public static orderTableSortButton = ".card-orderTable_sortButton";
    public static orderTableStateButton = ".card-orderTable_stateButton";
    public static orderTableDeleteButton = ".card-orderTable_deleteButton";

    public static cardOrderContainer = ".card-container_order";
    public static cardOrderButton = ".card-button_order";

    public static cardPrintControlSheetButton = ".card-submitButton_controlSheet";
    public static cardPrintOrderSheetButton = ".card-submitButton_orderSheet";

    public static cardDeliveryDataCompanySpan = ".deliveryData_company"
    public static cardDeliveryDataFirstNameSpan = ".deliveryData_firstName";
    public static cardDeliveryDataLastNameSpan = ".deliveryData_lastName";
    public static cardDeliveryDataStreetAddressSpan = ".deliveryData_streetAddress";
    public static cardDeliveryDataZipTownSpan = ".deliveryData_zipTown";
    public static cardDeliveryDataAdditionalInfoSpan = ".deliveryData_additionalInformation";
    public static cardDeliveryDataPhoneSpan = ".deliveryData_phone";
    public static cardDeliveryDataEMailSpan = ".deliveryData_email";

    public static cardOrderSummaryDiv = ".card_orderSummary";

    public static confirmationDialogContainer = ".confirmationDialog_container";

    public static confirmationDialogMessage = ".confirmationDialog_messageDiv";
    public static confirmationDialogAccept = ".confirmationDialog_acceptButton";
    public static confirmationDialogCancel = ".confirmationDialog_cancelButton";
    public static confirmationDialogClose = ".confirmationDialog_closeButton";

}

class ErrorDialogSelectors
{
    public static container = ".core-serverError_container";
    public static boxCenter =".core-serverError-boxCenter";
    public static box = ".core-serverError-box";
    public static title = ".core-serverError_title";
    public static text = ".core-serverError_text";
    public static refreshButton = ".core-serverError_refreshButton";
}

class InputDialogSelectors
{
    public static inputDialogContainer = ".admin-inputDialog_container";
    public static inputDialogCompanyInput = ".admin-inputDialog_company";
    public static inputDialogFirstNameInput = ".admin-inputDialog_firstName";
    public static inputDialogLastNameInput = ".admin-inputDialog_lastname";
    public static inputDialogStreetInput = ".admin-inputDialog_street";
    public static inputDialogNumberInput = ".admin-inputDialog_number";
    public static inputDialogZipInput = ".admin-inputDialog_zipCode";
    public static inputDialogCityInput = ".admin-inputDialog_city";
    public static inputDialogPhoneInput = ".admin-inputDialog_phone";
    public static inputDialogEMailInput = ".admin-inputDialog_eMail";
    public static inputDialogCommentArea = ".admin-inputDialog_comment";
    public static inputDialogAcceptButton = ".admin-inputDialog_acceptButton";
    public static inputDialogCancelButton = ".admin-inputDialog_cancelButton";
    public static inputDialogWarningLabel = ".admin-inputDialog_warningLabel";
}

class UserOrderSelector
{
    public static menuContainerSelector = ".userOrder-menu_container";
    public static menuClickSelectors = ".userOrder-menu_elementClick";
    public static setCategoriesHtml = "userOrder-categories_container";
    public static categoriesShowContainer = "userOrder-categoriesSelector-showContainer";
    public static groupSelectors = "userOrder-groupsSelector-container";
    public static CellSelectors = "userOrder-price_cell";

    public static additionalDialog = ".userOrder-additionalDialog_container";
    public static additionalBox = ".userOrder-additionalDialog_additionBox";
    public static additionalBoxMenus = "userOrder-additionalDialog-additionBoxMenus";
    public static additionalBoxMenusSelector = "userOrder-additionalDialog_additionBoxMenus";
    public static orderOverviewText = ".userOrder-additionalDialog_orderOverviewText";
    public static orderOverviewAddition = ".userOrder-additionalDialog_orderOverviewAddition";
    public static orderOverviewUserDiscount = ".userOrder-additionalDialog_orderOverviewUserDiscount";
    public static orderSubmit = ".userOrder-additionalDialog_orderSubmit";
    public static closeDialog = ".userOrder-additionalDialog_closeDialog";
}
