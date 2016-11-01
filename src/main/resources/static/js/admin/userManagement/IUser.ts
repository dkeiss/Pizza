
interface IUserList
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
    discount: number;
    admin: boolean;
}