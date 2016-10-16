/// <reference path="../../js/jquery.d.ts" />
/// <reference path="../../js/service.ts" />
$(document).ready(function () {
    createTableUser();
});
function generateJson() {
    Service.generateJson();
}
function checkAddUser() {
    var inputUser = document.getElementById("newUser");
    if (inputUser.value === "") {
        return;
    }
    var value = Service.addNewUser(inputUser.value);
    if (value) {
        inputUser.classList.add("newUser-accepted");
    }
    else {
        inputUser.classList.add("newUser-notAccepted");
    }
}
function removeAcceptedClass(element) {
    element.classList.remove("newUser-accepted");
    element.classList.remove("newUser-notAccepted");
}
function createTableUser() {
    var tData = Service.loadUserTable();
    var output = "<tr> <th class=\"sort icons\">Benutzer</th> <th>Rabatt</th> </tr>";
    for (var i = 0; i < tData.length; i++) {
        output += "<tr> <td>" + tData[i].UserName + "</td> <td>" + tData[i].Discount + "</td> </tr>";
    }
    document.getElementById("tableuser-input").innerHTML = output;
}
//# sourceMappingURL=function.js.map