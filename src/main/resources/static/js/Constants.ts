class WebService
{
    public static url = `${window.location.origin}/rest/`;
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
    public static adminTimepicker = ".admin-inputField_time";
    public static adminTimepickerLabel = ".admin-errorlabel_time";
    public static adminCardBtn = ".admin-button_card";
    public static adminUploadCatalogBtn = ".admin-button_uploadcatalog";

    public static adminCatalogPrefixLbl = ".admin-label_catalogPrefix";
    public static adminCatalogPostfixLbl = ".admin-label_catalogPostfix";
    public static adminCatalogCB = ".admin-combobox_productCatalogs";


    public static adminActivateCtlgBtn = ".admin-submitButton_activateCtlg";


    public static adminCardErrorLbl = ".admin-errorlabel_card";
    public static adminUMGMTErrorLbl = ".admin-errorlabel_umgmt";
    public static adminPrintErrorLbl = ".admin-errorlabel_print";
    public static adminUploadErrorLbl = ".admin-errorlabel_upload";
}

