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