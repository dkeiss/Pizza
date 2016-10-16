/// <reference path="../../js/jquery.d.ts" />
/// <reference path="../../js/service.ts" />

$(document).ready(() => {
    createTableUser();
});

function generateJson() {
    Service.generateJson();
}

function checkAddUser()
{
    const inputUser = (<HTMLInputElement>document.getElementById("newUser"));
    if (inputUser.value === "") { return }

    const value = Service.addNewUser(inputUser.value);

    if (value)
    {
        inputUser.classList.add("newUser-accepted");
    }
    else
    {
        inputUser.classList.add("newUser-notAccepted");
    }
}

function removeAcceptedClass(element)
{
    element.classList.remove("newUser-accepted");
    element.classList.remove("newUser-notAccepted");
}

function createTableUser()
{
    const tData = Service.loadUserTable();
    let output = `<tr> <th class="sort icons">Benutzer</th> <th>Rabatt</th> </tr>`;

    for (let i = 0; i < tData.length; i++)
    {
        output += `<tr> <td>${tData[i].UserName}</td> <td>${tData[i].Discount}</td> </tr>`;
    }

    document.getElementById("tableuser-input").innerHTML = output;
}