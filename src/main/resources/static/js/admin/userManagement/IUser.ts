interface IUserList
{
    id: number;
    firstName: string;
    lastName: string;
    userName: string;
    discount: number;
    isAdmin: boolean;
    creationDate: number;
    modificationDate?: number;
}