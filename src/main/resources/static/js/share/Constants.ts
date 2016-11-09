class WebService
{
    public static shoppingCard = `${window.location.origin}/rest/admin/shoppingcard`;
    public static adminProductCatalog = `${window.location.origin}/rest/admin/productcatalog`;
    public static productCatalog = `${window.location.origin}/rest/productcatalog`;
    public static bulkOrder = `${window.location.origin}/rest/bulkorder`;
    public static user = `${window.location.origin}/rest/user`;
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

    public static adminCardDiv = ".admin-container_cardDiv";
    public static adminUserManagementDiv = ".admin-container_umnmtDiv";
    public static adminBulkOrderDiv = ".admin-container_bulkOrderDiv";
    public static adminPrintDiv = ".admin-container_printDiv";
    public static adminCatalogDiv = ".admin-container_catalogDiv";

    public static adminFileInput= ".admin-inputField_file";

}

class ShoppingCardSelectors {
    public static orderTable = ".card-orderTable";
    public static orderTableSortButton = ".card-orderTable_sortButton";
    public static orderTableStateButton = ".card-orderTable_stateButton";
    public static orderTableDeleteButton = ".card-orderTable_deleteButton";
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

class UserOrderSelector
{
    public static menuContainerSelector = ".userOrder-menu_container";
    public static menuClickSelectors = ".userOrder-menu_elementClick";
}