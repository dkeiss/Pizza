interface IUserList extends Array<IUser>{}

interface IUser
{
    userId: number;
    firstName: string;
    lastName: string;
    userName: string;
    discount: number;
    admin: boolean;
    creationDate?: number;
    modificationDate?: number;
}

interface IEditUser
{
    userId: number;
    firstName: string;
    lastName: string;
    userName: string;
    discount: number;
    admin: boolean;
}

interface IAddNewUser
{
    firstName: string;
    lastName: string;
    userName: string;
    discount: number;
    admin: boolean;
}